package io.github.olivenbarcelon.umjavaws.commons.utils.crypto;

import io.github.olivenbarcelon.umjavaws.commons.utils.NumeralUtility;
import io.github.olivenbarcelon.umjavaws.commons.utils.StringUtility;

import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Source:
 *  Encode an ASCII string into Base-64 Format - https://www.geeksforgeeks.org/encode-ascii-string-base-64-format
 */
public class Base64 {
    private static Logger log = Logger.getLogger(Base64.class.getName());
    
    private static String base64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"; // replace + to - and / to _ for URL
    
    private static String binary(int index, char[] token) {
        if(index < token.length) return StringUtility.leftPad(String.valueOf(NumeralUtility.toBinary(decode(token[index]))), 6, '0') + binary(++index, token);
        return "";
    }
    
    public static String decode(String encoded) {
        return decode(encoded, "");
    }
    
    private static int decode(char c) {
        return base64.indexOf(c);
    }
    
    public static String decode(String encoded, String secretKey) {
        encoded = removePad(encoded);
        String binary = binary(0, encoded.toCharArray());
        int length = binary.length();
        int removeBit = length % 8;
        binary = binary.substring(0, length - removeBit);
        if(StringUtility.isValid(secretKey)) binary = NumeralUtility.subtractBinary(NumeralUtility.toBinary(secretKey), binary);
        String[] blocks = binary.split("(?<=\\G.{8})");
        String decode = Stream.of(blocks).map(NumeralUtility::toDecimal).map(m -> (char) m.intValue()).map(String::valueOf).collect(
                Collectors.joining());
        log.info("Decoded: " + decode);
        return decode;
    }
    
    public static String encode(String input) {
        return encode(input, "");
    }
    
    private static char encode(int decimal) {
        return base64.charAt(decimal);
    }
    
    public static String encode(String input, String secretKey) {
        String binary = NumeralUtility.toBinary(input);
        if(StringUtility.isValid(secretKey)) binary = NumeralUtility.addBinary(binary, NumeralUtility.toBinary(secretKey));
        int length = binary.length();
        int pad = length % 6 != 0 ? 6 - length % 6 : 0;
        binary = StringUtility.rightPad(binary, length + pad, '0');
        String[] blocks = binary.split("(?<=\\G.{6})");
        int lengthBlocks = blocks.length;
        int reserveChar = 4 - lengthBlocks % 4;
        String encode = Stream.of(blocks).map(NumeralUtility::toDecimal).map(Base64::encode).map(String::valueOf).collect(Collectors.joining());
        encode = StringUtility.rightPad(encode, encode.length() + reserveChar, '=');
        log.info("Encoded: " + encode);
        return encode;
    }
    
    private static String removePad(String input) {
        return input.replace("=", "");
    }
}
