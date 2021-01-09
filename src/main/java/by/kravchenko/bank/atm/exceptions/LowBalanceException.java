package by.kravchenko.bank.atm.exceptions;

public class LowBalanceException extends Exception{
    public String toString() {
        return ErrorMessages.LOWBALANCE.getMsg();
    }
}
