package semicolon.umjavaws.commons.utils;

import java.util.logging.Logger;

/**
 * Source:
 *  Add n binary strings - https://www.geeksforgeeks.org/add-n-binary-strings
 *  Convert a string to hexadecimal ASCII values - https://www.geeksforgeeks.org/convert-a-string-to-hexadecimal-ascii-values
 *  Convert Hexadecimal value String to ASCII value String - https://www.geeksforgeeks.org/convert-hexadecimal-value-string-ascii-value-string
 */
public class NumeralUtility {
    private static Logger log = Logger.getLogger(NumeralUtility.class.getName());
    // Addition and Subtraction
    public static String addBinary(String binary1, String binary2) {
        String result = "";
        int sum = 0;
        for(int i = binary1.length() - 1, j = binary2.length() - 1;i >= 0 || j >= 0 || sum == 1;i--, j--) {
            sum += ((i >= 0) ? binary1.charAt(i) - '0' : 0);
            sum += ((j >= 0) ? binary2.charAt(j) - '0' : 0);
            
            result = sum % 2 + result;
            
            sum /= 2;
        }
        log.info("Add Binary: " + result);
        if(sum != 0) result = sum + result;
        return result;
    }
    
    public static String subtractBinary(String binary1, String binary2) {
        String result = "";
        String complement = complement(binary2);
        int sum = 0;
        for(int i = binary1.length() - 1, j = complement.length() - 1;i >= 0 || j >= 0 || sum == 1;i--, j--) {
            sum += ((i >= 0) ? binary1.charAt(i) - '0' : 0);
            sum += ((j >= 0) ? complement.charAt(j) - '0' : 0);
            
            result = sum % 2 + result;
            
            sum /= 2;
        }
        log.info("Subtract Binary: " + result);
        if(sum != 0) return addBinary(result, String.valueOf(sum));
        return complement(result);
    }
    // Conversion
    public static String toAscii(String hexadecimal) {
        String decimal = "";
        
        for(int i = 0;i < hexadecimal.length();i += 2) {
            String part = hexadecimal.substring(i, i + 2);
            char ch = (char) Integer.parseInt(part, 16);
            decimal = decimal + ch;
        }
        log.info("Hexadecimal to String: " + decimal);
        return decimal;
    }
    //<p>[Decimal to binary number using recursion](https://www.geeksforgeeks.org/decimal-binary-number-using-recursion)</p>
    public static int toBinary(int decimal) {
        if(decimal == 0) return 0;
        return (decimal % 2 + 10 * toBinary(decimal / 2));
    }
    
    public static String toBinary(char c) {
        byte ascii = (byte) c;
        int binary = toBinary(ascii);
        String result = String.format("%08d", binary);
        log.info("Binary: " + result);
        return result;
    }
    
    public static String toBinary(String text) {
        return toBinary(0, text);
    }
    
    private static String toBinary(int index, String text) {
        if(index == text.length()) return "";
        return toBinary(text.charAt(index)) + toBinary(++index, text);
    }
    
    public static int toDecimal(String binary) {
        return toDecimal(0, binary);
    }
    
    private static int toDecimal(int index, String binary) {
        int length = binary.length();
        if(index == length - 1) return binary.charAt(index) - '0';
        return ((binary.charAt(index) - '0') << (length - index - 1)) + toDecimal(index + 1, binary);
    }
    
    public static String toHexadecimal(String input) {
        String hexadecimal = "";
        for(int i = 0;i < input.length();i++) {
            char ch = input.charAt(i);
            int in = ch;
            String part = Integer.toHexString(in);
            hexadecimal += part;
        }
        log.info("String to Hexadecimal: " + hexadecimal);
        return hexadecimal;
    }
    // Complement
    private static String complement(String text) {
        String result = "";
        for(int i = 0;i < text.length();i++) {
            char c = text.charAt(i);
            switch(c) {
                case '0':
                    result += '1';
                    break;
                default:
                    result += '0';
            }
        }
        log.info("Complement: " + result);
        return result;
    }
}
