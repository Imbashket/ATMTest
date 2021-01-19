package by.kravchenko.bank.atm;

import by.kravchenko.money.Banknotes;

import java.util.HashMap;
import java.util.Map;

public class Cash {
    private final Map<Banknotes, Integer> cash = new HashMap<>();

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

    public String convertCashToString() {
        StringBuilder msg = new StringBuilder("Banknotes: ");
        for (Map.Entry<Banknotes, Integer> entry : cash.entrySet()) {
            msg.append(entry.getValue())
                    .append(" - ")
                    .append(entry.getKey())
                    .append(" ");
        }
        return msg.toString();
    }
}
