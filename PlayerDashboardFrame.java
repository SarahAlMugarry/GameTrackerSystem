import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayerDashboardFrame extends JFrame implements ActionListener { // player's game collection

   JButton add, remove, search, view, back; // buttons
   JTextField title; // text field for the title
   JTextArea area; // area to display the collection
   JPanel p1, p2; // two panels
   Player currentPlayer; // the current user account object
   GameLibrary library; // gamelibrary object

   public PlayerDashboardFrame(Player currentPlayer, GameLibrary library) {
   
      this.currentPlayer = currentPlayer;
      this.library = library;
   
      setTitle(currentPlayer.getName() + "'s Dashboard"); // title
      setSize(800, 550); // size
      setLocation(200, 100); // position
      setLayout(null); // layout manual
      setResizable(false); // no resizing
   
      JLabel head = new JLabel(currentPlayer.getName() + "'s Collection"); // head label
      head.setFont(new Font("Segoe UI", Font.ITALIC, 26)); // font
      head.setBounds(50, 20, 500, 50); // position
      add(head);
   
      p1 = new JPanel(null); // the 1st panel: managing the collection
      p1.setBorder(BorderFactory.createTitledBorder("Manage Collection"));
      p1.setBounds(20, 80, 350, 370);
      add(p1);
   
      p2 = new JPanel(null); // the 2nd panel: viewing the collection
      p2.setBorder(BorderFactory.createTitledBorder("View Games"));
      p2.setBounds(400, 80, 350, 420);
      add(p2);
   
      JLabel l = new JLabel("Game Title:"); // game title label
      l.setBounds(20, 50, 150, 20);
      p1.add(l);
   
      title = new JTextField(); // the title input field 
      title.setBounds(120, 50, 180, 20);
      p1.add(title);
   
   // the buttons
      add = button(p1, "Add to My List", 120);
      remove = button(p1, "Remove from My List", 160);
      search = button(p1, "Search My List", 200);
   
      area = new JTextArea(); // text area
      JScrollPane scroll = new JScrollPane(area); // scroll inside the text field
      scroll.setBounds(25, 25, 300, 300);
      p2.add(scroll);
   
      view = button(p2, "View My Collection", 340); // view button
   
      back = new JButton("Back"); // back button
      back.setBounds(20, 460, 100, 30);
      add(back);
      back.addActionListener(this);
   }

   public JButton button(JPanel p, String name, int y) { // a method made to create buttons
      JButton b = new JButton(name); // creating
      b.setBounds(50, y, 200, 30); // setting position
      p.add(b); // adding the button to its paneln (parameter)
      b.addActionListener(this);
      return b;
   }

   public void actionPerformed(ActionEvent e) { // the button clicking handler
   
      if (e.getSource() == add) { // the add game button
         String searchTitle = title.getText(); // gets the title from its text field
         
         // checking if the game exists in the main library
         Game g = library.searchGame(searchTitle);
         
         if (g != null) { // if it exists
            if (currentPlayer.addGame(g)) { // adds it to player collection
               currentPlayer.saveCollectionToFile(currentPlayer.getPlayerID() + "_collection.dat"); // save the collection to files
               JOptionPane.showMessageDialog(this, "Game added to your collection!"); // approved message
            } else {
               JOptionPane.showMessageDialog(this, "Game is already in your list."); // when the game is already added
            }
         } else {
            JOptionPane.showMessageDialog(this, "Game not found in the Library. Add it to the library first!");
         }
      }
   
      if (e.getSource() == remove) { // remove game button
         if (currentPlayer.removeGame(title.getText())) { // remove game from collection
            currentPlayer.saveCollectionToFile(currentPlayer.getPlayerID() + "_collection.dat"); // updated saving
            JOptionPane.showMessageDialog(this, "Game removed from your collection.");
         } else {
            JOptionPane.showMessageDialog(this, "Game not found in your collection.");
         }
      }
   
      if (e.getSource() == search) { // search game button
         Game g = currentPlayer.searchGame(title.getText()); // searching
         if (g != null) { // when it exist
            JOptionPane.showMessageDialog(this, "Found in your collection:\n" + g.toString()); // showing its details
         } else {
            JOptionPane.showMessageDialog(this, "Game not found in your collection."); // a suitable message if the game doesn't exist
         }
      }
   
      if (e.getSource() == view) { // view button
         area.setText(""); // empty text area
         String details = currentPlayer.getCollectionDetails(); // get the collection's details
         area.setText(details); // showing it
      }
   
      if (e.getSource() == back) { // back button
         setVisible(false); // closing frame
      }
   }
}