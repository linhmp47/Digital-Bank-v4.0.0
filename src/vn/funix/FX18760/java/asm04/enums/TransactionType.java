package vn.funix.FX18760.java.asm04.enums;


public enum TransactionType {
    DEPOSIT("Deposit"),WITHDRAW("Withdraw"),TRANSFER("Transfer");
    private String value;
    TransactionType(String value){
        this.value=value;
    }

}
