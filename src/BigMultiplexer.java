import java.util.ArrayList;
import java.util.List;

class BigMultiplexer {
    private int threadCount = MyBig.threadCount;
    private MyBig result;
    private MyBig[] cache;

    MyBig mul(MyBig first, MyBig second) {
        result = new MyBig();
        cache = new MyBig[10];
        for (int i = 0; i < cache.length; i++) {
            cache[i] = new MyBig();
        }

        int step = second.size() / (threadCount + 1);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < second.size(); i += step) {
            Thread t = new Thread(new MulThread(first, second, i, Math.min(i + step, second.size())));
            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private MyBig getCache(MyBig first, int x) {
        if (cache[x].size() != 0) {
            return cache[x];
        }
        synchronized (cache[x]) {
            if (cache[x].size() != 0) {
                return cache[x];
            }
            cache[x].value = mulOne(first, (short) x).value;
            return cache[x];
        }
    }

    private synchronized void addToResult(MyBig second) {
        result.addToIt(second);
    }

    private MyBig mulOne(MyBig first, short x) {
        System.out.println("111");
        MyBig res = new MyBig(0);
        for (int i = 0; i < first.value.size(); i++) {
            int n = first.value.get(i) * x;
            int j = i;
            do {
                while (res.size() <= j) {
                    res.value.add((short) 0);
                }

                n += res.value.get(j);
                res.value.set(j, (short) (n % 10));

                n /= 10;
                j++;
            } while (n > 0);
        }
        return res;
    }


    private class MulThread implements Runnable {
        MyBig first;
        MyBig second;
        int start;
        int stop;

        public MulThread(MyBig first, MyBig second, int start, int stop) {
            this.first = first;
            this.second = second;
            this.start = start;
            this.stop = stop;
        }


        @Override
        public void run() {
            MyBig temp = new MyBig();
            for (int i = start; i < stop; i++) {
                MyBig added = getCache(first, second.value.get(i));
                temp.addToIt(added, i);
            }
            addToResult(temp);
        }
    }
}
