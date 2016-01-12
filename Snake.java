import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


/**
 * Created by Patrick on 11/25/15.
 */
public class Snake {
    static HashMap<String,Integer> directions = new HashMap<String, Integer>();
    static int size;
    static Integer direction;
    static Stack<Point> location;
    static boolean biggerFlag = false;



    public Stack<Point> getLocation() {
        return location;
    }

    Snake(){
        directions.put("up",1);
        directions.put("left",2);
        directions.put("down",3);
        directions.put("right",4);

        size = 1;
        direction = directions.get("right");
        location = new Stack<Point>();
        location.push(new Point(1,1));
    }

    public void getBigger(){
        biggerFlag = true;
    }

    public void updateLocation(){
        for (int i = location.size()-1; i>=0; i--){
            if(biggerFlag){
                location.push(new Point(location.elementAt(i).x,
                        location.elementAt(i).y));
                biggerFlag = false;
            }
            if(i!=0) {
                location.elementAt(i).set(location.elementAt(i-1).x,
                        location.elementAt(i-1).y);
            } else {
                if(direction.equals(directions.get("up"))){
                    location.elementAt(i).y--;
                }
                else if(direction.equals(directions.get("down"))){
                    location.elementAt(i).y++;
                }
                else if(direction.equals(directions.get("left"))){
                    location.elementAt(i).x--;
                }
                else if(direction.equals(directions.get("right"))){
                    location.elementAt(i).x++;
                }
            }
        }
    }

    public boolean isPointing(String string){
        return direction.equals(directions.get(string));
    }

    public void nowPoints(String string){
        if(directions.containsKey(string)){
            if(Math.abs(directions.get(string)-direction) != 2){
                direction = directions.get(string);
            }
        }
    }
}
