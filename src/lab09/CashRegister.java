/**
 * CSCI 205 - Software Engineering and Design
 *
 * Lab 09 - Learning how to use JUnit.
 *
 * Date: Sep 18, 2018
 * Time: 7:59:21 PM
 *
 * Project: csci205
 * Package: lab09
 * File: CashRegister
 * Description: This file contains the CashRegister class, which simulates a cash register.
 *
 * @author Brian King, Rick Zaccone (Modified by: Kartikeya Sharma)
 */
package lab09;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A custom exception is thrown if the cash register tries to give change before
 * enough money is collected
 *
 * @author Kartikeya Sharma
 */
class ChangeException extends Exception {

    public ChangeException(String message) {
        super(message);
    }

}

/**
 * The <code>CashRegister</code> class models a very simple cash register. The
 * cash register assumes that it has an unlimited supply of money in its drawer,
 * and thus this is not modeled. It handles the management of one transaction of
 * a time, where a transaction consists of a list of items purchased. This
 * register only logs the amount of each purchase in a single transaction.
 *
 * @author Prof. Rick Zaccone and Brian King (Modified by Kartikeya Sharma)
 */
public class CashRegister {

    /**
     * Maximum price of an item that a scanner is permitted to scan
     */
    private static double MAX_ITEM_PRICE = 1000.0;

    /**
     * The total amount of the current transaction
     */
    private double totalTransaction;

    /**
     * Payment collected from the customer so far
     */
    private double paymentCollected;

    /**
     * List of purchases in the current transaction
     */
    private final LinkedList<Double> listOfItemPrices;

    /**
     * Constructs a new cash register
     */
    public CashRegister() {
        totalTransaction = 0;
        paymentCollected = 0;
        listOfItemPrices = new LinkedList<Double>();
    }

    /**
     * @return the number of purchases in the current transaction
     */
    public int getPurchaseCount() {
        return listOfItemPrices.size();
    }

    /**
     * @return the list of purchases in the current transaction
     */
    public List<Double> getListOfPurchases() {
        return listOfItemPrices;
    }

    /**
     * @return get the total amount of the current transaction
     */
    public double getTransactionTotal() {
        return totalTransaction;
    }

    /**
     * @return the total amount of payment collected for the current transaction
     */
    public double getPaymentCollected() {
        return paymentCollected;
    }

    /**
     * Records the sale of an item in a transaction.
     *
     * @param price the price of the item. Precondition: price >= 0 or price < <code> MAX_ITEM_PRICE
     * </code>
     */
    public void scanItem(double price) {
        // First, check for a valid price
        if (price < 0.0 || price > MAX_ITEM_PRICE) {
            throw new IllegalArgumentException(String.format(
                    "scanItem: Bad Price: $%.2f", price));
        }

        // If this is the first purchase in the transaction, then clear out the
        // list of purchases
        if (totalTransaction == 0) {
            listOfItemPrices.clear();
        }

        listOfItemPrices.add(price);
        totalTransaction += price;
    }

    /**
     * Enters the payment received from the customer; should be called once for
     * each monetary unit moneyType
     *
     * @param moneyType the moneyType of the monetary units in the payment
     * @param unitCount the number of monetary units
     * @throwns IllegalArgumentException - thrown if a negative amount of
     * currency is passed in for <code> unitCount </code>
     */
    public void collectPayment(Money moneyType, int unitCount) {
        if (unitCount < 0) {
            throw new IllegalArgumentException(String.format(
                    "collectPayment: Negative Unit Count: %d", unitCount));
        }
        paymentCollected += unitCount * moneyType.getValue();
    }

    /**
     * Computes the change due and resets the machine for the next customer,
     * only if enough money was collected.
     *
     * @return the change due to the customer
     * @throws ChangeException - thrown if not enough payment collected
     */
    public double giveChange() throws ChangeException {
        // Check to see if enough payment has been collected first
        if (paymentCollected < totalTransaction) {
            throw new ChangeException(String.format(
                    "INSUFFICIENT PAYMENT: Collected %.2f, transaction = %.2f",
                    paymentCollected, totalTransaction));
        }
        double change = paymentCollected - totalTransaction;
        totalTransaction = 0;
        paymentCollected = 0;
        return change;
    }

    /**
     * Compares an inputted Object to this object to see if they are either the
     * same object or are both of the same class and share equivalent instance
     * fields
     *
     * @param obj - inputted Object to be compared to this object
     * @return Returns true if this object and <code> obj </code> are the same
     * object or are both of the same class and share equivalent instance fields
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CashRegister other = (CashRegister) obj;
        if (Double.doubleToLongBits(this.totalTransaction) != Double.doubleToLongBits(
                other.totalTransaction)) {
            return false;
        }
        if (Double.doubleToLongBits(this.paymentCollected) != Double.doubleToLongBits(
                other.paymentCollected)) {
            return false;
        }
        if (!Objects.equals(this.listOfItemPrices, other.listOfItemPrices)) {
            return false;
        }
        return true;
    }
}
