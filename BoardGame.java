//BoardGame it extends the abstract class Game.
public class BoardGame extends Game {

   private int minPlayers; // the minimum players for the game
   private int maxPlayers; // the maximum players for the game

   public BoardGame(String title, String genre , int releaseYear , int minPlayers , int maxPlayers){ // a constructor that initializes game's information + maxPlayers and minPlayers
      super(title , genre , releaseYear);
      this.minPlayers=minPlayers;
      this.maxPlayers=maxPlayers;
   }


   public int getMinPlayers(){ // getter for minimum players 
      return minPlayers;
   }

   public int getMaxPlayers(){ // getter for the maximum players
      return maxPlayers;
   }

   public void setMinPlayers(int minPlayers){  //setter for the minimum players
      this.minPlayers=minPlayers;
   }

   public void setMaxPlayers(int maxPlayers){ // setter for the maximum players
      this.maxPlayers=maxPlayers;
   }

   public String getGameType() { //  implementation of the abstract method from class Game
      return "Board Game";
   }


   public void displayDetails() {  // display the gamé's information + minPlayers and the MaxPlayers
      super.displayDetails();
      System.out.println("Players: " + minPlayers + " - " + maxPlayers);
   }


   public String toString() { // return a string representation for game information + minPlayers and the MaxPlayers
      return super.toString() + " , Players: " + minPlayers + "-"  + maxPlayers ;
   }
}