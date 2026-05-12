/*
Review:
- Review written by a certain player

   - Composition with Game
 */
 
import java.util.*;
import java.io.Serializable;

public class Review implements Serializable {

   private transient Scanner input = new Scanner(System.in);
   
   private String reviewerName; // name of the player writing the review
   private double score; // numeric rating: [0.0 to 10.0]
   private String comment; // written description or opinion about the game
    
    // Empty Constructor: new Review
   public Review(String reviewerName, double score, String comment) {
      this.reviewerName = reviewerName;
      this.comment = comment;
       
      boolean valid = false;
      while (!valid) {
         try {
            if (score < 0.0 || score > 10.0) {
               throw new InvalidRatingException(); // unchecked exception
            }
            this.score = score;
            valid = true; // valid input, exit loop
         } catch (InvalidRatingException e) {
            System.out.println(e.getMessage());
            System.out.print("Please enter a valid rating (0.0 to 10.0): ");
                // re-ask for correct rating
            score = input.nextDouble();
         }
      }
   }

   // returns the name of the reviewer
   public String getReviewerName() {
      return reviewerName;
   }

   // returns the game score given in this review
   public double getScore() {
      return score;
   }

   // returns the written comment
   public String getComment() {
      return comment;
   }

   // reviewerName
   public void setReviewerName(String reviewerName) {
      this.reviewerName = reviewerName;
   }

    // game score
   public void setScore(double score) {
      if (score < 0.0) {
         this.score = 0.0; // minus
      } else if (score > 10.0) {
         this.score = 10.0; // above 10
      } else {
         this.score = score;
      }
   }

   // comment
   public void setComment(String comment) {
      this.comment = comment;
   }
    
    // returns a short report of the review
   public String toString() {
      return "  Reviewer : " + reviewerName   + "\n" + "  Score    : " + score + " / 10" + "\n" + "  Comment  : " + comment;
   }
}