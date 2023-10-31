
package com.example.smp_3;

import java.text.DecimalFormat;


/**
 * The MoneyMarket class is an extended class of the Savings
 * This class initializes the variables needed to make a MoneyMarket savings account
 * it also prints the account information toString for printing in console along with updating loyalty values and withdraw amount
 *
 * @author David Rahbi, Judah Farkas
 */

public class MoneyMarket extends Savings {
    private int withdrawal; //number of withdrawals

    /**
     * Constructor for MoneyMarket
     *
     * @param profile Profile
     */
    public MoneyMarket(Profile profile) {
        super(profile);
    }

    /**
     * Constructor for MoneyMarket
     *
     * @param profile Profile
     * @param depo    Deposit amount
     */
    public MoneyMarket(Profile profile, double depo) {
        super(profile, depo);
    }

    /**
     * Constructor for MoneyMarket
     *
     * @param profile profile
     * @param depo    Deposit amount
     * @param loyal   isLoyal
     */
    public MoneyMarket(Profile profile, double depo, boolean loyal) {
        super(profile, depo, loyal);
    }

    /**
     * Calculates the monthly interest rate.
     *
     * @return the monthly interest
     */
    @Override
    public double monthlyInterest() {
        if (isLoyal) {
            return .0475 / 12.0 * balance;
        }
        return .045 / 12.0 * balance;
    }

    /**
     * Calculates the monthly fee.
     *
     * @return the monthly fee
     */
    @Override
    public double monthlyFee() {
        if (balance >= 2000) {
            if (withdrawal > 3) {
                return 10.0;
            }
            return 0.0;
        }
        if (withdrawal > 3) {
            return 35.0;
        }
        return 25.0;
    }

    /**
     * creates a string with the type of account
     *
     * @return a string containing the type of account abbreviated in parentheses
     */
    @Override
    public String printType() {
        return "(MM)";
    }

    /**
     * creates a string to print the account
     *
     * @return the string with the account values
     */
    @Override
    public String toString() {
        if (isLoyal) {
            return "Money Market" + "::Savings::" + this.holder.toString() + "::Balance $"
                    + String.format("%,.2f", this.balance) + "::is loyal::" + "withdrawal: " + withdrawal;
        }
        return "Money Market" + "::Savings::" + this.holder.toString() + "::Balance $"
                + String.format("%,.2f", this.balance) + "::withdrawal: " + withdrawal;

    }

    /**
     * creates a string to print the account with fees and interest
     *
     * @return the string with the account values
     */
    @Override
    public String printWithFeesAndInterest() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,##0.00");
        if (isLoyal) {
            return "Money Market" + "::Savings::" + this.holder.toString() + "::Balance $" + String.format("%,.2f", this.balance)
                    + "::is loyal" + "::withdrawal: " + withdrawal + "::fee $" + String.format("%,.2f", this.monthlyFee()) + "::monthly interest $"
                    + String.format("%,.2f", this.monthlyInterest());
        }
        return "Money Market" + "::Savings::" + this.holder.toString() + "::Balance $" + String.format("%,.2f", this.balance)
                + "::withdrawal: " + withdrawal + "::fee $" + String.format("%,.2f", this.monthlyFee()) + "::monthly interest $" + decimalFormat.format(this.monthlyInterest());
    }

    /**
     * creates a string to print the account with the updated balances based on the interest and fees.
     *
     * @return the string with the account values
     */
    @Override
    public String printUpdatedBalance() {
        balance += this.monthlyInterest();
        balance -= this.monthlyFee();
        if (isLoyal) {
            return "Money Market" + "::Savings::" + this.holder.toString() + "::Balance $" + String.format("%,.2f", this.balance) + "::is loyal::" + "withdrawal: " + 0;
        }
        return "Money Market" + "::Savings::" + this.holder.toString() + "::Balance $" + String.format("%,.2f", this.balance) + "::withdrawal: " + 0;
    }

    /**
     * adds 1 to the withdrawal value of the account
     */
    public void updateWithdrawal() {
        this.withdrawal++;
        if (balance < 2000) {
            isLoyal = false;
        }
    }

    /**
     * resets the withdrawal value of the account to zero
     */
    public void resetWithdrawal() {
        this.withdrawal = 0;

    }

    /**
     * Updates loyaalty according to balance
     */
    public void updateLoyalty() {
        if (this.balance >= 2000) {
            this.isLoyal = true;
        } else {
            this.isLoyal = false;
        }
    }

}
