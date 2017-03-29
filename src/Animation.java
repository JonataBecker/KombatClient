/*
 * KombatClient
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Descrição da classe.
 */
public class Animation {

    private final List<Image> frames;

    private int frame = 0;

    public Animation() {
        this.frames = new ArrayList<>();

        try {
            BufferedImage buff = ImageIO.read(getClass().getResource("sprite.png"));

            
            
            frames.add(buff.getSubimage(4, 22, 75, 140));
            frames.add(buff.getSubimage(100, 0, 75, 140));
            frames.add(buff.getSubimage(200, 0, 75, 140));
            frames.add(buff.getSubimage(300, 0, 75, 140));



        } catch (Exception e) {
        }

    }

    public void draw(Graphics2D g2d, int x, int y, JComponent component) {
        g2d.drawRect(x, y, 100, 100);

//        g2d.drawImage(frames.get(frame), x, y, component);
        frame++;
        if (frame >= frames.size()) {
            frame = 0;
        }
    }

}
