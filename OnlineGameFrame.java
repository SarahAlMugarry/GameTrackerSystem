import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OnlineGameFrame extends JFrame implements ActionListener { // main window for the system

   JButton add, remove, search, back, exit;  //buttons
   JRadioButton all, multi, single;     //radio buttons to filter online games
   ButtonGroup group = new ButtonGroup();   // groups radio buttons so only one can be selected
   JTextField title, genre, year, platform, developer; // text fields for game information 
   JComboBox region; // ComboBox for selecting server region 
   JTextArea area;  // text area to display games
   JPanel p1, p2; // two panels
   GameLibrary library;  //Game library object

   public OnlineGameFrame(GameLibrary library) { // constructor
      this.library = library;
        //frame settings
      setTitle("Online Game Menu");  // title
      setSize(800, 550);         // size
      setLocation(200, 100);
      setLayout(null);
      setResizable(false);
   
        // main title label
      JLabel head = new JLabel("Online Game Menu:");
      head.setFont(new Font("Segoe UI", Font.ITALIC, 26)); // font
      head.setBounds(50, 20, 500, 50);
      add(head);
   
      // panel for entering online game information
      p1 = new JPanel(null);
      p1.setBorder(BorderFactory.createTitledBorder("Online Game"));
      p1.setBounds(20, 80, 350, 370);
      add(p1);
      // panel for viewing the games
      p2 = new JPanel(null);
      p2.setBorder(BorderFactory.createTitledBorder("View Games"));
      p2.setBounds(400, 80, 350, 420);
      add(p2);
   
     // create text feilds 
      title = field("Title:", 20);
      genre = field("Genre:", 50);
      year = field("Year:", 80);
      platform = field("Platform:", 110);
      developer = field("Developer:", 140);
   
      JLabel r = new JLabel("Region:");   // region label
      r.setBounds(20, 170, 150, 20);
      p1.add(r);
   
      region = new JComboBox(new String[]{"Asia", "Europe", "America", "Middle East"});  // region ComboBox
      region.setBounds(180, 170, 120, 20);
      p1.add(region);
      // buttons inside the input panel
      add = button(p1, "Add Game", 220);
      remove = button(p1, "Remove Game", 260);
      search = button(p1, "Search Game", 300);
       // text area with scroll pane
      area = new JTextArea();
      JScrollPane scroll = new JScrollPane(area);
      scroll.setBounds(25, 25, 300, 300);
      p2.add(scroll);
       // Radio buttons for filtering games
      all = radio("All Games", 25, 330);
      multi = radio("Multiplayer", 160, 330);
      single = radio("Single Player", 25, 370);
      // back and exit buttons
      back = mainButton("Back", 20);
      exit = mainButton("Exit", 130);
   }

   public JTextField field(String name, int y) {  // method to create a label and text field 
      JLabel l = new JLabel(name);
      l.setBounds(20, y, 150, 20);
      p1.add(l);
   
      JTextField t = new JTextField();
      t.setBounds(180, y, 120, 20);
      p1.add(t);
      return t;
   }

   public JButton button(JPanel p, String name, int y) {  // method to create a button
      JButton b = new JButton(name);
      b.setBounds(20, y, 150, 30);
      p.add(b);
      b.addActionListener(this);
      return b;
   }

   public JRadioButton radio(String name, int x, int y) {  // method to create radio buttons
      JRadioButton r = new JRadioButton(name);
      r.setBounds(x, y, 130, 30);
      p2.add(r);
      r.addActionListener(this);
      group.add(r);
      return r;
   }

   public JButton mainButton(String name, int x) {  //method to create back and exit buttons 
      JButton b = new JButton(name);
      b.setBounds(x, 460, 100, 30);
      add(b);
      b.addActionListener(this);  // add action listener
      return b;
   }

   public void actionPerformed(ActionEvent e) {   // this method runs when a button or radio button is clicked
   
      if (e.getSource() == add) {  // add online game
         try {
            OnlineGame g = new OnlineGame(        // create an OnlineGame object using user input
                   title.getText(), genre.getText(),
                   Integer.parseInt(year.getText()),
                   platform.getText(), developer.getText(),
                   region.getSelectedItem().toString(),
                   JOptionPane.showConfirmDialog(this, "Is Multiplayer?") == JOptionPane.YES_OPTION
               );
             // add game aand save to file
            library.addGame(g);
            library.saveToFile("games.dat");
            JOptionPane.showMessageDialog(this, "Online game added");
         
         } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());  // show error when input is invalid
         }
      }
      
          // remove game using title 
      if (e.getSource() == remove) {  
         if (library.removeGame(title.getText())) {
            library.saveToFile("games.dat");
            JOptionPane.showMessageDialog(this, "Game removed");
         } else
            JOptionPane.showMessageDialog(this, "Game not found");
      }
          // search for online game using title
      if (e.getSource() == search) {
         Game g = library.searchGame(title.getText());
      
         if (g != null && g instanceof OnlineGame)
            JOptionPane.showMessageDialog(this, g.toString());
         else
            JOptionPane.showMessageDialog(this, "Game not found");
      }
           
          // show games depending on selected radio button
      if (e.getSource() == all || e.getSource() == multi || e.getSource() == single)
         showGames();
   
      if (e.getSource() == back)   // go back to previous frame
         setVisible(false);
   
      if (e.getSource() == exit) {    // save and exit program
         library.saveToFile("games.dat");
         System.exit(0);
      }
   }

   public void showGames() {   // method to display online games
      area.setText("");  // clear text area before displaying 
      Game[] list = library.getGames();   // get games from library
   
      for (int i = 0; i < list.length; i++) {
         if (list[i] instanceof OnlineGame) {   // display only onlinegame objects
            if (all.isSelected()   // filter games based on selected radio button
                   || multi.isSelected() && list[i].toString().contains("true")
                   || single.isSelected() && list[i].toString().contains("false")) {
            
               area.append(list[i].toString() + "\n====================\n");
            }
         }
      }
   }
}