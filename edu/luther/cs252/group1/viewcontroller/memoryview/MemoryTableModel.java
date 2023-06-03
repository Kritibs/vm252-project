package edu.luther.cs252.group1.viewcontroller.memoryview;

import javax.swing.table.AbstractTableModel;

public abstract class MemoryTableModel extends AbstractTableModel {

    public static final int singleByteTableModelColumns = 20;
    public static final int doubleByteTableModelColumns = 10;

    //
    // Protected Class Method String intToHexString(int originalInteger)
    //
    // Purpose:
    //     Convert an integer to an upper case hex string
    //
    // Formals:
    //     originalInteger (in) - any integer value
    //
    // Pre-conditions:
    //     none
    //
    // Post-conditions:
    //     none
    //
    // Returns:
    //     Upper case string for the hex value of originalInteger
    //
    // Worst-case asymptotic runtime:
    //     O(1)
    //
    protected static String intToHexString(int originalInteger) {
        return Integer.toHexString(originalInteger).toUpperCase();
    }


    //
    // Protected Class Method int hexStringToInteger(String hexString)
    //
    // Purpose:
    //     Convert a string representing a hex value to the corresponding integer value
    //
    // Formals:
    //     hexString (in) - hex value as a string
    //
    // Pre-conditions:
    //     hexString is a valid hex number, values must be 0-9, A-F
    //
    // Post-conditions:
    //     none
    //
    // Returns:
    //     Integer representation of hexString
    //
    // Worst-case asymptotic runtime:
    //     O(n) (length of the hexString)
    //
    protected static int hexStringToInteger(String hexString) {
        // Resulting value to be returned
        int resultInteger = 0;
        // Location of value within hexString
        int hexStringIndex = 0;
        int size = hexString.length();

        // Add each character from the hex string to the resultInteger
        // Invariant: index <= length of string,
        for (char character : hexString.toCharArray()) {
            // Convert letters and numbers to the value they represent for their location within the hexString
            //   Ex: EC -> E0 + 0C
            switch (Character.toUpperCase(character)) {
                case 'A' -> resultInteger += 10 * (Math.pow(16, size - (hexStringIndex + 1)));
                case 'B' -> resultInteger += 11 * (Math.pow(16, size - (hexStringIndex + 1)));
                case 'C' -> resultInteger += 12 * (Math.pow(16, size - (hexStringIndex + 1)));
                case 'D' -> resultInteger += 13 * (Math.pow(16, size - (hexStringIndex + 1)));
                case 'E' -> resultInteger += 14 * (Math.pow(16, size - (hexStringIndex + 1)));
                case 'F' -> resultInteger += 15 * (Math.pow(16, size - (hexStringIndex + 1)));
                default -> resultInteger += Integer.parseInt(String.valueOf(character)) * (Math.pow(16, size - (hexStringIndex + 1)));
            }
            ++hexStringIndex;
        }

        return resultInteger;
    }

}
