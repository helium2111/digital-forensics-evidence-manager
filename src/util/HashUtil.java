package util;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

public final class HashUtil {
    private HashUtil() {}

    public static String sha256(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) return "ERROR: FILE_NOT_FOUND";
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (InputStream input = Files.newInputStream(path)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = input.read(buffer)) != -1) {
                    digest.update(buffer, 0, read);
                }
            }
            StringBuilder result = new StringBuilder();
            for (byte b : digest.digest())
                result.append(String.format("%02x", b));
            return result.toString();
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}
