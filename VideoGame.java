// VideoGame class it extends the abstract class Game
public class VideoGame extends Game{

   protected String platform ; // the platofrm of the game ( can be accessible to the  Online class)
   protected String developer; // the developer of the game ( can be accessible to the  Online class)


   public VideoGame(String title , String genre , int releaseYear , String platform, String developer){ // constructor that initializes the game's information + the platform and developer
      super(title, genre , releaseYear);
      this.platform=platform;
      this.developer=developer;
   }

   public String getGameType(){ // implementation of the abstract method from class Game
      return "Video Game";
   }
   public String getPlatform(){ // getter for the game's platform
      return platform;
   }

   public String getDeveloper(){ // getter for the  game's developer
      return developer;
   }

   public void setPlatform(String platform){ // setter for the game's platform
      this.platform=platform;
   }

   public void setDeveloper(String developer){ // setter for the game's developer
      this.developer=developer;
   }

   public void displayDetails(){ // its display the game' s information + platofrm and developer
      super.displayDetails();
      System.out.println("Platform: " + platform);
      System.out.println("Developer: " + developer);
   }

   public String toString(){
      return super.toString() + " , Platform : " + platform + ", Developer : " + developer ; // return a string representation for the game's information and the platform and developer
   }
}