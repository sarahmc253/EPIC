public class HexConversion {

    public void decimal(int decimal){
        StringBuilder binary = new StringBuilder();
        int toHex = decimal;

        if(decimal < 0){
            System.out.print("-");
        }
        while (decimal != 0){
            int remainder = decimal % 2;
            if (remainder == -1){
                remainder = 1;
            }
            binary.insert(0, remainder);
            decimal = decimal /2;
        }

        String hex = Integer.toHexString(toHex);

        System.out.println("In binary: " + binary);
        System.out.println("In hexadecimal: " + hex);
    }

    public void binary(String binary){

        int inDecimal = Integer.parseInt(binary, 2);
        String inHex = Integer.toHexString(inDecimal);

        System.out.println("In decimal: " + inDecimal);
        System.out.println("In hexadecimal: " + inHex);
    }

    public void hex(String hex){
        int decimal = Integer.parseInt(hex, 16);
        int inDecimal = decimal;
        StringBuilder binary = new StringBuilder();

        if(decimal < 0){
            System.out.print("-");
        }
        while (decimal != 0){
            int remainder = decimal % 2;
            if (remainder == -1){
                remainder = 1;
            }
            binary.insert(0, remainder);
            decimal = decimal /2;
        }

        System.out.println("Decimal: " + inDecimal);
        System.out.println("Binary: " + binary);
    }

    public int binaryToDecimal(String input){
        return Integer.parseInt(input, 2);
    }

    public String decimalToHex(String input){
        int decimal = Integer.parseInt(input);
        return Integer.toHexString(decimal);
    }

    public int hexToDecimal(String input){
        return Integer.parseInt(input, 16);
    }
}
