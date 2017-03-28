
import com.github.jonatabecker.commons.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *
 * @author JonataBecker
 */
public class KombatComponent extends JComponent {

    private World world;
    private Image runRight;
    private Animation animation;

    public KombatComponent(World world) {
        super();
        this.world = world;
        initImages();

        Thread th = new Thread(() -> {
            try {
                while (true) {
                    repaint();
                    Thread.sleep(1000 / 12);
                }
            } catch (Exception e) {
            }
        });
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
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
        world.getPlayers().forEach((player) -> {
//            animation.draw(g2d, player.getX(), player.getY());
            g.drawImage(runRight, player.getX(), player.getY(), this);
        });
    }

}
