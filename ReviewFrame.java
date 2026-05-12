import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// frame for reviews and ratings
public class ReviewFrame extends JFrame implements ActionListener {

   JButton addReview, viewReviews, back; // buttons
   JTextField titleField, scoreField; // text
   JTextArea commentArea, displayArea; // text areas
   GameLibrary library; // gamelibrary objects
   Player currentPlayer; // the current logged in player

// constructor
   public ReviewFrame(Player currentPlayer, GameLibrary library) {
      
      // saving player and library
      this.currentPlayer = currentPlayer;
      this.library = library;
   
      setTitle("Game Reviews & Ratings"); // title
      setSize(800, 600); // size
      setLocation(200, 50); // position
      setLayout(null); // layout manual
      setResizable(false); // no resizing
   
     //heading
      JLabel head = new JLabel("Reviews & Ratings"); // heading label
      head.setFont(new Font("Segoe UI", Font.ITALIC, 26)); // font
      head.setBounds(50, 20, 400, 50); // position
      add(head);
   
   //adding reviews
      JPanel p1 = new JPanel(null);
      p1.setBorder(BorderFactory.createTitledBorder("Submit a Review"));
      p1.setBounds(20, 80, 350, 400);
      add(p1);
   
   // title label
      JLabel tLabel = new JLabel("Game Title:");
      tLabel.setBounds(20, 30, 100, 20);
      p1.add(tLabel);
   
   // title input
      titleField = new JTextField();
      titleField.setBounds(130, 30, 180, 20);
      p1.add(titleField);
   
   // score
      JLabel sLabel = new JLabel("Score (0-10):");
      sLabel.setBounds(20, 70, 100, 20);
      p1.add(sLabel);
   
   // score field
      scoreField = new JTextField();
      scoreField.setBounds(130, 70, 180, 20);
      p1.add(scoreField);
   
   // comment label
      JLabel cLabel = new JLabel("Comment:");
      cLabel.setBounds(20, 110, 100, 20);
      p1.add(cLabel);
   
   // comment text area
      commentArea = new JTextArea();
      commentArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
      commentArea.setBounds(20, 140, 300, 100);
      p1.add(commentArea);
   
   // review button
      addReview = new JButton("Save Review");
      addReview.setBounds(100, 260, 150, 30);
      p1.add(addReview);
      addReview.addActionListener(this);
   
   // displaying ratings
      JPanel p2 = new JPanel(null);
      p2.setBorder(BorderFactory.createTitledBorder("Library Ratings"));
      p2.setBounds(400, 80, 350, 400);
      add(p2);
   
   // displaying reviews
      displayArea = new JTextArea();
      JScrollPane scroll = new JScrollPane(displayArea); // scroll in the text area
      scroll.setBounds(20, 30, 310, 300);
      p2.add(scroll);
   
   // view review button
      viewReviews = new JButton("View Game Reviews");
      viewReviews.setBounds(100, 340, 160, 30);
      p2.add(viewReviews);
      viewReviews.addActionListener(this);
   
   // back button
      back = new JButton("Back");
      back.setBounds(20, 500, 100, 30);
      add(back);
      back.addActionListener(this);
   }

// handle button clicks
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == addReview) { // when the review button is clicked
         Game g = library.searchGame(titleField.getText()); // searcing for games
         if (g != null) { // if the game exist
            try {
               double score = Double.parseDouble(scoreField.getText()); // convert score to number
               Review r = new Review(currentPlayer.getName(), score, commentArea.getText()); // create an object for the review
               g.addReview(r); // adds a review
               JOptionPane.showMessageDialog(this, "Review added for " + g.getTitle()); // a message when the review is added
               
               scoreField.setText("");
               commentArea.setText("");
               
            } catch (Exception ex) {
               JOptionPane.showMessageDialog(this, "Invalid score! Please enter a number."); // when the score isn't in the range = invalid 
            }
         } else {
            JOptionPane.showMessageDialog(this, "Game not found in library."); // when the game is not found
         }
      }
   
      if (e.getSource() == viewReviews) { // when the view review button is clicked
         Game g = library.searchGame(titleField.getText()); // searching for games
         if (g != null) { // when the game exist
            displayArea.setText("Game: " + g.getTitle() + "\n"); // show the game title
            displayArea.append("Avg Rating: " + g.getRating() + "/10\n"); // aver rating
            displayArea.append("====================\n");
         
            displayArea.append("Check console for full text reviews!"); // message to lead the user where they can view reviews
         } else {
            JOptionPane.showMessageDialog(this, "Enter a valid title to see ratings."); // a suitable message when an invalid entry is recieved
         }
      }
   
      if (e.getSource() == back) { // when the back button is clicked
         setVisible(false); // closing the frame
      }
   }
}