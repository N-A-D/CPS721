import java.util.*;
import java.lang.*;

/*
 * TAG Badugi player
 * Version: 16, December 2nd, 2016
 * Author: Ned Austin Datiles, SID: 500622770
*/

public class BadugiCoB implements BadugiPlayer{
	
	private int score; //current score
	private int position; //current position: 0 dealer, 1 opponent
	private int last_raise_count; //How many times dealer or opponent raised the last betting round.
	private int current_raises; //How many times the dealer/opponent raised this betting round.
	private boolean snow; //To draw or not to draw... That is the question.
	private boolean breakWeakBadugi; //To break a badugi or not break a badugi... This is also another question :/
	
	private static Random rng = new Random(); //Random number generator.
	
	
	public BadugiCoB() {
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
		if (toCall > 0) { current_raises++;}
		int[] ranks = hand.getActiveRanks();
		int hand_size = hand.getActiveCards().size();
		int highestRank = ranks[0];
		int counter = 0;
		for (int i =0 ;i < ranks.length; i++) {
			if (ranks[i] >= 11) {
				counter++;
			}
		}
		if (hand_size == 4) {
			this.snow = false;
			int ret = 0;
			if (position == 1) {
				switch(drawsRemaining) {
					case 0: //no drawing left.
						this.breakWeakBadugi = false;//Don't break the badugi... Obviously...
						//If he raises but also draws...
						if (current_raises > 0 && opponentDrew > 0) {
							ret = (highestRank <= 10) ? 1:0; //Reraise when you have a jack-high or lower...
						}
						//If the opponent raises but didn't drew anything...
						else if (current_raises > 0 && opponentDrew <= 0) {
							ret = (last_raise_count == 0) ? ((highestRank <= 10) ? 1:0):((highestRank <= 9) ? 1:0);
						}
						//If the oppponent didn't raise but also drew then he is really in a bad position. 
						else if (current_raises <= 0 && opponentDrew > 0) {
							this.breakWeakBadugi = false; //Don't break the badugi.
							ret = (highestRank <= 12) ? 1:0;
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
							ret = (last_raise_count == 0) ? ((opponentDrew >= 2) ? 1:((highestRank <= 12) ? 1:0)):1;
						}
						else {
							ret = (last_raise_count == 0) ? ((highestRank <= 10) ? 1:0):((highestRank <= 11) ? 1:0);
						}
					break;
					case 2: //Two drawing rounds left.
						//If the opponent raised and also drew...
						if (current_raises > 0 && opponentDrew > 0) {
							this.breakWeakBadugi = false; //Do not break the badugi.
							ret = (opponentDrew >= 2) ? ((counter >= 2) ? 0:1):((counter >= 1) ? 0:1);
						}
						//Opponent raised while drawing nothing. 
						else if (current_raises > 0 && opponentDrew <= 0) {//Considers whether to break a badugi here based on previous betting actions.
							//If he didn't raise last round. It might be a bluff or he might have a weak badugi and is trying to push me off.
							if (last_raise_count == 0) {
								this.breakWeakBadugi = (counter >= 2) ? true:false;
								ret = (counter >= 1) ? 0:1; //Raise only when the badugi I hold doesn't have a royal.
							}
							else { //If he raised last betting round then he might have a quality 3 card hand or a good badugi. 
								if (counter >= 2) { //I have 1 or more royals.
									breakWeakBadugi = true; //probably worth breaking a high ranked badugi.
									ret = 0;
								}
								else {//I don't have a face card
									breakWeakBadugi = false; //Do not break the badugi.
									ret = (highestRank <= 9) ? 1:0; //If my highest card is less than 9 i'll raise him. Else call him. 
								}
							}
						}
						//If the opponent didn't raise but also drew. Clear sign he is weak atm.
						else if (current_raises <= 0 && opponentDrew >0) {
							this.breakWeakBadugi = false; //Do not break the badugi.
							ret = (counter >= 2) ? 0:1; //call when the badugi has 2 or more face cards. Otherwise raise because he is weak. 
						}
						//Opponent didn't raise but also didn't draw.
						else {
							this.breakWeakBadugi = false; //Dont break the badugi.
							ret = (last_raise_count == 0) ? ((highestRank <= 9) ? 1:0):((highestRank <= 10) ? 1:0);
						}
					break;
					case 3: //Three drawing rounds left...
						this.breakWeakBadugi = false; //Dont break the badugi.
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
							ret = (last_raise_count == 0 ) ? ((highestRank <= 12) ? 1:0): 1/*((highestRank <= 12) ? 1:0)*/;
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
							this.breakWeakBadugi = (last_raise_count == 0) ? ((highestRank == 13) ? true:false): ((highestRank >= 12) ? true:false);//(highestRank == 13) ? true:false;
							ret = (last_raise_count == 0) ? ((highestRank <= 11) ? 1:0) : ((highestRank <= 9) ? 1:0);
						}
						else if (current_raises <= 0 && opponentDrew > 0) {
							ret = (last_raise_count == 0) ? ((highestRank <= 12) ? 1:0):/*((highestRank <= 12) ? 1:0)*/1;
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
					case 1: //1 draw left. To flee or not to flee...
						if (current_raises > 0 && opponentDrew > 0) {
							//He shows strength but also that he is weak.
							if (last_raise_count == 0) {
								//The more cards he draws, the more likely he is bluffing.
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
							ret = (last_raise_count == 0) ? ((highestRank <= 10) ? 1:0):((highestRank <= 11) ? 1:0);
						}
						else {
							//Could be bluffing a pat hand or could have a poor badugi and so be more careful here.
							this.snow = false;
							ret = (last_raise_count == 0) ? ((highestRank <= 11) ? 0:-1):((highestRank <= 10) ? 0: -1);
						}
					break;
					case 2: // 2 draws left. 
						
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
							if (last_raise_count == 0) {
								this.snow = true;
								ret = (highestRank <= 11) ? 1:0;
							}
							else {
								this.snow = false;
								ret = (highestRank <= 10) ? 1:0;
							}
						}
						else {
							this.snow = false;
							ret = (last_raise_count == 0) ? ((highestRank < 9) ? 1:0):((highestRank < 8) ? 1:0);
						}
						
					break;
					case 3: //Starting hand. When out of position, play all three card hands below 8
						if (current_raises == 0) { 
							this.snow = (highestRank >= 11 || ranks[1] >= 9) ? false:true; 
							ret = (highestRank <= 11) ? 1:0; 
						}
						else {
							this.snow = false;
							ret = (highestRank <= 8) ? 1:0;
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
						this.snow = false;//(current_raises == 0) ? true:false;
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
							ret = 1;//(highestRank <= 6) ? 1:0;//(opponentDrew >= 1 || highestRank <= 2) ? 0:-1;
						}
						else {
							return -1;
						}
					break;
					case 2:
					
						if (current_raises > 0 && opponentDrew > 0) {
							this.snow = false;
							if (last_raise_count == 0) {
								ret = (opponentDrew >= 2) ? ((highestRank <=8)? ((current_raises > 1) ? 0:1):0):((highestRank <= 7)?0:-1);
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
							this.snow = (highestRank >= 6) ? false:true;
							ret = (highestRank <= 8) ? 1:0;
						}
						else {
							this.snow = false;
							ret = (highestRank <= 6) ? 0:-1;
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
							return -1;
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
							ret = (snow) ? 1:0;
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
							ret = -1;
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
						if (current_raises == 0) {
							this.snow = true;
							ret = 1;
						}
						else {
							this.snow = false;
							ret = (highestRank <= 5) ? 0:-1;
						}
					break;
				}
				return ret;
				
			}else {
				this.snow = false;
				return -1; //stub
			}
		}

	}
	public List<Card> drawingAction(int drawsRemaining, BadugiHand hand, int pot, int dealerDrew) {
		this.last_raise_count = this.current_raises;
		this.current_raises = 0;
		int hand_size = hand.getActiveCards().size();
		int[] ranks = hand.getActiveRanks();
		int counter = 0;
		for (int i =0; i < ranks.length; i++) {
			if (ranks[i] >= 9) {
				counter++;
			}
		}
		if (hand_size == 4) {
			//Need to add if agent is willing to break a badugi given the circumstances.
			if (this.breakWeakBadugi) {
				List<Card> toDiscard = new ArrayList<Card>(hand.getInactiveCards());
				toDiscard.add(hand.getActiveCards().get(0));
				return toDiscard;
			}
			else {
				return hand.getInactiveCards();
			}
		}
		else {
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
	}
	public void showdown(BadugiHand yourHand, BadugiHand opponentHand) {

	}
	public String getAgentName(){
	  return "CHICKEN OR BEEF!?!?";
	}
	public String getAuthor() {
		return "Datiles, Ned";
	}  
}