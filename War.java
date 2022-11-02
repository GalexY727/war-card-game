import java.util.List;
import java.util.Collections;
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
        Deck deck = new Deck();
        deck.initializeNewDeck();
        deck.shuffle();
        Deck[] halves = deck.dealDeck();
        Deck p1 = halves[0];
        Deck p2 = halves[1];
        // ...then run the event loop
        this.runEventLoop(p1, p2);
    }
    
    /**
     * This is the game's event loop. The code in here should come
     * from the War flowchart you created for this game
     *
     * @param p2 The first player's deck
     * @param p1 The second player's deck
     */
    public void runEventLoop(Deck p1, Deck p2) {

        int round = 0;
        ArrayList<Integer> h = new ArrayList<Integer>();

        while (p1.getDeckSize() > 0 && p2.getDeckSize() > 0) {

            System.out.println("\nRound " + round + "\n");
            round++;

            // used to prevent games from going on extremely long
            // randomizes the ordering of how cards are received
            int order = Math.random() > 0.5 ? 1 : 2;

            System.out.println("P1 Cards: " + p1.getDeckSize());
            System.out.println("P2 Cards: " + p2.getDeckSize());

            // try is used in case of p1.getDeckSize --> 0
            try {

                Card p1Card = p1.dealCardFromDeck();
                Card p2Card = p2.dealCardFromDeck();

                System.out.println("Player 1: " + p1Card.getFace() + " of " + p1Card.getSuit());
                System.out.println("Player 2: " + p2Card.getFace() + " of " + p2Card.getSuit());

                // if player one has the better card
                // they get both cards that are in play
                if (p1Card.getRank() > p2Card.getRank())
                {

                    System.out.println("Player 1 wins the round!");
                    h.add(1);

                    p1.addCardToDeck(order > 1 ? p1Card : p2Card);
                    p1.addCardToDeck(order > 1 ? p2Card : p1Card);
                }
                // if player two has the better card
                // they get both cards that are in play
                else if (p1Card.getRank() < p2Card.getRank())
                {

                    System.out.println("Player 2 wins the round!");
                    h.add(2);

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

                    // looped in case of another tie
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
                            h.add(1);

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
                            h.add(2);

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
                // Will prompt the winner to be printed
                break;
            }
        }

        if (p1.getDeckSize() > 0)
        {
            System.out.println("Player 1 wins the game!");
        }
        else
        {
            System.out.println("Player 2 wins the game!");
        }


        // Creates the history of the game to
        // print as a NodeTree
        int[] hArr = new int[h.size()];
        int spaces = 0;
        int p1Streak = 0;

        for (int i = 1; i < h.size(); i++)
        {
            int temp = spaces; 
            hArr[i] = h.get(i);

            if (hArr[i] == 1)
            {
                spaces++;

            }
            else
            {
                spaces--;
            }

            p1Streak = Math.max(temp, Math.max(p1Streak, spaces));

        }

        printHistory(hArr, p1Streak*2);
    }

    /**
     * Prints the history of the game as a NodeTree
     * @param h the history of the game
     * @param spaces the number of spaces to print before the fist node
     *               (used to make the tree look nice)
     *               (determined via the delta change of player 1's winnings)
     *               (because player 1 branches left)
     *               (to align with the left of the console)
     *               (these parentheses are getting out of hand)
     */
    private void printHistory(int[] h, int spaces) {

        int origin = spaces;

        for (int i = 0; i < h.length; i++) {
            if (h[i] == 1) {
                for (int j = 0; j < spaces; j++)
                {
                    if (j == origin)
                    {
                        System.out.print("|");
                    }
                    else
                    {
                        System.out.print(" ");
                    }
                }

                System.out.print("P1");
                if (origin > spaces)
                {
                    for (int j = spaces+2; j < origin; j++)
                    {
                        System.out.print(" ");
                    }
                    System.out.print("|");
                }
                System.out.println();
            }
            else
            {
                for (int j = 0; j < spaces; j++)
                {
                    if (j == origin)
                    {
                        System.out.print("|");
                    }
                    else
                    {
                        System.out.print(" ");
                    }
                }

                System.out.print("P2");
                if (origin > spaces)
                {
                    for (int j = spaces+2; j < origin; j++)
                    {
                        System.out.print(" ");
                    }
                    System.out.print("|");
                }
                System.out.println();
            }

            if (i+1 < h.length && h[i+1] == 1)
            {
                spaces = (spaces > 0) ? spaces - 1 : 0;

                for (int j = 0; j < spaces; j++)
                {
                    if (j == origin)
                    {
                        System.out.print("|");
                    }
                    else
                    {
                        System.out.print(" ");
                    }
                }

                System.out.print('/');

                spaces = (spaces > 0) ? spaces - 1 : 0;

                if (origin > spaces)
                {
                    for (int j = spaces+2; j < origin; j++)
                    {
                        System.out.print(" ");
                    }
                    System.out.print("|");
                }
            }
            else if (i+1 < h.length && h[i+1] == 2)
            {
                spaces++;

                for (int j = 0; j < spaces; j++)
                {
                    if (j == origin)
                    {
                        System.out.print("|");
                    }
                    else
                    {
                        System.out.print(" ");
                    }
                }

                System.out.print('\\');
                spaces++;

                if (origin > spaces-1)
                {
                    for (int j = spaces; j < origin; j++)
                    {
                        System.out.print(" ");
                    }
                    System.out.print("|");
                }

            }

            System.out.println();
        }
    }



    /**
     * The main method is called when Java starts your program
     */
    public static void main(String[] args) {
        War war = new War();
    }

}


