public class InvalidRatingException extends RuntimeException {
   public InvalidRatingException() {
      super("Invalid rating! The score must be between 0.0 and 10.0");
   }
}