package com.example.smp_3;


/**
 * The Account class is an abstract class that inherits all different types of accounts.
 * This class has all abstract methods that the other account classes calls for implementation
 *
 * @author Judah Farkas, David Rahabi
 */
public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;

    /**
     * compareTo compares 2 accounts to determines if the accounts are equal
     *
     * @param account the object to be compared.
     * @return 0 if equal, otherwise 1
     */

    @Override
    public int compareTo(Account account) {
        if (this.holder.compareTo(account.holder) == 0 && account.printType().equals(this.printType())) {
            return 0;
        }
        return 1;  //do we return 1 or -1 based on the balance?
    }


    public abstract double monthlyInterest();

    public abstract double monthlyFee();

    public abstract String printType();

    public abstract String printWithFeesAndInterest();

    public abstract String printUpdatedBalance();
}
