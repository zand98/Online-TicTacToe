
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Registration extends JFrame implements ActionListener, MouseListener {

    JLabel l[] = new JLabel[2];
    JTextField tf[] = new JTextField[2];

    JPanel mainPanel, childPanel;
    JButton registerButton;

    String htmlOpenTag = "<html><h1 style='font-size:11px; color:f73859;'>";
    String htmlCloseTag = ": </h1></html>";

    private final JButton cancel;
    private final JLabel companyLabel;
    private final JLabel welcomeLabel;

    Connection con;
    DbConnect connection;
    private PreparedStatement ps = null;
    private ResultSet rs;
    private final JButton loginButton;

    public Registration() {

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
        // mainPanel.setBackground(Color.white);

        field.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
        //field.add(tff);

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

        registerButton = new JButton();
        registerButton.setText("Register");
        registerButton.setBounds(427, 270, 90, 25);
        registerButton.setBackground(Color.white);

        registerButton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.decode("#5acef4")));

        loginButton = new JButton();
        loginButton.setText("Login");
        loginButton.setBounds(527, 270, 90, 25);
        loginButton.setBackground(Color.white);

        loginButton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.decode("#5acef4")));
        mainPanel.add(loginButton);
        
        Image userImage = new ImageIcon(this.getClass().getResource("/img/user.png")).getImage();
        l[0].setIcon(new ImageIcon(userImage));

        Image passImage = new ImageIcon(this.getClass().getResource("/img/pass.png")).getImage();
        l[1].setIcon(new ImageIcon(passImage));
        l[0].setBounds(745, 62, 40, 40);
        l[1].setBounds(745, 122, 40, 40);
        tf[1].setBounds(430, 140, 300, 28);

        mainPanel.add(registerButton);
        //////////////color///////////////
        for (byte i = 0; i < 2; i++) {
            l[i].setBackground(new Color(234, 157, 23));
            l[i].setFont(new Font("Tahoma", 1, 14)); // NOI18N
            tf[i].setFont(new Font("Tahoma", 1, 14)); // NOI18N
        }
        registerButton.setFont(new Font("Tahoma", 1, 12)); // NOI18N
        registerButton.addActionListener(this);
        registerButton.addMouseListener(this);

        loginButton.addActionListener(this);
        loginButton.addMouseListener(this);

        addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == cancel) {
            System.exit(0);
        }
        if (ae.getSource() == loginButton) {
            
                
     Login l = new Login();
            l.setBounds(280, 210, 800, 350);
            l.setVisible(true);
            this.dispose();
            
        }
        if (ae.getSource() == registerButton) {
            if (tf[0].getText().length() < 1) {
                JOptionPane.showMessageDialog(null, "please enter your username!");
            } else if (tf[0].getText().length() < 3) {
                JOptionPane.showMessageDialog(null, "you username can not less than 3 letters!");
            } else if (tf[0].getText().length() > 10) {
                JOptionPane.showMessageDialog(null, "you username can not more than 10 letters!");
            } else {

                if (tf[0].getText().length() >= 3) {

                    if (tf[1].getText().length() < 1) {
                        JOptionPane.showMessageDialog(null, "please enter your Password!");
                    } else if (tf[1].getText().length() < 3) {
                        JOptionPane.showMessageDialog(null, "you Password can not less than 3 letter!");
                    } else if (tf[1].getText().length() > 32) {
                        JOptionPane.showMessageDialog(null, "you Password can not more than 32 letters!");
                    }
                    if (tf[1].getText().length() >= 3 && tf[1].getText().length() <= 32) {
                        createUser();
                    }
                }

            }
        }
    }

    void createUser() {
        try {
            connection = new DbConnect();
            con = connection.db();
            
                       
           String sql = "insert into user (name,keypass) values"
            + "('" + tf[0].getText() + "','"+tf[1].getText()+"')";
                ps = con.prepareStatement(sql);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "your account successfully created!");
        } catch (Exception e) {
            System.out.println(e);
        }
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
        if (me.getSource() == registerButton) {
            registerButton.setBounds(429, 272, 90, 25);
            registerButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#5acef4")));
            setCursor(HAND_CURSOR);
        }
        if (me.getSource() == loginButton) {
            loginButton.setBounds(529, 272, 90, 25);
            loginButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#5acef4")));
            setCursor(HAND_CURSOR);
        }
        if (me.getSource() == cancel) {
            setCursor(HAND_CURSOR);
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {

        if (me.getSource() == registerButton) {
            registerButton.setBounds(427, 270, 90, 25);
            registerButton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.decode("#5acef4")));
            setCursor(DO_NOTHING_ON_CLOSE);

        }

        if (me.getSource() == loginButton) {
            loginButton.setBounds(527, 270, 90, 25);
            loginButton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.decode("#5acef4")));
            setCursor(DO_NOTHING_ON_CLOSE);

        }
        if (me.getSource() == cancel) {
            setCursor(DO_NOTHING_ON_CLOSE);
        }
    }
}
