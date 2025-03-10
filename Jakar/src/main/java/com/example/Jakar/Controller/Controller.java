package com.example.Jakar.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

@RestController
@RequestMapping("/file")
public class Controller {

    private static final String ALGORITHM = "AES";
    private static final String STORAGE_DIR = "encrypted_files";
    private static final String SALT = "mySalt123";

    static {
        try {
            Files.createDirectories(Paths.get(STORAGE_DIR));
        } catch (Exception e) {
            throw new RuntimeException("Error initializing storage directory", e);
        }
    }

    @PostMapping("/encrypt")
    public String encryptFile(@RequestParam("file") MultipartFile file, @RequestParam("password") String password) throws Exception {
        File encryptedFile = new File(STORAGE_DIR + "/encrypted_" + file.getOriginalFilename());
        processFile(file.getInputStream(), new FileOutputStream(encryptedFile), Cipher.ENCRYPT_MODE, password);
        return "File encrypted: " + encryptedFile.getAbsolutePath();
    }

    @PostMapping("/decrypt")
    public String decryptFile(@RequestParam("file") MultipartFile file, @RequestParam("password") String password) throws Exception {
        File decryptedFile = new File(STORAGE_DIR + "/decrypted_" + file.getOriginalFilename());
        processFile(file.getInputStream(), new FileOutputStream(decryptedFile), Cipher.DECRYPT_MODE, password);
        return "File decrypted: " + decryptedFile.getAbsolutePath();
    }

    private void processFile(InputStream input, OutputStream output, int mode, String password) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(mode, getKeyFromPassword(password));

        byte[] inputBytes = input.readAllBytes();
        byte[] outputBytes = cipher.doFinal(inputBytes);
        output.write(outputBytes);

        input.close();
        output.close();
    }

    private SecretKeySpec getKeyFromPassword(String password) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ALGORITHM);
        return new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
    }
}
