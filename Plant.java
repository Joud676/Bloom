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

    public String plantDetails (int plantId){
        return toString();
    }

    public int checkQuantity(int plantId){
        return quantity;
    }

    public int getQuantity() {
        return quantity;
    }


}