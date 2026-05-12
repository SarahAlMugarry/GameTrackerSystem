import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class FirstFrame extends JFrame implements ActionListener { // main window of the system

   JButton video, online, board, playerDash, reviews, save, load, exit; // buttons
   GameLibrary library;
   Player currentPlayer;

   public FirstFrame(Player currentPlayer, GameLibrary library) {
   
      this.currentPlayer = currentPlayer;  // assigning player
      this.library = library; // assigning library
   
   // frame setting & details
      setTitle("Game Tracker System"); // title
      setSize(700, 600); // size
      setLocation(200, 150); // pos
      setLayout(null);
      setResizable(false);
      
      // adding an image to the frame
      ImageIcon icon=new ImageIcon("gameimage.png");
      Image img=icon.getImage().getScaledInstance(250,250,Image.SCALE_SMOOTH);
      JLabel image = new JLabel(new ImageIcon(img));
      image.setBounds(380, 130, 250, 250);
      add(image);
   
      JLabel title = new JLabel("Welcome to Game Tracker System"); // main title
      title.setFont(new Font("Segoe UI", Font.BOLD, 28)); // font
      title.setBounds(50, 20, 600, 50);
      add(title);
   
   // creating buttons
      video = button("Video Games", 100);
      online = button("Online Games", 150);
      board = button("Board Games", 200);
      playerDash = button("My Dashboard", 250);
      reviews = button("Reviews & Ratings", 300);
      save = button("Save Data", 350);
      load = button("Load Data", 400);
      exit = button("Exit", 450);
   }

   public JButton button(String text, int y) { // method to create buttons
      JButton b = new JButton(text); // create
      b.setBounds(50, y, 160, 30); // sizing and pos
      add(b); // adding it to a frame
      b.addActionListener(this);
      return b;
   }

   public void actionPerformed(ActionEvent e) { // button clicking handler
      if (e.getSource() == video) // opens vg frame
         new VideoGameFrame(library).setVisible(true);
   
      if (e.getSource() == online) // opens og frame
         new OnlineGameFrame(library).setVisible(true);
   
      if (e.getSource() == board) // opens bg frame
         new BoardGameFrame(library).setVisible(true);
         
      if (e.getSource() == playerDash) // open pd frame
         new PlayerDashboardFrame(currentPlayer, library).setVisible(true);
         
      if (e.getSource() == reviews) // open r frame
         new ReviewFrame(currentPlayer, library).setVisible(true);
         
      if (e.getSource() == save) { // saving data
         library.saveToFile("games.dat"); // saves library data
        
         if (currentPlayer != null) { // shows curr playeer collection
            currentPlayer.saveCollectionToFile(currentPlayer.getPlayerID() + "_collection.dat");
         }
         JOptionPane.showMessageDialog(this, "All data has been saved successfully!", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
      }
      
      // load the saves data
      if (e.getSource() == load) {
         try {
            library.loadFromFile("games.dat"); // load library data
            if (currentPlayer != null) { // load curr player collection
               currentPlayer.loadCollectionFromFile(currentPlayer.getPlayerID() + "_collection.dat");
            }
            JOptionPane.showMessageDialog(this, "Data loaded successfully!", "Load Successful", JOptionPane.INFORMATION_MESSAGE);
         } catch (IOException ex) {
         // suitable message when loading fails
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
      }
   
      if (e.getSource() == exit) { // exit program
         library.saveToFile("games.dat"); // saves the data before the prgram is shut
         System.exit(0); // closes the system
      }
   }
}