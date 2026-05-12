public class DuplicateGameException extends Exception {
   
   public DuplicateGameException() {
      super("Game with same title already exsists in library");
   }
    
   public DuplicateGameException(String s) {
      super(s);
   }
}