/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author lama1
 */
public class Cart {
    private int customerId;
    private int plantId;
    private String plantName;
    private double total;
    private int quantity;
    private int sellerId;

    public Cart(int customerId, int plantId, String plantName, double total, int quantity ,int seller) {
        this.customerId = customerId;
        this.plantId = plantId;
        this.plantName = plantName;
        this.total = total;
        this.quantity = quantity;
        this.sellerId=seller;
    }

    public int getSellerId() {
        return sellerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getPlantId() {
        return plantId;
    }

    public String getPlantName() {
        return plantName;
    }

    public double getTotal() {
        return total;
    }

    public int getQuantity() {
        return quantity;
    }
    
}
