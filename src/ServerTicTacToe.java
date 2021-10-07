//import jaco.mp3.player.MP3Player;
//import java.io.File;

import javazoom.jl.player.*;
import java.io.FileInputStream;

import java.net.MalformedURLException;
import java.net.URL;
//import javazoom.jlgui.
//import javazoom.jlgui.basicplayer.BasicPlayerException;
import generic.Stack;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ServerTicTacToe extends JFrame implements KeyListener, MouseListener {

    private static boolean Next_Previews = false;
    private static String username;

    private JButton cancel;
    private Stack FiLo;
    private Stack LiFo;
    private JButton BackButton;
    private JButton BackForwardButton;
    private DbConnect connection;
    static boolean constructur = true;
    private final JLabel usernameLabel;
    private final JLabel scoreLabel;
    private static int score = 0;
    private Connection con;
    private String id2;
    private int pos;
    private final JLabel userProfileLabel;

    private void setImage(String name, JButton b) {
        Image ImageName = new ImageIcon(this.getClass().getResource(name)).getImage();
        b.setIcon(new ImageIcon(ImageName));
    }

    private void setImageLabel(String name, JLabel b, int x, int y) {
        Image ImageName
                = new ImageIcon(this.getClass().
                        getResource(name)).
                getImage().
                getScaledInstance(x, y, Image.SCALE_DEFAULT);

        b.setIcon(new ImageIcon(ImageName));
    }

    private void doReload() {

        for (int j = 0; j < 9; j++) {
            ch[j] = "";
            b[j].setBackground(null);
            b[j].setIcon(null);
        }
        yourTurn = true;
        finish = false;
        arrayList = new ArrayList<Integer>();
        FiLo = new Stack();
        LiFo = new Stack();
        id2 = null;
        setFocusable(true);
    }

    JButton b[] = new JButton[9];
    String ch[] = new String[9];
    JPanel p;
    int i = 0;
    boolean yourTurn = true;

    private static ServerSocket serversocket = null;
    private Socket socket = null;
    private static DataInputStream input;
    private static DataOutputStream output;
    private JButton ReloadButton;
    private ArrayList<Integer> arrayList;
    private boolean finish = false;
    private PreparedStatement ps = null;
    private ResultSet rs;

    public ServerTicTacToe() {

        connection = new DbConnect();
        setTitle("Server");
        arrayList = new ArrayList<Integer>();
        new DraggableJFrame(this);

        FiLo = new Stack();
        LiFo = new Stack();

        p = new JPanel();
        p.setLayout(null);
        ReloadButton = new JButton();
        ReloadButton.setBounds(140, 400, 64, 64);
        ReloadButton.addMouseListener(this);

        setImage("/img/reload.png", ReloadButton);
        ReloadButton.setBackground(null);
        ReloadButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));

        BackButton = new JButton();
        BackButton.setBounds(30, 400, 64, 64);
        BackButton.addMouseListener(this);
        BackButton.setBackground(null);
        BackButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
        p.add(BackButton);

        usernameLabel = new JLabel("username: " + username);
        usernameLabel.setBounds(20, 10, 100, 32);
        p.add(usernameLabel);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Tahoma", 1, 12));

        userProfileLabel = new JLabel();
        userProfileLabel.setBounds(150, 10, 64, 64);
        userProfileLabel.addMouseListener(this);
        userProfileLabel.setBackground(null);
        userProfileLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
        p.add(userProfileLabel);
        setImageLabel("/img/happy.png", userProfileLabel, 30, 30);

        scoreLabel = new JLabel("score: " + score);
        scoreLabel.setBounds(20, 40, 100, 32);
        p.add(scoreLabel);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Tahoma", 1, 12));

        setImage("/img/left.png", BackButton);

        BackForwardButton = new JButton();
        BackForwardButton.setBounds(250, 400, 64, 64);
        BackForwardButton.addMouseListener(this);
        p.add(BackForwardButton);
        setImage("/img/right.png", BackForwardButton);
        BackForwardButton.setBackground(null);
        BackForwardButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));

        cancel = new JButton();
        p.add(cancel);
        cancel.setBounds(300, 10, 32, 32);
        cancel.setBackground(null);
        cancel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
        cancel.addMouseListener(this);
        setImage("/img/cancel.png", cancel);

        p.add(ReloadButton);
        p.setBackground(new Color(40, 12, 2, 255));

        addKeyListener(this);
        setFocusable(true);
//        p.addKeyListener(this);

//        addKeyListener(this);
//        p.addKeyListener(this);
        add(p);
        i = 0;
        while (i < 9) {
            ch[i] = "";
            b[i] = new JButton();
            b[i].setBackground(null);
            p.add(b[i]);
            b[i].addMouseListener(this);
            if (i < 3) {
                b[i].setBounds(16 + i * 105, 80, 100, 100);
            } else if (i < 6) {
                b[i].setBounds(16 + (i - 3) * 105, 185, 100, 100);
            } else if (i < 9) {
                b[i].setBounds(16 + (i - 6) * 105, 290, 100, 100);
            }
            b[i].setFont(new Font("Tahoma", 1, 20)); // NOI18N
            i++;
        }
    }

    @Override
    public void setFocusable(boolean b) {
        super.setFocusable(b);
    }

    void createSocket() {
        try {
            serversocket = new ServerSocket(9999);
            socket = serversocket.accept();
        } catch (IOException ex) {
            Logger.getLogger(LoginServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    void createIO() {

        try {

            System.out.println(socket == null);
            System.out.println(serversocket == null);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            pos = 0;
            while (true) {
                int i = 0;
                String str = null;
                str = input.readUTF().trim();
                String a = "";
                if (!isNumeric(str)) {

                    for (int j = 1; j < str.length(); j++) {
                        a += str.charAt(j);
                    }
                    id2 = a;
                    if (str.charAt(0) == 'b') {
//                        JOptionPane.showMessageDialog(null, "draw la server xot = " + LoginServer.id + "baramabrakat= " + id2);

                    } else {
//                        JOptionPane.showMessageDialog(null, "server is numeric brawa  = " + LoginServer.id + "\n dorawa = " + id2);
                    }
                } else {
                    pos = Byte.parseByte(str);
                    if (pos == 20) {
                        System.exit(0);
                    }

                    if (pos < 9) {
                        FiLo.add(pos);
                        FiLo.display(FiLo);
                        arrayList.add(pos);
                    }
                    if (pos == 11) {
                        doReload();
                    }
                    if (pos == 10) {
                        int reply = JOptionPane.showConfirmDialog(null, "Do You Want Rematch?", "Question", JOptionPane.YES_NO_OPTION);

                        if (reply == JOptionPane.YES_OPTION) {

                            output.writeUTF("11");
                            doReload();
                        }
                    }

                    if (pos == 9) {
                        String s = input.readUTF().trim();
                        id2 = s;
                        output.writeUTF("a" + LoginServer.id);
                        minScore();
                        finish = true;
                        yourTurn = false;
                        //    JOptionPane.showMessageDialog(null, "you won");
//                        JOptionPane.showMessageDialog(null, "server brawa = " + id2 + "\n dorawa = " + LoginServer.id);
                    } else {
                        yourTurn = true;
                    }

                    if (pos == 15) {
                        output.writeUTF("b" + LoginServer.id);
                        finish = true;
                        JOptionPane.showMessageDialog(null, "Draw!");
                    }
                    if (pos < 9) {
                        ch[pos] = "*";
                        setImage("/img/sunglasses.png", b[pos]);
                    }
                }
            }
        } catch (IOException ee) {
            JOptionPane.showMessageDialog(null, ee);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        LoginServer l = new LoginServer();
        l.setBounds(280, 210, 800, 350);
        l.setVisible(true);

        while (l.getUsername() == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerTicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        username = l.username;

        ServerTicTacToe s = new ServerTicTacToe();
        s.setVisible(true);
        s.setBounds(420, 200, 345, 500);
        System.out.println("1");
        s.createSocket();
        s.createIO();
        System.out.println("2");
//            
        System.out.println("bb");
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Thread o = new Thread(()
                -> {
            if (e.getSource() == cancel) {
                try {
                    if (socket != null) {
                        output.writeUTF("" + 20);
                    }
                    System.exit(0);
                } catch (IOException ex) {
                    Logger.getLogger(ServerTicTacToe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (e.getSource() == ReloadButton) {
                if (finish) {
                    try {
                        output.writeUTF("10");
                    } catch (IOException ex) {
                    }
                }
            }

            if (finish) {
                if (e.getSource() == BackButton) {
                    int back = Integer.parseInt("" + FiLo.pop());
                    LiFo.push(back);
//                    FiLo.display(FiLo);
//                    LiFo.display(LiFo);
                    Next_Previews = !Next_Previews;
                    b[back].setIcon(null);
                }

                if (e.getSource() == BackForwardButton) {
                    int forward = Integer.parseInt("" + LiFo.pop());
                    FiLo.push(forward);
//                    FiLo.display(FiLo);
//                    LiFo.display(LiFo);

                    if (Next_Previews) { // true
                        setImage("/img/sunglasses.png", b[forward]);
                        Next_Previews = !Next_Previews;
                    } else {  // false
                        setImage("/img/happy.png", b[forward]);
                        Next_Previews = !Next_Previews;
                    }
                }

            }

            i = 0;
            label:
            if (socket != null) {
                while (i < 9) {
                    if (b[i] == e.getSource()) {
                        if (!arrayList.contains(i) && yourTurn) {

                            doMove();
                            playSound();
                        } else {
                            break label;
                        }
                    }
                    i++;
                }
            } else {
                JOptionPane.showMessageDialog(null, "ther is a problem please call your frind to make connection!");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerTicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        o.start();
    }

    void equalScore() {
        try {

            connection = new DbConnect();
            con = connection.db();

            String sql = "insert into challenge (id1,id2,win,datte,movement) values"
                    + "('33" + id2 + "','" + LoginServer.id + "','-1','date(" + "'now'" + ")','0')";
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void minScore() {
        try {

            connection = new DbConnect();
            con = connection.db();
            String aaa = "datetime('now','localtime')";
            String sql = "insert into challenge (id1,id2,win,datte) values"
                    + "('22" + id2 + "','" + LoginServer.id + "','" + id2 + "'," + aaa + ")";
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void addScore() {
        System.out.println("111");
        try {
            System.out.println("2");

            connection = new DbConnect();
            System.out.println("3");
            con = connection.db();
            String aaa = "datetime('now','localtime')";
            String sql = "insert into challenge (id1,id2,win,datte) values"
                    + "('11" + LoginServer.id + "','" + id2 + "','" + LoginServer.id
                    + "'," + aaa + ")";
            System.out.println("4");
            ps = con.prepareStatement(sql);
            System.out.println("5");
            ps.executeUpdate();
//            JOptionPane.showMessageDialog(null, "socesssfully");
        } catch (Exception e) {
            System.out.println("6");
            System.out.println(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e
    ) {
    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == cancel || e.getSource() == ReloadButton || e.getSource() == BackButton || e.getSource() == BackForwardButton) {
            setCursor(HAND_CURSOR);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (e.getSource() == cancel || e.getSource() == ReloadButton || e.getSource() == BackButton || e.getSource() == BackForwardButton) {
            setCursor(DO_NOTHING_ON_CLOSE);
        }
    }

    private boolean isWon() {

        switch (i) {
            case 0:
                if ((ch[1].equals("0") && ch[2].equals("0"))
                        || (ch[3].equals("0") && ch[6].equals("0"))
                        || (ch[4].equals("0") && ch[8].equals("0"))) {
                    finish = true;
                    return true;
                }
                break;

            case 1:
                if ((ch[0].equals("0") && ch[2].equals("0"))
                        || (ch[4].equals("0") && ch[7].equals("0"))) {
                    finish = true;
                    return true;
                }
                break;

            case 2:
                if ((ch[0].equals("0") && ch[1].equals("0"))
                        || (ch[5].equals("0") && ch[8].equals("0"))
                        || (ch[4].equals("0") && ch[6].equals("0"))) {
                    finish = true;
                    return true;
                }
                break;

            case 3:
                if ((ch[0].equals("0") && ch[6].equals("0"))
                        || (ch[4].equals("0") && ch[5].equals("0"))) {
                    finish = true;
                    return true;
                }
                break;

            case 4:
                if ((ch[0].equals("0") && ch[8].equals("0"))
                        || (ch[2].equals("0") && ch[6].equals("0"))
                        || (ch[3].equals("0") && ch[5].equals("0"))
                        || (ch[1].equals("0") && ch[7].equals("0"))) {
                    finish = true;
                    return true;
                }
                break;
            case 5:
                if ((ch[3].equals("0") && ch[4].equals("0"))
                        || (ch[2].equals("0") && ch[8].equals("0"))) {
                    finish = true;
                    return true;
                }
                break;

            case 6:
                if ((ch[0].equals("0") && ch[3].equals("0"))
                        || (ch[7].equals("0") && ch[8].equals("0"))
                        || (ch[4].equals("0") && ch[2].equals("0"))) {
                    finish = true;
                    return true;
                }
                break;
            case 7:
                if ((ch[6].equals("0") && ch[8].equals("0"))
                        || (ch[1].equals("0") && ch[4].equals("0"))) {
                    finish = true;
                    return true;
                }
                break;
            case 8:
                if ((ch[0].equals("0") && ch[4].equals("0"))
                        || (ch[6].equals("0") && ch[7].equals("0"))
                        || (ch[2].equals("0") && ch[5].equals("0"))) {
                    finish = true;
                    return true;
                }
                break;
        }
        return false;
    }

    private boolean isDraw() {
        for (int j = 0; j < 9; j++) {
            if (ch[j].equals("")) {
                return false;
            }
        }
        finish = true;

        try {
            output.writeUTF("15");
            output.writeUTF("b" + LoginServer.id);
        } catch (IOException ex) {
            Logger.getLogger(ServerTicTacToe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        Thread o = new Thread(()
                -> {
            if (e.getKeyCode() == KeyEvent.VK_1) {
                System.out.println(0);
                i = 0;
                label:
                if (socket != null) {
                    if (!arrayList.contains(0) && yourTurn) {
                        doMove();
                        playSound();
                    } else {
                        break label;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ther is a problem please call your frind to make connection!");
                }

            }
            if (e.getKeyCode() == KeyEvent.VK_2) {

                i = 1;
                label:
                if (socket != null) {
                    if (!arrayList.contains(1) && yourTurn) {
                        doMove();
                    } else {
                        break label;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ther is a problem please call your frind to make connection!");
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_3) {

                i = 2;
                label:
                if (socket != null) {
                    if (!arrayList.contains(2) && yourTurn) {
                        doMove();
                    } else {
                        break label;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ther is a problem please call your frind to make connection!");
                }

            }

            if (e.getKeyCode() == KeyEvent.VK_4) {

                i = 3;
                label:
                if (socket != null) {
                    if (!arrayList.contains(3) && yourTurn) {
                        doMove();
                    } else {
                        break label;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ther is a problem please call your frind to make connection!");
                }

            }

            if (e.getKeyCode() == KeyEvent.VK_5) {

                i = 4;
                label:
                if (socket != null) {
                    if (!arrayList.contains(4) && yourTurn) {
                        doMove();
                    } else {
                        break label;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ther is a problem please call your frind to make connection!");
                }

            }

            if (e.getKeyCode() == KeyEvent.VK_6) {

                i = 5;
                label:
                if (socket != null) {
                    if (!arrayList.contains(5) && yourTurn) {
                        doMove();
                    } else {
                        break label;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ther is a problem please call your frind to make connection!");
                }

            }

            if (e.getKeyCode() == KeyEvent.VK_7) {

                i = 6;
                label:
                if (socket != null) {
                    if (!arrayList.contains(6) && yourTurn) {
                        doMove();
                    } else {
                        break label;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ther is a problem please call your frind to make connection!");
                }

            }

            if (e.getKeyCode() == KeyEvent.VK_8) {

                i = 7;
                label:
                if (socket != null) {
                    if (!arrayList.contains(7) && yourTurn) {
                        doMove();
                    } else {
                        break label;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ther is a problem please call your frind to make connection!");
                }

            }

            if (e.getKeyCode() == KeyEvent.VK_9) {

                i = 8;
                label:
                if (socket != null) {
                    if (!arrayList.contains(8) && yourTurn) {
                        doMove();
                    } else {
                        break label;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ther is a problem please call your frind to make connection!");
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerTicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        o.start();
    }

    private void doMove() {
        arrayList.add(i);

        yourTurn = false;
        ch[i] = "0";
        setImage("/img/happy.png", b[i]);

        if (socket == null) {
            JOptionPane.showMessageDialog(null, "ther is a problem please call your frind to make connection!");
        } else {
            try {
                output.writeUTF(("" + i).trim());
            } catch (IOException ex) {
                Logger.getLogger(ServerTicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
            FiLo.add(i);
            FiLo.display(FiLo);

        }

        if (isWon()) {
            score++;
            scoreLabel.setText("score: " + score);
            Next_Previews = true;
            try {
                output.writeUTF(("" + 9).trim());
                output.writeUTF(("" + LoginServer.id).trim());

            } catch (IOException ex) {
                Logger.getLogger(ServerTicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "you won!");
            yourTurn = false;
            finish = true;
            addScore();
        }

        if (isDraw()) {

            equalScore();
            JOptionPane.showMessageDialog(null, "Draw!");
        }
    }

    private void playSound() {
        try {

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(ServerTicTacToe.class.getResource("/audio/move.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

        } catch (Exception e) {
        }
    }
}
