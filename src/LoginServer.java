
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginServer extends JFrame implements ActionListener, MouseListener {

    public static String username=null;
    public static String id=null;

    JLabel l[] = new JLabel[2];
    JTextField tf[] = new JTextField[2];

    JPanel mainPanel, childPanel;
    JButton b;

    String htmlOpenTag = "<html><h1 style='font-size:11px; color:f73859;'>";
    String htmlCloseTag = ": </h1></html>";

    private final JButton cancel;
    private final JLabel companyLabel;
    private final JLabel welcomeLabel;

    Connection con;
    DbConnect connection;
    private PreparedStatement ps = null;
    private ResultSet rs;
    private final JButton registerButton;
    private ServerSocket serversocket=null;
    private Socket socket=null;

    public LoginServer() {
            connection = new DbConnect();

            con = connection.db();

        new DraggableJFrame(this);
        childPanel = new JPanel();
        childPanel.setLayout(null);
        childPanel.setSize(400, 350);
        childPanel.setLocation(0, 0);
        childPanel.setBackground(Color.decode("#5acef4"));
        add(childPanel);

        welcomeLabel = new JLabel("<html><p style='color:white;font-size:40px;'>Tic</p></html>");
        welcomeLabel.setBounds(40, -40, 285, 200);
        childPanel.add(welcomeLabel);

        companyLabel = new JLabel("<html><p style='color:gray;font-size:20px;'>TacToe</p></html>");
        companyLabel.setBounds(130, 0, 285, 200);
        childPanel.add(companyLabel);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setSize(400, 350);
        mainPanel.setLocation(400, 0);
        mainPanel.setBackground(Color.white);
        add(mainPanel);

        JTextField field = new JTextField();
        mainPanel.add(field);
          field.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
   
        for (byte i = 0; i < 2; i++) {
            tf[i] = new JTextField();   //textFiled
            tf[i].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
            l[i] = new JLabel();        // label

            mainPanel.add(tf[i]);
            mainPanel.add(l[i]);

        }

        tf[0].setBounds(430, 80, 300, 28);
        tf[1].setBounds(430, 140, 300, 28);

        cancel = new JButton();
        mainPanel.add(cancel);
        cancel.setBounds(760, 8, 32, 32);
        cancel.setBackground(null);
        cancel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
        Image cancelImage = new ImageIcon(this.getClass().getResource("/img/cancel.png")).getImage();
        cancel.setIcon(new ImageIcon(cancelImage));

        cancel.addActionListener(this);
        cancel.addMouseListener(this);

        b = new JButton();
        b.setText("Login");
        b.setBounds(427, 270, 90, 25);
        b.setBackground(Color.white);

        b.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.decode("#5acef4")));

        registerButton = new JButton("Registration");
        registerButton.setBounds(527, 270, 90, 25);
        mainPanel.add(registerButton);
        registerButton.setBackground(Color.white);
        registerButton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.decode("#5acef4")));

        Image userImage = new ImageIcon(this.getClass().getResource("/img/user.png")).getImage();
        l[0].setIcon(new ImageIcon(userImage));

        Image passImage = new ImageIcon(this.getClass().getResource("/img/pass.png")).getImage();
        l[1].setIcon(new ImageIcon(passImage));
        l[0].setBounds(745, 62, 40, 40);
        l[1].setBounds(745, 122, 40, 40);
        tf[1].setBounds(430, 140, 300, 28);

        mainPanel.add(b);
        //////////////color///////////////
        for (byte i = 0; i < 2; i++) {
            l[i].setBackground(new Color(234, 157, 23));
            l[i].setFont(new Font("Tahoma", 1, 14)); // NOI18N
            tf[i].setFont(new Font("Tahoma", 1, 14)); // NOI18N

        }
        b.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        b.addActionListener(this);
        b.addMouseListener(this);
        registerButton.addActionListener(this);
        registerButton.addMouseListener(this);
        
        tf[0].setText("zand yasin");
        tf[1].setText("12345678");
        addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (registerButton == ae.getSource()) {
            Registration r = new Registration();
            r.setBounds(280, 210, 800, 350);
            r.setVisible(true);
//            this.dispose();
        }

        if (ae.getSource() == cancel) {
            System.exit(0);
        }
        if (ae.getSource() == b) {
            System.out.println("clicked");
            if (tf[0].getText().length() < 1) {
                JOptionPane.showMessageDialog(null, "please enter your username!");
            } else if (tf[0].getText().length() < 3) {
                JOptionPane.showMessageDialog(null, "you username can not less than 3 letter!");
            } else {

                if (tf[0].getText().length() >= 3) {

                    if (tf[1].getText().length() < 1) {
                        JOptionPane.showMessageDialog(null, "please enter your Password!");
                    } else if (tf[1].getText().length() < 3) {
                        JOptionPane.showMessageDialog(null, "you Password can not less than 3 letters!");
                    }

                    if (tf[1].getText().length() >= 3) {
                        try {
                           LogintoDatabase();
//                                               this.dispose();

                        } catch (Exception ex) {
                            Logger.getLogger(LoginServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }
    
    String getUsername()
    {
        return username;
    }
    
    void LogintoDatabase() throws IOException {

        String sql = "select id,name,keypass from user";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString(2).equals(tf[0].getText())
                        && rs.getString(3).equals(tf[1].getText())) {
                    username=rs.getString(2);
                    id=rs.getString(1);
                    this.dispose();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        
        
        
//     LoginServer l = new LoginServer();
//     
//        
//            System.out.println("is "+l.socket);
//            System.out.println("is "+l.serversocket);
//     
//            l.setBounds(280, 210, 800, 350);
//            l.setVisible(true);
//            
//            System.out.println("is "+l.socket);
//            System.out.println("is "+l.serversocket);
    }
    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (me.getSource() == b) {
            b.setBounds(429, 272, 90, 25);
            b.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#5acef4")));
            setCursor(HAND_CURSOR);
        }
        if (me.getSource() == registerButton) {
            registerButton.setBounds(529, 272, 90, 25);
            registerButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#5acef4")));
            setCursor(HAND_CURSOR);
        }
        if (me.getSource() == cancel) {
            setCursor(HAND_CURSOR);
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {

        if (me.getSource() == b) {
            b.setBounds(427, 270, 90, 25);
            b.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.decode("#5acef4")));
            setCursor(DO_NOTHING_ON_CLOSE);

        }
        if (me.getSource() == registerButton) {
            registerButton.setBounds(527, 270, 90, 25);
            registerButton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.decode("#5acef4")));
            setCursor(DO_NOTHING_ON_CLOSE);

        }
        if (me.getSource() == cancel) {
            setCursor(DO_NOTHING_ON_CLOSE);
        }
    }
}