import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener { // frame for player login and account setup

   JButton create, switchAcc, continueBtn, exit; // buttons
   JTextField nameField, idField; // text fields
   Player[] players; // array for the players
   int playerCount; // current no of the players
   Player currentPlayer; // the player object that is currently logged in
   GameLibrary library; // gamelibrary object

   public LoginFrame(GameLibrary library, Player[] players, int playerCount) {
   // saving the data
      this.library = library;
      this.players = players;
      this.playerCount = playerCount;
      
      this.currentPlayer = null; // no player selected at the beginning
   
      setTitle("Game Tracker - Player Setup"); // title frame
      setSize(600, 400); // size
      setLocation(200, 150); // pos
      setLayout(null); // layout manual
      setResizable(false); // resizing is disabled
   
      JLabel head = new JLabel("Welcome to Game Tracker"); // main heading
      head.setFont(new Font("Segoe UI", Font.BOLD, 28)); // its font
      head.setBounds(50, 20, 500, 50); // its pos
      
      JLabel subHead = new JLabel("Set Up Your Profile"); // sub heading
      subHead.setFont(new Font("Segoe UI", Font.ITALIC, 18)); // its font
      subHead.setBounds(50, 60, 300, 30); // its pos
      add(subHead);
   
      JLabel nLabel = new JLabel("Display Name:"); // label
      nLabel.setBounds(50, 120, 150, 20);
      add(nLabel);
   
      nameField = new JTextField(); // input field
      nameField.setBounds(180, 120, 150, 20);
      add(nameField);
   
      JLabel idLabel = new JLabel("Player ID:"); // label
      idLabel.setBounds(50, 160, 150, 20);
      add(idLabel);
   
      idField = new JTextField(); // input field
      idField.setBounds(180, 160, 150, 20);
      add(idField);
   
   // buttons
      create = button("Create Account", 210);
      switchAcc = button("Switch Account", 250);
      continueBtn = button("Continue to Menu", 290);
      exit = button("Exit", 330);
   }

   public JButton button(String name, int y) { // method to create buttons
      JButton b = new JButton(name); // creating
      b.setBounds(50, y, 160, 30); // setting pos
      add(b); // adding them to their frames
      b.addActionListener(this);
      return b;
   }

   public void actionPerformed(ActionEvent e) { // button click handler
   
      if (e.getSource() == create) { // create accoung button
         String name = nameField.getText(); // get the value
         String id = idField.getText();
      
         if (name.length() == 0 || id.length() == 0) { // checking if the user left them empty
            JOptionPane.showMessageDialog(this, "The display name and ID cannot be empty!");
            return;
         }
      
         if (playerCount >= players.length) { // checking if the the player max is reached
            JOptionPane.showMessageDialog(this, "Number of players reached the maximum limit (5).");
            return;
         }
      
         boolean nameCopied = false; // to make sure id and name are not taken
         for (int i = 0; i < playerCount; i++) {
            if (players[i].getName().equalsIgnoreCase(name) || players[i].getPlayerID().equalsIgnoreCase(id)) {
               nameCopied = true;
               break;
            }
         }
      
         if (nameCopied) { // if one of them are already occupied
            JOptionPane.showMessageDialog(this, "This name or ID is already taken.");
         } else {
            players[playerCount] = new Player(name, id); // create new player obj
            currentPlayer = players[playerCount]; // sets it as current player
            playerCount++; // increases player count
            
            JOptionPane.showMessageDialog(this, "The account \"" + currentPlayer.getName() + "\" has been created successfully!"); // suitable message
            
            nameField.setText(""); // empty field
            idField.setText(""); // ^2
         }
      }
   
      if (e.getSource() == switchAcc) { // switch acc button
         if (playerCount == 0) { // if no acc exist (no player obj is created)
            JOptionPane.showMessageDialog(this, "No accounts created yet. Please create an account first.");
            return;
         }
      
         String switchName = JOptionPane.showInputDialog(this, "Enter player name to switch account:"); // player name
         
         if (switchName != null && switchName.length() > 0) { // checking the entry
            boolean playerExists = false;
            
            for (int i = 0; i < playerCount; i++) { // search in the players array
               if (players[i].getName().equalsIgnoreCase(switchName)) {
                  currentPlayer = players[i]; // switch the curr acc
                  playerExists = true;
                  JOptionPane.showMessageDialog(this, "Profile switched successfully!\nWelcome " + currentPlayer.getName() + "!"); // suitable message
                  break;
               }
            }
            
            if (!playerExists) { // invalid name (acc doesn't exist)
               JOptionPane.showMessageDialog(this, "Invalid player name.");
            }
         }
      }
   
      if (e.getSource() == continueBtn) { // the continue button
         if (currentPlayer == null) { // checking if a player is created (once an acc is created it is automatically set as the current acc)
            JOptionPane.showMessageDialog(this, "Please create an account first before continuing!");
         } else {
            new FirstFrame(currentPlayer, library).setVisible(true); // an account is already created => openning next frame
            setVisible(false); // hiding (closing) the login frame
         }
      }
   
      if (e.getSource() == exit) { // exit button
         library.saveToFile("games.dat"); // saving the library
         System.exit(0); // closing the program
      }
   }
}