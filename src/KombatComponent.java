
import com.github.jonatabecker.commons.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *
 * @author JonataBecker
 */
public class KombatComponent extends JComponent {

    private World world;
    private Image runRight;

    public KombatComponent(World world) {
        super();
        this.world = world;
        initImages();
    }

    private void initImages() {
        try {
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
        world.getPlayers().forEach((player) -> {
            g.drawImage(runRight, player.getX(), player.getY(), this);
        });
        System.out.println("paint" + System.currentTimeMillis());
    }

}
