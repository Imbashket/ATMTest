package by.kravchenko.bank.atm.exceptions;

public class InvalidCardException extends Exception {
    public String toString() {
        return ErrorMessages.INVALIDCARD.getMsg();
    }
}
