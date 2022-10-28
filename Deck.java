import java.util.List;
import java.util.ArrayList;

/**
 * Emulate a deck of cards
 *
 * @author Mr. Jaffe
 * @version 2022-10-19
 */
public class Deck
{
    private List<Card> cards;

    /**
     * Deck constructor: Create an empty deck of cards
     */
    public Deck()
    {
        cards = new ArrayList<Card>();
    }

    /**
     * Initialize a new deck of cards
     */
    public void initializeNewDeck()
    {
        //String[] suits = {"Hearts","Clubs","Spades","Diamonds"};
        String[] suits = {"H","C","S","D"};
        int[] ranks = {2,3,4,5,6,7,8,9,10,11,12,13,14};
        //String[] faces = {"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
        String[] faces = {"2","3","4","5","6","7","8","9","10","J ","Q ","K" ,"A "};

        for (String suit : suits)
        {
            for (int i=0; i<ranks.length; i++)
            {

                Card c = new Card(ranks[i], faces[i], suit);
                this.cards.add(c);

            }
        }
    }

    /**
     * Get the number of cards in the deck
     * 
     * @return Number of cards in the deck
     */
    public int getDeckSize() {
        return cards.size();
    }
    
    /**
     * Shuffles the cards in the deck
     */
    public void shuffle()
    {

        for (int i = 0; i < getDeckSize(); i++)
        {
            int random = (int)(Math.random() * getDeckSize());

            Card temp = cards.get(i);

            this.cards.set(i, cards.get(random));

            this.cards.set(random, temp);

        }
    }

    /**
     * Deal all the cards in the deck to make two new decks of cards
     * 
     * @return Deck array where index 0 is the first deck and index 1 is the second deck
     */
    public Deck[] dealDeck()
    {

        Deck[] halves = new Deck[2];
        halves[0] = new Deck();
        halves[1] = new Deck();
        boolean idx = false;

        while (this.cards.size() > 0)
        {
            halves[idx ? 0 : 1].addCardToDeck(this.dealCardFromDeck());
            idx = !idx;

        }

        return halves;
    }
    
    /**
     * Deal the top card of the deck and remove it from the deck
     * @return The top card of the deck (at cards index 0)
     */
    public Card dealCardFromDeck()
    {
        Card temp = this.cards.get(0);
        this.cards.remove(0);
        return temp;

    }
    
    /**
     * Adds the provided card to the deck
     * @param cardToAdd: Card to add to this deck
     */
    public void addCardToDeck(Card cardToAdd)
    {
        this.cards.add(cardToAdd);

    }
    
}
