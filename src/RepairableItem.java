import java.util.List;
import java.util.ArrayList;

public class RepairableItem {
    private String name;
    private Node node;
    private List<Detail> replacedDetails = new ArrayList<>();
    private double repairTime;

    public RepairableItem(String name, Node node) {
        this.name = name;
        this.node = node;
    }

    public String getName() {
        return name;
    }

    public Node getNode() {
        return node;
    }

    public List<Detail> getReplacedDetails() {
        return replacedDetails;
    }

    public void addReplacedDetail(Detail detail) {
        replacedDetails.add(detail);
    }

    public double getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(double repairTime) {
        this.repairTime = repairTime;
    }
}