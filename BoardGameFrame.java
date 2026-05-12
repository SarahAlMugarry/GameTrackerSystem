import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardGameFrame extends JFrame implements ActionListener { // main window for the system

   JButton add, remove, search, view, back, exit; //buttons
   JTextField title, genre, year, min, max;  // text fields for game information
   JTextArea area;   // text area to display games
   JPanel p1, p2;   // two panels
   GameLibrary library;   //Game library object

   public BoardGameFrame(GameLibrary library) {  // constructor
      this.library = library;   //assign library object
       // frame settings
      setTitle("Board Game Menu"); //title
      setSize(800, 550);    //size
      setLocation(200, 100);
      setLayout(null);
      setResizable(false);
   
       // main title label
      JLabel head = new JLabel("Board Game Menu:");
      head.setFont(new Font("Segoe UI", Font.ITALIC, 26)); // font
      head.setBounds(50, 20, 500, 50);
      add(head);
        //panel for enetering board game information
      p1 = new JPanel(null);
      p1.setBorder(BorderFactory.createTitledBorder("Board Game"));
      p1.setBounds(20, 80, 350, 370);
      add(p1);
      // panel for viewing games
      p2 = new JPanel(null);
      p2.setBorder(BorderFactory.createTitledBorder("View Games"));
      p2.setBounds(400, 80, 350, 420);
      add(p2);
     // create input fields
      title = field("Title:", 20);
      genre = field("Genre:", 50);
      year = field("Year:", 80);
      min = field("Min Players:", 110);
      max = field("Max Players:", 140);
       // create buttons
      add = button(p1, "Add Game", 200);
      remove = button(p1, "Remove Game", 240);
      search = button(p1, "Search Game", 280);
   
      area = new JTextArea();  // text area for displaying games
      JScrollPane scroll = new JScrollPane(area);  //add scroll panel 
      scroll.setBounds(25, 25, 300, 300);
      p2.add(scroll);
   
      view = button(p2, "View All", 340);  // view button
       // back and exit buttons
      back = mainButton("Back", 20);
      exit = mainButton("Exit", 130);
   }

   public JTextField field(String name, int y) { // create label and text field
      JLabel l = new JLabel(name);
      l.setBounds(20, y, 150, 20);
      p1.add(l);
   
      JTextField t = new JTextField();
      t.setBounds(180, y, 120, 20);
      p1.add(t);
      return t;
   }

   public JButton button(JPanel p, String name, int y) {  // create a button inside a panel
      JButton b = new JButton(name);
      b.setBounds(20, y, 150, 30);
      p.add(b);
      b.addActionListener(this);
      return b;
   }

   public JButton mainButton(String name, int x) {  //creates main back and exit buttons
      JButton b = new JButton(name);
      b.setBounds(x, 460, 100, 30);
      add(b);
      b.addActionListener(this);
      return b;
   }

   public void actionPerformed(ActionEvent e) {   //for button clicks
   
      if (e.getSource() == add) {   //add a board game
         try {
            BoardGame g = new BoardGame(
                   title.getText(),
                   genre.getText(),
                   Integer.parseInt(year.getText()),
                   Integer.parseInt(min.getText()),
                   Integer.parseInt(max.getText())
               );
         
            library.addGame(g);
            library.saveToFile("games.dat");
            JOptionPane.showMessageDialog(this, "Board game added");
         
         } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
         }
      }
   
      if (e.getSource() == remove) {    // remove a board game
         if (library.removeGame(title.getText())) {
            library.saveToFile("games.dat");
            JOptionPane.showMessageDialog(this, "Game removed");
         } else
            JOptionPane.showMessageDialog(this, "Game not found");
      }
   
      if (e.getSource() == search) {    // search board game
         Game g = library.searchGame(title.getText());
      
         if (g != null && g instanceof BoardGame)
            JOptionPane.showMessageDialog(this, g.toString());
         else
            JOptionPane.showMessageDialog(this, "Game not found");
      }
   
      if (e.getSource() == view) {   // view all board games
         area.setText("");
         Game[] list = library.getGames();
      
         for (int i = 0; i < list.length; i++) {
            if (list[i] instanceof BoardGame)
               area.append(list[i].toString() + "\n====================\n");
         }
      }
   
      if (e.getSource() == back)  // go back ot previous fram
         setVisible(false);
        // save and exit
      if (e.getSource() == exit) {   
         library.saveToFile("games.dat");
         System.exit(0);
      }
   }
}