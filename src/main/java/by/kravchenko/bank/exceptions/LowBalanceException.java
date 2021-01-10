package by.kravchenko.bank.exceptions;

public class LowBalanceException extends Exception{
    public String toString() {
        return ErrorMessages.LOWBALANCE.getMsg();
    }
}
