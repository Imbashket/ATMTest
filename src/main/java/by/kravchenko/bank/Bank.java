package by.kravchenko.bank;

import by.kravchenko.bank.cards.*;
import by.kravchenko.bank.exceptions.InvalidCardException;
import by.kravchenko.bank.exceptions.LowBalanceException;
import by.kravchenko.bank.exceptions.WrongPinException;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<Integer, CardInfo> cards = new HashMap<Integer, CardInfo>();

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

    public void checkRequest(Card card, int PIN, int value)
            throws InvalidCardException, WrongPinException, LowBalanceException{
        int ID = card.getID();
        if (!checkCardExists(ID))
            throw new InvalidCardException();
        CardInfo cardInfo = cards.get(ID);
        checkPIN(cardInfo, PIN);
        checkBalanceAvailability(cardInfo, value);
    }

    private boolean checkCardExists(int ID) {
        return cards.containsKey(ID);
    }

    private void checkPIN(CardInfo cardInfo, int PIN) throws WrongPinException{
        if (cardInfo.getPIN() != PIN)
            throw new WrongPinException();
    }

    private void checkBalanceAvailability(CardInfo cardInfo, int value) throws LowBalanceException {
        if (cardInfo.getBalance() < value)
            throw new LowBalanceException();
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

