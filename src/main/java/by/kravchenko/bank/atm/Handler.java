package by.kravchenko.bank.atm;

import by.kravchenko.bank.exceptions.WrongAmountException;
import by.kravchenko.money.Banknotes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Handler {
    protected static List<Cash> calculateCashOptions(Map<Banknotes, Integer> moneyBank,
                                                     int value)
            throws WrongAmountException {
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
                new int[differentBanknotes], value, differentBanknotes - 1),
                moneyBank);
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
                    if (results.size() == ATM.getQuantityOfCashOptions()) {
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

    private static List<Cash> createCashOptionsList(List<Integer[]> result,
                                             Map<Banknotes, Integer> moneyBank)
            throws WrongAmountException {
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
}
