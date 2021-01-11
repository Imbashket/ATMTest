package by.kravchenko.money;

import by.kravchenko.bank.cards.DebitCard;

public enum Banknotes {
    TWENTY(20), FIFTY(50), HUNDRED(100);

    private final int DENOMINATION;

    Banknotes(int DENOMINATION) {
        this.DENOMINATION = DENOMINATION;
    }

    public int getDenomination() {
        return DENOMINATION;
    }
}
