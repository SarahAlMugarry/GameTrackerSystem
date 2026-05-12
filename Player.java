/*
Player:
 - User of the Game Tracker System
 
each player maintains their own personal list of games they have played

   - Aggregation with Game
   - Implements Rateable
*/
import java.io.*;

public class Player implements Rateable, Serializable {

   private Node head; //start of the linked list
   private String name; //players display name
   private String playerID; // the players id
   private int count; //tracks how mny games are currently stored


   public Player(String name, String playerID){
      this.name=name;
      this.playerID=playerID;
      this.head=null;
      this.count=0;
   }

   // adds a game to the linked list
   public boolean addGame(Game game) {
      //check if the game exists by going through all nodes until it finds same game name
      Node current=head;
      while(current!=null){
         if (current.getData().getTitle().equalsIgnoreCase(game.getTitle())) {
            System.out.println("\"" + game.getTitle() + "\" is already in " + name + "'s list.");
            return false;
         }
         current=current.getNext(); //goes to next one to check if name is the same
      }
   
      //create a new node for this game
      Node n = new Node(game);
      //insert at front of the list
      n.setNext(head);
      head=n;
      count++;
   
      System.out.println("\""+game.getTitle()+"\" was added to"+name+ "'s game list.");
      return true;
   }


    //removes a game from the linked list by title
   public boolean removeGame(String title){
      if (head==null) { //if its empty
         System.out.println("\"" +title +"\" was not found in "+name+"'s list.");
         return false;
      }
   
      //if game is head (the first one)
      if (head.getData().getTitle().equalsIgnoreCase(title)) {
         head=head.getNext();//the old head will be replaced because its getting deleted
         count--;
         System.out.println("\"" +title+"\" was removed from "+name+"'s list.");
         return true;
      }
   
      Node prev=head;
      Node current=head.getNext();
   
      while(current!=null) {//walks through nodes until it finds the game 
         if (current.getData().getTitle().equalsIgnoreCase(title)){
            prev.setNext(current.getNext());
            count--;
            System.out.println("\""+title+"\" was removed from "+name+"'s list.");
            return true;
         }
         prev=current; //keep walking through nodes
         current=current.getNext();
      }
   
      System.out.println("\""+title+"\" was not found in "+name+"'s list.");
      return false;
   }


   //searches for games in the players list
   public Game searchGame(String title) {
      return searchRecursive(head, title);
   }

   private Game searchRecursive(Node current, String title) {
      if(current==null) 
         return null;
      if (current.getData().getTitle().equalsIgnoreCase(title))
         return current.getData();
      return searchRecursive(current.getNext(), title);
   }


   //displays all games stored in the players list
   public void displayGames() {
      if(head ==null) { //if its empty means there are no games
         System.out.println(name+" has no games in their list yet");
         return;
      }

   
      System.out.println("========================================");
      System.out.println("   "+name+"'s Game List ("+count+" games)");
      System.out.println("========================================");
   
      Node current=head;
      int index=1;
      while(current!=null) {
         System.out.println("--- Game " + index + " ---");
         current.getData().displayDetails(); 
         System.out.println();
         current = current.getNext(); //loop through nodes and display details
         index++;
      }
   }


   //calculates the average rating of the games in the players list 
   public double getAverageRating() {
      if (head==null) 
         return 0; // if empty
      double total=sumRecursive(head); //start recursive sum
      return total/count;
   }


   private double sumRecursive(Node current) {
      if (current==null) 
         return 0;
      return current.getData().getRating()+sumRecursive(current.getNext());
   }


   //returns the players name
   public String getName() {
      return name;
   }

   //returns the players ID
   public String getPlayerID() {
      return playerID;
   }

   //returns the total number of games currently in the players list
   public int getCount() {
      return count;
   }

   //sets the players name
   public void setName(String name) {
      this.name = name;
   }

   //sets the players ID
   public void setPlayerID(String playerID) {
      this.playerID = playerID;
   }

   //returns details of player
   public String toString() {
      return "Player    : "+name+"\n"
           + "Player ID : "+playerID+"\n"
           + "Games     : "+count+"\n"
           + "Avg Rating: "+Math.round(getAverageRating()*100.0);
   }
   
   // File I/O [The Player's Personal Collection]
   public void saveCollectionToFile(String filename) {
      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
         oos.writeInt(count); 
         Node current = head;
         while (current != null) { 
            oos.writeObject(current.getData());
            current = current.getNext();
         }
         System.out.println(name + "'s collection successfully saved to \"" + filename + "\".");
      
      } catch (IOException e) {
         System.out.println("Error: could not save " + name + "'s collection. " + e.getMessage());
      }
   }

   public void loadCollectionFromFile(String filename) throws IOException {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
         int savedCount = ois.readInt();
            
         this.head = null;
         this.count = 0;
      
         for (int i = 0; i < savedCount; i++) {
            Game g = (Game) ois.readObject();
            this.addGame(g); 
         }
         System.out.println(name + "'s collection successfully loaded from \"" + filename + "\".");
      
      } catch (ClassNotFoundException e) {
         throw new IOException("File is incompatible with this version of the program.");
      }
   }
   
   // GUI
   public String getCollectionDetails() {
      if (head == null) {
         return name + " has no games in their list yet!";
      }
      
      String result = "";
      Node current = head;
      int index = 1;
      
      while (current != null) {
         result += "--- Game " + index + " ---\n";
         result += current.getData().toString() + "\n\n";
         current = current.getNext(); // loop through nodes
         index++;
      }
      return result;
   }
}