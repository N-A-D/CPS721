import java.util.*;

/**
 * A simple rule-based player for heads-up badugi.
 * 
 * @author Ilkka Kokkarinen
 */
public class Badugi2 implements BadugiPlayer
{
    private static int count = 0;
    private int id;
    private String nick;
    private int position;
    private int totalRaises; // total number of bets and raises in the entire hand
    private Random rng = new Random();

    public Badugi2 () { this.id = ++count; }
    
    private static String[][] thresholdString = {
        // 0 draws remaining
        { "Kh7h6d5c", "9h8d7s6c", "8h7d6s5c", "7h6d5s4c", "6h5d4s3c" },
        
        // 1 draw remaining
        { "Kh9h8s7d", "Kh7h6d5c", "9h8d7s6c", "8h7d6s5c", "7h6d5s4c" },
        
        // 2 draws remaining
        { "KhQh3h2s", "Kh9h8s7d", "Kh7h6d5c", "KhQdJsTc", "9h8d7s6c" },
        
        // 3 draws remaining
        { "KhQh8h4s", "KhQh3h2s", "Kh9h8s7d", "Kh7h4d3s", "KhQdJsTc" }
       
    };
    
    private static BadugiHand[][] thresholdHands = new BadugiHand[4][5];
    static { // static initializer block is executed once when JVM loads the class bytecode 
        for(int draws = 0; draws < 4; draws++) {
            for(int bets = 0; bets < 5; bets++) {
                thresholdHands[draws][bets] = new BadugiHand(thresholdString[draws][bets]);
            }
        }
    }
    
    public void startNewHand(int position, int handsToGo, int currentScore) {
        //System.out.println("Score for " + getAgentName() + " is " + currentScore);
        this.position = position; totalRaises = 0;
    }
    
    public int bettingAction(int drawsRemaining, BadugiHand hand, int bets, int pot, int toCall, int opponentDrew) {
        if(toCall > 0) { totalRaises++; }
        int beatsIdx = 0;
        if(opponentDrew < 0) { opponentDrew = 0; }
        while(beatsIdx < 5 && hand.compareTo(thresholdHands[drawsRemaining][beatsIdx]) > 0) { beatsIdx++; }
        int off = beatsIdx - bets - totalRaises / 3 + position + opponentDrew - 2;
        double ran = rng.nextDouble();
        if(off < 0) { // Looks like we are behind
            if(ran < .6) { return -1; }
            if(ran < .8) { return 0; }
            return +1; // raise as a bluff anyway
        }
        else if(off == 0) { // Looks like we are par
            if(ran < .1) { return -1; }
            if(ran < .7) { return 0; }
            else return +1;
        }
        else { // Looks like we are ahead, ram and jam
            if(ran < .5 - .2 * off) { return 0; }
            else return +1;
        }
    }
    
    public List<Card> drawingAction(int drawsRemaining, BadugiHand hand, int pot, int dealerDrew) {
		int hand_size = hand.getActiveCards().size();
		int[] ranks = hand.getActiveRanks();
		int counter = 0;
		
		for (int i =0; i < ranks.length; i++) { 
			if (ranks[i] >= 9) {
				counter++;
			}
		}
		if (hand_size == 4) {
			return hand.getInactiveCards();
		}
		else { 
			
				List<Card> ret = new ArrayList<Card>(hand.getInactiveCards());
				if (counter == 4) { return new ArrayList<Card>(hand.getAllCards());}
				for (int i =0; i < counter; i++) {
					ret.add(hand.getActiveCards().get(i));
				}
				return ret;
		}
    }
    
    public void showdown(BadugiHand yourHand, BadugiHand opponentHand) { }
    
    public String getAgentName() { 
        return "Dummy #" + this.id;
    }
    
    public String getAuthor() { return "Kokkarinen, Ilkka"; }
}