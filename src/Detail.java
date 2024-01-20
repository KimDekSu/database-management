public class Detail {
    private String code;
    private String name;
    private String materialType;
    private int quantity;
    private double materialConsumption;
    private double wear;

    public Detail(String code, String name, String materialType) {
        this.code = code;
        this.name = name;
        this.materialType = materialType;
        this.wear = 0;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getMaterialType() {
        return materialType;
    }

    public double getMaterialConsumption() {
        return materialConsumption;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public void setMaterialConsumption(double materialConsumption) {
        this.materialConsumption = materialConsumption;
    }
    // Метод для проверки износа
    public boolean isWorn() {
        return wear > 0.5;
    }

    public void setWear(double wear) {
        this.wear = wear;
    }
}

