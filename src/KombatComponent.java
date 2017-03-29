
import com.github.jonatabecker.commons.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author JonataBecker
 */
public class KombatComponent extends JPanel {

    private World world;
    private Image runRight;
    private Animation animation;

    public KombatComponent(World world) {
        super();
        this.world = world;
        initImages();

        
        Timer timer = new Timer(175, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                
                repaint();
            }
        });
        timer.start();
        
        
        Thread th = new Thread(() -> {
            try {
                while (true) {
//                    SwingUtilities.invokeLater(() -> {
                        updateUI();
//                    });
                    Thread.sleep(1000 / 12);
                }
            } catch (Exception e) {
            }
        });
        th.setDaemon(true);
        th.start();
    }

    private void initImages() {
        try {
            runRight = new ImageIcon(getClass().getResource("walk_r.gif")).getImage();
        } catch (Exception e) {
            System.out.println(e);
        }
        animation = new Animation();
    }

    public void setWord(World world) {
        this.world = world;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
        world.getPlayers().forEach((player) -> {
            animation.draw(g2d, player.getX(), player.getY(), this);
            System.out.println(System.currentTimeMillis());
        });
    }

}
