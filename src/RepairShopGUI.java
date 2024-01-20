import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class RepairShopGUI {

    private JFrame frame;
    private RepairShopDatabase database;

    public RepairShopGUI() {
        database = new RepairShopDatabase();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Repair Shop Management");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        JButton addNodeButton = new JButton("Добавление информации о новом узле");
        addNodeButton.setBounds(20, 30, 500, 30);
        addNodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Добавление информации о новом узле
                String nodeCode = JOptionPane.showInputDialog(frame, "Введите обозначение узла:");
                double allowedWear = Double.parseDouble(JOptionPane.showInputDialog(frame, "Введите допустимый износ для узла:"));
                double replacementTime = Double.parseDouble(JOptionPane.showInputDialog(frame, "Введите время замены для узла:"));

                // Ввод списка деталей и их количества через диалоговое окно
                String detailsInput = JOptionPane.showInputDialog(frame, "Введите список деталей через запятую (например, деталь1,деталь2):");
                int quantity = Integer.parseInt(JOptionPane.showInputDialog(frame, "Введите количество деталей:"));
                Node node = new Node(nodeCode, allowedWear, replacementTime);
                // Разбиваем строку с деталями на массив
                String[] detailsArray = detailsInput.split(",");
                // Добавляем детали и их количество в узел
                for (String detail : detailsArray) {
                    node.addDetail(detail, new Detail(detail, detail, detail), quantity);
                }
                database.addNode(nodeCode, node, node.getDetailsCount());
            }
        });
        frame.getContentPane().add(addNodeButton);
        JButton deleteItemButton = new JButton("Удаление информации о ремонтируемом изделии");
        deleteItemButton.setBounds(20, 70, 500, 30);
        deleteItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Удаление информации о ремонтируемом изделии
                String itemName = JOptionPane.showInputDialog(frame, "Введите название изделия, которое нужно удалить:");
                database.deleteRepairableItem(itemName);
            }
        });
        frame.getContentPane().add(deleteItemButton);
        JButton changeDetailTypeButton = new JButton("Изменение типа заготовки детали:");
        changeDetailTypeButton.setBounds(20, 110, 500, 30);
        changeDetailTypeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Изменение типа заготовки детали
                String detailCode = JOptionPane.showInputDialog(frame, "Введите обозначение детали:");
                String newType = JOptionPane.showInputDialog(frame, "Введите новый тип заготовки:");
                database.changeDetailType(detailCode, newType);
            }
        });
        frame.getContentPane().add(changeDetailTypeButton);
        JButton calculateMaterialButton = new JButton("Рассчитать материал для деталей");
        calculateMaterialButton.setBounds(20, 150, 500, 30);
        calculateMaterialButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String detailCode = JOptionPane.showInputDialog(frame, "Введите обозначение детали:");
                String materialType = JOptionPane.showInputDialog(frame, "Введите тип материала:");
                double materialQuantity = database.calculateMaterialForReplacedDetails(detailCode, materialType);
                JOptionPane.showMessageDialog(frame, "Требуемое количество материала: " + materialQuantity);
            }
        });
        frame.getContentPane().add(calculateMaterialButton);
        JButton maxWornDetailsNodeButton = new JButton("Узел с наибольшим износом деталей");
        maxWornDetailsNodeButton.setBounds(20, 190, 500, 30);
        maxWornDetailsNodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String materialType = JOptionPane.showInputDialog(frame, "Введите тип материала:");
                Node maxNode = database.findNodeWithMaxWornDetails(materialType);
                if (maxNode != null) {
                    JOptionPane.showMessageDialog(frame, "Узел: " + maxNode.getCode());
                } else {
                    JOptionPane.showMessageDialog(frame, "Нет данных");
                }
            }
        });
        frame.getContentPane().add(maxWornDetailsNodeButton);
        JButton minMaterialConsumptionTypeButton = new JButton("Тип с минимальным расходом материала");
        minMaterialConsumptionTypeButton.setBounds(20, 230, 500, 30);
        minMaterialConsumptionTypeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String detailCode = JOptionPane.showInputDialog(frame, "Введите обозначение детали:");
                String minMaterialType = database.findMinMaterialConsumptionType(detailCode);
                if (minMaterialType != null) {
                    JOptionPane.showMessageDialog(frame, "Тип с минимальным расходом материала: " + minMaterialType);
                } else {
                    JOptionPane.showMessageDialog(frame, "Нет данных");
                }
            }
        });
        frame.getContentPane().add(minMaterialConsumptionTypeButton);
        JButton repairTimeButton = new JButton("Время на ремонт изделия");
        repairTimeButton.setBounds(20, 270, 500, 30);
        repairTimeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String itemName = JOptionPane.showInputDialog(frame, "Введите название изделия:");
                double repairTime = database.calculateRepairTime(itemName);
                JOptionPane.showMessageDialog(frame, "Время на ремонт изделия: " + repairTime);
            }
        });
        frame.getContentPane().add(repairTimeButton);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RepairShopGUI();
            }
        });
    }
}
