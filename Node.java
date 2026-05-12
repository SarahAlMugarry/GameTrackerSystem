import java.io.Serializable;

public class Node implements Serializable {

   private Game data;   //stores one Game object
   private Node next;   //reference to the next node in the list
   
   //constructor
   public Node(Game data) {
      this.data = data;
      this.next = null;
   }
   
   //getter and setter for data
   public Game getData() {
      return data;
   }
   
   public void setData(Game data) {
      this.data = data;
   }
   
   //getter and setter for the next node
   public Node getNext() {
      return next;
   }
   
   public void setNext(Node next) {
      this.next = next;
   }
}