package j2ee_project.model.order;

/**
 * The enum Order status.
 */
public enum OrderStatus {
    /**
     * Waiting payment
     */
    WAITING_PAYMENT("WAITING_PAYMENT"),
    /**
     * Preparing order
     */
    PREPARING("PREPARING"),
    /**
     * Ready to be shipped
     */
    READY_TO_BE_SHIPPED("READY_TO_BE_SHIPPED"),
    /**
     * Shipped
     */
    SHIPPED("SHIPPED"),
    /**
     * Cancelled
     */
    CANCELLED("CANCELLED"),
    /**
     * Delivered
     */
    DELIVERED("DELIVERED");

    private final String name;

    /**
     * Constructor of an OrderStatus
     * @param name String associated to the status (e.g. DELIVERED)
     */
    OrderStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
