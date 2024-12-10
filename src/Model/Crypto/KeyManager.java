package Model.Crypto;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * <p>
 * A KeyManager acts as a facade that simplifies the process of generating a
 * shared secret for a symmetric Cipher using a public-key key agreement
 * scheme.
 * </p>
 *
 * @author Sam K
 * @version 12/9/2024
 */
public abstract class KeyManager implements Cloneable {
// Attributes

    /**
     * <p>
     * The Private and Public KeyPair of this KeyManager.
     * </p>
     */
    protected KeyPair keyPair;

// Getters and Setters

    /**
     * <p>
     * Gets this KeyManager's PublicKey.
     * </p>
     *
     * @return This KeyManager's PublicKey.
     */
    public PublicKey getPublicKey() {
        return this.getKeyPair().getPublic();
    }

    /**
     * <p>
     * Gets the Private and Public KeyPair of this KeyManager.
     * </p>
     *
     * @return The Private and Public KeyPair of this KeyManager.
     */
    protected KeyPair getKeyPair() {
        return this.keyPair;
    }

    /**
     * <p>
     * Sets this KeyManager's Private and Public KeyPair to the given KeyPair.
     * </p>
     *
     * @param keyPair The Private and Public KeyPair to give to this
     *                KeyManager.
     */
    protected void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    /**
     * <p>
     * Gets this KeyManager's PrivateKey.
     * </p>
     *
     * @return This KeyManager's PrivateKey.
     */
    protected PrivateKey getPrivateKey() {
        return this.getKeyPair().getPrivate();
    }

// Methods

    /**
     * <p>
     * Uses a KeyAgreement scheme to derive a shared secret using this
     * KeyManager's PrivateKey and another KeyManager's PublicKey.
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
    public abstract byte[] getSharedKey(PublicKey secondPartyPublicKey)
    throws NoSuchAlgorithmException, InvalidKeyException;

    /**
     * <p>
     * Creates and returns a copy of this KeyManager.
     * </p>
     *
     * @return A copy of this KeyManager.
     */
    @Override
    public KeyManager clone() {
        try {
            KeyManager clone = (KeyManager) super.clone();
            clone.setKeyPair(
                    new KeyPair(this.getPublicKey(), this.getPrivateKey()));
            return clone;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }
}
