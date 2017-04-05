
import com.github.jonatabecker.commons.Commands;
import static com.github.jonatabecker.commons.Commands.PUNCH;
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
    private PlacarComponent placarComponent;
    
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
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        component = new KombatComponent(world);
        placarComponent = new PlacarComponent(world);
        add(placarComponent, BorderLayout.NORTH);
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
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    out.println("P" + PUNCH);
                }                
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    out.println("P" + BULLET);
                }                
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    out.println("P" + DOWN);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    out.println("P" + UP);
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
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    out.println("R" + PUNCH);
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    out.println("R" + BULLET);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    out.println("R" + DOWN);
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    out.println("R" + UP);
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
                    placarComponent.setWord(world);
                    SwingUtilities.invokeLater(() -> {
                        component.revalidate();
                        placarComponent.repaint();
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
