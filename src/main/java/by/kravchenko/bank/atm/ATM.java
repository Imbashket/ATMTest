package by.kravchenko.bank.atm;

import by.kravchenko.bank.Bank;
import by.kravchenko.bank.exceptions.InvalidCardException;
import by.kravchenko.bank.exceptions.LowBalanceException;
import by.kravchenko.bank.exceptions.WrongAmountException;
import by.kravchenko.bank.exceptions.WrongPinException;
import by.kravchenko.bank.cards.Card;
import by.kravchenko.money.Banknotes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ATM {
    private int ID = 0;
    private final static int cashOptions = 3;
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
        Cash cash = chooseOptionOfCash(checkAmountInATM(value));
        bank.chargeOff(card, value);
        withdraw(cash);
        return cash;
    }

    private Cash chooseOptionOfCash(List<Cash> cashList) {
        int options = cashList.size();
        if (options == 1) {
            return cashList.get(0);
        }
        showOptionsOfCash(cashList);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int option = 0;
        do {
            System.out.print("Choose one of " + options + " options of cash: ");
            try {
                option = Integer.parseInt(br.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter only number from 1 to " + options);
            } catch (IOException e) {
                System.out.println("Try else");
            }
        } while (!(option != 0 && option <= options));
        return cashList.get(option - 1);
    }

    private void showOptionsOfCash(List<Cash> cashList) {
        for (Cash cash : cashList) {
            System.out.println(cash.convertCashToString());
        }
    }

    private List<Cash> checkAmountInATM(int value) throws WrongAmountException {
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
        return createCashOptionsList(findSolution(banknotes, banknotesQuantity,
                new int[differentBanknotes], value, differentBanknotes - 1));
    }

    private static List<Integer[]> findSolution(int[] banknotes, int[] banknotesQuantity,
                                                int[] variation, int expectedSum, int arrayIndex) {
        List<Integer[]> results = new ArrayList<>();
        int currentSum = calcSum(banknotes, variation);
        if (currentSum < expectedSum) {
            for (int i = arrayIndex; i >= 0; i--) {
                if (banknotesQuantity[i] > variation[i]) {
                    int[] newVariation = variation.clone();
                    newVariation[i]++;
                    List<Integer[]> newList = findSolution(banknotes, banknotesQuantity,
                            newVariation, expectedSum, i);
                    if (newList.size() != 0) {
                        results.addAll(newList);
                    }
                    if (results.size() == cashOptions) {
                        return results;
                    }
                }
            }
        }
        if (currentSum == expectedSum)
            results.add(arrayCopy(variation));
        return results;
    }

    private static int calcSum(int[] banknotes, int[] variation) {
        int sum = 0;
        for (int i = 0; i < variation.length; i++) {
            sum += banknotes[i] * variation[i];
        }
        return sum;
    }

    private static Integer[] arrayCopy(int[] array) {
        Integer[] ret = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            ret[i] = array[i];
        }
        return ret;
    }

    private List<Cash> createCashOptionsList(List<Integer[]> result) throws WrongAmountException {
        if (result.size() == 0) {
            throw new WrongAmountException();
        }
        List<Cash> cashList = new ArrayList<>();
        for (Integer[] res : result) {
            int i = 0;
            Cash cash = new Cash();
            for (Map.Entry<Banknotes, Integer> entry : moneyBank.entrySet()) {
                if (res[i] != 0)
                    cash.addBanknotes(entry.getKey(), res[i]);
                i++;
            }
            cashList.add(cash);
        }
        return cashList;
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
