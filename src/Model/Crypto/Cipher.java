package Model.Crypto;

/**
 * <p>
 * A Cipher is capable of encrypting cleartext and decrypting ciphertext, given
 * the cleartext/ciphertext and key.
 * </p>
 *
 * @author Sam K
 * @version 12/2/2024
 */
public interface Cipher {
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
     */
    byte[] decrypt(byte[] ciphertext, byte[] key);

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
     */
    byte[] encrypt(byte[] cleartext, byte[] key);
}
