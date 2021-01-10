package by.kravchenko.bank.exceptions;

public class WrongAmountException extends Exception {
    public String toString() {
        return ErrorMessages.WRONGAMOUNT.getMsg();
    }
}
