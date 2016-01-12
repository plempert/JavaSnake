/**
 * Created by Patrick on 12/3/15.
 */
public class Point {
    int x;
    int y;
    Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    void set(int x, int y){
        this.x = x;
        this.y = y;
    }

    static boolean equals(Point a, Point b){
        return (a.x==b.x && a.y==b.y);
    }

}
