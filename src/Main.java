import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyBig myBig = new MyBig(21);
        MyBig myBig2 = new MyBig(20);

        System.out.println(myBig.mul(myBig2));
    }
}
