package Model.Crypto;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * Implements a ShiftCipher. Ignores characters outside a-z and A-Z. Not even
 * slightly secure - more of a proof-of-concept/demonstration than anything.
 * </p>
 *
 * @author Sam K
 * @version 12/2/2024
 */
public class ShiftCipher implements Cipher {
// Methods

    /**
     * <p>
     * Takes a ciphertext and key and attempts to decrypt the ciphertext.
     * </p>
     *
     * @param ciphertext The ciphertext to decrypt, as an array of bytes. Should
     *                   be in UTF-8.
     * @param key        The key to decrypt the ciphertext with, as an array of
     *                   bytes. Should contain a single byte of value 1 through
     *                   25.
     *
     * @return The resulting cleartext, as an array of bytes. Conforms to the
     * UTF-8 standard.
     */
    @Override
    public byte[] decrypt(byte[] ciphertext, byte[] key) {
        this.validateKey(key);
        return this.shift(ciphertext, (byte) -key[0]);
    }

    /**
     * <p>
     * Takes a cleartext and key and attempts to encrypt the cleartext.
     * </p>
     *
     * @param cleartext The cleartext to encrypt, as an array of bytes. Should
     *                  be in UTF-8.
     * @param key       The key to encrypt the cleartext with, as an array of
     *                  bytes. Should contain a single byte of value 1 through
     *                  25.
     *
     * @return The resulting ciphertext, as an array of bytes. Conforms to the
     * UTF-8 standard.
     */
    @Override
    public byte[] encrypt(byte[] cleartext, byte[] key) {
        this.validateKey(key);
        return this.shift(cleartext, key[0]);
    }

    /**
     * <p>
     * Handles the bulk of the logic of a ShiftCipher, as it is nearly identical
     * between encryption and decryption.
     * </p>
     *
     * @param message The message to shift, as an array of bytes. Should be in
     *                UTF-8.
     * @param key     The key to shift the message with, as a byte. Should be of
     *                value -25 through 25, excluding 0.
     *
     * @return The shifted message, as an array of bytes. Conforms to the UTF-8
     * standard.
     */
    private byte[] shift(byte[] message, byte key) {
        char[] characters =
                new String(message, StandardCharsets.UTF_8).toCharArray();

        for (int characterIndex = 0; characterIndex < characters.length;
             characterIndex++) {
            char offset;
            char character = characters[characterIndex];
            boolean isLowercaseLetter = character >= 'a' && character <= 'z';
            boolean isUppercaseLetter = character >= 'A' && character <= 'Z';
            if (isLowercaseLetter || isUppercaseLetter) {
                if (isLowercaseLetter) {
                    offset = 'a';
                } else {
                    offset = 'A';
                }
                characters[characterIndex] =
                        (char) (Math.floorMod((character - offset) + key, 26) +
                                offset);
            }
        }

        return new String(characters).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * <p>
     * Ensures the provided key is a valid ShiftCipher key. A valid ShiftCipher
     * key should contain a single byte of value 1 through 25, inclusive. We
     * don't allow 0 or 26, as these both result in no actual change.
     * </p>
     *
     * @param key The key to validate, as an array of bytes.
     */
    private void validateKey(byte[] key) {
        if (key.length == 0) {
            throw new IllegalArgumentException(
                    "ShiftCipher key must be a single byte of value 1 through" +
                    " 25. Given key was empty!");
        } else if (key.length > 1) {
            throw new IllegalArgumentException(
                    "ShiftCipher key must be a single byte of value 1 through" +
                    " 25. Given key was too long!");
        } else if (key[0] < 1) {
            throw new IllegalArgumentException(
                    "ShiftCipher key must be a single byte of value 1 through" +
                    " 25. Given key was less than 1!");
        } else if (key[0] > 25) {
            throw new IllegalArgumentException(
                    "ShiftCipher key must be a single byte of value 1 through" +
                    " 25. Given key was greater than 25!");
        }
    }
}
