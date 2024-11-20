package util;

public class User {
    private static Integer sellerId;
    private static Integer customerId;

    public static Integer getSellerId() {
        return sellerId;
    }

    public static void setSellerId(Integer sellerId) {
        User.sellerId = sellerId;
    }

    public static Integer getCustomerId() {
        return customerId;
    }

    public static void setCustomerId(Integer customerId) {
        User.customerId = customerId;
    }
}
