import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class SnakeGame extends JFrame{
    MainPanel jp;
    JLabel statusText = new JLabel();
    JLabel scoreText = new JLabel();
    static int FrameWidth = 300;
    static int FrameHeight = 300;
    int score = 0;
    Snake s;
    boolean freeze = false;

    public void playAgain(String opt){
        if (freeze) {
            if(opt.equals("Y")) {
                s = new Snake();
                score = 0;
                statusText.setText("Playing...");
                scoreText.setText("Score: " + score);
                freeze = false;
            }
            if(opt.equals("N")){
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        }
    }

    public SnakeGame() {
        super("Simple Drawing");
        setSize(FrameWidth, FrameHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new FlowLayout());

        jp = new MainPanel(FrameWidth-70,FrameHeight-70);
        
        s = new Snake();

        statusText.setText("Playing...");
        scoreText.setText("Score: "+score);

        add(jp);
        add(statusText);
        add(scoreText);


        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                String str; // default
                switch( key ) {
                    case KeyEvent.VK_UP:
                        str = "up";// up
                        break;
                    case KeyEvent.VK_DOWN:
                        str = "down";
                        // down
                        break;
                    case KeyEvent.VK_LEFT:
                        str = "left";
                        // left
                        break;
                    case KeyEvent.VK_RIGHT :
                        str = "right";
                        // right
                        break;
                    case KeyEvent.VK_Y :
                        playAgain("Y");
                        str = "blank";
                        break;
                    case KeyEvent.VK_N :
                        playAgain("N");
                        str = "blank";
                        break;
                    default:
                        str = "right";
                }
                s.nowPoints(str);
            } // keyPressed

            @Override
            public void keyReleased(KeyEvent e) {

            }
        }); // addKeyListener

    }

    class MainPanel extends JPanel {
        int GWIDTH = 10;
        int mFrameWidth;
        int mFrameHeight;
        //int horizontalGridBoundary = 0;
        //int verticalGridBoundary = 0;
        int GridBoundary = 0;
        boolean eaten = false;
        Point food = new Point(GWIDTH,GWIDTH);
        Random r = new Random();

        public MainPanel(int w, int h) {
            mFrameWidth = w;
            mFrameHeight = h;
            setPreferredSize(new Dimension(mFrameWidth+1, mFrameHeight+1));
        }

        public int scale(double x, int y){
            return (int)(x*y);
        }

        public void setGridBoundary(int gridBoundary) {
            GridBoundary = gridBoundary;
        }

        public void drawGrid(Graphics g){
            int grid_boundary = 0;

            for(int k = 0; k <= mFrameWidth; k+=GWIDTH){
                g.drawLine(k,0,k,mFrameHeight);
                g.drawLine(0,k,mFrameWidth,k);
                grid_boundary++;
            }

            if(GridBoundary == 0){
                setGridBoundary(grid_boundary);
            }
        }

        public void drawSnake(Graphics g){

            Stack<Point> loc = s.getLocation();
            for(Point i:loc){
                g.setColor(Color.RED);
                g.fillRect(i.x*GWIDTH, i.y*GWIDTH, GWIDTH, GWIDTH);
            }
        }

        public boolean collisionWithSnake(Point f){
            for(Point i: s.getLocation()){
                if(i.x==f.x && i.y==f.y){
                    return true;
                }
            }
            return false;
        }

        public void drawFood(Graphics g){
            if(Point.equals(s.getLocation().elementAt(0), food)){
                eaten = true;
            }
            if(eaten){
                s.getBigger();
                do {
                    food.x = r.nextInt(GridBoundary-1);
                    food.y = r.nextInt(GridBoundary-1);
                } while(collisionWithSnake(food));
                eaten = false;
                score += 10;
                scoreText.setText("Score: "+score);
            }
            if(!eaten){
                g.setColor(Color.RED);
                g.fillRect(food.x*GWIDTH, food.y*GWIDTH, GWIDTH, GWIDTH);
            }

        }

        public boolean gameLost(){
            Point head = s.getLocation().elementAt(0);
            boolean lost = false;
            if(head.x>GridBoundary-2 ||
                    head.y>GridBoundary-2 ||
                    head.x<0 ||
                    head.y<0){
                System.out.println("Snake out of bounds");
                lost = true;
            }
            for(Point i:s.getLocation())
                for(Point j:s.getLocation())
                    if(!i.equals(j))
                        if(Point.equals(i,j)) {
                            System.out.println("Snake ate itself.");
                            System.out.println("i.x\t"+i.x);
                            System.out.println("i.y\t"+i.y);
                            System.out.println("j.x\t"+j.x);
                            System.out.println("j.y\t"+j.y);
                            lost = true;
                        }

            if(lost){
                statusText.setText("You Lost! Play again? (y/n)");
                freeze = true;
            }

            return lost;
        }

        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            g.drawRect(scale(0,mFrameWidth), scale(0,mFrameHeight),
                    scale(1,mFrameWidth), scale(1,mFrameHeight));
            drawGrid(g);



            if(!freeze) {
                s.updateLocation();
                if (gameLost()) {
                    System.out.println("Game Lost");
                }
            }
            drawSnake(g);
            drawFood(g);

            //drawScore();

            //System.out.println("Bound "+GridBoundary);

        }
    }

}