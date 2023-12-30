import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;

public class GraphicsProgram extends JFrame {

    private Image background;
    private Image planet;
    private int planetX;
    private int planetY;
    private int starSize;
    private int mouseX;
    private int mouseY;
    private String soundFile = "click.wav";

    public GraphicsProgram() {
        setTitle("Graphics Program");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            background = ImageIO.read(new File("background.jpg"));
            planet = ImageIO.read(new File("planet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        planetX = getWidth() / 2 - planet.getWidth(null) / 2;
        planetY = getHeight() / 2 - planet.getHeight(null) / 2;
        starSize = 5;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                playClickSound();
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    starSize += 5;
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    starSize -= 5;
                    if (starSize < 5) {
                        starSize = 5;
                    }
                    repaint();
                }
            }
        });

        setFocusable(true);
        requestFocus();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }
        if (planet != null) {
            g.drawImage(planet, planetX, planetY, null);
        }
        g.setColor(Color.YELLOW);
        g.fillOval(mouseX - starSize / 2, mouseY - starSize / 2, starSize, starSize);
    }

    private void playClickSound() {
        try {
            File file = new File(soundFile);
            if (file.exists()) {
                if (file.canRead()) {
                    AudioClip clip = Applet.newAudioClip(file.toURI().toURL());
                    clip.play();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphicsProgram program = new GraphicsProgram();
            program.setVisible(true);
        });
    }
}




            