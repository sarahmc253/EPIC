
/*
 * Anna Maughan, 2024
 */

public class HexConversion {

    // method for when user wants to convert a decimal number into binary and hexadecimal
    public void decimal(String decimal) {
        // string builder to contain the binary output
        StringBuilder binary = new StringBuilder();

        // string to contain hexadecimal output
        String hex;

        // check if inputted string is a double or integer
        if (decimal.contains(".")) {
            // convert to hexadecimal
            hex = Double.toHexString(Double.parseDouble(decimal));

            // split double into integer part and fractional part
            // this allows them to be dealt with separately
            String[] doubleParts = decimal.split("\\.");

            // store integer part in a variable
            int integerPart = Integer.parseInt(doubleParts[0]);
            // subtract integer part from input amount to get fractional part
            double fractionalPart = Math.abs(Double.parseDouble(decimal) - integerPart);

            //calculate integerPart in binary
            while (integerPart != 0) {
                int remainder = integerPart % 2;    // calculate number in binary using divide by 2 method
                binary.insert(0, Math.abs(remainder)); // insert either 1 or 0 at the start of the string, depending on the remainder
                integerPart = integerPart / 2;  // divide by two for the next iteration
            }

            binary.append(".");     // add a decimal point between the integer and fractional parts

            // calculate to 16 decimal places
            for (int i = 0; i < 16; i++) {
                fractionalPart *= 2;    // multiply fractional part by two
                if (fractionalPart > 1) {   // if it is greater than 1, append 1 to the string builder
                    binary.append("1");
                    fractionalPart -= 1;    // subtract 1 for the next iteration
                } else {
                    binary.append("0");     // if it is not greater than 1, append 0 to the string builder

                }

            }

        //if string is an integer
        } else {
            int decimalInt = Integer.parseInt(decimal);     // convert input string to int
            hex = Integer.toHexString(decimalInt);   // convert to hexadecimal

            while (decimalInt != 0) {
                int remainder = decimalInt % 2;     // convert to binary using the divide by 2 method
                binary.insert(0, Math.abs(remainder));
                decimalInt = decimalInt / 2;    // divide by two for the next iteration
            }
        }

        // add minus sign if input is negative
        if(Double.parseDouble(decimal) < 1){
            binary.insert(0, "-");
        }

        // output results
        System.out.println("In binary: " + binary);
        System.out.println("In hexadecimal: " + hex);
    }

    // method used when user wants to convert a binary number to decimal and hexadecimal
    public void binary(String binary){

        int inDecimal = Integer.parseInt(binary, 2);    // convert to decimal
        String inHex = Integer.toHexString(inDecimal);      // convert to hexadecimal

        // print results
        System.out.println("In decimal: " + inDecimal);
        System.out.println("In hexadecimal: " + inHex);
    }

    // method used when user wants to convert a hexadecimal number to decimal and binary
    public void hex(String hex){
        int decimal = Integer.parseInt(hex, 16);    // convert to decimal
        int inDecimal = decimal;    // save decimal value for printing
        StringBuilder binary = new StringBuilder();     // string builder to contain binary output

        while (decimal != 0){
            int remainder = decimal % 2;    // convert to binary using divide by 2 method

            binary.insert(0, Math.abs(remainder));  //  add remainder to binary string builder
            decimal = decimal /2;   // divide by 2 for the next iteration
        }

        if(inDecimal < 1){
            binary.insert(0, "-");  // add minus sign if input is negative
        }

        // print results
        System.out.println("Decimal: " + inDecimal);
        System.out.println("Binary: " + binary);
    }
}
