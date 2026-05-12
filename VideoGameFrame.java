import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VideoGameFrame extends JFrame implements ActionListener { // main window of the system

   JButton add, remove, search, view, back, exit;  // buttons used in the frame 
   JTextField title, genre, year, platform, developer;  // text fields for video game information 
   JTextArea area;  //text area to display games
   JPanel p1, p2;  // two panels
   GameLibrary library;  //Game library object to store games

   public VideoGameFrame(GameLibrary library) {   //constructor 
      this.library = library;
    
       //Frame settings and details
      setTitle("Video Game Menu");    //title
      setSize(800, 550);      //size
      setLocation(200, 100);     
      setLayout(null);
      setResizable(false);
   
       //main title label
      JLabel head = new JLabel("Video Game Menu:");     
      head.setFont(new Font("Segoe UI", Font.ITALIC, 26));  //font 
      head.setBounds(50, 20, 500, 50);
      add(head);
   
   
       // panel for entering video game information
      p1 = new JPanel(null);     
      p1.setBorder(BorderFactory.createTitledBorder("Video Game"));
      p1.setBounds(20, 80, 350, 370);
      add(p1);
      
      
       //panel for displaying saved games
      p2 = new JPanel(null);
      p2.setBorder(BorderFactory.createTitledBorder("View Games"));
      p2.setBounds(400, 80, 350, 420);
      add(p2);
      
       //create input fields
      title = field("Title:", 20);
      genre = field("Genre:", 50);
      year = field("Year:", 80);
      platform = field("Platform:", 110);
      developer = field("Developer:", 140);
      //create buttons inside input panel
      add = button(p1, "Add Game", 200);
      remove = button(p1, "Remove Game", 240);
      search = button(p1, "Search Game", 280);
   
      area = new JTextArea();  //text area to show all video games
   
      JScrollPane scroll = new JScrollPane(area);   // scroll bar for text area 
      scroll.setBounds(25, 25, 300, 300);
      p2.add(scroll);
   
      view = button(p2, "View All", 340);  // button to view all video games
   
       // main fraem buttons
      back = mainButton("Back", 20);  
      exit = mainButton("Exit", 130);
   }

   public JTextField field(String name, int y) {  //method to create a label and text field 
   
      JLabel l = new JLabel(name);
      l.setBounds(20, y, 150, 20);
      p1.add(l);
   
      JTextField t = new JTextField();
      t.setBounds(180, y, 120, 20);
      p1.add(t);
   
      return t;
   }

   public JButton button(JPanel p, String name, int y) {   // method to create  a button inside a panel
   
      JButton b = new JButton(name);
      b.setBounds(20, y, 150, 30);
      p.add(b);
   
      b.addActionListener(this);  // connect button with actionPerformed method
   
      return b;
   }

   public JButton mainButton(String name, int x) {  // method to create back and exit buttons
   
      JButton b = new JButton(name);
      b.setBounds(x, 460, 100, 30);
      add(b);
   
      b.addActionListener(this);  // connect button with actionPerformed method
      return b;
   }

   public void actionPerformed(ActionEvent e) {  // this method runs when any button is clicked
   
      if (e.getSource() == add) {  // add a new video game
      
         try {
           // create VideoGame object using input fields
            VideoGame g = new VideoGame( title.getText(), genre.getText(),  Integer.parseInt(year.getText()), 
               platform.getText(),  developer.getText() );
         
            library.addGame(g);  // add game to the library
         
            library.saveToFile("games.dat");  // save changes to file
         
            JOptionPane.showMessageDialog( this,"Video game added" );
         
         } catch (Exception ex) {
             //show error message if input is wrong 
            JOptionPane.showMessageDialog(   this,   ex.getMessage() );
         }
      }
       // remove game by title
      if (e.getSource() == remove) {
      
         if (library.removeGame(title.getText())) {
         
            library.saveToFile("games.dat");  // save after removing 
         
            JOptionPane.showMessageDialog(  this,  "Game removed" );
         }
         
         else {
         
            JOptionPane.showMessageDialog(  this,  "Game not found"
               );
         }
      }
       // search for a video game by title
      if (e.getSource() == search) {
      
         Game g =  library.searchGame( title.getText() );
      
         if (g != null && g instanceof VideoGame &&  !(g instanceof OnlineGame ) ) {  // make sure the game is VideoGame but not OnlineGame
         
            JOptionPane.showMessageDialog(  this,  g.toString() );
         }
         
         else {
         
            JOptionPane.showMessageDialog( this , "Game not found" );
         }
      }
         // view all video games
      if (e.getSource() == view) {
      
         area.setText("");   // clear text area first 
      
         Game[] list = library.getGames();  // get all games from library
      
          //Display  only VideoGame objects not OnlineGame objects
         for (int i = 0; i < list.length; i++) {
         
            if (list[i] instanceof VideoGame && !(list[i] instanceof OnlineGame) ) {
            
               area.append(  list[i].toString()    + "\n " );
            
               area.append( "====================\n"  );
            }
         }
      }
   
      if (e.getSource() == back)   // go back to previous frame
         setVisible(false);
   
      if (e.getSource() == exit) {    // exit program
      
         library.saveToFile("games.dat");   // save before exiting
      
         System.exit(0);
      }
   }
}