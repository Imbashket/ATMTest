package by.kravchenko.bank.exceptions;

public enum ErrorMessages {
    INVALIDCARD("Invalid Card"),
    WRONGPIN("Wrong PIN"),
    LOWBALANCE("You don't have enough money"),
    WRONGAMOUNT("Wrong amount");

    private final String msg;

    ErrorMessages(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
