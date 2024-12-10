# Final Project

*CSCI2500: Object Oriented Design*

---

Details pending.

## <span style="color:red">Resources</span>

#### Refresher on material → [Link](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)

* Model:
    * It directly manages the data, logic and rules of the application
        * Observer Patter
        * Facade
* View:
    * Any representation of information such as a chart, diagram or table.
* Controller:
    * Accepts input and converts it to commands for the model or view.
        * Command Pattern
        * ActionListeners, which execute the commands.
* Server
    * Singleton

### Paige Grimes

* TextChanel
    * [JTextPane stackoverflow] (https://stackoverflow.com/questions/31928306/how-to-create-and-use-a-jtextpane)
    * [JTextPane JavaDocs] (https://docs.oracle.com/javase/8/docs/api/javax/swing/JTextPane.html)
    * [JTextPane stackOverflow] (https://stackoverflow.com/questions/4059198/jtextpane-appending-a-new-string)
    * [DefaultStyledDocument for JTextPane] (https://stackoverflow.com/questions/50152438/jtextpane-wont-display-text-added-by-defaultstyleddocument)
* User class
    * [RandomColorGenerator] (https://stackoverflow.com/questions/4246351/creating-random-colour-in-java)
    * Professor Salu reminded me of swingInvokers and why they are important
      * ``"Remember, invoke later is like politely asking the renderer to show 
         when you have the chance.  If you want to be more demanding, invoke 
         and wait. "``
* NetworkListener
    * After familiarizing myself with Sam's implementation of the class and
      how it interacts with other classes, I was able to come to the
      conclusion of calling the observers notifier there.
* NetworkRelay
    * I had begun looking at different resources to integrate the gui with the
      NetworkRelay but nothing was quite working. I had gotten the gui to
      update with the ObjectStreamReader but not the Users input. After Ryan
      shared his part on a separate branch (That worked) to get the users'
      message, I was able to add it to my files so that both of our
      implementations worked together.

## Local Machine IP
- [Baeldung: Get Local IP Address](https://www.baeldung.com/java-get-ip-address)

## Command Pattern
- [Stack Overflow: Implementing the Command Pattern with GUIs](https://stackoverflow.com/questions/30058903/implementing-the-command-pattern-with-guis)
- [Baeldung: The Command Pattern](https://www.baeldung.com/java-command-pattern)
- [DZone: The Command Design Pattern](https://dzone.com/articles/the-command-design-pattern)

## GUI Handling
- [Stack Overflow: Switching Between JFrames](https://stackoverflow.com/questions/6588984/switching-between-jframes)

## Networking
- [Vogella: Java Networking](https://www.vogella.com/tutorials/JavaNetworking/article.html)
- [Stack Overflow: Multiple Clients to Single Server](https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-single-server)

## Thread Management
- [Reddit: Making a Thread Wait for Input](https://www.reddit.com/r/learnjava/comments/10it7j8/making_a_thread_wait/)

## Sound in Java
- [Stack Overflow: Stopping Clip with Clip.close](https://stackoverflow.com/questions/34123969/stopping-clip-with-clip-close)
- [Stack Overflow: Sound on Button Press for Java Calculator](https://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator)
- [Baeldung: Play Sound in Java](https://www.baeldung.com/java-play-sound)