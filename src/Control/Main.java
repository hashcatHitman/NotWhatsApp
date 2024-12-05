package Control;

import Model.Crypto.Cipher;
import Model.Crypto.KeyManagerAES128DH;
import Model.Crypto.KeyManagerShiftDH;
import Model.Crypto.ShiftCipher;
import View.ServerView;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * <p>
 * A class containing the main entrypoint of the program.
 * </p>
 *
 * @author Paige G
 * @author Sam K
 * @version 12/2/2024
 * @implNote Currently acts as a sort of scratch paper for testing development
 * code. This is liable to change!
 */
public class Main {
// Methods

    /**
     * <p>
     * The main entrypoint of the program.
     * </p>
     *
     * @param args The arguments passed to the program, as an array of Strings.
     */
    public static void main(String[] args)
    throws NoSuchAlgorithmException, InvalidKeyException {
        new ServerView();

        /*
        Example Cipher usage.
        TODO: Adapter/Facade for Cipher/String interactions? Client should
         not need to convert between Strings and byte arrays itself.
         */
        String message = "Hello World! My name is John Doe, and I hate " +
                         "six-sided dice! I'm 47 years old, by the by.";

        Cipher cipher = new ShiftCipher();

        // Using KeyManager to get a key
        KeyManagerShiftDH alice = new KeyManagerShiftDH();
        KeyManagerShiftDH bob = new KeyManagerShiftDH();
        byte[] aliceKey = alice.getSharedSecret(bob.getPublicKey());
        byte[] bobKey = bob.getSharedSecret(alice.getPublicKey());

        String ciphertext = new String(
                cipher.encrypt(message.getBytes(StandardCharsets.UTF_8),
                               aliceKey), StandardCharsets.UTF_8);
        String cleartext = new String(
                cipher.decrypt(ciphertext.getBytes(StandardCharsets.UTF_8),
                               bobKey), StandardCharsets.UTF_8);

        System.out.println("ORIGINAL:\t" + message);
        System.out.println("ENCRYPTED:\t" + ciphertext);
        System.out.println("DECRYPTED:\t" + cleartext);

        /*
        Example KeyManager usage. Use ObjectOutputStream and
        ObjectInputStream to send PublicKeys over the socket chat.
         */
        KeyManagerAES128DH clientA = new KeyManagerAES128DH();
        KeyManagerAES128DH clientB = new KeyManagerAES128DH();
        KeyManagerAES128DH server = new KeyManagerAES128DH();

        // SecretA is ClientA <-> Server
        byte[] secretA1 = clientA.getSharedSecret(server.getPublicKey());
        byte[] secretA2 = server.getSharedSecret(clientA.getPublicKey());

        // SecretB is ClientB <-> Server
        byte[] secretB1 = clientB.getSharedSecret(server.getPublicKey());
        byte[] secretB2 = server.getSharedSecret(clientB.getPublicKey());

        // SecretC is ClientA <-> ClientB
        byte[] secretC1 = clientA.getSharedSecret(clientB.getPublicKey());
        byte[] secretC2 = clientB.getSharedSecret(clientA.getPublicKey());

        System.out.println(
                "SecretA identical?:\t" + Arrays.equals(secretA1, secretA2));
        System.out.println(
                "SecretB identical?:\t" + Arrays.equals(secretB1, secretB2));
        System.out.println(
                "SecretC identical?:\t" + Arrays.equals(secretC1, secretC2));
    }
}
