import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RepairShopDatabase {
    private Map<String, Node> nodes = new HashMap<>();
    private Map<String, RepairableItem> repairableItems = new HashMap<>();
    public void addNode(String nodeCode, Node node, int quantity) {
        nodes.put(nodeCode, node);
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "12345")) {
            String insertNodeQuery = "INSERT INTO Узлы (Обозначение, СписокДеталей, КоличествоДеталей, ДопустимыйИзнос, ВремяЗамены) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertNodeQuery)) {
                preparedStatement.setString(1, nodeCode);
                preparedStatement.setString(2, node.getDetailsListAsString());
                preparedStatement.setInt(3, quantity);
                preparedStatement.setDouble(4, node.getAllowedWear());
                preparedStatement.setDouble(5, node.getReplacementTime());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRepairableItem(String itemName) {
        repairableItems.remove(itemName);
        // Удаление ремонтируемого изделия из базы данных
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "12345")) {
            String deleteItemQuery = "DELETE FROM изделия WHERE Название = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteItemQuery)) {
                preparedStatement.setString(1, itemName);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при обновлении типа детали: " + e.getMessage());
        }
    }

    public void changeDetailType(String detailCode, String newType) {
        Detail detail = findDetailByCode(detailCode);
        if (detail != null) {
            detail.setMaterialType(newType);
            // Обновление типа детали в базе данных
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema", "root", "12345")) {
                String updateDetailQuery = "UPDATE детали SET ТипЗаготовки = ? WHERE Обозначение = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateDetailQuery)) {
                    preparedStatement.setString(1, newType);
                    preparedStatement.setString(2, detailCode);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Ошибка при обновлении типа детали: " + e.getMessage());
            }
        }
    }

    private Detail findDetailByCode(String detailCode) {
        for (Node node : nodes.values()) {
            for (Detail detail : node.getDetails().values()) {
                if (detail.getCode().equals(detailCode)) {
                    return detail;
                }
            }
        }
        return null;
    }

    public double calculateMaterialForReplacedDetails(String nodeCode, String detailCode) {
        Node node = nodes.get(nodeCode);
        if (node != null) {
            double totalMaterialConsumption = 0.0;
            Detail detail = node.getDetails().get(detailCode);
            if (detail != null) {
                totalMaterialConsumption += detail.getMaterialConsumption();
            }
            return totalMaterialConsumption;
        }
        return 0.0;
    }

    public Node findNodeWithMaxWornDetails(String detailType) {
        Node nodeWithMaxWornDetails = null;
        int maxWornDetailsCount = 0;
        for (Node node : nodes.values()) {
            int wornDetailsCount = calculateWornDetailsCount(node, detailType);
            if (wornDetailsCount > maxWornDetailsCount) {
                maxWornDetailsCount = wornDetailsCount;
                nodeWithMaxWornDetails = node;
            }
        }
        return nodeWithMaxWornDetails;
    }

    private int calculateWornDetailsCount(Node node, String detailType) {
        int wornDetailsCount = 0;
        for (Detail detail : node.getDetails().values()) {
            if (detail.getMaterialType().equals(detailType) && detail.isWorn()) {
                wornDetailsCount++;
            }
        }
        return wornDetailsCount;
    }

    public String findMinMaterialConsumptionType(String detailCode) {
        String minMaterialType = null;
        double minMaterialConsumption = Double.MAX_VALUE;
        for (Node node : nodes.values()) {
            Detail detail = node.getDetails().get(detailCode);
            if (detail != null) {
                double materialConsumption = detail.getMaterialConsumption();
                if (materialConsumption < minMaterialConsumption) {
                    minMaterialConsumption = materialConsumption;
                    minMaterialType = detail.getMaterialType();
                }
            }
        }
        return minMaterialType;
    }

    public double calculateRepairTime(String itemName) {
        double totalRepairTime = 0.0;
        RepairableItem repairableItem = repairableItems.get(itemName);
        if (repairableItem != null) {
            totalRepairTime = repairableItem.getRepairTime();
        }
        return totalRepairTime;
    }
}