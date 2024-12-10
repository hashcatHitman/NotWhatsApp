package Model.Crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * <p>
 * Implements AES-128, in Galois-Counter-Mode with no padding.
 * </p>
 *
 * @author Sam K
 * @version 12/9/2024
 */
public class AES128 implements Model.Crypto.Cipher {
// Attributes

    /**
     * <p>
     * The algorithm to use, as a String.
     * </p>
     */
    private static final String ALGORITHM = "AES/GCM/NoPadding";

    /**
     * <p>
     * The IV length in bytes, as an integer.
     * </p>
     */
    private static final int IV_LENGTH = 12;

    /**
     * <p>
     * The key length in bytes, as an integer.
     * </p>
     */
    private static final int KEY_LENGTH = 16;

    /**
     * <p>
     * The authentication tag length in bits, as an integer.
     * </p>
     */
    private static final int TAG_LENGTH = 128;

// Methods

    /**
     * <p>
     * Takes a ciphertext and key and attempts to decrypt the ciphertext.
     * </p>
     *
     * @param ciphertext The ciphertext to decrypt, as an array of bytes.
     * @param key        The key to decrypt the ciphertext with, as an array of
     *                   bytes.
     *
     * @return The resulting cleartext, as an array of bytes.
     *
     * @throws NoSuchPaddingException             When a particular padding
     *                                            mechanism is requested but is
     *                                            not available in the
     *                                            environment.
     * @throws NoSuchAlgorithmException           When a particular
     *                                            cryptographic algorithm is
     *                                            requested but is not available
     *                                            in the environment.
     * @throws InvalidKeyException                When invalid keys are
     *                                            provided.
     * @throws IllegalBlockSizeException          When the length of data
     *                                            provided to a block cipher is
     *                                            incorrect, i. e., does not
     *                                            match the block size of the
     *                                            cipher.
     * @throws BadPaddingException                When a particular padding
     *                                            mechanism is expected for the
     *                                            input data but the data is not
     *                                            padded properly.
     * @throws InvalidAlgorithmParameterException When invalid or inappropriate
     *                                            algorithm parameters are
     *                                            provided.
     */
    @Override
    public byte[] decrypt(byte[] ciphertext, byte[] key)
    throws NoSuchPaddingException, NoSuchAlgorithmException,
           InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
           InvalidAlgorithmParameterException {
        // Extract the IV from the ciphertext
        byte[] iv = Arrays.copyOfRange(ciphertext, 0, AES128.IV_LENGTH);
        byte[] justCiphertext = Arrays.copyOfRange(ciphertext, AES128.IV_LENGTH,
                                                   ciphertext.length);

        // Create a proper key
        SecretKey aesKey = new SecretKeySpec(key, 0, AES128.KEY_LENGTH, "AES");

        // Initialize the cipher
        Cipher cipher = Cipher.getInstance(AES128.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, aesKey,
                    new GCMParameterSpec(AES128.TAG_LENGTH, iv));

        // Decrypt
        return cipher.doFinal(justCiphertext);
    }

    /**
     * <p>
     * Takes a cleartext and key and attempts to encrypt the cleartext.
     * </p>
     *
     * @param cleartext The cleartext to encrypt, as an array of bytes.
     * @param key       The key to encrypt the cleartext with, as an array of
     *                  bytes.
     *
     * @return The resulting ciphertext, as an array of bytes.
     *
     * @throws NoSuchPaddingException             When a particular padding
     *                                            mechanism is requested but is
     *                                            not available in the
     *                                            environment.
     * @throws NoSuchAlgorithmException           When a particular
     *                                            cryptographic algorithm is
     *                                            requested but is not available
     *                                            in the environment.
     * @throws InvalidKeyException                When invalid keys are
     *                                            provided.
     * @throws IllegalBlockSizeException          When the length of data
     *                                            provided to a block cipher is
     *                                            incorrect, i. e., does not
     *                                            match the block size of the
     *                                            cipher.
     * @throws BadPaddingException                When a particular padding
     *                                            mechanism is expected for the
     *                                            input data but the data is not
     *                                            padded properly.
     * @throws InvalidAlgorithmParameterException When invalid or inappropriate
     *                                            algorithm parameters are
     *                                            provided.
     */
    @Override
    public byte[] encrypt(byte[] cleartext, byte[] key)
    throws NoSuchPaddingException, NoSuchAlgorithmException,
           InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
           InvalidAlgorithmParameterException {
        // Generate a random iv
        byte[] iv = new byte[AES128.IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        // Create a proper key
        SecretKey aesKey = new SecretKeySpec(key, 0, AES128.KEY_LENGTH, "AES");

        // Initialize the cipher
        Cipher cipher = Cipher.getInstance(AES128.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, aesKey,
                    new GCMParameterSpec(AES128.TAG_LENGTH, iv));

        // Encrypt
        byte[] ciphertext = cipher.doFinal(cleartext);

        // Append the iv to the ciphertext
        byte[] ciphertextWithIV = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, ciphertextWithIV, 0, iv.length);
        System.arraycopy(ciphertext, 0, ciphertextWithIV, iv.length,
                         ciphertext.length);
        return ciphertextWithIV;
    }
}
