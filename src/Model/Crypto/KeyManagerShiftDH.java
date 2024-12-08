package Model.Crypto;

import javax.crypto.KeyAgreement;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * <p>
 * A KeyManager acts as a facade that simplifies the process of generating a
 * shared secret for a symmetric Cipher using a public-key key agreement scheme.
 * KeyManagerShiftDH uses a 512-bit Diffie-Hellman key agreement to generate a
 * 512 bit shared secret, which we then use to determine a much smaller shared
 * secret suitable for use with a ShiftCipher.
 * </p>
 *
 * @author Sam K
 * @version 12/7/2024
 */
public class KeyManagerShiftDH extends KeyManager implements Cloneable {
// Constructors

    /**
     * <p>
     * Constructs a new KeyManagerShiftDH. This generates a new KeyPair.
     * </p>
     *
     * @throws NoSuchAlgorithmException If no Provider supports a
     *                                  KeyPairGeneratorSpi implementation for
     *                                  the specified algorithm.
     */
    public KeyManagerShiftDH() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
        keyPairGenerator.initialize(512);
        this.setKeyPair(keyPairGenerator.generateKeyPair());
    }

// Methods

    /**
     * <p>
     * Creates and returns a copy of this KeyManagerShiftDH.
     * </p>
     *
     * @return A copy of this KeyManagerShiftDH.
     */
    @Override
    public KeyManagerShiftDH clone() {
        return (KeyManagerShiftDH) super.clone();
    }

    /**
     * <p>
     * Uses Diffie-Hellman to agree upon a 512-bit shared secret, then uses some
     * modular arithmetic to make it a shared secret suitable for use with a
     * ShiftCipher.
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
        byte[] largeSecret = keyAgreement.generateSecret();

        int sum = 0;
        for (byte secretByte : largeSecret) {
            sum += secretByte;
            sum = Math.floorMod(sum, 25);
        }
        sum++;

        return new byte[]{(byte) sum};
    }
}
