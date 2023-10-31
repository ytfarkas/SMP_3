package com.example.smp_3;

/**
 * Campus enum class is the class used to associate the campus and the campus number with each other.
 *
 * @author David Rahabi, Judah Farkas
 */

public enum Campus {
    NEW_BRUNSWICK(0),
    NEWARK(1),
    CAMDEN(2);


    int campusNumber;

    /**
     * Setter method for campus
     *
     * @param campus campus
     */
    Campus(int campus) {
        this.campusNumber = campus;

    }


    /**
     * isValid checks is campus number is a valid campus number
     *
     * @return true if valid, otherwise false
     */
    public boolean isValid() {
        for (Campus campus : Campus.values()) {
            if (campus.campusNumber == this.campusNumber) {
                return true;
            }
        }
        return false;
    }
}
