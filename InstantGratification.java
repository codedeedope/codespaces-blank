import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.util.*;
import java.util.List;
import javax.swing.Timer;
import java.awt.geom.AffineTransform;

/**
 * InstantGratification.java
 *
 * A tiny Swing program that shows an animated confetti burst and a cheerful message
 * when you press the button. No external libraries required.
 *
 * Usage:
 *   javac InstantGratification.java
 *   java InstantGratification
 */
public class InstantGratification extends JPanel {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 420;
    private final List<Particle> particles = Collections.synchronizedList(new ArrayList<>());
    private final Random rnd = new Random();
    private final Timer timer;
    private String message = "Welcome — press the button for a little joy ✨";
    private float messageAlpha = 1.0f;

    private static final String[] MESSAGES = new String[] {
        "You're doing great — keep going! 🌟",
        "Small wins matter. Celebrate them! 🎉",
        "You're capable of amazing things. 💪",
        "Smile — you make the world better. 😊",
        "One step at a time. You've got this. 🚀",
        "Kindness looks good on you. 🌈",
        "Brilliant move — take a bow. 👏",
        "Your creativity is contagious. ✨",
        "Breathe. Relax. Shine. ✨"
    };

    public InstantGratification() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(20, 23, 28));
        setLayout(null); // we'll place buttons manually

        JButton burstButton = new JButton("Make me smile");
        burstButton.setFocusPainted(false);
        burstButton.setBounds(20, HEIGHT - 70, 160, 40);
        burstButton.addActionListener(e -> burst());
        add(burstButton);

        JButton copyButton = new JButton("Copy message");
        copyButton.setBounds(200, HEIGHT - 70, 140, 40);
        copyButton.addActionListener(e -> copyToClipboard(message));
        add(copyButton);

        JButton calmButton = new JButton("Calm sparkle");
        calmButton.setBounds(360, HEIGHT - 70, 140, 40);
        calmButton.addActionListener(e -> calm());
        add(calmButton);

        timer = new Timer(28, e -> {
            updateParticles();
            repaint();
        });
        timer.start();

        // subtle initial burst
        SwingUtilities.invokeLater(() -> {
            spawnParticles(WIDTH / 2, HEIGHT / 2, 40);
            fadeInNewMessage("Hello! Instant joy, loading... ✨");
        });
    }

    private void burst() {
        spawnParticles(WIDTH / 2, HEIGHT / 2 - 40, 120);
        fadeInNewMessage(MESSAGES[rnd.nextInt(MESSAGES.length)]);
    }

    private void calm() {
        // gentle, few slow particles + calm message
        spawnParticles(WIDTH / 2, HEIGHT / 2 - 50, 30, 0.8, 0.6, 0.2);
        fadeInNewMessage("Breathe. You're exactly where you need to be. 🌿");
    }

    private void spawnParticles(int cx, int cy, int count) {
        spawnParticles(cx, cy, count, 1.0, 1.0, 1.0);
    }

    private void spawnParticles(int cx, int cy, int count, double speedScale, double spreadScale, double sizeScale) {
        for (int i = 0; i < count; i++) {
            double angle = rnd.nextDouble() * Math.PI * 2;
            double speed = (rnd.nextDouble() * 6 + 2) * speedScale;
            double vx = Math.cos(angle) * speed * (0.6 + rnd.nextDouble() * spreadScale);
            double vy = Math.sin(angle) * speed * (0.6 + rnd.nextDouble() * spreadScale) - 3;
            Color color = Color.getHSBColor(rnd.nextFloat(), 0.8f, 0.95f);
            double size = 6 + rnd.nextDouble() * 10 * sizeScale;
            particles.add(new Particle(cx + rnd.nextInt(32) - 16, cy + rnd.nextInt(32) - 16, vx, vy, color, size));
        }
    }

    private void updateParticles() {
        synchronized (particles) {
            Iterator<Particle> it = particles.iterator();
            while (it.hasNext()) {
                Particle p = it.next();
                p.vy += 0.18; // gravity
                p.x += p.vx;
                p.y += p.vy;
                p.life -= 0.018f;
                p.rotation += p.spin;
                if (p.y > HEIGHT - 90) { // ground bounce
                    p.y = HEIGHT - 90;
                    p.vy *= -0.45;
                    p.vx *= 0.85;
                    p.life -= 0.06f;
                }
                if (p.life <= 0.01f || p.x < -50 || p.x > WIDTH + 50) {
                    it.remove();
                }
            }
            // slowly fade message
            if (messageAlpha > 0.98f) {
                // hold
            } else if (messageAlpha > 0.02f) {
                messageAlpha -= 0.004f;
            } else {
                messageAlpha = 0f;
            }
        }
    }

    private void fadeInNewMessage(String msg) {
        this.message = msg;
        this.messageAlpha = 1.0f;
    }

    private void copyToClipboard(String text) {
        try {
            Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(new StringSelection(text), null);
            // small confirmation burst
            spawnParticles(120, HEIGHT - 60, 18);
            fadeInNewMessage("Copied to clipboard ✨");
        } catch (Exception ex) {
            fadeInNewMessage("Couldn't copy — but you're still awesome!");
        }
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0.create();
        // smooth
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // soft radial glow behind center
        Point center = new Point(WIDTH / 2, HEIGHT / 2 - 30);
        GradientPaint gp = new GradientPaint(center.x, center.y - 120, new Color(40, 45, 55, 200),
                                               center.x, center.y + 160, new Color(10, 12, 14, 200));
        g.setPaint(gp);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // draw particles (sparkles and confetti)
        synchronized (particles) {
            for (Particle p : particles) {
                float a = Math.max(0f, Math.min(1f, p.life));
                Color c = new Color(p.color.getRed(), p.color.getGreen(), p.color.getBlue(), (int)(a * 255));
                g.setColor(c);
                int s = (int) Math.max(2, p.size);
                // draw as rotated rounded rectangle or sparkle text
                if (rnd.nextDouble() < 0.18) {
                    // sparkle glyph
                    g.setFont(g.getFont().deriveFont((float)s + 10f));
                    g.drawString("✨", (int)p.x - s, (int)p.y);
                } else {
                    AffineTransform old = g.getTransform();
                    g.rotate(p.rotation, p.x, p.y);
                    g.fillRoundRect((int)(p.x - s/2), (int)(p.y - s/2), s, Math.max(2, s/2), 4, 4);
                    g.setTransform(old);
                }
            }
        }

        // message
        g.setFont(new Font("SansSerif", Font.BOLD, 26));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.min(1f, messageAlpha)));
        // shadow
        g.setColor(new Color(0, 0, 0, 140));
        drawCenteredString(g, message, WIDTH/2 + 2, HEIGHT/2 - 10 + 2);
        // main text
        g.setColor(new Color(255, 255, 255, 230));
        drawCenteredString(g, message, WIDTH/2, HEIGHT/2 - 10);

        // footer hint
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.setColor(new Color(200,200,200,160));
        g.drawString("Tip: click \"Make me smile\" or \"Copy message\" for a tiny boost.", 20, HEIGHT - 20);

        g.dispose();
    }

    private void drawCenteredString(Graphics2D g, String text, int cx, int cy) {
        FontMetrics fm = g.getFontMetrics();
        int w = fm.stringWidth(text);
        int h = fm.getAscent() - fm.getDescent();
        g.drawString(text, cx - w/2, cy + h/2);
    }

    private static class Particle {
        double x,y,vx,vy;
        Color color;
        double size;
        float life;
        double rotation = 0;
        double spin;

        Particle(double x, double y, double vx, double vy, Color color, double size) {
            this.x = x; this.y = y; this.vx = vx; this.vy = vy; this.color = color; this.size = size;
            this.life = 1.0f;
            this.spin = (Math.random() - 0.5) * 0.3;
            this.rotation = Math.random() * Math.PI * 2;
        }
    }

    private static void createAndShow() {
        JFrame frame = new JFrame("Instant Gratification ✨");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        InstantGratification panel = new InstantGratification();
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        // friendly icon (optional)
        try {
            frame.setIconImage(new ImageIcon(InstantGratification.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")).getImage());
        } catch (Exception ignored) { }
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InstantGratification::createAndShow);
    }
}
