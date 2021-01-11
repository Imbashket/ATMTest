package by.kravchenko.bank.atm;

import by.kravchenko.bank.Bank;
import by.kravchenko.bank.exceptions.InvalidCardException;
import by.kravchenko.bank.exceptions.LowBalanceException;
import by.kravchenko.bank.exceptions.WrongAmountException;
import by.kravchenko.bank.exceptions.WrongPinException;
import by.kravchenko.bank.cards.Card;
import by.kravchenko.money.Banknotes;

import java.util.*;

public class ATM {
    private int ID = 0;
    private final Map<Banknotes, Integer> moneyBank = new TreeMap<>(
            Comparator.comparingInt(Banknotes::getDenomination)
    );
    private final Bank bank;

    public ATM(Bank bank) {
        this.bank = bank;
    }

    public ATM(Bank bank, int ID) {
        this(bank);
        this.ID = ID;
    }

    public Cash getMoney(Card card, int PIN, int value)
            throws InvalidCardException, WrongPinException, LowBalanceException, WrongAmountException {
        bank.checkRequest(card, PIN, value);
        Cash cash = checkAmountInATM(value);
        bank.chargeOff(card, value);
        withdraw(cash);
        return cash;
    }

    private Cash checkAmountInATM(int value) throws WrongAmountException {
        int differentBanknotes = moneyBank.size();
        int[] banknotes = new int[differentBanknotes];
        int[] banknotesQuantity = new int[differentBanknotes];
        int i = 0;
        //Put money denominations and quantity in two int arrays
        for (Map.Entry<Banknotes, Integer> entry : moneyBank.entrySet()) {
            banknotes[i] = entry.getKey().getDenomination();
            banknotesQuantity[i] = entry.getValue();
            i++;
        }
        return createCashFromArray(findSolution(banknotes, banknotesQuantity,
                new int[differentBanknotes], value, differentBanknotes - 1));
    }

    private int[] findSolution(int[] banknotes, int[] banknotesQuantity,
                               int[] variation, int expectedSum, int arrayIndex) {
        int currentSum = calcSum(banknotes, variation);
        if (currentSum == expectedSum)
            return variation;
        if (currentSum < expectedSum) {
            for (int i = arrayIndex; i >= 0; i--) {
                if (banknotesQuantity[i] > variation[i]) {
                    int[] newVariation = variation.clone();
                    newVariation[i]++;
                    int[] result = findSolution(banknotes, banknotesQuantity,
                            newVariation, expectedSum, i);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }

    private static int calcSum(int[] banknotes, int[] variation) {
        int sum = 0;
        for (int i = 0; i < variation.length; i++) {
            sum += banknotes[i] * variation[i];
        }
        return sum;
    }

    private Cash createCashFromArray(int[] result) throws WrongAmountException {
        if (result == null) {
            throw new WrongAmountException();
        }
        Cash cash = new Cash();
        int i = 0;
        for (Map.Entry<Banknotes, Integer> entry : moneyBank.entrySet()) {
            if(result[i] != 0)
                cash.addBanknotes(entry.getKey(), result[i]);
            i++;
        }
        return cash;
    }

    private void withdraw(Cash cash) {
        Map<Banknotes, Integer> cashMap = cash.getCashMap();
        for (Map.Entry<Banknotes, Integer> entry : cashMap.entrySet()) {
            moneyBank.put(entry.getKey(), moneyBank.get(entry.getKey()) - entry.getValue());
        }
    }

    public void addMoneyToATM(Banknotes banknote, int number) {
        if (moneyBank.containsKey(banknote)) {
            moneyBank.put(banknote, moneyBank.get(banknote) + number);
        } else
            moneyBank.put(banknote, number);
    }

    public int getID() {
        return ID;
    }
}
