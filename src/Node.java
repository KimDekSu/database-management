import java.util.HashMap;
import java.util.Map;

public class Node {
    private String code;
    private Map<String, Detail> details = new HashMap<>();
    private double allowedWear;
    private double replacementTime;

    public Node(String code, double allowedWear, double replacementTime) {
        this.code = code;
        this.allowedWear = allowedWear;
        this.replacementTime = replacementTime;
    }

    public void addDetail(String detailCode, Detail detail, int quantity) {
        details.put(detailCode, detail);
        detail.setQuantity(quantity);
    }

    public String getCode() {
        return code;
    }

    public Map<String, Detail> getDetails() {
        return details;
    }

    public double getAllowedWear() {
        return allowedWear;
    }

    public double getReplacementTime() {
        return replacementTime;
    }

    public String getDetailsListAsString() {
        StringBuilder detailsList = new StringBuilder();
        for (Map.Entry<String, Detail> entry : details.entrySet()) {
            detailsList.append(entry.getKey()).append(": ").append(entry.getValue().getName()).append(", ");
        }
        if (detailsList.length() > 2) {
            detailsList.setLength(detailsList.length() - 2);
        }
        return detailsList.toString();
    }

    public int getDetailsCount() {
        return details.size();
    }
    public double calculateWornDetailsCount(String materialType) {
        double wornDetailsCount = 0;
        for (Detail detail : details.values()) {
            if (detail.getMaterialType().equals(materialType)) {
                if (detail.isWorn()) {
                    wornDetailsCount++;
                }
            }
        }
        return wornDetailsCount;
    }
}