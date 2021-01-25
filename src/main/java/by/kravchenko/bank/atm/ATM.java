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
    private final static int QUANTITY_OF_CASH_OPTIONS = 3;
    private Map<Banknotes, Integer> moneyBank = new TreeMap<>(
            Comparator.comparingInt(Banknotes::getDenomination)
    );
    private final Bank bank;

    public ATM(Bank bank) {
        this.bank = bank;
    }

    public Cash getMoney(Card card, int PIN, int value)
            throws InvalidCardException, WrongPinException, LowBalanceException, WrongAmountException {
        bank.checkRequest(card, PIN, value);
        Cash cash = chooseOptionOfCash(Handler.calculateCashOptions(moneyBank, value));
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

    public static int getQuantityOfCashOptions() {
        return QUANTITY_OF_CASH_OPTIONS;
    }
}
