package by.kravchenko.bank;

import by.kravchenko.bank.atm.ATM;
import by.kravchenko.bank.cards.Card;
import by.kravchenko.money.Banknotes;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.lang.Integer.parseInt;

public class MainClass {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        Bank bank = new Bank();
        ATM atm = new ATM(bank);
        atm.addMoneyToATM(Banknotes.TWENTY, 10);
        atm.addMoneyToATM(Banknotes.FIFTY, 10);
        atm.addMoneyToATM(Banknotes.HUNDRED, 10);

        Card card1 = bank.addNewDebitCard(1111);
        Card card2 = bank.addNewDebitCard(2222);
        Card card3 = bank.addNewDebitCard(3333);
        bank.addBalance(card1, 300);
        bank.addBalance(card2, 80);
        bank.addBalance(card3, 10000);


        System.out.println("Balance of card1: " + bank.getBalance(card1));
        System.out.println("Balance of card2: " + bank.getBalance(card2));
        System.out.println("Balance of card3: " + bank.getBalance(card3));

        System.out.println();
        System.out.println("Using card1");
        System.out.println(makeRequestForGetMoney(atm, card1));

        System.out.println();
        System.out.println("Using card2");
        System.out.println(makeRequestForGetMoney(atm, card2));

        System.out.println();
        System.out.println("Using card3");
        System.out.println(makeRequestForGetMoney(atm, card3));

    }

    private static String makeRequestForGetMoney(ATM atm, Card card) {
        int PIN;
        int amount;
        System.out.print("Enter PIN: ");
        try {
            PIN = parseInt(br.readLine());
        } catch (Exception e) {
            return "Incorrect enter of PIN";
        }
        System.out.print("Enter amount of money: ");
        try {
            amount = parseInt(br.readLine());
        } catch (Exception e) {
            return "Incorrect enter of amount";
        }
        try {
            return atm.getMoney(card, PIN, amount).toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

}
