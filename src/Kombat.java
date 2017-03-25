
import com.github.jonatabecker.commons.Commands;
import com.github.jonatabecker.commons.World;
import com.github.jonatabecker.commons.WorldParser;
import java.awt.BorderLayout;
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
public class Kombat extends JFrame implements Commands {

    private World world;
    private final WorldParser worldParser;

    private Socket s;
    private BufferedReader in;
    private PrintWriter out;
    private KombatComponent component;

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
        setLayout(new BorderLayout());
        component = new KombatComponent(world);
        add(component, BorderLayout.CENTER);
    }

    private void initEvents() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    out.println("P" + RIGHT);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    out.println("P" + LEFT);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    out.println("R" + RIGHT);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    out.println("R" + LEFT);
                }
            }
        });
        addWindowListener(new WindowAdapter() {

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
                    component.setWord(world);
                    SwingUtilities.invokeLater(() -> {
                        component.revalidate();
                        System.out.println("Parser" + System.currentTimeMillis());
                    });
                }
            } catch (Exception e) {
                System.exit(1);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public static void main(String[] args) {
        Kombat kombat = new Kombat();
    }

}