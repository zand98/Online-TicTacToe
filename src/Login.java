
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

public class Login extends JFrame implements ActionListener, MouseListener {

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

    public Login() {
        try {
            connection = new DbConnect();
            String x = "zand";
            con = connection.db();
//                String sql = "update computeridentifier set counter=counter+1";
            String sql = "insert into user (name,keypass) values('" + x + "','" + 7 + "')";
//                ps = con.prepareStatement(sql);
//                ps.executeUpdate();

//                sql = "select counter from ComputerIdentifier";
//                ps = con.prepareStatement(sql);
//                rs = ps.executeQuery();
//                cond = false;
//                while (rs.next()) {
//                    if(rs.getInt(1)>=1000)
//                    {
//                        cond = true;
//                    }
//                }
        } //        String ssql = "delete from kash";
        //            ps = con.prepareStatement(ssql);
        //            ps.executeUpdate();
        //        ssql = "delete from qarz";
        //            ps = con.prepareStatement(ssql);
        //            ps.executeUpdate();
        //        
        //        ssql = "delete from main";
        //            ps = con.prepareStatement(ssql);
        //            ps.executeUpdate();
        //        con.close();
        //                   connection=new DbConnect();
        catch (Exception e) {
            System.out.println(e);
        }

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

        addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (registerButton == ae.getSource()) {
            Registration r = new Registration();
            r.setBounds(280, 210, 800, 350);
            r.setVisible(true);
            this.dispose();
        }

        if (ae.getSource() == cancel) {
            System.exit(0);
        }
        if (ae.getSource() == b) {

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
                        LogintoDatabase();
                    }
                }
            }
        }
    }

    void LogintoDatabase() {

        String sql = "select name, keypass from user";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString(1).equals(tf[0].getText())
                        && rs.getString(2).equals(tf[1].getText())) {
                    JOptionPane.showMessageDialog(null, "true");
                    return;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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