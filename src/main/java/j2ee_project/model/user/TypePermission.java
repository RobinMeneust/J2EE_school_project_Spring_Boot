package j2ee_project.model.user;

public enum TypePermission {
    CAN_MANAGE_DISCOUNT("CAN_MANAGE_DISCOUNT"),
    CAN_MANAGE_MODERATOR("CAN_MANAGE_MODERATOR"),
    CAN_MANAGE_PRODUCT("CAN_MANAGE_PRODUCT"),
    CAN_MANAGE_CATEGORY("CAN_MANAGE_CATEGORY"),
    CAN_MANAGE_CUSTOMER("CAN_MANAGE_CUSTOMER"),
    CAN_MANAGE_LOYALTY("CAN_MANAGE_LOYALTY"),
    CAN_MANAGE_ORDER("CAN_MANAGE_ORDER");

    private final String name;

    TypePermission(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
