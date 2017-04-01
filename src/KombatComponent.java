
import com.github.jonatabecker.commons.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author JonataBecker
 */
public class KombatComponent extends JPanel {

    private World world;
    private Image runRight;
    private Image waiting;
    private Image punching;

    public KombatComponent(World world) {
        super();
        this.world = world;
        initImages();
    }

    private void initImages() {
        try {
            waiting = new ImageIcon(getClass().getResource("waiting.gif")).getImage();
            punching = new ImageIcon(getClass().getResource("punch.gif")).getImage();
            runRight = new ImageIcon(getClass().getResource("walk_r.gif")).getImage();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setWord(World world) {
        this.world = world;
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
        world.getPlayers().forEach((player) -> {
            int width = waiting.getWidth(this);
            int height = waiting.getHeight(this);
            Image image = waiting;
            if (player.isWalking()) {
                width = runRight.getWidth(this);
                height = runRight.getHeight(this);
                image = runRight;
            }

            if (player.isPunching()) {
                width = punching.getWidth(this);
                height = punching.getHeight(this);
                image = punching;
            }
            int x = player.getX();
            int y = player.getY();
            if (player.isPosRight()) {
                x += width;
                width = width * -1;
            }
            g.drawImage(image, x, y, width, height, this);
        });
        world.getBullets().forEach((b) -> {
            g.setColor(Color.PINK);
            g.fillOval(b.getX(), b.getY(), 15, 15);
        });
    }

}
