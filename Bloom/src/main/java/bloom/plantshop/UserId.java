package application;

public class UserId {
    private static Integer sellerId;
    private static Integer customerId;

    public static Integer getSellerId() {
        return sellerId;
    }

    public static void setSellerId(Integer sellerId) {
        UserId.sellerId = sellerId;
    }

    public static Integer getCustomerId() {
        return customerId;
    }

    public static void setCustomerId(Integer customerId) {
        UserId.customerId = customerId;
    }
}
