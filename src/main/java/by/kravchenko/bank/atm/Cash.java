package by.kravchenko.bank.atm;

import by.kravchenko.money.Banknotes;

import java.util.HashMap;
import java.util.Map;

public class Cash {
    private final Map<Banknotes, Integer> cash = new HashMap<Banknotes, Integer>();

    public void addBanknotes(Banknotes banknotes, int number) {
        if (cash.containsKey(banknotes)) {
            cash.put(banknotes, cash.get(banknotes) + number);
        } else {
            cash.put(banknotes, number);
        }
    }

    public Map<Banknotes, Integer> getCashMap() {
        return cash;
    }

    public String toString() {
        String msg = null;
        for (Map.Entry<Banknotes, Integer> entry : cash.entrySet()) {
            msg = entry.getValue() + " - " + entry.getKey() + " ";
        }
        return msg;
    }
}
