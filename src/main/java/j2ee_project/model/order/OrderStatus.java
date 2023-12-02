package j2ee_project.model.order;

public enum OrderStatus {
    WAITING_PAYMENT("WAITING_PAYMENT"),
    PREPARING("PREPARING"),
    READY_TO_BE_SHIPPED("READY_TO_BE_SHIPPED"),
    SHIPPED("SHIPPED"),
    CANCELLED("CANCELLED"),
    DELIVERED("DELIVERED");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
