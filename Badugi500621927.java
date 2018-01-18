
import java.util.*;


public class Badugi500621927 implements BadugiPlayer {
    private int position;
    private int totalRaises;
    private int x;
    private int y;
     private Random rng = new Random();
     private int z = 0;
      private int i = 0;
    private int score = 0;
    
    private static String[][] examples = {
        // 0 draws remaining
        { "Kh7h6d5c", "9h8d7s6c", "8h7d6s5c", "7h6d5s4c", "6h5d4s3c" },
        
        // 1 draw remaining
        { "Kh9h8s7d", "Kh7h6d5c", "9h8d7s6c", "8h7d6s5c", "7h6d5s4c" },
        
        // 2 draws remaining
        { "KhQh3h2s", "Kh9h8s7d", "Kh7h6d5c", "KhQdJsTc", "9h8d7s6c" },
        
        // 3 draws remaining
        { "KhQh8h4s", "KhQh3h2s", "Kh9h8s7d", "Kh7h4d3s", "KhQdJsTc" }
       
    };
    
    private static BadugiHand[][] hands = new BadugiHand[4][5];
    static {  
        for(int a = 0; a < 4; a++) {
            for(int b = 0; b < 5; b++) {
                hands[a][b] = new BadugiHand(examples[a][b]);
            }
        }
    }
    
    
   public void startNewHand(int position, int handsToGo, int currentScore) 
    {
        this.position = position; totalRaises = 0;
        z = 0; i = 0; y = 0; x = 0;
        if(currentScore > score)
       {
           i++;
           
           }
        
        score = currentScore;
    }

    public int bettingAction(int drawsRemaining, BadugiHand hand, int bets, int pot, int toCall, int opponentDrew) {
     int length = hand.getActiveRanks().length;
     int first = hand.getActiveRanks()[1];
      int z = 0;
      int x = 0;
       if(length == 4)
     {
     if(drawsRemaining == 3)
     {
        if( position == 1)
         {
           if(toCall> 0)
             {
               z++;
             }
           
             
             
             
             if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
             
             
           if(toCall > 0 && opponentDrew>= 1 && first<= 8) { x = +1;}
           //if(first >= 11) {x = 0;} 
           //if(toCall > 0) { x= 0;}
           if (toCall >= 0 && first < 12){x = 0;}
           if (toCall < 0 && first <= 12 ) {x = +1;}
           if (toCall <= 0 && first > 12){ x = +1;}
           if (toCall <= 0 && first <= 12) {x = 0;}
        
           
           
           
           if(i >= 1) { x = +1;}
           if(z>=1 && toCall <= 0) { x = +1;}
           if(opponentDrew >=2 ) { x = +1;}
           if(opponentDrew >= 2 && first < 11) { x = +1;}
          // if(hand.compareTo(hands[drawsRemaining][bets]) > 0) {x = +1;}
           else x = +1;  
         }
        if(position == 0)
         {
            if(toCall> 0)
               {
               z++;
                   }
                  
            //if(first >= 10) {x =  +1;}
            if(toCall > 0 && opponentDrew >= 1) { x = 0;}
            if(z>=1 && toCall <= 0) { x = +1;}
            if(i >= 1) { x = +1;}
           // if(toCall <= 0) { x = 0;}
            if (toCall <= 0 && first < 8) {x = +1;}
            if (toCall <= 0 && first >= 8) {x = 0;}
            //if (toCall <= 0 && first <=6){ x = 0;}
       
             if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
            
             if(toCall <= 0 && opponentDrew >= 2 && first <= 10) { x = +1;}
           // if(toCall <= 0  && first > 10) { x = 0;}
            
           
           
           
           
          // if(toCall > 0 && first > 10) { x = 0;}
              //  if( toCall > 0 && first <= 10) { x = +1;}
           // if (toCall > 0 && first <= ) {x = +1;}
            if(opponentDrew >=2 ) { x = +1;}
            if(opponentDrew >= 2 && first < 10) { x = +1;}
            if( first<=5 && i>= 1) {  x = +1;}
            if(z>=1 &&   first<= 7 ) { x = 0;}
           // if(hand.compareTo(hands[drawsRemaining][bets]) < 0) {x = 0;}
            else x = +1;
          }
     }
     if(drawsRemaining == 2)
     {
        if( position == 1)
         {
           if(toCall> 0)
             {
               z++;
             }
           
           //if(first >= 11) {x = +1;} 
           //if(toCall >0) { x = 0;}
           //if(toCall <= 0) { x = 0;}
           if(i >= 1) { x = +1;}
           if(z>=1 && toCall <= 0) { x = +1;}
           
           //if(toCall > 0 && opponentDrew>= 2 && first<= 12) { x = +1;}
           if(toCall > 0 && opponentDrew>= 1 && first<= 8) { x = +1;}
           //if(first >= 11) {x = 0;} 
           //if(toCall > 0) { x= 0;}
           if (toCall >= 0 && first < 12){x = 0;}
           if (toCall < 0 && first <= 12 ) {x = +1;}
           if (toCall <= 0 && first > 12){ x = +1;}
           if (toCall <= 0 && first <= 12) {x = 0;}
           
           
           
           
           
           
            if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
           
           
           if(opponentDrew >=2 ) { x =  +1;}
           if(opponentDrew >= 2 && first < 11) { x = +1;}
          // if(toCall > 0 && opponentDrew>= 2 && first<= 5) { x = +1;}
           //if (opponentDrew<=0 && toCall >= 0 && first <= 6) { x = +1;}
          // if(hand.compareTo(hands[drawsRemaining][bets]) > 0) {x = +1;}
          else x = +1;  
         }
         if(position == 0)
         {
            if(toCall> 0)
              {
              z++;
              }
            //if(first >= 10) {x = +1;}
            if(toCall > 0 && opponentDrew >= 1) { x =  0;}
            if(z>=1 && toCall <= 0) { x = +1;}
            //if(toCall <= 0) { x = 0;}
            if(i >= 1) { x =  +1;}
            if(opponentDrew >=2 ) { x = +1;}
            if(opponentDrew >= 2 && first < 10) { x = +1;}
            
             if(toCall <= 0 && opponentDrew >= 2 && first <= 10) { x = +1;}
            // if(toCall <= 0  && first > 10) { x = 0;}
             
             if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
            
             if(toCall > 0 && first >= 8) { x = 0;}
                 if( toCall > 0 && first < 7) { x = +1;}
                 
                  //if(toCall <= 0  && first > 10) { x = 0;}
            
           //if(toCall > 0 && first > 10) { x = 0;}
               // if( toCall > 0 && first <= 10) { x = +1;}
                if (toCall <= 0 && first < 8) {x = +1;}
            if (toCall <= 0 && first >= 8) {x = 0;}
                
            if (opponentDrew >= 2 && toCall <= 0 && first <= 8) {x = +1;}
            if (opponentDrew>=2 && toCall >=0 && first <= 8) { x = +1;}
            if (opponentDrew ==1 && toCall >=0 && first <= 8) { x = 0;}
            if (opponentDrew ==1 && toCall <= 0 && first <= 8){ x = 0;}
            if (opponentDrew ==0 && toCall <= 0 && first <= 8){ x = 0;}
            if (opponentDrew ==0 && toCall <= 0 && first <= 8) {x = 0;}
            //if(hand.compareTo(hands[drawsRemaining][bets]) < 0) {x = 0;}
            else x = +1;
        }
        }
     if(drawsRemaining == 1)
        {
         if( position == 1)
         {
           if(toCall> 0)
            {
               z++;
            }
           
           //if(first >= 11) {x =  +1;} 
          // if(toCall >0) {  x = 0;}
          // if(toCall <= 0) { x =  0;}
           if(i >= 1) { x = +1;}
           if(z>=1 && toCall <= 0) { x =  +1;}
           if(opponentDrew >=2 ) {x = +1;}
           if(opponentDrew >= 2 && first < 11) { x = +1;}
          
           
           
           //if(toCall > 0 && opponentDrew>= 2 && first<= 12) { x = +1;}
           if(toCall > 0 && opponentDrew>= 1 && first<= 8) { x = +1;}
           //if(first >= 11) {x = 0;} 
           //if(toCall > 0) { x= 0;}
           if (toCall >= 0 && first < 12){x = 0;}
           if (toCall < 0 && first <= 12 ) {x = +1;}
           if (toCall <= 0 && first > 12){ x = +1;}
           if (toCall <= 0 && first <= 12) {x = 0;}
           
           
           
            if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
            
           if(z>=1 && opponentDrew>= 2 && first<= 5) { x = +1;}
           //if (toCall >= 0 && first <= 6) { x = +1;}
           if(z>=1 && opponentDrew>= 2 && first<= 5) { x = +1;}
          // if(hand.compareTo(hands[drawsRemaining][bets]) > 0) {x = +1;}
           else x = +1;  
        }
        if(position == 0)
         {
           if(toCall> 0)
                  {
              z++;
                   }
           
          // if(first >= 10) {x = +1;}
           if(toCall > 0 && opponentDrew >= 1) { x = 0;}
           if(z>=1 && toCall <= 0) { x = 0;}
          // if(toCall <= 0) { x = 0;}
           if(i >= 1) { x = +1;}
           
           
           
            if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
           
            if(toCall <= 0 && opponentDrew >= 2 && first <= 10) { x = +1;}
            
            // if(toCall <= 0  && first > 10) { x = 0;}
            
          // if(toCall > 0 && first > 10) { x = 0;}
                //if( toCall > 0 && first <= 10) { x = +1;}
            
            if (toCall <= 0 && first < 8) {x = +1;}
            if (toCall <= 0 && first >= 8) {x = 0;}
            
          //  if(toCall > 0 && first >= 8) { x = 0;}
                 //if( toCall > 0 && first < 7) { x = +1;}
           if(opponentDrew >=2 ) { x =  +1;}
           if(opponentDrew >= 2 && first < 10) { x = +1;}
           if (toCall <= 0 && first <= 8){ x = +1;}
           //if(hand.compareTo(hands[drawsRemaining][bets]) < 0) {x = 0;}
           else x = +1;
            }
        }
     if(drawsRemaining == 0)
     {
        
         //if (opponentDrew <= 0 && toCall > 0 && first > 10){ x = -1;}
        // if (opponentDrew <= 0 && toCall > 0 && first <= 10 ){ x = 0;}
        // if (opponentDrew <= 0 && toCall > 0  && first < 6) { x = +1;}
        // if (opponentDrew <= 0 && toCall <= 0){ x = 0;}
         
         if(position == 1)
         {
          if(toCall > 0 && first >= 10) { x = 0;}
            if(toCall > 0 && z >= 1 && first >= 10) { x = -1;}
            if(first <= 10 ){ x = +1;}
         
          if (toCall <= 0 && first < 8) {x = +1;}
            if (toCall <= 0 && first >= 8) {x = 0;}
            
         if (opponentDrew > 0 && toCall <= 0  && first >= 10) { x = 0;}
         
         if (opponentDrew > 0 && toCall <= 0 && first < 10) { x = +1;}
         
         if (opponentDrew > 0 && toCall > 0 && first >= 6) { x = 0;}
         if (opponentDrew > 0 && toCall > 0 && first < 6) { x = +1;}
         if(opponentDrew >= 2 && first < 10) { x = +1;}
         if(toCall > 0 && opponentDrew >= 1) { x = 0;}
         if(z>=1 && toCall <= 0) { x = +1;}
         if(opponentDrew >=2 ) { x = 0;}
        // if(first >= 10) {x = -1;}
         //if(hand.compareTo(hands[drawsRemaining][bets]) < 0) {x = 0;}
         else x = +1;
          }
        
        if(position == 0)
        {
            
            if(toCall > 0 && opponentDrew >= 1) { x = 0;}
           if(z>=1 && toCall <= 0) { x = 0;}
          // if(toCall <= 0) { x = 0;}
           if(i >= 1) { x = +1;}
           
           
           
            if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
           
            if(toCall <= 0 && opponentDrew >= 2 && first <= 10) { x = +1;}
            
            // if(toCall <= 0  && first > 10) { x = 0;}
            
          // if(toCall > 0 && first > 10) { x = 0;}
                //if( toCall > 0 && first <= 10) { x = +1;}
            
            if (toCall <= 0 && first < 8) {x = +1;}
            if (toCall <= 0 && first >= 8) {x = 0;}
            
          //  if(toCall > 0 && first >= 8) { x = 0;}
                 //if( toCall > 0 && first < 7) { x = +1;}
           if(opponentDrew >=2 ) { x =  +1;}
           if(opponentDrew >= 2 && first < 10) { x = +1;}
           if (toCall <= 0 && first <= 8){ x = +1;}
           //if(hand.compareTo(hands[drawsRemaining][bets]) < 0) {x = 0;}
           else x = +1;
            
        }
        }
    }
      if(length == 3)
        {
            if(drawsRemaining == 3)
            {
                if(position == 1)
                {
                  if(toCall> 0)
                  {
                     z++;
                  }
               
                  if(toCall > 0 && opponentDrew>= 1 && first<= 8) { x = +1;}
                  //if(toCall > 0 && opponentDrew>= 2 && first<= 12) { x = +1;}
           //if(first >= 11) {x = 0;} 
           //if(toCall > 0) { x= 0;}
           if (toCall >= 0 && first < 12){x = 0;}
           if (toCall < 0 && first <= 12 ) {x = +1;}
           if (toCall <= 0 && first > 12){ x = +1;}
           if (toCall <= 0 && first <= 12) {x = 0;}
                  
                  
                  
                  
                  
                    
            if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
           
                  
                  //if(toCall >0) { x = 0;}
                 // if(toCall <= 0) { x = 0;}
                 // if(first >= 11) {x = +1;} 
                  if(i >= 1) { x = +1;}
                  if(z>=1 && toCall <= 0) { x = +1;}
                  if(opponentDrew >= 2 && first < 11) { x = +1;}
                  if(opponentDrew >=2 ) { x = +1;}
                 //if(z >= 1 && opponentDrew >= 2 && first <= 6) { x = +1;}//newhere
                  if(toCall > 0 && opponentDrew >= 1) { x = 0;}
                //  if(toCall > 0 && opponentDrew>= 2 && first<= 5) { x = +1;}
                  //if (toCall > 0 && first <=6) {x = 0;}
                 // if (toCall > 0 && first > 6) { x = -1;}
                //  if(hand.compareTo(hands[drawsRemaining][bets]) > 0) {x = +1;}
                 else x = 0;  
                 }
                if(position == 0)
                {
                    if(toCall> 0)
                  {
                     z++;
                   }
                 
                    if(toCall <= 0 && opponentDrew >= 2 && first <= 10) { x = +1;}
                
                     
            if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
           
                   
              //if(toCall <= 0  && first > 10) { x = 0;}
            
           //if(toCall > 0 && first > 10) { x = 0;}
                //if( toCall > 0 && first <= 10) { x = +1;}
                 //iftoCall <= 0 && first < 8) {x = +1;}
            //if ( toCall <= 0 && first >= 8) {x = 0;}
                //  if(first >= 10) {x = -1;}
                 // if(toCall <= 0) { x = 0;}
                  if(i >= 1) { x = +1;}
                  if(z>=1 && toCall <= 0) { x = +1;}
                  if(opponentDrew >= 2 && first < 10) { x = +1;}
                  if(opponentDrew >=2 ) { x = +1;}
                 
                  if(toCall > 0 && opponentDrew >= 1) { x = 0;}
                  if(toCall > 0 && opponentDrew>= 2 && first<= 5) { x = 0;}
                  if (first <= 6){ x = +1;}
                //if(hand.compareTo(hands[drawsRemaining][bets]) < 0) {x = 0;}
                  else x = 0;
                }
            }
            if(drawsRemaining == 2)
            {
                 if(position == 1)
                {
                 if(toCall> 0)
                  {
                     z++;
                  }
                 
                    
            if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
           if(toCall > 0 && opponentDrew>= 1 && first<= 8) { x = +1;}
                  
                  //if(toCall > 0 && opponentDrew>= 2 && first<= 12) { x = +1;}
           //if(first >= 11) {x = 0;} 
           //if(toCall > 0) { x= 0;}
           if (toCall >= 0 && first < 12){x = 0;}
           if (toCall < 0 && first <= 12 ) {x = +1;}
           if (toCall <= 0 && first > 12){ x = +1;}
           if (toCall <= 0 && first <= 12) {x = 0;}
             
             
             
             
             
                 //if(toCall >0) { x = 0;}
                // if(toCall <= 0) { x = 0;}
                 if(i >= 1) { x = +1;}
                 if(z>=1 && toCall <= 0) { x = +1;}
                 //if(first >= 11) {x = -1;}
                 if(opponentDrew >= 2 && first < 11) { x = +1;}
                 if(opponentDrew >=2 ) { x = +1;}
                 if(toCall > 0 && opponentDrew >= 1) { x = 0;}
                 //if( toCall > 0 && opponentDrew>= 2 && first<= 5) { x = +1;}
                 
                 
                 //if (toCall > 0 && first > 7) { x = -1;}
                 if (toCall > 0 && opponentDrew <= 0){ x = -1;}
                //if(hand.compareTo(hands[drawsRemaining][bets]) > 0) {x = +1;}
                 else x = 0;  
                }
                if(position == 0)
                {
                    if(toCall> 0)
                  {
                     z++;
                   }
                   
                   
                      if(toCall <= 0 && opponentDrew >= 2 && first <= 10) { x = +1;}
            if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
           
                   
                   // if(toCall <= 0  && first > 10) { x = 0;}
            
           //if(toCall > 0 && first > 10) { x = 0;}
                //if( toCall > 0 && first <= 10) { x = +1;}
                  
                 // if(first >= 10) {x = -1;}
                 // if(toCall >= 0) { x = 0;}
                  if(z>=1 && toCall <= 0) { x = +1;}
                  if(i >= 1) { x = +1;}
                  if(opponentDrew >= 2 && first < 10) { x = +1;}
                  if(opponentDrew >=2 ) { x = +1;}
                  if(toCall > 0 && opponentDrew >= 1) { x = 0;}
                  
                //  if (toCall <= 0 && first < 8) {x = +1;}
           // if (toCall <= 0 && first >= 8) {x = 0;}
                  
                  
                  //if(toCall > 0 && first >= 8) { x = 0;}
                 //if( toCall > 0 && first < 7) { x = +1;}
                 
                  if(toCall > 0 && opponentDrew>= 2 && first<= 5) { x = 0;}
                  if (toCall > 0 && first > 7) { x = -1;}
                  if (toCall > 0 && opponentDrew <= 0){ x = -1;}
                // if(hand.compareTo(hands[drawsRemaining][bets]) < 0) {x = 0;}
                   else x = 0;
                }
            }
            if(drawsRemaining == 1)
            {
                if(position == 1)
                {
                if(toCall> 0)
                  {
                     z++;
                  }
                  
                  
                    
            if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
           
             
             if(toCall > 0 && opponentDrew>= 1 && first<= 8) { x = +1;}
            // if(toCall > 0 && opponentDrew>= 2 && first<= 12) { x = +1;}
           //if(first >= 11) {x = 0;} 
           //if(toCall > 0) { x= 0;}
           if (toCall >= 0 && first < 12){x = 0;}
           if (toCall < 0 && first <= 12 ) {x = +1;}
           if (toCall <= 0 && first > 12){ x = +1;}
           if (toCall <= 0 && first <= 12) {x = 0;}
             
             
                
               // if(toCall >0) { x = 0;}
                //if(toCall <= 0) { x = 0;}
                if(z>=1 && toCall <= 0) { x = +1;}
                if(i >= 1) { x = +1;}
               // if(first >= 11) {x = -1;} 
                if(opponentDrew >= 2 && first < 11) { x = +1;}
                if(opponentDrew >=2 ) { x = +1;}
               
                if(toCall > 0 && opponentDrew >= 1) { x = 0;}
                if(opponentDrew >= 1 && toCall <=0) {x = +1;}
                
               
               if (toCall > 0 || first > 7){x = -1;}
               // if(toCall > 0 && opponentDrew>= 2 && first<= 5) { x = +1;}
              //  if(hand.compareTo(hands[drawsRemaining][bets]) > 0) {x = +1;}
                 else x = 0;  
               }
                if(position == 0)
                {
                 if(toCall> 0)
                  {
                     z++;
                   }
                
                    if(toCall <= 0 && opponentDrew >= 2 && first <= 10) { x = +1;}
                // if(first >= 10) {x = -1;} 
                // if(toCall <= 0) { x = 0;}
                 if(i >= 1) { x = +1;}
                 if(z>=1 && toCall <= 0) { x = +1;}
                 if(opponentDrew >= 2 && first < 10) { x = +1;}
                 if(opponentDrew >=2 ) {x = +1;}
                
                   
            if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
           
                 
                  //if(toCall <= 0  && first > 10) { x = 0;}
            
          // if(toCall > 0 && first > 10) { x = 0;}
                //if( toCall > 0 && first <= 10) { x = +1;}
                
                   // if (toCall <= 0 && first < 8) {x = +1;}
           // if (toCall <= 0 && first >= 8) {x = 0;}
            
                 if(toCall > 0 && opponentDrew >= 1) { x = 0;}
                 if(opponentDrew >= 1 && toCall <=0) {x = +1;}
                 if (toCall > 0 || first > 7){x = -1;}
                 //if(hand.compareTo(hands[drawsRemaining][bets]) < 0) {x = 0;}
                 else x = 0;
               }
              }
            if(drawsRemaining == 0)
            {
                if(position == 1)
                {
              if(toCall > 0 && first >= 10) { x = -1;}
            if(toCall > 0 && z >= 1 && first >= 10) { x = -1;}
            if(first < 10 ){ x = +1;}
         
                
                
                
                if(opponentDrew >= 2 && first < 10) { x = +1;}
                if(toCall > 0 && opponentDrew >= 1) { x = 0;}
                if(opponentDrew >=2 ) { x = 0;}
                if(z>=1 && toCall <= 0) {x = +1;}
                if (toCall > 0){ x = -1;}
                if(first >= 10) { x = -1;}
               // if(hand.compareTo(hands[drawsRemaining][bets]) < 0) {x = 0;}
                else x = +1;
            }
            if ( position == 0)
            {
                 if(toCall > 0 && first >= 10) { x = -1;}
            if(toCall > 0 && z >= 1 && first >= 10) { x = -1;}
            if(first < 10 ){ x = +1;}
         
                
                
                
                if(opponentDrew >= 2 && first < 10) { x = +1;}
                if(toCall > 0 && opponentDrew >= 1) { x = 0;}
                if(opponentDrew >=2 ) { x = 0;}
                if(z>=1 && toCall <= 0) {x = +1;}
                if (toCall > 0){ x = -1;}
                if(first >= 10) { x = -1;}
                //if(hand.compareTo(hands[drawsRemaining][bets]) < 0) {x = 0;}
                //if(hand.compareTo(hands[drawsRemaining][bets]) < 0) {x = 0;}
                else x = +1;
            }
             }
        }    
     
      if(length <= 2)
        {
             if (drawsRemaining == 3) 
             {
               if(toCall > 0)
                {
                  z++;
                 }
              
                 
                // if(position == 1 && toCall > 0 && opponentDrew>= 2 && first<= 12) { x = +1;}
                if(position == 1 && toCall > 0 && opponentDrew>= 1 && first<= 8) { x = +1;}
           //if(first >= 11) {x = 0;} 
           //if(toCall > 0) { x= 0;}
           if (position == 1 && toCall >= 0 && first < 12){x = 0;}
           if (position == 1 && toCall < 0 && first <= 12 ) {x = +1;}
           if (position == 1 && toCall <= 0 && first > 12){ x = +1;}
           if (position == 1 && toCall <= 0 && first <= 12) {x = 0;}
                 
                  if(position == 0 && toCall <= 0 && opponentDrew >= 2 && first <= 10) { x = +1;}
                 
            // if(position == 0 && toCall <= 0  && first > 10) { x = 0;}
            
           //if(position == 0 && toCall > 0 && first > 10) { x = 0;}
               // if(position == 0 && toCall > 0 && first <= 10) { x = +1;}
                   
            if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
           
              
               if(position == 0 && toCall <=0){ x =  0;}
              // if(hand.compareTo(hands[drawsRemaining][bets]) > 0) {x = +1;}
               if(i >= 1) { x = +1;}
               if( i >= 1 && first <= 10) {x = 0;}
              
              // if(toCall >0) { x = 0;}
               //if(toCall <= 0) {x = 0;}
              // if(first >= 11) {x = +1;}
               if(opponentDrew >= 2 && first < 11) { x = +1;}
               if(opponentDrew >=2 ) { x = +1;}
              // if(toCall > 0 && opponentDrew >= 1) { x = 0;}
               else x = 0;
             }
            if (drawsRemaining == 2) 
             {
                if(toCall> 0)
                  {
                     z++;
                   }
                
                   if(position == 1 && toCall > 0 && opponentDrew>= 1 && first<= 8) { x = +1;}
                   
                   // if(position == 1 && toCall > 0 && opponentDrew>= 2 && first<= 12) { x = +1;}
           //if(first >= 11) {x = 0;} 
           //if(toCall > 0) { x= 0;}
           if (position == 1 && toCall >= 0 && first < 12){x = 0;}
           if (position == 1 && toCall < 0 && first <= 12 ) {x = +1;}
           if (position == 1 && toCall <= 0 && first > 12){ x = +1;}
           if (position == 1 && toCall <= 0 && first <= 12) {x = 0;}
                 
                   
                
             //if(position == 0 && toCall <= 0  && first > 10) { x = 0;}
            
           //if(position == 0 && toCall > 0 && first > 10) { x = 0;}
                //if(position == 0 && toCall > 0 && first <= 10) { x = +1;}
           
           
                if(opponentDrew >=2 && toCall<=0){ x = 0;}
                //if(hand.compareTo(hands[drawsRemaining][bets]) > 0) {x = +1;}
                if(toCall > 0 && opponentDrew>= 2 && first<= 5) { x = +1;}
                
                   if(position == 0 && toCall <= 0 && opponentDrew >= 2 && first <= 10) { x = +1;}
                   
            if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
           
                 
                if(i >= 1) { x = +1;}
                if( i >= 1 && first <= 10) { x = 0;}
               // if(toCall >0) { x = 0;}
                //if(toCall <= 0) { x =  0;}
               // if(first >= 11) {x =-1;}
               //if (position == 0 && toCall <= 0 && first < 8) {x = +1;}
            //if (position == 0 && toCall <= 0 && first >= 8) {x = 0;}
               
                if(opponentDrew >= 2 && first < 11) { x = +1;}
                if(opponentDrew >=2 ) { x = +1;}
                //if(toCall > 0 && opponentDrew >= 1) {x = 0;}
                else x = 0 ;
              }
            if (drawsRemaining == 1) 
            {
                if(toCall> 0)
                  {
                     z++;
                  }
                  
                  if(position == 1 && toCall > 0 && opponentDrew>= 1 && first<= 8) { x = +1;}
               
                if(opponentDrew >= 1 && toCall <=0) {x = +1;}
                //if(hand.compareTo(hands[drawsRemaining][bets]) > 0) {x = +1;}
                if(toCall > 0 && opponentDrew>= 2 && first<= 5) { x = 0;}
                if(i >= 1) { x = +1;}
                if( i >= 1 && first <= 10) { x = 0;}
                
                       
             if(position == 0 && toCall <= 0  && first > 10) { x = 0;}
            
          // if(position == 0 && toCall > 0 && first > 10) { x = 0;}
               // if(position == 0 && toCall > 0 && first <= 10) { x = +1;}
                
                // if (position == 0 && toCall <= 0 && first < 8) {x = +1;}
            //if (position == 0 && toCall <= 0 && first >= 8) {x = 0;}
                // if(position == 0 && toCall <= 0 && opponentDrew >= 2 && first <= 10) { x = +1;}
                
            if(toCall <= 0 ) { x = +1;}
             if( first >= 10) { x = 0;}
             if(first < 10) { x = +1;}
             if( toCall > 0 ) { x = 0;}
           
                
                 if(position == 1 && toCall > 0 && opponentDrew>= 2 && first<= 12) { x = +1;}
           //if(first >= 11) {x = 0;} 
           //if(toCall > 0) { x= 0;}
           if (position == 1 && toCall >= 0 && first < 12){x = 0;}
           if (position == 1 && toCall < 0 && first <= 12 ) {x = +1;}
           if (position == 1 && toCall <= 0 && first > 12){ x = +1;}
           if (position == 1 && toCall <= 0 && first <= 12) {x = 0;}
                 
               // if(toCall >0) { x = 0;}
                //if(toCall <= 0) { x = 0;}
                
                //if(first >= 11) {x = -1;}
                if(opponentDrew >= 2 && first < 11) { x = +1;}
                if(opponentDrew >=2 ) { x = +1;}
                if(toCall > 0 && opponentDrew >= 1) { x = 0;}
                else x = 0;
            }
            if (drawsRemaining == 0) 
             {
                 if(position == 1)
                 {
              if(toCall> 0)
              {
                 z++;
                }
              if(opponentDrew > 0 || toCall <=0) { x = +1;}
             //if(hand.compareTo(hands[drawsRemaining][bets]) > 0) {x = +1;}
              if(i >= 1) { x = +1;}
              if( i >= 1 && first <= 10) { x =  0;}
              
              
              if(toCall > 0 && first >= 10) { x = -1;}
            if(toCall > 0 && z >= 1 && first >= 10) { x = -1;}
            if(first < 10 ){ x = +1;}
             if (position == 0 && toCall <= 0 && first < 8) {x = +1;}
            if (position == 0 && toCall <= 0 && first >= 8) {x = 0;}
          
            
              if(toCall >0) { x = 0;}
              if(toCall <= 0) { x = 0;}
              if(first >= 11) {x = -1;}
              if(opponentDrew >= 2 && first < 11) { x = +1;}
              if(opponentDrew >=2 ) { x = +1;}
              if(toCall > 0 && opponentDrew >= 1) { x =  0;}
              // if(hand.compareTo(hands[drawsRemaining][bets]) < 0) {x = 0;}
              else x = 0;
            }
            if(position == 0)
            {
                
                if(toCall> 0)
              {
                 z++;
                }
              if(opponentDrew > 0 || toCall <=0) { x = +1;}
             //if(hand.compareTo(hands[drawsRemaining][bets]) > 0) {x = +1;}
              if(i >= 1) { x = +1;}
              if( i >= 1 && first <= 10) { x =  0;}
              
              
              if(toCall > 0 && first >= 10) { x = -1;}
            if(toCall > 0 && z >= 1 && first >= 10) { x = -1;}
            if(first < 10 ){ x = +1;}
             if (position == 0 && toCall <= 0 && first < 8) {x = +1;}
            if (position == 0 && toCall <= 0 && first >= 8) {x = 0;}
          
            
              if(toCall >0) { x = 0;}
              if(toCall <= 0) { x = 0;}
              if(first >= 11) {x = -1;}
              if(opponentDrew >= 2 && first < 11) { x = +1;}
              if(opponentDrew >=2 ) { x = +1;}
              if(toCall > 0 && opponentDrew >= 1) { x =  0;}
              // if(hand.compareTo(hands[drawsRemaining][bets]) < 0) {x = 0;}
              else x = 0;
            }
              }
            }


  
        
    return x;
    
    }

    public List<Card> drawingAction(int drawsRemaining, BadugiHand hand, int pot, int dealerDrew) {
      int[] activeRanks = hand.getActiveRanks();
      int high_cards = 0;
        int i = 0;
         int first = hand.getActiveRanks()[1];

if(drawsRemaining >= 1)
         {
      if (hand.getActiveCards().size() == 4 || first > 6) {
   return hand.getInactiveCards();
  }
  else
  {
  while (i < activeRanks.length) {
   high_cards += (activeRanks[i] >= 9) ? 1:0;
   i++;
  }
  ArrayList<Card> discard = new ArrayList<Card>();
  for (int j = 0; j < high_cards; j++) {
   discard.add(hand.getActiveCards().get(j));
  }
  discard.addAll(hand.getInactiveCards());

  return discard;
}
}
else 
{
    return hand.getInactiveCards();
}


    
    }
    public void showdown(BadugiHand yourHand, BadugiHand opponentHand) {
    }

    public String getAgentName() {
        return "howdy";
    }

    public String getAuthor() {
        return "Adekanye, Isaiah";
    }

}

