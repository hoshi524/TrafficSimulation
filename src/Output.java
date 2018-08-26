import problem.Point;
import problem.Type;

import java.util.Scanner;

public class Output {
    public Input input;
    public double cost, time;

    public Output(Input input, String output) {
        try (Scanner sc = new Scanner(output)) {
            int n = sc.nextInt();
            double[][] addJunction = new double[n][2];
            for (int i = 0; i < n; ++i) {
                addJunction[i][0] = sc.nextDouble();
                addJunction[i][1] = sc.nextDouble();
            }

            n = sc.nextInt();
            int[][] roads = new int[n][3];
            for (int i = 0; i < n; ++i) {
                roads[i][0] = sc.nextInt();
                roads[i][1] = sc.nextInt();
                roads[i][2] = sc.nextInt();
            }

            int[][] paths = new int[input.N][];
            for (int i = 0; i < input.N; ++i) {
                n = sc.nextInt();
                paths[i] = new int[n];
                for (int j = 0; j < n; ++j) {
                    paths[i][j] = sc.nextInt();
                }
            }

            parse(input, addJunction, roads, paths);
        } catch (Exception e) {
            throw new VerifyException(e);
        }
    }

    private void parse(Input input, double[][] addJunction, int[][] roads, int[][] paths) {
        this.input = input;
        int N = input.N + addJunction.length;
        cost = addJunction.length * input.junctionCost;
        time = 0;

        Point[] points = new Point[N];
        for (int i = 0; i < input.N; ++i) {
            points[i] = input.points[i];
        }
        for (int i = 0; i < N - input.N; ++i) {
            points[i + input.N] = new Point(addJunction[i][0], addJunction[i][1]);
        }
        Type[][] types = new Type[N][N];
        for (int i = 0; i < roads.length; ++i) {
            if (roads[i].length != 3) throw new VerifyException();
            int a = roads[i][0];
            int b = roads[i][1];
            int c = roads[i][2];
            if (a < 0 || N <= a) throw new VerifyException();
            if (b < 0 || N <= b) throw new VerifyException();
            if (a == b) throw new VerifyException();
            Type t = Type.values()[c];
            types[a][b] = types[b][a] = t;
            cost += (t == Type.road ? input.roadCost : input.railCost) * dist(points[a], points[b]);
        }

        int count[][] = new int[N][N];
        for (int i = 0; i < input.N; ++i) {
            int p = i;
            for (int j = 0; j < paths[i].length; ++j) {
                int n = paths[i][j];
                Type t = types[p][n];
                if (t == null) throw new VerifyException();
                ++count[p][n];
                ++count[n][p];
                p = n;
            }
            if (p != input.toPoint[i]) throw new VerifyException();
        }
        for (int i = 0; i < input.N; ++i) {
            int p = i;
            double d = 0;
            boolean car = true;
            for (int j = 0; j < paths[i].length; ++j) {
                int n = paths[i][j];
                Type t = types[p][n];
                if (t == Type.road) {
                    d += dist(points[p], points[n]) * Math.sqrt(count[p][n]);
                } else {
                    car = false;
                    d += dist(points[p], points[n]) / 2;
                }
                p = n;
            }
            if (car) d /= 2;
            time += d * d;
        }
    }

    public double getScore() {
        return cost * time / 1000000000;
    }

    private double dist(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }
}
