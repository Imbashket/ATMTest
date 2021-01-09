package by.kravchenko.bank.atm.exceptions;

public class WrongPinException extends Exception {
    public String toString() {
        return ErrorMessages.WRONGPIN.getMsg();
    }
}
