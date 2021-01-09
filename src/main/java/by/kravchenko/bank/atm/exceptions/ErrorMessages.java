package by.kravchenko.bank.atm.exceptions;

public enum ErrorMessages {
    INVALIDCARD("Invalid Card"),
    WRONGPIN("Wrong PIN"),
    LOWBALANCE("You have not enough money"),
    WRONGAMOUNT("Wrong amount, try another");

    private String msg;

    ErrorMessages(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
