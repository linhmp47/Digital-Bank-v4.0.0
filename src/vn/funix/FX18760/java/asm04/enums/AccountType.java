package vn.funix.FX18760.java.asm04.enums;

public enum AccountType {
    PREMIUM("Premium"),NORMAL("Normal");
    private String value;
    AccountType(String value){
        this.value=value;
    }
    public String getValue(){
        return value;
    }

}
