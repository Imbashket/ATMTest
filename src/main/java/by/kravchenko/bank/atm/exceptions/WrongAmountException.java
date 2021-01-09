package by.kravchenko.bank.atm.exceptions;

public class WrongAmountException extends Exception {
    public String toString() {
        return ErrorMessages.WRONGAMOUNT.getMsg();
    }
}
