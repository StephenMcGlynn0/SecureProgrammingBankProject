package bankproject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtils {

    private PasswordUtils(){

    }

    // ✅ Reuse one SecureRandom instance (Sonar-safe)
    private static final SecureRandom secureRandom = new SecureRandom();

    // Generate a 16-byte salt and return as hex string
    public static String generateSalt() {
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return bytesToHex(salt);
    }

    // Hash password + salt using SHA-512
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(hexToBytes(salt));  // feed the salt in first
            byte[] hashed = md.digest(password.getBytes());
            return bytesToHex(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 not supported", e);
        }
    }

    // just use this i store the bytes as a hex string in the database because its easier.
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // translate the hex back to bytes when coming from the database
    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public static String generateOTP() {
        int otp = 100000 + secureRandom.nextInt(900000); // ensures 6-digit number
        return String.valueOf(otp);
    }
}