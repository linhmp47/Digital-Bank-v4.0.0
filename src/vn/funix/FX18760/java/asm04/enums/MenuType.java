package vn.funix.FX18760.java.asm04.enums;

/**
 * Hằng số menu
 */

public enum MenuType {
    LIST_CUSTOMER(1),
    ADD_CUSTOMER(2),
    ADD_ACCOUNT_ATM(3),
    TRANSFERS(4),
    WITHDRAW(5),
    TRANSACTION_HISTORY(6),
    EXIT(0);
    private int value;//giá trị của mỗi loại menu
    MenuType(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }

    /**
     * lấy menu theo từng lựa chọn đỡ nhập từ bàn phím
     * tham số value là giá trị menu đã nhập từ bàn phím
     * @param value
     * @return lấy ra MenuType theo value
     */
    public static MenuType getMenuType(int value){
        for (MenuType menu: MenuType.values()) {
            if (menu.getValue() == value) return menu;
        }
        return null;
    }

}
