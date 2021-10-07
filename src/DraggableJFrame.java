import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DraggableJFrame extends JFrame implements MouseListener,MouseMotionListener {

    JFrame frame;
    JPanel panel;
    public DraggableJFrame(JFrame x) {
        frame=x;
        frame.setUndecorated(true);
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
    }
    public DraggableJFrame(JFrame f, JPanel x) {
        frame=f;
        panel=x;
        frame.setUndecorated(true);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
    }
   private int posX=0,posY=0;

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        posX=e.getX();
        posY=e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       }

    @Override
    public void mouseEntered(MouseEvent e) {
        }

    @Override
    public void mouseExited(MouseEvent e) {
       }

    @Override
    public void mouseDragged(MouseEvent e) {
        frame.setLocation (e.getXOnScreen()-posX,e.getYOnScreen()-posY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
       
    }
}