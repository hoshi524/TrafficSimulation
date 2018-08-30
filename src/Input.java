import problem.Point;

import java.security.SecureRandom;
import java.util.Random;

public class Input {

    private static final int SIZE = 100;
    private static final double MIN_roadCost = 1, MAX_roadCost = 5;
    private static final double MIN_railCost = 5, MAX_railCost = 10;

    public final int N = 1000;
    public final double junctionCost = 10;
    public final double roadCost;
    public final double railCost;
    public final Point[] points;
    public final int[] toPoint;

    public Input(long seed) {
        Random random = new MTRandom(seed);
        roadCost = MIN_roadCost + random.nextDouble() * (MAX_roadCost - MIN_roadCost);
        railCost = MIN_railCost + random.nextDouble() * (MAX_railCost - MIN_railCost);
        points = new Point[N];
        toPoint = new int[N];
        for (int i = 0; i < N; ++i) {
            points[i] = new Point(random.nextInt(SIZE), random.nextInt(SIZE));
            while (true) {
                int to = random.nextInt(N);
                if (i != to) {
                    toPoint[i] = to;
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%f %f\n", roadCost, railCost));
        for (int i = 0; i < N; ++i) {
            sb.append(String.format("%d %f %f\n", toPoint[i], points[i].x, points[i].y));
        }
        return sb.toString();
    }
}
