package j2ee_project.service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Manage hashing functions
 */
public class HashService {

    /**
     * Generate the password hash
     *
     * @param password the origin password
     * @return the hashed password
     * @throws NoSuchAlgorithmException if no Provider supports a SecureRandomSpi implementation for the specified algorithm
     * @throws InvalidKeySpecException  the exception for invalid key specifications.
     */
    public static String generatePasswordHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * Get the salt used in the hashed password generation
     *
     * @return the generated salt
     * @throws NoSuchAlgorithmException if no Provider supports a SecureRandomSpi implementation for the specified algorithm
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * Convert byte to hexadecimal format
     *
     * @param array array of byte
     * @return the converted hexadecimal
     */
    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    /**
     * Check if a password corresponds with a hashed password
     *
     * @param originalPassword the password to check
     * @param storedPassword   the hashed password
     * @return true or false according to the passwords correspond
     * @throws NoSuchAlgorithmException if no Provider supports a SecureRandomSpi implementation for the specified algorithm
     * @throws InvalidKeySpecException  the exception for invalid key specifications.
     */
    public static boolean validatePassword(String originalPassword, String storedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);

        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),
                salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    /**
     * Convert hexadecimal format to byte array
     *
     * @param hex hexadecimal string
     * @return the converted array of byte
     */
    private static byte[] fromHex(String hex)
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    /**
     * Generate a token for a forgotten password query
     *
     * @param length the length of the token to be generated
     * @return the token
     */
    public static String generateToken(int length) {
        SecureRandom secureRandom;
        byte[] tokenBytes = new byte[length];

        try {
            secureRandom = SecureRandom.getInstanceStrong(); // Utilisation d'un algorithme sécurisé
            secureRandom.nextBytes(tokenBytes); // Remplissage du tableau de bytes avec des données aléatoires
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Gérer l'exception comme nécessaire
        }

        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

}

