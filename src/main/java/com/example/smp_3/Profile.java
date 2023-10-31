package com.example.smp_3;

/**
 * Profile creates a profile that is able to be used in creating an account.
 * This class creates a profile by taking in the input of first name, last name, and DOB
 * This makes it so the account class initialize a holder profile
 *
 * @author Judah Farkas, David Rahabi
 */

public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructor for the Profile class
     *
     * @param fname first name
     * @param lname last name
     * @param dob   Date of Birth
     */
    Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * compareTo overides the java compareTo method to compare 2 profiles
     *
     * @param profile the object to be compared.
     * @return 0 if profile =, otherwise -1
     */
    @Override
    public int compareTo(Profile profile) {
        String thisFName = this.fname.toLowerCase();
        String thisLName = this.lname.toLowerCase();
        String profileFName = profile.fname.toLowerCase();
        String profileLName = profile.lname.toLowerCase();
        if (thisFName.equals(profileFName) && thisLName.equals(profileLName) && this.dob.equals(profile.dob)) {
            return 0; //if profile equal
        }
        if ((thisLName.compareTo(profileLName) > 0) || ((thisLName.compareTo(profileLName)) == 0 &&
                thisFName.compareTo(profileFName) > 0)) {
            return 1;
        }
        if ((thisLName.compareTo(profileLName) == 0 && thisFName.compareTo(profileFName) == 0) &&
                this.dob.compareTo(profile.dob) > 0) {
            return 1;
        }
        return -1;
    }

    /**
     * toString overrides the java toString method allowing to get profile information on a string
     *
     * @return fname Lname dob First name, Last name, DOB to a string
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob.toString();
    }

    /**
     * Getter Method for Date of Birth
     *
     * @return dob Date of Birth
     */
    public Date getDOB() {
        return dob;
    }

}