package Model.Crypto;

import javax.crypto.KeyAgreement;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * <p>
 * A KeyManager acts as a facade that simplifies the process of generating a
 * shared secret for a symmetric Cipher using a public-key key agreement scheme.
 * KeyManagerAES128DH uses a 512-bit Diffie-Hellman key agreement and MD5
 * hashing to generate a 128 bit shared secret, suitable for use with AES-128.
 * </p>
 *
 * @author Sam K
 * @version 12/7/2024
 */
public class KeyManagerAES128DH extends KeyManager implements Cloneable {
// Constructors

    /**
     * <p>
     * Constructs a new KeyManagerAES128DH. This generates a new KeyPair.
     * </p>
     *
     * @throws NoSuchAlgorithmException If no Provider supports a
     *                                  KeyPairGeneratorSpi implementation for
     *                                  the specified algorithm.
     */
    public KeyManagerAES128DH() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
        keyPairGenerator.initialize(512);
        this.setKeyPair(keyPairGenerator.generateKeyPair());
    }

// Methods

    /**
     * <p>
     * Creates and returns a copy of this KeyManagerAES128DH.
     * </p>
     *
     * @return A copy of this KeyManagerAES128DH.
     */
    @Override
    public KeyManagerAES128DH clone() {
        return (KeyManagerAES128DH) super.clone();
    }

    /**
     * <p>
     * Uses Diffie-Hellman to agree upon a 512-bit shared secret, then uses MD5
     * to reduce this to a 128-bit shared secret, suitable for use with
     * AES-128.
     * </p>
     *
     * @param secondPartyPublicKey The other KeyManager's PublicKey.
     *
     * @return The shared secret, as an array of bytes.
     *
     * @throws NoSuchAlgorithmException If no Provider supports an
     *                                  implementation for one or more of the
     *                                  specified algorithms.
     * @throws InvalidKeyException      If the given PublicKey is inappropriate
     *                                  for this key agreement, e. g., is of the
     *                                  wrong type or has an incompatible
     *                                  algorithm type.
     */
    @Override
    public byte[] getSharedKey(PublicKey secondPartyPublicKey)
    throws NoSuchAlgorithmException, InvalidKeyException {
        KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
        keyAgreement.init(this.getPrivateKey());
        keyAgreement.doPhase(secondPartyPublicKey, true);

        MessageDigest hash = MessageDigest.getInstance("MD5");
        return hash.digest(keyAgreement.generateSecret());
    }
}
