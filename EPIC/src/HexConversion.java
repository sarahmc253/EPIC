public class HexConversion {

    public void decimal(String decimal) {
        StringBuilder binary = new StringBuilder();

        int decimalInt;
        String hex;
        if (decimal.contains(".")) {
            hex = Double.toHexString(Double.parseDouble(decimal));
            String[] doubleParts = decimal.split("\\.");

            int partOne = Integer.parseInt(doubleParts[0]);
            double partTwo = Math.abs(Double.parseDouble(decimal) - partOne);

            if (partOne < 0) {
                binary.insert(0, "-");
            }
            while (partOne != 0) {
                int remainder = partOne % 2;
                if (remainder == -1) {
                    remainder = 1;
                }
                binary.insert(0, remainder);
                partOne = partOne / 2;
            }

            binary.append(".");

            for (int i = 0; i < 10; i++) {
                partTwo *= 2;
                if (partTwo > 1) {
                    binary.append("1");
                    partTwo -= 1;
                } else {
                    binary.append("0");

                }

            }

        } else {
            hex = Integer.toHexString(Integer.parseInt(decimal));
            decimalInt = Integer.parseInt(decimal);

            if (decimalInt < 0) {
                System.out.print("-");
            }
            while (decimalInt != 0) {
                int remainder = decimalInt % 2;
                if (remainder == -1) {
                    remainder = 1;
                }
                binary.insert(0, remainder);
                decimalInt = decimalInt / 2;
            }
        }



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
}
