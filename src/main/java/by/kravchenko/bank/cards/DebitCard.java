package by.kravchenko.bank.cards;

public class DebitCard implements Card{
    private final int ID;

    public DebitCard(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DebitCard debitCard = (DebitCard) o;

        return ID == debitCard.ID;
    }

    @Override
    public int hashCode() {
        return ID;
    }
}
