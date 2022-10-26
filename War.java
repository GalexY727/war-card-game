import java.util.List;
import java.util.ArrayList;

/**
 * War game class
 *
 * @author Mr. Jaffe
 * @version 2022-10-19
 */
public class War
{
    /**
     * Constructor for the game
     * Include your initialization here -- card decks, shuffling, etc
     * Run the event loop after you've done the initializations
     */
    public War()
    {
        // Initializations here...
        Card card = new Card(1, "Ace", "Spades");
        Deck deck = new Deck();
        deck.initializeNewDeck();
        deck.shuffle();
        Deck[] halves = deck.dealDeck();
        Deck player1 = halves[0];
        Deck player2 = halves[1];
        boolean gameRunning = true;

        // ...then run the event loop
        this.runEventLoop(player1, player2);






    }
    
    /**
     * This is the game's event loop. The code in here should come
     * from the War flowchart you created for this game
     */
    public int runEventLoop(Deck p1, Deck p2) {

        int round = 0;
        boolean printWinner = true;

        while (p1.getDeckSize() > 0 && p2.getDeckSize() > 0) {

            System.out.println("\nRound " + round + "\n");
            int order = Math.random() > 0.5 ? 1 : 2;
            try {
                round++;
                if (round > 5000)
                {

                    System.out.println("After 5000 rounds, the game is a draw.");
                    printWinner = false;
                    break;
                }

                System.out.println("P1 Cards: " + p1.getDeckSize());
                System.out.println("P2 Cards: " + p2.getDeckSize());

                Card p1Card = p1.dealCardFromDeck();
                Card p2Card = p2.dealCardFromDeck();

                System.out.println("Player 1: " + p1Card.getFace() + " of " + p1Card.getSuit());
                System.out.println("Player 2: " + p2Card.getFace() + " of " + p2Card.getSuit());

                if (p1Card.getRank() > p2Card.getRank())
                {

                    System.out.println("Player 1 wins the round!");


                    p1.addCardToDeck(order > 1 ? p1Card : p2Card);
                    p1.addCardToDeck(order > 1 ? p2Card : p1Card);
                }
                else if (p1Card.getRank() < p2Card.getRank())
                {

                    System.out.println("Player 2 wins the round!");

                    p2.addCardToDeck(order > 1 ? p1Card : p2Card);
                    p2.addCardToDeck(order > 1 ? p2Card : p1Card);
                }
                else
                {

                    System.out.println("War!");

                    List<Card> p1WarCards = new ArrayList<Card>();
                    List<Card> p2WarCards = new ArrayList<Card>();

                    p1WarCards.add(p1Card);
                    p2WarCards.add(p2Card);

                    while (p1Card.getRank() == p2Card.getRank())
                    {

                        for (int i = 0; i < 3; i++)
                        {
                            p1WarCards.add(p1.dealCardFromDeck());
                            p2WarCards.add(p2.dealCardFromDeck());
                        }

                        p1Card = p1.dealCardFromDeck();
                        p2Card = p2.dealCardFromDeck();

                        p1WarCards.add(p1Card);
                        p2WarCards.add(p2Card);

                        System.out.println("Player 1: " + p1Card.getFace() + " of " + p1Card.getSuit());
                        System.out.println("Player 2: " + p2Card.getFace() + " of " + p2Card.getSuit());

                        System.out.println("P1 Cards: " + p1.getDeckSize());
                        System.out.println("P2 Cards: " + p2.getDeckSize());

                        if (p1Card.getRank() > p2Card.getRank())
                        {

                            System.out.println("Player 1 wins the war!");
                            for (Card c : p1WarCards) {
                                p1.addCardToDeck(c);
                            }
                            for (Card c : p2WarCards) {
                                p1.addCardToDeck(c);
                            }
                            System.out.println("P1 Cards: " + p1.getDeckSize());
                            System.out.println("P2 Cards: " + p2.getDeckSize());

                        }
                        else if (p1Card.getRank() < p2Card.getRank())
                        {

                            System.out.println("Player 2 wins the war!");

                            for (Card c : p1WarCards) {
                                p2.addCardToDeck(c);
                            }

                            for (Card c : p2WarCards) {
                                p2.addCardToDeck(c);
                            }

                            System.out.println("P1 Cards: " + p1.getDeckSize());
                            System.out.println("P2 Cards: " + p2.getDeckSize());
                        }
                        else
                        {
                            //run it back!
                            System.out.println("War!");
                        }
                        
                    }
                }
            }

            // Exception (array out of bounds)
            // Meaning that a player has 0 cards
            catch (Exception e)
            {
                // If player 1 has 0 cards, player 2 wins
                // If player 2 has 0 cards, player 1 wins
                if (p1.getDeckSize() > 0)
                {
                    System.out.println("Player 1 wins the game!");
                }
                else
                {
                    System.out.println("Player 2 wins the game!");
                }
                printWinner = false;
                break;
            }
        }
        if (printWinner)
        {
            if (p1.getDeckSize() > 0)
            {
                System.out.println("Player 1 wins the game!");
            }
            else
            {
                System.out.println("Player 2 wins the game!");
            }
        }
        return round;
        
    }
    
    /**
     * The main method is called when Java starts your program
     */
    public static void main(String[] args) {
        War war = new War();
    }

}
