// Online game class that extends VideoGame
public class OnlineGame extends VideoGame{

   private String serverRegion; // the server region of the game
   private boolean isMultiplayer; // its shows if the game is single player or more

   public OnlineGame(String title, String genre , int releaseYear, String platform , String developer, String serverRegion , boolean isMultiplayer) // constructor that initializes video game's information + serverRegion and isMultiplayer
   {
      super(title, genre , releaseYear , platform , developer);
      this.serverRegion=serverRegion ;
      this.isMultiplayer=isMultiplayer;
   }

   public String getServerRegion(){ // getter for serverRegion
      return serverRegion;
   }
   public void setServerRegion(String serverRegion){ //setter for the serverRegion
      this.serverRegion=serverRegion; 
   }

   public String getGameType(){ //implementation of the abstract method from class Game
      return " Online Game " ;
   }

   public void displayDetails(){ //display the video gamé's information + serverRegion and isMultiplayer
      super.displayDetails();
      System.out.println("Server Region: " + serverRegion);
      System.out.println("Multiplayer:" + isMultiplayer);
   }

   public String toString() { // return string representation for video game's information + serverRegion and isMultiplayer
      return super.toString() + " , Server: " + serverRegion + " , Multiplayer: " + isMultiplayer ;
   }
}