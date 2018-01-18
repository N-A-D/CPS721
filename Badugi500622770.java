import java.util.*;
import java.lang.*;

/**
 * TAG/LAG (Tight Aggressive/Loose Aggressive) Badugi player 
 * Version: 18, December 5th, 2016
 * Author: Ned Austin Datiles, SID: 500622770 SID: 500622770
 * Notes: Added in remarks on overall strategy.
 * STRATEGY: 
 * Divided into two parts. The dealer & the opponent.
 * Each strategy considers the draws remaining in the hand as well as the betting behaviour of the opponent/dealer for 1 betting round in the past.
 
 Generally, as the dealer: gameplay is TAG
 - Play tight. Dealer's are in the blind and so it isn't worth risking $$$ to chase the opponent if he could be bluffing with not so good hands. 
 - Only raise the opponent with fair poor hands when he is weak. 
 - Bluff badugi's when the opponent shows that he is extremely weak. Say draws 3 and calls the last betting round and this betting round.
 - Raise or reraise the opponent when the hand is good. 
 
 As the opponent: gameplay is LAG/TAG
 - Aim is to get the dealer to fold as much as possible.
 - Play a wider range of hands. Play tight when the opponent show's strength. Nothing hits the wallet harder than trying to bluff someone who really likes their hand. 
 - raise as much as possible so that the dealer will be more likely to fold if their hand isn't up to snuff.
*/

public class Badugi500622770 implements BadugiPlayer{
	
	private int score; //current score
	private int position; //current position: 0 dealer, 1 opponent
	private int last_raise_count; //How many times dealer or opponent raised the last betting round.
	private int current_raises; //How many times the dealer/opponent raised this betting round.
	private boolean snow; //To draw or not to draw... That is the question.
	private boolean breakWeakBadugi; //To break a badugi or not break a badugi... This is another question :(
	
	private static Random rng = new Random(); //Random number generator.
	
	
	public Badugi500622770() {
		this.score = 0;
		this.breakWeakBadugi = false;
		this.snow = true;
	}
	public void startNewHand(int position, int handsToGo, int currentScore) {
		this.position = position;
		this.current_raises = 0;
		if (handsToGo == BadugiRunner.HANDS_PER_MATCH-1) {
			this.snow = true;
			this.breakWeakBadugi = false;
			this.score = 0;
		}
		else 
		{	
			if (Math.abs(this.score) > Math.abs(currentScore)) { 
				this.snow = false;
			}
			else {
				this.snow = true;
			}
			this.breakWeakBadugi = false;
			this.score = Math.abs(currentScore);
		}
	}
	public int bettingAction(int drawsRemaining, BadugiHand hand, int bets, int pot, int toCall, int opponentDrew) {
		if (toCall > 0) { current_raises++;} //Count how many times the opponent raised.
		int[] ranks = hand.getActiveRanks(); 
		int hand_size = hand.getActiveCards().size();
		int highestRank = ranks[0];
		int counter = 0;
		for (int i =0 ;i < ranks.length; i++) { //Count  how many face cards are in the current hand.
			if (ranks[i] >= 11) {
				counter++;
			}
		}
		if (hand_size == 4) { 
			this.snow = false;
			int ret = 0;
			if (position == 1) {
				switch(drawsRemaining) {
					case 0: 
						this.breakWeakBadugi = false;
						if (current_raises > 0 && opponentDrew > 0) {
							ret = (highestRank <= 10) ? 1:0; 
						}
						else if (current_raises > 0 && opponentDrew <= 0) {
							ret = (last_raise_count == 0) ? ((highestRank <= 10) ? 1:0):((highestRank <= 9) ? 1:0);
						}
						else if (current_raises <= 0 && opponentDrew > 0) {
							this.breakWeakBadugi = false; 
							ret = 1;
						}
						else { 
							ret = (last_raise_count == 0) ? ((highestRank <= 11) ? 1:0):((highestRank <= 12) ? 1:0);
						}
					break;
					case 1: 
						if (current_raises > 0 && opponentDrew > 0) { 
							this.breakWeakBadugi = false;
							ret = (last_raise_count == 0) ? ((opponentDrew >= 2) ? ((highestRank >= 11) ? 0:1):((highestRank >= 10) ? 0:1)):((opponentDrew >= 2) ? 1:((highestRank >= 12) ? 0:1));
						}
						else if (current_raises > 0 && opponentDrew <= 0) { 
							if (last_raise_count == 0) {
								this.breakWeakBadugi = false;
								ret = (highestRank <= 10) ? 1:0;
							}
							else { 
								this.breakWeakBadugi = (highestRank >= 11) ? true:false ;
								ret = (highestRank <= 9) ? 1:0;
							}
						}
						else if (current_raises <= 0 && opponentDrew > 0) { 
							this.breakWeakBadugi = false;
							ret = 1; 
						}
						else { 
							ret = (last_raise_count == 0) ? ((highestRank <= 10) ? 1:0):((highestRank <= 11) ? 1:0);
						}
					break;
					case 2: 
						if (current_raises > 0 && opponentDrew > 0) {
							this.breakWeakBadugi = false; 
							ret = (opponentDrew >= 2) ? ((counter >= 2) ? 0:1):((counter >= 1) ? 0:1);
						}
						else if (current_raises > 0 && opponentDrew <= 0) {
							if (last_raise_count == 0) {
								this.breakWeakBadugi = (counter >= 2) ? true:false;
								ret = (counter >= 1) ? 0:1; 
							}
							else { 
								if (counter >= 2) { 
									breakWeakBadugi = true; 
									ret = 0;
								}
								else {
									breakWeakBadugi = false; 
									ret = (highestRank <= 9) ? 1:0; 
								}
							}
						}
						else if (current_raises <= 0 && opponentDrew >0) {
							this.breakWeakBadugi = false; 
							ret = 1;
						}
						
						else {
							this.breakWeakBadugi = false; 
							ret = (last_raise_count == 0) ? ((highestRank <= 9) ? 1:0):((highestRank <= 10) ? 1:0);
						}
					break;
					case 3: 
						this.breakWeakBadugi = false; 
						ret = (counter >= 2) ? ((current_raises == 0) ? 1:0): 1;
					break;
				}
				return ret;
			}
			else {
				this.breakWeakBadugi = false;
				switch(drawsRemaining) {
					case 0:
						if (current_raises > 0 && opponentDrew > 0) { 
							ret = (last_raise_count == 0) ? ((highestRank <= 9) ? 1:0):((highestRank <= 8) ? 1:0);
						}
						else if (current_raises > 0 && opponentDrew <= 0) { 
							ret = (last_raise_count == 0) ? ((highestRank <= 10) ? 1:0) : ((highestRank <= 9) ? 1:0); 
						}
						else if (current_raises <= 0 && opponentDrew > 0) { 
							ret = 1; 
						}
						else {
							ret = (last_raise_count == 0) ? ((highestRank <= 10) ? 1:0) :((highestRank <= 11) ? 1:0);
						}
					break;
					case 1:
						if (current_raises > 0 && opponentDrew > 0) { 
							ret = (last_raise_count == 0) ? ((highestRank <= 9) ? 1:0): ((highestRank <= 10) ? 1:0);
						}
						else if (current_raises > 0 && opponentDrew <= 0) {
							this.breakWeakBadugi = (last_raise_count == 0) ? ((highestRank == 13) ? true:false): ((highestRank >= 12) ? true:false); 
							ret = (last_raise_count == 0) ? ((highestRank <= 11) ? 1:0) : ((highestRank <= 9) ? 1:0); 
						}
						else if (current_raises <= 0 && opponentDrew > 0) { 
							ret = 1;
						}
						else { 
							ret = (last_raise_count == 0) ? ((highestRank <= 9) ? 1:0) : ((highestRank <= 10) ? 1:0 );
						}
					break;
					case 2:
						if (current_raises > 0 && opponentDrew > 0) { 
							ret = (last_raise_count == 0) ? ((opponentDrew >= 2) ? ((highestRank <= 11) ? 1:0) : ((highestRank <= 10) ? 1:0)) : ((opponentDrew >= 2) ? ((highestRank <= 11) ? 1:0) : ((highestRank <= 10) ? 1:0));
						}
						else if (current_raises > 0 && opponentDrew <= 0) { 
							this.breakWeakBadugi = (last_raise_count == 0) ? false:((highestRank >= 12) ? true:false);
							ret = (last_raise_count == 0) ? ((highestRank <= 10) ? 1:0): ((highestRank <= 9) ? 1:0); 
						}
						else if (current_raises <= 0 && opponentDrew > 0) { 
							ret = (last_raise_count == 0) ? ((highestRank <= 12) ? 1:0): 1;
						}
						else { 
							ret = (last_raise_count == 0) ? ((highestRank <= 10) ? 1:0):((highestRank <= 11) ? 1:0);
						}
					case 3:
					
						if (counter >= 2) {
							ret = (current_raises == 0) ? 1:0;
						}
						else {
							ret = (counter == 1) ? ((current_raises > 0) ? 0:1):1;
						}
					break;
				}
				return ret;
			}
		}
		else if (hand_size == 3) {	
			int ret = 0;
			if (this.position == 1) { 
				switch(drawsRemaining) {
					case 0:
						this.snow = false;
						if (current_raises > 0 && opponentDrew > 0) { 
							ret = (last_raise_count == 0) ? ((opponentDrew >= 2) ? ((highestRank <= 9) ? 1:0): ((highestRank <=8) ? 0:-1)): ((opponentDrew >= 2) ? ((highestRank <= 8) ? 1:0): ((highestRank <= 7) ? 0:-1));
						}
						else if (current_raises > 0 && opponentDrew <= 0) { 
							ret = (last_raise_count == 0) ? ((highestRank <= 4) ? 0:-1):((highestRank <= 3) ? 0:-1);
						}
						
						else if (current_raises <= 0 && opponentDrew > 0) { 
							ret = (last_raise_count == 0) ? ((highestRank <= 9) ? 1: 0):((highestRank <= 10) ? 1: 0);
						}
						else { 
							ret = (last_raise_count == 0) ? ((highestRank <= 6) ? 0:-1):((highestRank <= 7) ? 0:-1);
						}
					break;
					case 1:
						if (current_raises > 0 && opponentDrew > 0) {
							if (last_raise_count == 0) {
								if (opponentDrew >= 2) {
									this.snow = (opponentDrew >= 3) ? true:false;
									ret = (snow) ? 1:0;
								}
								else {
									this.snow = false;
									ret = (highestRank <= 6) ? 1:0;
								}
							}
							else {
								this.snow = false;
								ret = (opponentDrew >= 2) ? ((highestRank <= 7) ? 1:0):((highestRank <= 6) ? 1:0);
							}
						}
						else if (current_raises > 0 && opponentDrew <= 0) {
							this.snow = false;
							ret = (last_raise_count == 0) ? ((highestRank <= 6) ? 0:-1):((highestRank <= 5) ? 0:-1);
						}
						
						else if (current_raises <= 0 && opponentDrew > 0) {
							this.snow = true;
							ret = 1;
						}
						else {
							this.snow = false;
							ret = (last_raise_count == 0) ? ((highestRank <= 11) ? 0:-1):((highestRank <= 10) ? 0: -1);
						}
					break;
					case 2:
						if (current_raises > 0 && opponentDrew > 0) {
							if (last_raise_count == 0) {
								this.snow = false;
								ret = (highestRank < 8) ? 1:0;
							}
							else {
								this.snow = false;
								ret = (opponentDrew >= 2) ? ((highestRank < 10) ? 1:0):((counter >=2) ? -1:0);
							}
						}
						else if (current_raises > 0 && opponentDrew <= 0) {
							this.snow = false;
							ret = (last_raise_count == 0) ? ((highestRank < 8) ? 1:0):((highestRank < 7) ? 1:0);
						}
						else if (current_raises <= 0 && opponentDrew > 0) {
							ret = 1;
						}
						else {
							this.snow = false;
							ret = (last_raise_count == 0) ? ((highestRank < 9) ? 1:0):((highestRank < 8) ? 1:0);
						}
						
					break;
					case 3: 
						if (current_raises == 0) { 
							this.snow = (highestRank >= 10 || ranks[1] >= 9) ? false:true; 
							ret = (highestRank <= 12) ? 1:0; 
						}
						else {
							this.snow = false;
							ret = (highestRank <= 7) ? 1:0;
						}
					
				}
				return ret;
			}
			
			else {
				switch(drawsRemaining) {
					case 0:
						this.snow = false;
						if (current_raises > 0 && opponentDrew  > 0) {
							if (last_raise_count == 0) {
								ret = (opponentDrew >= 2) ? ( (highestRank <= 8) ? 1:0):((highestRank <= 7) ? 0:-1);
							}
							else {
								ret = (opponentDrew >= 3) ? ((highestRank <= 9) ? 1:0):((highestRank <= 8) ? 0:-1 );
							}
						}
						else if (current_raises > 0 && opponentDrew  <= 0) {
							ret = (last_raise_count == 0) ? ((highestRank <= 4) ? 0:-1):((highestRank <= 3) ? 0: -1);
						}
						else if (current_raises <= 0 && opponentDrew > 0) {
							
							if (last_raise_count == 0) {
								if (opponentDrew >= 2) {
									ret = (highestRank < 7) ? 1:0;
								}
								else {
									ret = (highestRank < 6) ? 1:0;
								}
							}
							else {
								if (opponentDrew >= 2) {
									ret = (highestRank < 8) ? 1:0;
								}
								else {
									ret = (highestRank < 7) ? 1:0;
								}
							}
						}
						else {
							if (last_raise_count == 0) {
								ret = (highestRank <= 6) ? 0:-1;
							}
							else {
								ret = (highestRank <= 7) ? 0:-1;
							}
						}
					break;
					case 1:
						this.snow = false;
						if (current_raises > 0 && opponentDrew  > 0) {
							if (last_raise_count == 0) {
								ret = (opponentDrew >= 2) ? ((highestRank <= 9)? 1:0):((highestRank <= 8) ? 0:-1);
							}
							else {
								ret = (opponentDrew >= 2) ? ((highestRank <=10) ? 1:0):((highestRank <= 9) ? 0:-1);
							}
						}
						else if (current_raises > 0 && opponentDrew  <= 0) {
							if (last_raise_count == 0) {
								ret = (highestRank <= 8) ? 0:-1;
							}
							else {
								ret = (highestRank <= 7) ? 0:-1;
							}
						}
						
						else if (current_raises <= 0 && opponentDrew > 0) {
							
							if (last_raise_count == 0) {
								
								ret = (highestRank <= 9) ? 1:0;
							}
							else {
								
								ret = (highestRank <= 10) ? 1:0;
							}
						}
						
						else {
							if (last_raise_count == 0) {
								ret = (highestRank <= 8) ? 0:-1;
							}
							else {
								ret = (highestRank <= 7) ? 0:-1;
							}
						}
					break;
					case 2:
						if (current_raises > 0 && opponentDrew  > 0) {
							this.snow = false;
							ret = (last_raise_count == 0) ? ((opponentDrew >= 2) ? ((highestRank <= 9) ? 1:0):((highestRank <= 8) ? 1:0)): ((opponentDrew >= 2) ? ((highestRank <= 8) ? 1:0):((highestRank <= 7) ? 1:0));
						}
						
						else if (current_raises > 0 && opponentDrew  <= 0) {
							this.snow = false;
							if (last_raise_count == 0) {
								ret = (highestRank <= 7) ? 1:0;
							}
							else {
								ret = (highestRank <= 6) ? 1:0;
							}
						}
						else if (current_raises <= 0 && opponentDrew > 0) {
							
							if (last_raise_count == 0) {
								this.snow = (opponentDrew >= 3) ? true:false;
								ret = (highestRank <= 9) ? 1:0;
							}
							else {
								this.snow = true;
								if (opponentDrew >= 2) {
									ret = (highestRank <= 9) ? 1:0;
								}
								else {
									ret = (highestRank <= 8) ? 1:0;
								}
							}
						}
						else {
							this.snow = false;
							if (last_raise_count == 0) {
								ret = (highestRank <= 5) ? 1:0;
							}
							else {
								ret = (highestRank <= 6) ? 1:0;
							}
						}
					break;
					case 3:
						this.snow = false;
						ret = ((highestRank <= 8 && ranks[1] <= 5) || highestRank <= 7) ? 1:0;
					break;
				}
				return ret;
			}
			
		}
		else if (hand_size == 2) {
			int ret = 0;
			if (this.position == 1) {
				switch(drawsRemaining) {
					case 0:
						ret = -1;
					break;
					case 1:
						this.snow = false;
						if (current_raises > 0 && opponentDrew > 0) {
							ret = (opponentDrew >= 2) ? 0:-1;
							
						}
						else if (current_raises > 0 && opponentDrew <= 0) {
							ret = -1;
						}
						else if (current_raises <= 0 && opponentDrew > 0) {
							this.snow = (opponentDrew >= 3) ? true:false;
							ret = 1;
						}
						else {
							return -1;
						}
					break;
					case 2:
					
						if (current_raises > 0 && opponentDrew > 0) {
							this.snow = false;
							if (last_raise_count == 0) {
								ret = (opponentDrew >= 2) ? ((highestRank <=8)? ((current_raises > 1) ? 0:1):0):((highestRank <= 7) ? 0:-1);
							}
							else {
								ret = (opponentDrew >= 1) ? 0:-1;
							}
						}
						
						else if (current_raises > 0 && opponentDrew <= 0) {
							this.snow = false;
							if (last_raise_count == 0) {
								ret  = (highestRank <=8) ? 0:-1;
							}
							else {
								ret  = (highestRank <=7) ? 0:-1;
							}
						}
						
						else if (current_raises <= 0 && opponentDrew > 0) {
							
							if (last_raise_count == 0) {
								this.snow = false;
								ret = (highestRank <= 9) ? 1:0;
							}
							else {
								this.snow = true;
								ret = (highestRank < 10) ? 1:0;
							}
						}
						else {
							this.snow = false;
							if (last_raise_count == 0) {
								ret = (highestRank <= 6) ? 1:0;
							}
							else {
								ret = (highestRank <= 7) ? 1:0;
							}
						}
					break;
					case 3:
						if (current_raises == 0) {
							this.snow = (highestRank >= 3) ? false:true;
							ret = (highestRank <= 8) ? 1:0;
						}
						else {
							this.snow = false;
							ret = (highestRank <= 7) ? 0:-1;
						}
					break;
				}
			return ret;
			}
			else {
				switch(drawsRemaining) {
					case 0:
						ret = -1;
					break;
					case 1:
						this.snow = false;
						if (current_raises > 0 && opponentDrew > 0) {
							ret = -1;
						}
						else if (current_raises > 0 && opponentDrew <= 0) {
							ret =  -1;
						}
						else if (current_raises <= 0 && opponentDrew > 0) {
							if (last_raise_count == 0) {
								ret = (highestRank <= 3) ? ((opponentDrew >= 2) ? 1:0):-1;
							}
							else {
								ret = (highestRank <= 4) ? ((opponentDrew >= 2) ? 1:0):-1;
							}
						}
						else {
							ret = -1;
						}
					break;
					case 2:
						if (current_raises > 0 && opponentDrew > 0) {
							this.snow = false;
							if (last_raise_count == 0) {
								ret = (opponentDrew >= 2) ? ((highestRank <= 5) ? 1:0):-1;
							}
							else {
								ret = (opponentDrew >= 3) ? ((highestRank <= 6) ? 1:0):-1;
							}
						}
						else if (current_raises > 0 && opponentDrew <= 0) {
							this.snow = false;
							if (last_raise_count == 0) {
								ret = (highestRank <= 5) ? 0:-1;
							}
							else {
								ret = (highestRank <= 4) ? 0:-1;
							}
						}
						
						else if (current_raises <= 0 && opponentDrew > 0) {
							if (last_raise_count == 0) {
								this.snow = (opponentDrew >= 3) ? true:false;
								ret = (highestRank <= 7) ? 1:0;
							}
							else {
								this.snow = (opponentDrew >= 2) ? true:false;
								ret = (highestRank <= 8) ? 1:0;
							}
						}
						else {
							if (last_raise_count == 0) {
								this.snow = false;
								ret = (highestRank <= 4) ? 1:0;
							}
							else {
								this.snow = false;
								ret = (highestRank <= 5) ? 1:0;
							}
						}
					break;
					case 3: 
						this.snow =false;
						ret = (highestRank <= 6) ? ((ranks[1] <= 4) ? 1:0):((current_raises > 0) ? ((highestRank <= 5) ? 0: -1): 0);
					break;
				} 
				return ret;
			}
			
		}
		else{
			if (this.position == 1) {
				int ret = 1;
				switch(drawsRemaining) {
					case 0:
						this.snow = false;
						if (current_raises == 0 && opponentDrew > 0) {
							ret = (opponentDrew >= 2) ? 1:0;
						}
						else {
							ret = -1;
						}
					break;
					case 1:
						if (current_raises == 0 && opponentDrew > 0) {
							this.snow = (opponentDrew >= 3) ? true:false;
							ret = (highestRank <= 3) ? ((snow) ? 1 : 0):-1;
						}
						else {
							this.snow = false;
							ret = -1;
						}
					break;
					case 2:
						if (current_raises > 0 && opponentDrew > 0) {
							this.snow = (opponentDrew >= 3) ? true:false;
							ret = (last_raise_count == 0) ? ((snow && highestRank <= 3) ? 1:0):-1;
						}
						else if (current_raises > 0 && opponentDrew <= 0) {
							this.snow = false;
							ret = (highestRank <= 2) ? 0:-1;
						}
						else if (current_raises <= 0 && opponentDrew > 0) {
							this.snow = (opponentDrew >= 2) ? true:false;
							ret = (last_raise_count == 0) ? ((snow) ? 1:0):1;
						}
						else {
							ret = -1;
						}
					break;
					case 3:
						this.snow = false;
						if (current_raises == 0) {
							ret = 1;
						}
						else {
							ret = (highestRank <= 3) ? 0:-1;
						}
					break;
				}
				return ret;
				
			}else {
				this.snow = false;
				return -1; 
			}
		}

	}
	public List<Card> drawingAction(int drawsRemaining, BadugiHand hand, int pot, int dealerDrew) {
		this.last_raise_count = this.current_raises;
		this.current_raises = 0;
		int hand_size = hand.getActiveCards().size();
		int[] ranks = hand.getActiveRanks();
		int counter = 0;
		
		for (int i =0; i < ranks.length; i++) { //Get a count of all active cards over 9. These will be thrown out.
			if (ranks[i] >= 9) {
				counter++;
			}
		}
		if (hand_size == 4) { // Badugi's are never broken unless specified via breakWeakBadugi.
			if (this.breakWeakBadugi) {
				List<Card> toDiscard = new ArrayList<Card>(hand.getInactiveCards());
				toDiscard.add(hand.getActiveCards().get(0));
				return toDiscard;
			}
			else {
				return hand.getInactiveCards();
			}
		}
		else { //If not bluffing a badugi, return all the cards that are equal to or greater than 9.
			if (!snow)  {
				List<Card> ret = new ArrayList<Card>(hand.getInactiveCards());
				if (counter == 4) { return new ArrayList<Card>(hand.getAllCards());}
				for (int i =0; i < counter; i++) {
					ret.add(hand.getActiveCards().get(i));
				}
				return ret;
			}
			else {
				return new ArrayList<Card>();
			}
		}
		
		//return hand.getInactiveCards();
	}
	public void showdown(BadugiHand yourHand, BadugiHand opponentHand) {

	}
	public String getAgentName(){
	  return "500622770";
	}
	public String getAuthor() {
		return "Datiles, Ned";
	}  
}