package by.kravchenko.bank;

import by.kravchenko.bank.cards.*;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<Integer, CardInfo> cards = new HashMap<Integer, CardInfo>();

    public DebitCard addNewDebitCard(int PIN) {
        int ID = getFreeID();
        cards.put(ID, new CardInfo(PIN));
        return new DebitCard(ID);
    }

    private int getFreeID() {
        int i = 0;
        while (cards.containsKey(i)) {
            i++;
        }
        return i;
    }

    public boolean checkPIN(Card card, int PIN) {
        return cards.get(card.getID()).getPIN() == PIN;
    }

    public boolean checkBalanceAvailability(Card card, int value) {
        return cards.get(card.getID()).getBalance() >= value;
    }

    public void addBalance(Card card, int value) {
        cards.get(card.getID()).addBalance(value);
    }

    public void chargeOff(Card card, int value) {
        cards.get(card.getID()).chargeOff(value);
    }

    private static class CardInfo {
        private int balance = 0;
        private final int PIN;

        public CardInfo(int PIN) {
            this.PIN = PIN;
        }

        void addBalance(int value) {
            balance += value;
        }

        int getBalance() {
            return balance;
        }

        void chargeOff(int value) {
            balance -= value;
        }

        int getPIN() {
            return PIN;
        }
    }

}

