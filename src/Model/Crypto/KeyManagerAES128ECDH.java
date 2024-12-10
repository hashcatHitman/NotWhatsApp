package Model.Crypto;

import javax.crypto.KeyAgreement;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;

/**
 * <p>
 * A KeyManager acts as a facade that simplifies the process of generating a
 * shared secret for a symmetric Cipher using a public-key key agreement scheme.
 * KeyManagerAES128ECDH uses the secp256r1 Elliptic-Curve Diffie-Hellman key
 * agreement to generate a 256 bit shared secret, the first 128 bits of which
 * may be used with AES128.
 * </p>
 *
 * @author Sam K
 * @version 12/9/2024
 */
public class KeyManagerAES128ECDH extends KeyManager implements Cloneable {
// Constructors

    /**
     * <p>
     * Constructs a new KeyManagerAES128ECDH. This generates a new KeyPair.
     * </p>
     *
     * @throws NoSuchAlgorithmException           If no Provider supports an
     *                                            implementation for one or more
     *                                            of the specified algorithms.
     * @throws InvalidAlgorithmParameterException If the given parameters are
     *                                            inappropriate for the
     *                                            underlying key pair
     *                                            generator.
     */
    public KeyManagerAES128ECDH()
    throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
        this.setKeyPair(keyPairGenerator.generateKeyPair());
    }

// Methods

    /**
     * <p>
     * Creates and returns a copy of this KeyManagerAES128ECDH.
     * </p>
     *
     * @return A copy of this KeyManagerAES128ECDH.
     */
    @Override
    public KeyManagerAES128ECDH clone() {
        return (KeyManagerAES128ECDH) super.clone();
    }

    /**
     * <p>
     * Uses Elliptic-Curve Diffie-Hellman over the secp256r1 curve to agree upon
     * a 256-bit shared secret, the first 128 bits of which are suitable for use
     * with AES128.
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
        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH");
        keyAgreement.init(this.getPrivateKey());
        keyAgreement.doPhase(secondPartyPublicKey, true);
        return keyAgreement.generateSecret();
    }
}
