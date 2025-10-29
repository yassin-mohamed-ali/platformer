
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class main extends JPanel {
    int x = 800;
    int x2 = 400;
    int py = 300;
    int score = 0;
    boolean jump = false;
    boolean fall = false;
    boolean alive = true;
    Image playerImage = new ImageIcon("assets/player.png").getImage();
    Image objImage = new ImageIcon("assets/object.png").getImage();
    
   
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public main() {
        setBackground(Color.CYAN);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        Timer timer = new Timer(1, e -> {
            if (alive){
                x-=2;
                x2-=2;
            
                if (x < -20) { 
                    x = WIDTH;
                }
                if (x2 < -20) {
                    x2 = WIDTH;
                }
            repaint();
        }
        });
        timer.start();



        setupKeyBindings();
    }
    private void setupKeyBindings() {
    getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "jump");
    getActionMap().put("jump", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (py == 300){
                jump = true;
            }
        }
    });
    getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "restart");
    getActionMap().put("restart", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!alive) {
                py = 300;
                x = 800;
                x2 = 400;
                alive = true;
                jump = false;
                fall = false;
                score = 0;
            }
        }
    });
}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.black);
        g.drawString("score: " + score, 10, 50);
        g.setColor(Color.green);
        g.fillRect(0, 500, 800, 600);
        g.setColor(Color.blue);
        g.drawImage(playerImage,100, py, 50, 200, this);
        Rectangle playerRectangle = new Rectangle(100, py, 50, 200);
        g.setColor(Color.red);
        g.drawImage(objImage, x, 400, 20, 100, this);
        Rectangle objRectangle = new Rectangle(x, 400, 20, 100);
        g.drawImage(objImage, x2, 400, 20, 100, this);
        Rectangle obj2Rectangle = new Rectangle(x2, 400, 20, 100);
        
        if (playerRectangle.intersects(objRectangle) || playerRectangle.intersects(obj2Rectangle)){
            g.setColor(Color.BLACK);
            g.drawString("GAME OVER!", 300, 300);
            g.drawString("PRESS ENTER TO RESTART", 300, 400);
            alive = false;
        }
        if (x == 10 || x2 == 10){
            score++;
        }
        if (alive) {
        
            if (jump) {
                if (py > 150){
                    py -= 2;
        }
                else {
                jump = false;
                fall = true;
            }

        }
            if(fall) {
                if (py < 300) {
                    py += 2;
            }
                else {
                    fall = false;
            }
        }}
    }

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("platformer");
        main gamePanel = new main();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
}