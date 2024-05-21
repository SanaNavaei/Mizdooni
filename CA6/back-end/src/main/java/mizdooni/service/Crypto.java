package mizdooni.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Crypto {
    public static String hash(String input) {
        return base64(sha256(input));
    }

    private static byte[] sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String base64(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }
}
