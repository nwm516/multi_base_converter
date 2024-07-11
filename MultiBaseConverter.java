import java.util.Scanner;

public class MultiBaseConverter {
    private static final String DIGITS = "0123456789ABCDEF";    // Takes into account base-16 (hex)

    public static void main(String[] args) {

        Scanner digitScanner = new Scanner(System.in);

        String inputNumber = null;
        int inputBase = 0;
        int outputBase = 0;

        try {
            System.out.println("Enter a number for conversion: ");
            inputNumber = digitScanner.next().toUpperCase();
            validateInput(inputNumber, 16);

            System.out.println("Enter the base of the input number (2 - 16): ");
            inputBase = getValidBase(digitScanner);

            System.out.println("Enter the base to convert the number to (2 - 16): ");
            outputBase = getValidBase(digitScanner);

            validateInput(inputNumber, inputBase);
            String result = baseConverter(inputNumber, inputBase, outputBase);
            System.out.println("Converted number: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        digitScanner.close();
    }

    // getValidBase verifies that the base being entered spans from 2 to 16, and is numeric in nature.
    private static int getValidBase(Scanner validScanner) {
        while (true) {
            if (validScanner.hasNextInt()) {
                int base = validScanner.nextInt();
                if (base >= 2 && base <= 16) {
                    return base;
                } else {
                    System.out.println("Invalid base: " + base + ". Base must be between 2 and 16.");
                }
            } else {
                System.out.println("Invalid input: please enter a number between 2 and 16.");
                validScanner.next();
            }
        }
    }

    // validateInput does just that: validates entered values and makes sure they are usable input
    private static void validateInput(String number, int inputBase) {
        if (inputBase < 2 || inputBase > 16) {
            throw new IllegalArgumentException("Invalid base number: base must be between 2 and 16");
        }

        boolean isNegative = number.startsWith("-");
        if (isNegative) {
            number = number.substring(1);
        }

        for (char digit : number.toCharArray()) {
            int digitValue = DIGITS.indexOf(digit);
            if (digitValue < 0 || digitValue >= inputBase) {
                throw new IllegalArgumentException("Invalid digit for base " + inputBase + ": " + digit);
            }
        }
    }

    /*
    Converts input number, next converting that value to base-10 (decimal),
    and finally to the output base of the user's choice
     */
    private static String baseConverter(String number, int inputBase, int outputBase) {
        boolean isNegative = number.startsWith("-");
        if (isNegative) {
            number = number.substring(1);
        }

        int decimalValue = convertToDecimal(number, inputBase);
        String numResult = convertFromDecimal(decimalValue, outputBase);

        return isNegative ? "-" + numResult : numResult;
    }

    // Converts input number to base-10 (decimal)
    private static int convertToDecimal(String number, int base) {
        int decimalValue = 0;
        int power = 1;

        for (int i = number.length() - 1; i >= 0; i--) {
            char digit = number.charAt(i);
            int digitValue = DIGITS.indexOf(digit);
            decimalValue += digitValue * power;
            power *= base;
        }
        return decimalValue;
    }

    // Converts converted decimal value to intended base of user's choice
    private static String convertFromDecimal(int decimalValue, int base) {
        if (decimalValue == 0) {
            return "0";
        }

        StringBuilder result = new StringBuilder();
        while (decimalValue > 0) {
            int remainder = decimalValue % base;
            result.insert(0, DIGITS.charAt(remainder));
            decimalValue /= base;
        }
        return result.toString();
    }
}
