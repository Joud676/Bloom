import java.util.List;

public class Plant {
    private int plantId;
    private String name;
    private String characteristic ;
    private String careInfo ;
    private Double price ;
    private int quantity ;
    private List<String> fertilization;



    public Plant(int plantId, String name, String characteristic, String careInfo, Double price, int quantity, List<String> fertilization) {
        this.plantId = plantId;
        this.name = name;
        this.characteristic = characteristic;
        this.careInfo = careInfo;
        this.price = price;
        this.quantity = quantity;
        this.fertilization = fertilization;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public void setFertilization(List<String> fertilization) {
        this.fertilization = fertilization;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCareInfo(String careInfo) {
        this.careInfo = careInfo;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlantId() {
        return plantId;
    }

    public List<String> getFertilization() {
        return fertilization;
    }

    public String getCareInfo() {
        return careInfo;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String plantDetails (int plantId){
        return toString();
    }

    public int checkQuantity(int plantId){

        return quantity;


    }

    public int getQuantity() {
        return quantity;

    }
//   public boolean checkfertilization(String plant){
//
//
//
//        return false;
//
//   }

}