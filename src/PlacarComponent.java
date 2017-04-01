
import com.github.jonatabecker.commons.Player;
import com.github.jonatabecker.commons.World;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author JonataBecker
 */
public class PlacarComponent extends JComponent {

    private World world;

    public PlacarComponent(World world) {
        super();
        this.world = world;

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(0, 150));
        setLayout(new BorderLayout());
        add(panel);
        setBackground(Color.red);
    }

    public void setWord(World world) {
        this.world = world;
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        if (world == null) {
            return;
        }
        List<Player> list = new ArrayList<>(world.getPlayers());
        int y = 15;
        for (int i = 0; i < list.size(); i++) {
            Player player = list.get(i);
            int x = (i % 2 == 0) ? 5 : 415;
            if (i % 2 == 0 && i > 0) {
                y += 40;
            }
            g.setColor(Color.black);
            if (player.isDead()) {
                g.setColor(Color.red);
            }
            g.drawString("Jogador " + player.getKey() + (player.isDead() ? " (dead)" :""), x, y);
            g.setColor(Color.decode("#999999"));
            g.fillRect(x, y + 10, 375, 15);
            g.setColor(Color.red);
            int w = 373;
            w =  w * (int) player.getLivePercent() / 100;
            g.fillRect(x+ 1, y + 11, w, 13);
        }

    }

}
