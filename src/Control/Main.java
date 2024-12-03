package Control;

import Model.Crypto.Cipher;
import Model.Crypto.ShiftCipher;
import View.View;

import java.nio.charset.StandardCharsets;

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
    public static void main(String[] args) {
        new View();

        /*
        Example Cipher usage.
        TODO: Adapter/Facade for Cipher/String interactions? Client should
         not need to convert between Strings and byte arrays itself.
         */
        String message = "Hello World! My name is John Doe, and I hate " +
                         "six-sided dice! I'm 47 years old, by the by.";

        Cipher cipher = new ShiftCipher();
        byte[] key = {14};
        String ciphertext = new String(
                cipher.encrypt(message.getBytes(StandardCharsets.UTF_8), key),
                StandardCharsets.UTF_8);
        String cleartext = new String(
                cipher.decrypt(ciphertext.getBytes(StandardCharsets.UTF_8),
                               key), StandardCharsets.UTF_8);

        System.out.println("ORIGINAL:\t" + message);
        System.out.println("ENCRYPTED:\t" + ciphertext);
        System.out.println("DECRYPTED:\t" + cleartext);
    }
}
