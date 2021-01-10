package by.kravchenko.bank.exceptions;

public class InvalidCardException extends Exception {
    public String toString() {
        return ErrorMessages.INVALIDCARD.getMsg();
    }
}
