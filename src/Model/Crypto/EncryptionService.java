package Model.Crypto;

import Model.Message;
import Model.Utility.TabInserter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Arrays;

/**
 * <p>
 * An EncryptionService acts as a facade that simplifies the process of
 * establishing end-to-end encryption, encrypting Messages, and decrypting
 * Messages.
 * </p>
 *
 * @author Sam K
 * @version 12/9/2024
 */
public class EncryptionService {
// Attributes

    /**
     * <p>
     * The Cipher to use for encryption and decryption.
     * </p>
     */
    private final Cipher cipher;

    /**
     * <p>
     * The owner's ObjectInputStream, used to receive Objects sent over the
     * network.
     * </p>
     */
    private final ObjectInputStream in;

    /**
     * <p>
     * The owner's KeyManager.
     * </p>
     */
    private final KeyManager myKeys;

    /**
     * <p>
     * The owner's ObjectOutputStream, used to send Objects over the network.
     * </p>
     */
    private final ObjectOutputStream out;

    /**
     * <p>
     * A PublicKey belonging to a Client.
     * <br><br>
     * If the owner of this EncryptionService is a Client, this PublicKey
     * belongs to the other Client.
     * <br><br>
     * If the owner of this EncryptionService is a ServerClientHandler, this
     * PublicKey belongs to the Client it handles.
     * </p>
     */
    private PublicKey clientKey;

    /**
     * <p>
     * A PublicKey belonging to a Server.
     * <br><br>
     * If the owner of this EncryptionService is a Client, this PublicKey
     * belongs to the Server.
     * <br><br>
     * If the owner of this EncryptionService is a ServerClientHandler, this
     * PublicKey is null.
     * </p>
     */
    private PublicKey serverKey;

// Getters and Setters

    /**
     * <p>
     * Gets a PublicKey belonging to a Client.
     * <br><br>
     * If the owner of this EncryptionService is a Client, this PublicKey
     * belongs to the other Client.
     * <br><br>
     * If the owner of this EncryptionService is a ServerClientHandler, this
     * PublicKey belongs to the Client it handles.
     * </p>
     *
     * @return A PublicKey belonging to a Client.
     */
    public PublicKey getClientKey() {
        return this.clientKey;
    }

    /**
     * <p>
     * Sets a PublicKey belonging to a Client.
     * <br><br>
     * If the owner of this EncryptionService is a Client, this PublicKey should
     * belong to the other Client.
     * <br><br>
     * If the owner of this EncryptionService is a ServerClientHandler, this
     * PublicKey should belong to the Client it handles.
     * </p>
     *
     * @param clientKey A PublicKey belonging to a Client.
     */
    private void setClientKey(PublicKey clientKey) {
        this.clientKey = clientKey;
    }

    /**
     * <p>
     * Gets the Cipher to use for encryption and decryption.
     * </p>
     *
     * @return The Cipher to use for encryption and decryption.
     */
    private Cipher getCipher() {
        return this.cipher;
    }

    /**
     * <p>
     * Gets this EncryptionService's owner's ObjectInputStream, used to receive
     * Objects sent over the network.
     * </p>
     *
     * @return The ObjectInputStream, used to receive Objects sent over the
     * network.
     */
    private ObjectInputStream getIn() {
        return this.in;
    }

    /**
     * <p>
     * Gets the owner's KeyManager.
     * </p>
     *
     * @return The owner's KeyManager.
     */
    private KeyManager getMyKeys() {
        return this.myKeys;
    }

    /**
     * <p>
     * Gets this EncryptionService's owner's ObjectOutputStream, used to send
     * Objects over the network.
     * </p>
     *
     * @return The ObjectOutputStream, used to send Objects over the network.
     */
    private ObjectOutputStream getOut() {
        return this.out;
    }

    /**
     * <p>
     * Gets a PublicKey belonging to a Server.
     * <br><br>
     * If the owner of this EncryptionService is a Client, this PublicKey
     * belongs to the Server.
     * <br><br>
     * If the owner of this EncryptionService is a ServerClientHandler, this
     * PublicKey is null.
     * </p>
     *
     * @return A PublicKey belonging to a Server, or null.
     */
    private PublicKey getServerKey() {
        return this.serverKey;
    }

    /**
     * <p>
     * Sets a PublicKey belonging to a Server.
     * <br><br>
     * If the owner of this EncryptionService is a Client, this PublicKey should
     * belong to the Server.
     * <br><br>
     * If the owner of this EncryptionService is a ServerClientHandler, this
     * PublicKey should be null.
     * </p>
     *
     * @param serverKey A PublicKey belonging to a Server, or null.
     */
    private void setServerKey(PublicKey serverKey) {
        this.serverKey = serverKey;
    }

// Constructors

    /**
     * <p>
     * Constructs a new EncryptionService.
     * </p>
     *
     * @param keyManager The owner's KeyManager.
     * @param cipher     The Cipher to use for encryption and decryption.
     * @param in         The owner's ObjectInputStream, used to receive Objects
     *                   sent over the network.
     * @param out        The owner's ObjectOutputStream, used to send Objects
     *                   over the network.
     */
    public EncryptionService(KeyManager keyManager, Cipher cipher,
                             ObjectInputStream in, ObjectOutputStream out) {
        this.cipher = cipher;
        this.myKeys = keyManager;
        this.in = in;
        this.out = out;
        this.setClientKey(null);
        this.setServerKey(null);
    }

// Methods

    /**
     * <p>
     * Returns a decrypted Message. For use by Clients.
     * </p>
     *
     * @param message The Message to decrypt.
     *
     * @return A decrypted clone of the original Message.
     */
    public Message decrypt(Message message) {
        // Clone the input
        Message clone = message.clone();

        // Start building a debug log
        StringBuilder log = new StringBuilder();
        try {
            log.append(Thread.currentThread().getName()).append("\n\tKeyAB:\t")
               .append(this.getMyKeys().getSharedKey(this.getClientKey())[0])
               .append("\n\tKey?S:\t")
               .append(this.getMyKeys().getSharedKey(this.getServerKey())[0])
               .append("\n\tCiphertext:\t").append(clone);

            // Decrypt with the Client-Server key first and log
            /*
            @formatter:off
             */
            byte[] half = this.getCipher()
                              .decrypt(clone.getContent(),
                                       this.getMyKeys()
                                           .getSharedKey(
                                                   this.getServerKey()));
            /*
            @formatter:on
             */
            log.append("\n\tDecrypted with Key?S:\t")
               .append(new String(half, StandardCharsets.UTF_8));

            // Decrypt the result with the Client-Client key and log
            /*
            @formatter:off
             */
            byte[] clear = this.getCipher()
                               .decrypt(half, this.getMyKeys()
                                                  .getSharedKey(
                                                          this.getClientKey()));
            /*
            @formatter:on
             */
            log.append("\n\tDecrypted with KeyAB:\t")
               .append(new String(clear, StandardCharsets.UTF_8)).append("\n");

            // Print the log, update the text in the clone, and return the clone
            System.out.println(log);
            clone.setContent(clear);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return clone;
    }

    /**
     * <p>
     * Returns a decrypted Message. For use by the Server.
     * </p>
     *
     * @param message The Message to decrypt.
     *
     * @return A decrypted clone of the original Message.
     */
    public Message decryptServer(Message message) {
        // Clone the input
        Message clone = message.clone();
        try {

            // Start building a debug log
            StringBuilder log = new StringBuilder();
            log.append(Thread.currentThread().getName()).append("\n\tKey?S:\t")
               .append(this.getMyKeys().getSharedKey(this.getClientKey())[0])
               .append("\n\tCiphertext:\t").append(clone);

            // Decrypt with the Client-Server key and log
            /*
            @formatter:off
             */
            byte[] clear = this.getCipher()
                               .decrypt(clone.getContent(),
                                        this.getMyKeys()
                                            .getSharedKey(
                                                    this.getClientKey()));
            /*
            @formatter:on
             */
            log.append("\n\tDecrypted with Key?S:\t")
               .append(new String(clear, StandardCharsets.UTF_8));

            // Print the log, update the text in the clone, and return the clone
            System.out.println(log);
            clone.setContent(clear);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return clone;
    }

    /**
     * <p>
     * Returns an encrypted Message. For use by Clients.
     * </p>
     *
     * @param message The Message to encrypt.
     *
     * @return An encrypted clone of the original Message.
     */
    public Message encrypt(Message message) {
        // Clone the input
        Message clone = message.clone();
        try {
            // Start building a debug log
            StringBuilder log = new StringBuilder();
            log.append(Thread.currentThread().getName()).append("\n\tKeyAB:\t")
               .append(this.getMyKeys().getSharedKey(this.getClientKey())[0])
               .append("\n\tKey?S:\t")
               .append(this.getMyKeys().getSharedKey(this.getServerKey())[0])
               .append("\n\tCleartext:\t").append(clone);

            // Encrypt with the Client-Client key and log
            /*
            @formatter:off
             */
            byte[] half = this.getCipher()
                              .encrypt(clone.getContent(),
                                       this.getMyKeys()
                                           .getSharedKey(
                                                   this.getClientKey()));
            /*
            @formatter:on
             */
            log.append("\n\tEncrypted with KeyAB:\t")
               .append(new String(half, StandardCharsets.UTF_8));

            // Encrypt with the Client-Server key and log
            /*
            @formatter:off
             */
            byte[] ciphered = this.getCipher()
                                  .encrypt(half,
                                           this.getMyKeys()
                                               .getSharedKey(
                                                       this.getServerKey()));
            /*
            @formatter:on
             */
            log.append("\n\tEncrypted with Key?S:\t")
               .append(new String(ciphered, StandardCharsets.UTF_8))
               .append("\n");

            // Print the log, update the text in the clone, and return the clone
            System.out.println(log);
            clone.setContent(ciphered);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return clone;
    }

    /**
     * <p>
     * Returns an encrypted Message. For use by the Server.
     * </p>
     *
     * @param message The Message to encrypt.
     *
     * @return An encrypted clone of the original Message.
     */
    public Message encryptServer(Message message) {
        // Clone the input
        Message clone = message.clone();
        try {
            // Start building a debug log
            StringBuilder log = new StringBuilder();
            log.append(Thread.currentThread().getName()).append("\n\tKey?S:\t")
               .append(this.getMyKeys().getSharedKey(this.getClientKey())[0])
               .append("\n\tCleartext:\t").append(clone);

            // Encrypt with the Client-Server key and log
            /*
            @formatter:off
             */
            byte[] ciphered = this.getCipher()
                                  .encrypt(clone.getContent(),
                                           this.getMyKeys()
                                               .getSharedKey(
                                                       this.getClientKey()));
            /*
            @formatter:on
             */
            log.append("\n\tEncrypted with Key?S:\t")
               .append(new String(ciphered, StandardCharsets.UTF_8))
               .append("\n");

            // Print the log, update the text in the clone, and return the clone
            System.out.println(log);
            clone.setContent(ciphered);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return clone;
    }

    /**
     * <p>
     * Attempts to exchange PublicKeys to establish end-to-end encryption. For
     * use by Clients.
     * </p>
     *
     * @throws IOException            Any of the usual Input/Output related
     *                                exceptions.
     * @throws ClassNotFoundException If the Class of a serialized object cannot
     *                                be found.
     */
    public void establishSecret() throws IOException, ClassNotFoundException {
        // Start building a log
        StringBuilder log = new StringBuilder();
        log.append(Thread.currentThread().getName())
           .append("\n\tMy Public Key:\n").append(TabInserter.insertTabs(
                   this.getMyKeys().getPublicKey().toString()));

        // While we're missing a key and have attempts left...
        int attempts = 0;
        while (this.getServerKey() == null ||
               this.getClientKey() == null && (attempts < 3)) {

            // Send our PublicKey
            this.getOut().writeObject(this.getMyKeys().getPublicKey());
            this.getOut().flush();

            // Read an Object
            Object object = this.getIn().readObject();
            if (object instanceof PublicKey publicKey) {
                // If it's a PublicKey...
                if (keysDiffer(publicKey, this.getMyKeys().getPublicKey())) {
                    // And it isn't our own...
                    if (this.getServerKey() == null) {
                        // If we're missing the Server key, this should be it!
                        this.setServerKey(publicKey);
                        log.append("\n\n\tServer's Key:\n")
                           .append(TabInserter.insertTabs(
                                   this.getServerKey().toString(), 2));

                    } else if (keysDiffer(publicKey, this.getServerKey()) &&
                               this.getClientKey() == null) {
                        // Else, if it's different from the Server key, and we
                        // still need the other Client's key, this should be it!
                        this.setClientKey(publicKey);
                        log.append("\n\n\tOther Client's Key:\n")
                           .append(TabInserter.insertTabs(
                                   this.getClientKey().toString(), 2));

                    } else {
                        // Else, we found a weird key!
                        log.append("\n\n\tDEBUG - Found Problematic Key:");
                        log.append("\n\t\tFound Public Key:\n")
                           .append(TabInserter.insertTabs(publicKey.toString(),
                                                          3))
                           .append("\n\t\tFound Client Public Key:\n")
                           .append(TabInserter.insertTabs(
                                   this.getClientKey().toString(), 3))
                           .append("\n\t\tFound Server Public Key:\n")
                           .append(TabInserter.insertTabs(
                                   this.getServerKey().toString(), 3))
                           .append("\n\t\tMy Public Key:\n")
                           .append(TabInserter.insertTabs(
                                   this.getMyKeys().getPublicKey().toString(),
                                   3));
                    }
                }
            }
            attempts++;
        }

        // If we exit the loop and exceeded out attempt limit, log the failure.
        if (attempts >= 3) {
            log.append("\n\n\tThree failed key retrievals.\n");
        } else {
            // Else, we should be successful!
            log.append("\n\n\tSuccessful exchange!\n");
        }

        // Print the log
        System.out.println(log);
    }

    /**
     * <p>
     * Attempts to exchange PublicKeys to establish end-to-end encryption. For
     * use by the Server.
     * </p>
     *
     * @throws IOException            Any of the usual Input/Output related
     *                                exceptions.
     * @throws ClassNotFoundException If the Class of a serialized object cannot
     *                                be found.
     */
    public void establishSecretServer()
    throws IOException, ClassNotFoundException {
        // Start building a log
        StringBuilder log = new StringBuilder();
        log.append(Thread.currentThread().getName()).append("\n\tMy Public Key")
           .append(":\n").append(TabInserter.insertTabs(
                   this.getMyKeys().getPublicKey().toString(), 2));

        // While we're missing a key and have attempts left...
        int attempts = 0;
        while (this.getClientKey() == null && (attempts < 3)) {

            // Send our PublicKey
            this.getOut().writeObject(this.getMyKeys().getPublicKey());
            this.getOut().flush();

            // Read an Object
            Object object = this.getIn().readObject();
            if (object instanceof PublicKey publicKey) {
                // If it's a PublicKey...
                if (keysDiffer(publicKey, this.getMyKeys().getPublicKey())) {
                    // And it isn't our own...
                    if (this.getClientKey() == null) {
                        // If we're missing the Client key, this should be it!
                        this.setClientKey(publicKey);
                        log.append("\n\n\tHandler's Client's Key:\n")
                           .append(TabInserter.insertTabs(
                                   this.getClientKey().toString(), 2));

                    } else {
                        // Else, we found a weird key!
                        log.append("\n\n\tDEBUG - Found Problematic Key:");
                        log.append("\n\t\tFound Public Key:\n")
                           .append(TabInserter.insertTabs(publicKey.toString(),
                                                          3))
                           .append("\n\t\tFound Handler's Client's Key:\n")
                           .append(TabInserter.insertTabs(
                                   this.getClientKey().toString(), 3))
                           .append("\n\t\tMy Public Key:\n")
                           .append(TabInserter.insertTabs(
                                   this.getMyKeys().getPublicKey().toString(),
                                   3));

                    }
                }
            }
            attempts++;
        }

        // If we exit the loop and exceeded out attempt limit, log the failure.
        if (attempts >= 3) {
            log.append("\n\n\tThree failed key retrievals.\n");
        } else {
            // Else, we should be successful!
            log.append("\n\n\tSuccessful exchange!\n");
        }

        // Print the log
        System.out.println(log);
    }

    /**
     * <p>
     * Compares the encoding format of two PublicKeys.
     * </p>
     *
     * @param key1 The first PublicKey.
     * @param key2 The second PublicKey.
     *
     * @return True if the encoded keys are not identical, False otherwise.
     */
    private boolean keysDiffer(PublicKey key1, PublicKey key2) {
        return !Arrays.equals(key1.getEncoded(), key2.getEncoded());
    }
}
