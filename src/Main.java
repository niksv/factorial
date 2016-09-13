import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyBig myBig = new MyBig(999);
        myBig.addToIt(new MyBig(999999));
        System.out.println(myBig);
    }
}
