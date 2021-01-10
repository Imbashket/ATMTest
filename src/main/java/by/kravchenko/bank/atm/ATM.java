package by.kravchenko.bank.atm;

import by.kravchenko.bank.Bank;
import by.kravchenko.bank.exceptions.InvalidCardException;
import by.kravchenko.bank.exceptions.LowBalanceException;
import by.kravchenko.bank.exceptions.WrongAmountException;
import by.kravchenko.bank.exceptions.WrongPinException;
import by.kravchenko.bank.cards.Card;
import by.kravchenko.money.Banknotes;

import java.util.HashMap;

import java.util.Map;

public class ATM {
    private int ID = 0;
    private Map<Banknotes, Integer> moneyBank = new HashMap<Banknotes, Integer>();
    private final Bank bank;

    ATM(Bank bank) {
        this.bank = bank;
    }

    ATM(Bank bank, int ID) {
        this(bank);
        this.ID = ID;
    }

    public Cash getMoney(Card card, int PIN, int value)
            throws InvalidCardException, WrongPinException, LowBalanceException, WrongAmountException {
        bank.checkRequest(card, PIN, value);
        Cash cash = checkAmountInATM(value);
        bank.chargeOff(card, value);
        giveOutMoney(cash);
        return cash;
    }

    private Cash checkAmountInATM(int value) throws WrongAmountException{
        return new Cash();
    }

    private void giveOutMoney(Cash cash) {

    }

    public void addMoneyToATM(Banknotes banknote, int number) {
        if (moneyBank.containsKey(banknote)) {
            moneyBank.put(banknote, moneyBank.get(banknote) + number);
        } else
            moneyBank.put(banknote, number);
    }

    private int getBalance() {
        int balance = 0;
        for (Map.Entry<Banknotes, Integer> entry : moneyBank.entrySet()) {
            balance += entry.getKey().getDenomination() * entry.getValue();
        }
        return balance;
    }

    public int getID() {
        return ID;
    }
}
