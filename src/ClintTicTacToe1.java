
import generic.Stack;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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

public class ClintTicTacToe1 extends JFrame implements MouseListener, KeyListener {

	private static boolean Next_Previews = false;
	private static String username;

	private final JButton BackButton;
	private Stack LiFo;
	private final JButton BackForwardButton;
	private final JButton cancel;
	private final JButton makeConnectionButton;
	private final JLabel usernameLabel;
	private static int score;
	private final JLabel scoreLabel;
	Connection con;
	DbConnect connection;
	private PreparedStatement ps = null;
	private ResultSet rs;
	private int pos;
	private final JLabel userProfileLabel;
	private final JButton SettingButton;

	private void setImage(String name, JButton b) {
		Image ImageName = new ImageIcon(this.getClass().getResource(name)).getImage();
		b.setIcon(new ImageIcon(ImageName));
	}

	private void setImageLabel(String name, JLabel b, int x, int y) {
		Image ImageName = new ImageIcon(this.getClass().getResource(name)).getImage().getScaledInstance(x, y,
				Image.SCALE_DEFAULT);

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
	}

	JButton b[] = new JButton[9];
	String ch[] = new String[9];
	JPanel p;
	int i = 0;

	boolean yourTurn = true;
	private static ServerSocket serversocket;
	private static Socket socket = null;
	private static DataInputStream input;
	private static DataOutputStream output;
	private Stack FiLo;
	private ArrayList<Integer> arrayList;
	private final JButton ReloadButton;
	private boolean finish = false;
	public String id2;

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	void makeSocket() {

		try {
			socket = new Socket("localhost", 9999);
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
						// JOptionPane.showMessageDialog(null, "drawwww la clint xot " + LoginClint.id +
						// "barambarakat = " + id2);
					} else {
						// JOptionPane.showMessageDialog(null, "clint clint clint is numeric brawa = " +
						// LoginClint.id + "\n dorawa =" + id2);
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
						int reply = JOptionPane.showConfirmDialog(null, "Do You Want Rematch?", "Question",
								JOptionPane.YES_NO_OPTION);

						if (reply == JOptionPane.YES_OPTION) {
							output.writeUTF("11");
							doReload();
						}
					}

					if (pos == 9) {
						String s = input.readUTF().trim();
						id2 = s;
						output.writeUTF("a" + LoginClint.id);
						System.out.println("pos==9 in clint " + LoginClint.id);
						minScore();
						finish = true;
						yourTurn = false;
						JOptionPane.showMessageDialog(null, "you are Lost!");
						// ionPane.showMessageDialog(null, "clint brawa = " + id2 + "\n dorawa = " +
						// LoginClint.id);
					} else {
						yourTurn = true;
					}

					if (pos == 15) {
						output.writeUTF("b" + LoginClint.id);
						finish = true;
						JOptionPane.showMessageDialog(null, "Draw!");
					}
					if (pos < 9) {
						ch[pos] = "0";
						setImage("/img/happy.png", b[pos]);

					}
				}
			}
		} catch (Exception e) {
		}
	}

	public ClintTicTacToe1() throws IOException {

		arrayList = new ArrayList<Integer>();

		FiLo = new Stack();
		LiFo = new Stack();
		setTitle("Clint");
		p = new JPanel();
		p.setLayout(null);

		new DraggableJFrame(this);

		makeConnectionButton = new JButton("Reconnection");
		makeConnectionButton.setBounds(110, 475, 124, 30);
		makeConnectionButton.addMouseListener(this);
		makeConnectionButton.setBackground(null);
		makeConnectionButton.setForeground(Color.white);
		p.add(makeConnectionButton);

		ReloadButton = new JButton();
		ReloadButton.setBounds(140, 400, 64, 64);
		ReloadButton.addMouseListener(this);
		ReloadButton.setFont(new Font("Tahoma", 1, 20));
		ReloadButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		p.add(ReloadButton);
		ReloadButton.setBackground(null);
		p.setBackground(new Color(40, 12, 2, 255));
		setImage("/img/reload.png", ReloadButton);

		BackButton = new JButton();
		BackButton.setBounds(30, 400, 64, 64);
		BackButton.addMouseListener(this);
		BackButton.setFont(new Font("Tahoma", 1, 20));
		p.add(BackButton);
		BackButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		BackButton.setBackground(null);
		setImage("/img/left.png", BackButton);

		SettingButton = new JButton();
		SettingButton.setBounds(220, 10, 32, 32);
		SettingButton.addMouseListener(this);
		SettingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

				System.out.print(1111);
			}
		});

		SettingButton.setFont(new Font("Tahoma", 1, 20));
		p.add(SettingButton);
		SettingButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		SettingButton.setBackground(null);
		setImage("/img/success.png", SettingButton);

		userProfileLabel = new JLabel();
		userProfileLabel.setBounds(260, 10, 32, 32);
		userProfileLabel.addMouseListener(this);
		userProfileLabel.setBackground(null);
		userProfileLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		p.add(userProfileLabel);
		setImageLabel("/img/sunglasses.png", userProfileLabel, 30, 30);

		BackForwardButton = new JButton();
		BackForwardButton.setBounds(264, 400, 64, 64);
		BackForwardButton.addMouseListener(this);
		BackForwardButton.setFont(new Font("Tahoma", 1, 20));
		p.add(BackForwardButton);
		BackForwardButton.setBackground(null);
		BackForwardButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
		setImage("/img/right.png", BackForwardButton);

		usernameLabel = new JLabel("username: " + username);
		usernameLabel.setBounds(20, 10, 100, 20);
		p.add(usernameLabel);
		usernameLabel.setForeground(Color.WHITE);
		usernameLabel.setFont(new Font("Tahoma", 1, 12));

		scoreLabel = new JLabel("score: " + score);
		scoreLabel.setBounds(20, 40, 100, 20);
		p.add(scoreLabel);
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setFont(new Font("Tahoma", 1, 12));

		cancel = new JButton();
		p.add(cancel);
		cancel.setBounds(300, 10, 32, 32);
		cancel.setBackground(null);
		cancel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
		cancel.addMouseListener(this);
		setImage("/img/stop.png", cancel);

		add(p);

		i = 0;
		while (i < 9) {
			ch[i] = "";
			b[i] = new JButton();
			p.add(b[i]);
			b[i].setBackground(null);
			b[i].addMouseListener(this);
			if (i < 3) {
				b[i].setBounds(16 + i * 105, 80, 100, 100);
			} else if (i < 6) {
				b[i].setBounds(16 + (i - 3) * 105, 185, 100, 100);
			} else if (i < 9) {
				b[i].setBounds(16 + (i - 6) * 105, 290, 100, 100);
			}
			b[i].setFont(new Font("Tahoma", 1, 20));
			i++;
		}

		setFocusable(true);
		addKeyListener(this);
	}

	@Override
	public void setFocusable(boolean b) {
		super.setFocusable(b);
	}

	public static void main(String[] args) throws IOException {

		LoginClint l = new LoginClint();
		l.setBounds(280, 210, 810, 350);
		l.setVisible(true);

		while (l.username == null) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
		}
		username = l.username;
		ClintTicTacToe1 s = new ClintTicTacToe1();
		s.setVisible(true);
		s.setBounds(420, 140, 345, 520);

		if (socket == null) {
			s.makeSocket();
		}

//     ClintTicTacToe1 l = new ClintTicTacToe1();
//            l.setBounds(280, 210, 800, 350);
//            l.setVisible(true);
	}

	void equalScore() {
		try {
//                        JOptionPane.showMessageDialog(null,"brdmanawa");

			connection = new DbConnect();
			con = connection.db();

			String sql = "insert into challenge (id1,id2,win,datte,movement) values" + "('66" + LoginClint.id + "','"
					+ id2 + "','-1','date('now')','0')";
			ps = con.prepareStatement(sql);
			ps.executeUpdate();
//                JOptionPane.showMessageDialog(null, "sucessfully!");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void minScore() {
		try {

			connection = new DbConnect();
			con = connection.db();
			String aaa = "datetime('now','localtime')";
			String sql = "insert into challenge (id1,id2,win,datte) values" + "('55" + id2 + "','" + LoginClint.id
					+ "','" + id2 + "'," + aaa + ")";
			ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void addScore() {
		try {
			connection = new DbConnect();
			con = connection.db();
			String aaa = "datetime('now','localtime')";
			String sql = "insert into challenge (id1,id2,win,datte) values" + "('44" + LoginClint.id + "','" + id2
					+ "','" + LoginClint.id + "'," + aaa + ")";
			ps = con.prepareStatement(sql);
			ps.executeUpdate();
			// JOptionPane.showMessageDialog(null, "socesssfully");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Thread o = new Thread(() -> {
			if (socket == null) {
				if (e.getSource() == makeConnectionButton) {
					makeSocket();
				}
			}

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
						Logger.getLogger(ClintTicTacToe1.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
			if (finish) {
				if (e.getSource() == BackButton) {
					int back = Integer.parseInt("" + FiLo.pop());
					LiFo.push(back);
					FiLo.display(FiLo);
					LiFo.display(LiFo);
					Next_Previews = !Next_Previews;
					b[back].setIcon(null);
				}

				if (e.getSource() == BackForwardButton) {
					int forward = Integer.parseInt("" + LiFo.pop());
					FiLo.push(forward);
//                    FiLo.display(FiLo);
//                    LiFo.display(LiFo);
//                    
//                    FiLo.display(FiLo);
//                    LiFo.display(LiFo);
					if (Next_Previews) {
						setImage("/img/happy.png", b[forward]);
						Next_Previews = !Next_Previews;
					} else {
						setImage("/img/sunglasses.png", b[forward]);
						Next_Previews = !Next_Previews;
					}
				}

			}

			i = 0;
			label: if (socket != null) {
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

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == cancel || e.getSource() == ReloadButton || e.getSource() == BackButton
				|| e.getSource() == BackForwardButton || e.getSource() == makeConnectionButton) {
			setCursor(HAND_CURSOR);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {

		if (e.getSource() == cancel || e.getSource() == ReloadButton || e.getSource() == BackButton
				|| e.getSource() == BackForwardButton || e.getSource() == makeConnectionButton) {
			setCursor(DO_NOTHING_ON_CLOSE);
		}
	}

	private boolean isWon() {

		switch (i) {
		case 0:
			if ((ch[1].equals("*") && ch[2].equals("*")) || (ch[3].equals("*") && ch[6].equals("*"))
					|| (ch[4].equals("*") && ch[8].equals("*"))) {
				finish = true;
				return true;
			}
			break;
		case 1:
			if ((ch[0].equals("*") && ch[2].equals("*")) || (ch[4].equals("*") && ch[7].equals("*"))) {
				finish = true;
				return true;
			}
			break;
		case 2:
			if ((ch[0].equals("*") && ch[1].equals("*")) || (ch[5].equals("*") && ch[8].equals("*"))
					|| (ch[4].equals("*") && ch[6].equals("*"))) {
				finish = true;
				return true;
			}
			break;

		case 3:
			if ((ch[0].equals("*") && ch[6].equals("*")) || (ch[4].equals("*") && ch[5].equals("*"))) {
				finish = true;
				return true;
			}
			break;

		case 4:
			if ((ch[0].equals("*") && ch[8].equals("*")) || (ch[2].equals("*") && ch[6].equals("*"))
					|| (ch[3].equals("*") && ch[5].equals("*")) || (ch[1].equals("*") && ch[7].equals("*"))) {
				finish = true;
				return true;
			}
			break;
		case 5:
			if ((ch[3].equals("*") && ch[4].equals("*")) || (ch[2].equals("*") && ch[8].equals("*"))) {
				finish = true;
				return true;
			}
			break;

		case 6:
			if ((ch[0].equals("*") && ch[3].equals("*")) || (ch[7].equals("*") && ch[8].equals("*"))
					|| (ch[4].equals("*") && ch[2].equals("*"))) {
				finish = true;
				return true;
			}
			break;
		case 7:
			if ((ch[6].equals("*") && ch[8].equals("*")) || (ch[1].equals("*") && ch[4].equals("*"))) {
				finish = true;
				return true;
			}
			break;
		case 8:
			if ((ch[0].equals("*") && ch[4].equals("*")) || (ch[6].equals("*") && ch[7].equals("*"))
					|| (ch[2].equals("*") && ch[5].equals("*"))) {
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
			output.writeUTF("b" + LoginClint.id);
		} catch (IOException ex) {
			Logger.getLogger(ClintTicTacToe1.class.getName()).log(Level.SEVERE, null, ex);
		}
		return true;
	}

	private void doMove() {
		arrayList.add(i);
		yourTurn = false;

		ch[i] = "*";
		setImage("/img/sunglasses.png", b[i]);
		try {
			if (socket != null) {
				output.writeUTF(("" + i).trim());
				FiLo.add(i);
				FiLo.display(FiLo);

				if (isWon()) {
					score++;
					scoreLabel.setText("score: " + score);
					yourTurn = false;
					Next_Previews = true;
					finish = true;
					output.writeUTF(("" + 9).trim());
					output.writeUTF(("" + LoginClint.id).trim());
					addScore();
					JOptionPane.showMessageDialog(null, "You Won!");
//                                        JOptionPane.showMessageDialog(null, "brawa  = "+LoginClint.id+"\n dorawa = "+id2);
				}

				if (isDraw()) {
					equalScore();
					JOptionPane.showMessageDialog(null, "Draw!");
				}
			}
		} catch (IOException ex) {
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Thread o = new Thread(() -> {
			if (e.getKeyCode() == KeyEvent.VK_1) {
				System.out.println(0);
				i = 0;
				label: if (socket != null) {
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
				label: if (socket != null) {
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
				label: if (socket != null) {
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
				label: if (socket != null) {
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
				label: if (socket != null) {
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
				label: if (socket != null) {
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
				label: if (socket != null) {
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
				label: if (socket != null) {
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
				label: if (socket != null) {
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

	private void playSound() {
		try {
			AudioInputStream audioIn = AudioSystem
					.getAudioInputStream(ServerTicTacToe.class.getResource("/audio/move.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();

		} catch (Exception e) {
		}
	}
}
