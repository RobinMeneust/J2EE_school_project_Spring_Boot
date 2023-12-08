package j2ee_project.model.user;

/**
 * Permission types
 */
public enum TypePermission {
    /**
     * Can manage customers
     */
    CAN_MANAGE_CUSTOMER("Can manage customers","manage-customers-permission"),
    /**
     * Can manage moderators
     */
    CAN_MANAGE_MODERATOR("Can manage moderators","manage-moderators-permission"),
    /**
     * Can manage products
     */
    CAN_MANAGE_PRODUCT("Can manage products","manage-products-permission"),
    /**
     * Can manage categories
     */
    CAN_MANAGE_CATEGORY("Can manage categories","manage-categories-permission"),
    /**
     * Can manage orders
     */
    CAN_MANAGE_ORDER("Can manage orders","manage-orders-permission"),
    /**
     * Can manage discounts
     */
    CAN_MANAGE_DISCOUNT("Can manage discounts","manage-discounts-permission"),
    /**
     * Can manage the loyalty system
     */
    CAN_MANAGE_LOYALTY("Can manage loyalties","manage-loyalties-permission");

    private final String name;
    private final String id;

    TypePermission(String name, String id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Get name string.
     *
     * @return the string
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get id string.
     *
     * @return the string
     */
    public String getId(){
        return this.id;
    }
}
