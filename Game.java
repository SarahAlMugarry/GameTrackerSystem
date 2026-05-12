import java.io.Serializable;

public abstract class Game implements Serializable {

   protected String title; //title of the game ( can be accessible by all games ) 
   protected String genre; //the genre of the game ( can be accessible by all games ) 
   protected int releaseYear; //the year the game was released ( can be accessible by all games ) 
   private Review [] reviews; //array of Review objects
   private int reviewCount; //counter ( the number of reviews currently added )

   public Game( String title , String genre , int releaseYear) //Constructor that initializes the Game with their information
   {
      this.title=title;
      this.genre=genre;
      this.releaseYear=releaseYear;
      this.reviews=new Review[10]; //initializes the review array with maximum capacity of 10
      this.reviewCount=0;
   }

   public abstract String getGameType(); // abstarct method that will be implemented by subclasses to specify the type of game

   public String getTitle() // getter for the game title
   {
      return title;
   }

   public String getGenre() // getter for the game genre
   {
      return genre;
   }

   public int getReleaseYear() // getter for the game release year
   {
      return releaseYear;
   }

   public void setTitle(String title){ // setter for the game title
      this.title=title;
   }

   public void setGenre(String genre){ // setter for the game genre
      this.genre=genre;
   }

   public void setReleaseYear( int releaseYear){ // setter for the game release year
      this.releaseYear=releaseYear;
   }

   public double getRating(){  // calculates the average score of the game based on all the reviews , returns 0 if there is no reviews
      if(reviewCount==0)
         return 0;
      double sum=0;
      for(int i=0 ; i<reviewCount; i++){
         sum+=reviews[i].getScore();
      }
      return sum / reviewCount;
   }

   public void addReview(Review r){   //Adds a new Review to the game's review list 
      if(reviewCount< reviews.length){
         reviews[reviewCount++] = r;       // check's if there is space in the array before adding.
      }
      else{
         System.out.println("Review list is full ");
      }
   }

   public void displayReviews(){    // it display all reviews that are currently stored for this game.
      System.out.println("Reviews for : " + title );
      if(reviewCount ==0){
         System.out.println("No reviews have been added for this game " );
      }
      else {
         for( int i=0 ; i<reviewCount; i++){
            System.out.println("Review " + (i+1) + ": ");
            System.out.println(reviews[i].toString() );
         }
      }
   }

   public void displayDetails(){  // prints the game's information 
      System.out.println("Title: " + title);
      System.out.println("Genre: " + genre);
      System.out.println("Release Year: " + releaseYear);
   }


   public String toString(){  // return a string representation of the game's information
      return "Title:" + title + ", Genre: " + genre + ", Release Year: " + releaseYear + ", Reviews: " + reviewCount;
   }
}