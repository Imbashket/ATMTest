package by.kravchenko.bank.exceptions;

public class WrongPinException extends Exception {
    public String toString() {
        return ErrorMessages.WRONGPIN.getMsg();
    }
}
