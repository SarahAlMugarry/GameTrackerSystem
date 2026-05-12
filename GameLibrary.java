/*
GameLibrary:
 - Storage for all games in the tracker
 
    - Aggregation with Game
    - Implements Rateable
 */
 
import java.io.*;
 
public class GameLibrary implements Rateable {

   private static final int MAX_GAMES = 100;

   private Game[] games; // holds all game objects in the library
   private int count; // tracks how many games are currently stored

   // Empty Constructor
   public GameLibrary() {
      this.games = new Game[MAX_GAMES];
      this.count = 0;
   }
    
    // adds games to the library (case-insensitive & prevents duplicates)
   public boolean addGame(Game game) throws DuplicateGameException //it will be handled in main 
   {
      
      if (count >= MAX_GAMES) {//games in library reach maximum limit
         System.out.println("Library is full. Cannot add more games.");
         return false;
      }
    // check for duplicate titles
      for (int i = 0; i < count; i++) {
         if (games[i].getTitle().equalsIgnoreCase(game.getTitle())) {
          
            throw new DuplicateGameException();
         }
      }
    // reaches here when it made sure that game title is not duplicated
      games[count++] = game;
      System.out.println("Game \"" + game.getTitle() + "\" added to the library.");
      return true;
      
   }    
    // removes games from the library [by title (case-insensitive) & delete by shifting)
   public boolean removeGame(String title) {
      for (int i = 0; i < count; i++) {
         if (games[i].getTitle().equalsIgnoreCase(title)) {
         
            // shifting the remaining games
            for (int j = i; j < count - 1; j++) {
               games[j] = games[j + 1];
            }
            games[--count] = null;
            
            System.out.println("\"" + title + "\" is being removed from the library!");
            return true;
         }
      }
   
      System.out.println("\"" + title + "\" is not found in the library!");
      return false; // game not found
   }
   

      // searches for games in the library [by title (case-insensitive)] [recursion]
   public Game searchGame(String title) {
      return searchGameRecursive(title, 0);
   }

   private Game searchGameRecursive(String title, int index) {
      // base case
      if (index >= count) 
         return null;
   
      if (games[index].getTitle().equalsIgnoreCase(title)) {
         return games[index];
      }
   
      return searchGameRecursive(title, index + 1);
   }
    
    
    // displays all games stored in the library
   public void displayAllGames() {
      if (count == 0) { // while the library is empty
         System.out.println("The library is currently empty.");
         return;
      }
   
      System.out.println("========================================");
      System.out.println("   Game Library  (" + count + " games)");
      System.out.println("========================================");
   
      for (int i = 0; i < count; i++) {
         System.out.println("--- Game " + (i + 1) + " ---");
         games[i].displayDetails(); // polymorphism [every game's 'displayDetails()' is called (different display output based on every game type (VideoGame, BoardGame, etc...))]
         System.out.println();
      }
   }
    
    // calculates the average rating of the library's games [recursive]
   public double getAverageRating() {
      if (count == 0) 
         return 0.0;
   
      double total = sumRatingsRecursive(0);
      return total / count;
   }

   private double sumRatingsRecursive(int index) {
      // base case
      if (index >= count) 
         return 0;
   
      // recursive case
      return games[index].getRating() + sumRatingsRecursive(index + 1);
   }

   // returns the total number of games in the library
   public int getCount() {
      return count;
   }
    
    // returns a copy of the games array
   public Game[] getGames() {
      Game[] copy = new Game[count];
      for (int i = 0; i < count; i++) {
         copy[i] = games[i];
      }
      return copy; // returns an array of the stored Game objects
   }

    // returns a short report of the library 'games count' & 'avg rating'
   public String toString() {
      return "GameLibrary | Total Games : " + count + "\n" + "            | Avg Rating  : " + Math.round(getAverageRating() * 100.0);
   }
   
   
// File I/O [Library]
   public void saveToFile(String filename) { // save library to file
   
      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
      
         oos.writeInt(count); // save each count
         
         // going through the games and saving them each
         for (int i = 0; i < count; i++) {
            oos.writeObject(games[i]);
         }
         
         System.out.println("Library successfully saved to \"" + filename + "\".");
         
      } catch (IOException e) {
         System.out.println("Error: could not save the library. " + e.getMessage());
      }
   }


   public void loadFromFile(String filename) throws IOException { // load from library to file
   
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
      
         int savedCount = ois.readInt(); // read number of games
         count = 0;
         
         // going through games and loading them each
         for (int i = 0; i < savedCount; i++) {
            games[count++] = (Game) ois.readObject();
         }
         
         System.out.println("Library successfully loaded from \"" + filename + "\".");
         
      } catch (ClassNotFoundException e) {
         throw new IOException("File is incompatible with this version of the program.");
      }
   }


   public void savePlayersToFile(String filename, Player[] players, int playerCount) { // save players to file
   
      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
      
         oos.writeInt(playerCount); // save player count
         
         // saving players
         for (int i = 0; i < playerCount; i++) {
            oos.writeObject(players[i]);
         }
         
         System.out.println("Players successfully saved to \"" + filename + "\".");
         
      } catch (IOException e) {
         System.out.println("Error: could not save players. " + e.getMessage());
      }
   }

   public int loadPlayersFromFile(String filename, Player[] players) throws IOException { // load players from file
   
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
      
         int savedCount = ois.readInt(); // read number of players
         
         // loading players
         for (int i = 0; i < savedCount; i++) {
            players[i] = (Player) ois.readObject();
         }
         
         System.out.println("Players successfully loaded from \"" + filename + "\".");
         
         return savedCount;
         
      } catch (ClassNotFoundException e) {
         throw new IOException("Player file is incompatible with this version of the program.");
      }
      
   }
}