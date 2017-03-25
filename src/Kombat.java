
import com.github.jonatabecker.commons.World;
import com.github.jonatabecker.commons.WorldParser;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author JonataBecker
 */
public class Kombat extends JFrame {

    private World world;
    private final WorldParser worldParser;

    private Socket s;
    private BufferedReader in;
    private PrintWriter out;

    public Kombat() {
        this.world = new World();
        this.worldParser = new WorldParser();
        initGui();
        initEvents();
        connect();
        run();
    }

    private void initGui() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initEvents() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    out.println("PR_R");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    out.println("RE_R");
                }
            }
        });
        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.
//            }
//            
            
            
            @Override
            public void windowClosing(WindowEvent e) {
                out.println("exit");
            }

        });
    }

    private void connect() {
        try {
            s = new Socket("localhost", 8880);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() {
        Thread thread = new Thread(() -> {
            String command;
            try {
                while (true) {
                    command = in.readLine();
                    world = worldParser.toObject(command);
                    System.out.println(System.currentTimeMillis());
                    
//                    SwingUtilities.invokeLater(() -> {
                        repaint();
//                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        g2d.setColor(Color.red);
        world.getPlayers().forEach((player) -> {
            g2d.fillRect(player.getX(), player.getY(), 100, 100);
        });
    }

    public static void main(String[] args) {
        Kombat kombat = new Kombat();
    }

}
