import java.util.ArrayList;
import java.util.List;

public class MyBig {
    public static final int threadCount = 4;
    List<Short> value;

    public MyBig(List<Short> value) {
        this.value = value;
    }

    public MyBig() {
        value = new ArrayList<>();
    }

    public MyBig(int v) {
        value = new ArrayList<>();
        if (v == 0) {
            value.add((short) 0);
            return;
        }

        while(v > 0) {
            value.add((short) (v % 10));
            v /= 10;
        }
    }

    MyBig mul(MyBig second) {
        BigMultiplexer b = new BigMultiplexer();
        return b.mul(this, second);
    }

    void addToIt(MyBig second) {
        addToIt(second, 0);
    }

    void addToIt(MyBig second, int shift) {
        for (int i = 0; i < second.size(); i++) {
            int j = i + shift;
            int n = second.value.get(i);
            do {
                while (value.size() <= j) {
                    value.add((short) 0);
                }

                n += value.get(j);
                value.set(j, (short) (n % 10));

                n /= 10;
                j++;
            } while (n > 0);
        }
    }

    int size() {
        return value.size();
    }

    public List<Short> getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        for (int i = value.size() - 1; i >= 0; i--) {
            sb.append(value.get(i));
        }
        return sb.toString();
    }
}
