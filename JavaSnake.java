import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by Patrick on 12/3/15.
 */
public class JavaSnake {
    static boolean gameWon = false;
    static boolean gameLost = false;

    public static void main(String[] args) {
        // g1 is the SnakeGame aka JFrame
        SnakeGame g1 = new SnakeGame();
        g1.setVisible(true);
        int counter = 0;

        while (!gameWon) {
            counter++;
            //System.out.println("loop " + counter);
            sleep(0.1); // Sleep for 0.1 seconds
            g1.repaint();
        }


    }

    static void sleep(double x){
        try {
            TimeUnit.MILLISECONDS.sleep((int)(x*1000));
        } catch (InterruptedException e) {
            //Handle exception
        }
    }
}
