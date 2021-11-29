package io.github.olivenbarcelon.umjavaws.commons.utils.crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import io.github.olivenbarcelon.umjavaws.commons.utils.MapperUtility;

/**
 * @since 2021-07-12 [JDK11]
 * @version 2021-11-29
 * @author Oliven C. Barcelon
 * @see Reference:
 * <a href="https://www.geeksforgeeks.org/logging-in-java">Logging in Java</a>,
 * <a href="https://metamug.com/article/security/jwt-java-tutorial-create-verify.html">Generate JWT Token and Verify in Plain Java</a>
 */
public class CryptoUtility {
    
    public static String decode(String encoded) {
        return Base64.decode(encoded);
    }
    
    public static String decode(String encoded, String secretKey) {
        return Base64.decode(encoded, secretKey);
    }
    
    public static String encode(String input) {
        return Base64.encode(input);
    }
    
    public static String encode(String input, String secretKey) {
        return Base64.encode(input, secretKey);
    }
    
    public static String decrypt(String encrypt) {
        return decrypt(encrypt, "olivenbarcelon");
    }
    
    public static String decrypt(String encrypt, String secret) {
        return decrypt(encrypt, secret, secret);
    }
    
    public static String decrypt(String encrypt, String secret, String salt) {
        try {
            String algorithm = "AES/CBC/PKCS5Padding";
            SecretKey secretKey = generateKeyFromPassword(secret, salt);
            IvParameterSpec ivParameterSpec = generateIv();
            String decrypt = decryptAes(algorithm, secretKey, ivParameterSpec, encrypt);
            return decrypt;
        }
        catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static String decryptAes(String algorithm, SecretKey secretKey, IvParameterSpec ivParameterSpec, String input) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] bytes = cipher.doFinal(java.util.Base64.getDecoder().decode(input));
        return new String(bytes);
    }
    
    public static String encrypt(String input) {
        return encrypt(input, "olivenbarcelon");
    }
    
    /**
     * Encrypt input with secret key
     * @param input {@link String}
     * @param secret {@link String}
     * @return {@link String}
     * @since 2021-07-12 [JDK11]
     * @version 2021-11-29
     * @author Oliven C. Barcelon
     * @see Reference:
     * <a href="https://www.baeldung.com/java-aes-encryption-decryption">Java AES Encryption and Decryption</a>
     */
    public static String encrypt(String input, String secret) {
        return encrypt(input, secret, secret);
    }
    
    public static String encrypt(String input, String secret, String salt) {
        try {
            String algorithm = "AES/CBC/PKCS5Padding";
            SecretKey secretKey = generateKeyFromPassword(secret, salt);
            IvParameterSpec ivParameterSpec = generateIv();
            String encrypt = encryptAes(algorithm, secretKey, ivParameterSpec, input);
            return encrypt;
        }
        catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static String encryptAes(String algorithm, SecretKey secretKey, IvParameterSpec ivParameterSpec, String input) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] bytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        return java.util.Base64.getEncoder().encodeToString(bytes);
    }
    
    // Generate AES Key
    /*private static SecretKey generateKey(int bit) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(bit);
        return keyGenerator.generateKey();
    }*/
    
    // Generate AES Key from password
    private static SecretKey generateKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secret;
    }
    
    private static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        //new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
    
    /**
     * Type 1 UUID Generation
     * @return {@link UUID}
     * @since 2021-07-12 [JDK11]
     * @version 2021-11-29
     * @author Oliven C. Barcelon
     * @see Reference:
     * <a href="https://www.baeldung.com/java-uuid">Guide to UUID in Java</a>
     */
    public static UUID generateUuid() {
        long most64SigBits = get64MostSignificantBits();
        long least64SigBits = get64LeastSignificantBits();
        
        return new UUID(most64SigBits, least64SigBits);
    }
    
    private static long get64LeastSignificantBits() {
        Random random = new Random();
        long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
        long variant3BitFlag = 0x8000000000000000L;
        return random63BitLong + variant3BitFlag;
    }
    
    private static long get64MostSignificantBits() {
        LocalDateTime start = LocalDateTime.of(1582, 10, 15, 0, 0, 0);
        Duration duration = Duration.between(start, LocalDateTime.now());
        long seconds = duration.getSeconds();
        long nanos = duration.getNano();
        long timeForUuidIn100Nanos = seconds * 10000000 + nanos * 100;
        long least12SignificantBitOfTime = (timeForUuidIn100Nanos & 0x000000000000FFFFL) >> 4;
        long version = 1 << 12;
        return (timeForUuidIn100Nanos & 0xFFFFFFFFFFFF0000L) + version + least12SignificantBitOfTime;
    }
    // JWT Token
    private static String generateHeader() {
        Map<String, Object> header = new LinkedHashMap<>();
        header.put("alg", "HS256"); // HMAC + SHA256
        header.put("typ", "JWT");
        String parseHeader = MapperUtility.toJson(header).replaceAll("\\s", "");
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(parseHeader.getBytes(StandardCharsets.UTF_8));
    }
    
    private static String generatePayload(String subject) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("sub", subject); // User ID (subject)
        payload.put("aud", "user"); // Role (audience)
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        now = now.plusMinutes(5);
        long epochMilli = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        /*LocalDateTime instant = Instant.ofEpochMilli(epochMilli).atZone(ZoneId.systemDefault()).toLocalDateTime();
        log.info("Is Equal: " + now.isEqual(instant));*/
        payload.put("exp", epochMilli); // Token Expiry Timestamp (expiry)
        String parsePayload = MapperUtility.toJson(payload).replaceAll("\\s", "");
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(parsePayload.getBytes(StandardCharsets.UTF_8));
    }
    
    public static String hmacSha256(String data, String secret) {
        try {
            //MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = secret.getBytes(StandardCharsets.UTF_8); //digest.digest(secret.getBytes(StandardCharsets.UTF_8));
            
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
            sha256Hmac.init(secretKey);
            
            byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            
            return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(signedBytes);
        }
        catch(NoSuchAlgorithmException | InvalidKeyException ex) {
            return null;
        }
    }
    
    public static String generateJws(String subject) {
        String body = generateHeader() + "." + generatePayload(subject);
        String signature = hmacSha256(body, "olivenbarcelon");
        return body + "." + signature;
    }
    
    public static String getSubjectFromToken(String token) {
        String split[] = token.split("\\.");
        validateToken(split[0] + "." + split[1], split[2]);
        String base64EncodedBody = split[1];
        String decodedString = decode(base64EncodedBody);
        Map<String, Object> map = MapperUtility.toMap(decodedString);
        return map.get("sub").toString();
    }
    
    private static boolean isExpired(long epochMilli) {
        LocalDateTime expireAt = Instant.ofEpochMilli(epochMilli).atZone(ZoneId.systemDefault()).toLocalDateTime();
        //log.info("Expiration Date: " + expireAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        LocalDateTime currentDate = LocalDateTime.now(ZoneId.systemDefault());
        return currentDate.isAfter(expireAt);
    }
    
    public static boolean validateToken(String token) {
        String split[] = token.split("\\.");
        boolean validate = validateToken(split[0] + "." + split[1], split[2]);
        String base64EncodedBody = split[1];
        String decodedString = decode(base64EncodedBody);
        Map<String, Object> map = MapperUtility.toMap(decodedString);
        boolean isExpired = isExpired(Long.valueOf(map.get("exp").toString()));
        return validate && !isExpired;
    }
    
    private static boolean validateToken(String body, String signature) {
        return hmacSha256(body, "olivenbarcelon").equals(signature);
    }
    
    public static String fillMD5(String md5){
        return md5.length() == 32 ? md5 : fillMD5("0" + md5);
    }
    
    public static String convertMD5(String inStr){
        char[] a = inStr.toCharArray();
        for(int i = 0;i < a.length;i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }
}
