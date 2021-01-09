package by.kravchenko.bank.atm;

import by.kravchenko.bank.cards.Card;
import by.kravchenko.money.Banknotes;

import java.util.HashMap;

import java.util.Map;

public class ATM {
    private int ID;
    private Map<Banknotes, Integer> moneyBank = new HashMap<Banknotes, Integer>();

    ATM() {
        this.ID = 0;
    }

    ATM(int ID){
        this.ID = ID;
    }

    public Cash getMoney(Card card, int PIN, int value) throws Exception {

        return new Cash();
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
