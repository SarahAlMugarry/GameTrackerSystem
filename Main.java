import java.io.*;

public class Main {

   static GameLibrary library = new GameLibrary(); // create a gemelibrary obj
   
   public static void main(String[] args) {
   
      File f = new File("games.dat"); // file obj for game data
   
      if (f.exists()) { // checks if the saved game file exist
         try {
            library.loadFromFile("games.dat"); // loads the saved fames
         } catch (Exception e) {
            System.out.println(e.getMessage()); // error message if loading fails
         }
      } else {
         // default games
         VideoGame v1 = new VideoGame("FIFA", "Sports", 2023, "PS5", "EA Sports");
         OnlineGame o1 = new OnlineGame("Fortnite", "Battle Royale", 2017, "PC", "Epic Games", "Middle East", true);
         BoardGame b1 = new BoardGame("Monopoly", "Strategy", 1935, 2, 6);
      
         try {
         // adds the default game to the library
            library.addGame(v1);
            library.addGame(o1);
            library.addGame(b1);
         } catch (DuplicateGameException e) {
            System.out.println(e.getMessage());
         }
      }
      
      Player[] players = new Player[5]; // array to store players (5 max)
      int playerCount = 0; // var to count players
      File pf = new File("players.dat");
      
      if (pf.exists()) { // checks if the player file exist
         try {
            playerCount = library.loadPlayersFromFile("players.dat", players);
         } catch (Exception e) {
            System.out.println(e.getMessage());
         }
      }
      
      LoginFrame login = new LoginFrame(library, players, playerCount); // opens the login window
      login.setVisible(true);
   }
}