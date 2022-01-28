package io.github.olivenbarcelon.umjavaws.commons.utils;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @since 2021.09.09 [JDK11]
 * @version 2022-01-27
 * @author Oliven C. Barcelon
 */
public class StringUtility {

    public static String randomString(int len) {
        byte[] bytes = new byte[256];
        new SecureRandom().nextBytes(bytes);
        String random = new String(bytes, Charset.forName("UTF-8"));

        StringBuffer stringBuffer = new StringBuffer();
        for(int k = 0, n = len; k < random.length(); k++) {
            char ch = random.charAt(k);

            if(((ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {
                stringBuffer.append(ch);
                n--;
            }
        }
        return stringBuffer.toString();
    }

    public static String generateTransactionNumber(int len) {
        byte[] bytes = new byte[256];
        new SecureRandom().nextBytes(bytes);
        String random = new String(bytes, Charset.forName("UTF-8"));

        StringBuffer stringBuffer = new StringBuffer();
        for(int k = 0, n = len; k < random.length(); k++) {
            char ch = random.charAt(k);
            if(((ch >= '0' && ch <= '9')) && (n > 0)) {
                stringBuffer.append(ch);
                n--;
            }
        }
        return stringBuffer.toString();
    }

    public static String randomString() {
        byte[] bytes = new byte[256];
        new SecureRandom().nextBytes(bytes);
        String random = new String(bytes, Charset.forName("UTF-8"));

        StringBuffer stringBuffer = new StringBuffer();
        for(int k = 0, n = 16; k < random.length(); k++) {
            char ch = random.charAt(k);

            if(((ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {
                stringBuffer.append(ch);
                n--;
            }
        }
        return stringBuffer.toString();
    }

    public static String generateBatchNumber() {
        byte[] bytes = new byte[256];
        new SecureRandom().nextBytes(bytes);
        String random = new String(bytes, Charset.forName("UTF-8"));

        StringBuffer stringBuffer = new StringBuffer();
        for(int k = 0, n = 9; k < random.length(); k++) {
            char ch = random.charAt(k);

            if(((ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {
                stringBuffer.append(ch);
                n--;
            }
        }
        String timeStr = String.valueOf(System.currentTimeMillis()).substring(9);
        return stringBuffer + timeStr;
    }

    public static String randomStringUpperCase(int number) {
        byte[] bytes = new byte[256];
        new SecureRandom().nextBytes(bytes);
        String random = new String(bytes, Charset.forName("UTF-8"));

        StringBuffer stringBuffer = new StringBuffer();
        for(int k = 0; k < random.length(); k++) {
            char ch = random.charAt(k);

            if((ch >= 'A' && ch <= 'Z') && (number > 0)) {
                stringBuffer.append(ch);
                number--;
            }
        }
        return stringBuffer.toString();
    }

    public static String removeDiacritic(String input) {
        if(isNullOrEmpty(input)) return input;
        return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
    
    /**
     * Remove all special character except white space
     * @param input {@code String} input
     * @return {@code String}
     * @since 2022-01-27 [JDK11]
     * @version 2022-01-27
     * @author Oliven C. Barcelon
     * @see
     * <a href="https://www.geeksforgeeks.org/how-to-remove-all-non-alphanumeric-characters-from-a-string-in-java/">How to remove all non-alphanumeric characters from a string in Java</a>
     */
    public static String removeNonAlphaNumeric(String input) {
        if(isAlphaNumeric(input)) return input;
        else return input.replaceAll("[^a-zA-Z0-9\\s]", "");
    }
    
    public static boolean isAlphaNumeric(String input) {
        if(isNullOrEmpty(input)) return true;
        String regex = "^[\\w\\sÑñ]+";
        return input.matches(regex);
    }
    
    private static boolean isLowerCase(char c) {
        return (c >= 'a' && c <= 'z'); // Check if the character is in lower case.
    }
    
    /**
     * Check if input is null or empty
     * @param inputs {@link String}
     * @return {@link Boolean}
     * @since 2021.09.09 [JDK11]
     * @version 2021.11.29
     * @author Oliven C. Barcelon
     */
    public static boolean isNullOrEmpty(String input) {
        return input == null || input.isEmpty();
    }
    
    public static boolean isValid(String input) {
        return !isNullOrEmpty(input);
    }
    
    /**
     * Validate input
     * @param inputs {@link String}
     * @return {@link Boolean}
     * @since 2021.09.09 [JDK11]
     * @version 2021.11.29
     * @author Oliven C. Barcelon
     */
    public static boolean isValid(String... inputs) {
        long count = Stream.of(inputs).filter(StringUtility::isNullOrEmpty).count();
        if(count > 0) return false;
        return true;
    }
    
    private static boolean isWhitespace(char c) {
        return (c == ' ');
    }
    
    public static boolean isWord(String input) {
        if(isNullOrEmpty(input)) return true;
        String regex = "^[a-zA-Z\\sÑñ]+";
        return input.matches(regex);
    }
    
    public static String formatPhoneNumber(String input) {
        if(isNullOrEmpty(input)) return input;

        String result = input;
        switch(input.toCharArray()[0]) {
            case '0':
                result = input.replaceFirst("[0]", "63");
                break;
            case '9':
                result = "63" + input;
                break;
            default:
                if(input.contains("+63")) {
                    result = input.replace("+63", "63");
                }
        }

        return result;
    }

    public static String leftPad(String text, int length, char c) {
        return String.format("%1$" + length + "s", text).replace(' ', c);
    }

    public static String rightPad(String text, int length, char c) {
        return String.format("%1$-" + length + "s", text).replace(' ', c);
    }

    public static String toFullName(String firstName, String middleName, String lastName) {
        String fullName = "";
        if(isValid(firstName)) {
            fullName += isValid(middleName) || isValid(lastName) ? firstName + " " : firstName;
        }
        if(isValid(middleName)) {
            fullName += isValid(lastName) ? middleName + " " : middleName;
        }
        fullName += isValid(lastName) ? lastName : "";
        return toUpperCaseAllFirst(fullName.toLowerCase());
    }

    public static String toUpperCaseFirst(String text) {
		char[] c = text.toCharArray();
		c[0] = Character.toUpperCase(c[0]);

		return new String(c);
	}

    public static String toUpperCaseAllFirst(String text) {
        char[] c = text.toCharArray();
        for(int i = 0;i < text.length();i++) {
            // Check the first character of a word.
            if(i == 0 && !isWhitespace(c[i]) || !isWhitespace(c[i]) && isWhitespace(c[i - 1])) {
                c[i] = toUpperCase(c[i]); // Convert into upper case.
            }
            /*else if(c[i] >= 'A' && c[i] <= 'Z') { // If apart from first character. Any one is in Upper-case.
                // Convert into Lower-Case.
                c[i] = (char) (c[i] + 'a' - 'A');
            }*/
        }

        return new String(c);
    }

    private static char toUpperCase(char c) {
        if(isLowerCase(c)) {
            c = (char) (c - 'a' + 'A'); // Convert into upper case.
        }

        return c;
    }

    public static String toUpperCase(String text) {
        char[] c = text.toCharArray();
        for(int i = 0;i < text.length();i++) {
            c[i] = toUpperCase(c[i]);
        }

        return new String(c);
    }

    public static String toCamelCase(String str) {
        if(!str.contains("_")) return str;
        return toCamelCase(str.replaceFirst("_[a-z]", String.valueOf(toUpperCase(str.charAt(str.indexOf("_") + 1)))));
    }
    
    /**
     * Convert {@link Map} key to snake case.
     * @param map {@link Map} with string key and object value
     * @return {@code Map<String, Object>}
     * @since 2021.09.09 [JDK11]
     * @version 2021.11.25
     * @author Oliven C. Barcelon
     */
    public static Map<String, Object> toSnakeCase(Map<String, Object> map) {
        return map.entrySet().stream().collect(LinkedHashMap::new, (m, e) -> m.put(StringUtility.toSnakeCase(e.getKey()), e.getValue()), LinkedHashMap::putAll);
    }
    
    /**
     * Convert {@link String} input to snake case.
     * @param str {@link String} with string key and object value
     * @return {@code String}
     * @since 2021.09.09 [JDK11]
     * @version 2021.11.25
     * @author Oliven C. Barcelon
     */
    private static String toSnakeCase(String str) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        str = str.replaceAll(regex, replacement).toLowerCase();
        return str;
    }
    
    /**
     * Convert {@link Byte} array of input to {@link String}.
     * @param bytes array of input
     * @return {@code String}
     * @since 2022.01.13 [JDK11]
     * @version 2022.01.13
     * @author Oliven C. Barcelon
     * @see
     * <a href="https://mkyong.com/java/how-do-convert-byte-array-to-string-in-java/">How to convert byte[] array to String in Java</a>
     */
    public static String toString(byte[] bytes) {
        return new String(bytes, Charset.forName("UTF-8"));
    }
    
    public static String insertStr(String originalStr, String insertStr, int index) {
        if(isValid(originalStr)) return originalStr.substring(0, index + 1) + insertStr + originalStr.substring(index + 1);
        else return insertStr;
    }
}
