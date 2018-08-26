import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        new Main().execute(args);
    }

    void execute(String[] args) {
        if (args.length == 0) {
            usage();
            return;
        }
        if (args[0].equals("submit")) {
            if (args.length < 3) {
                usage();
                return;
            }
            String command = args[1];
            String name = args[2];
            double sum = 0;
            for (int i = 0; i < 100; ++i) {
                double score = run(i, command);
                System.out.println(String.format("%2d : %f", i, score));
                sum += score;
            }
            System.out.println(sum);
            submit(name, sum);
        } else if (args[0].equals("input")) {
            if (args.length < 2) {
                usage();
                return;
            }
            System.out.println(new Input(Long.parseLong(args[1])));
        } else {
            if (args.length < 2) {
                usage();
                return;
            }
            String command = args[0];
            long seed = Long.parseLong(args[1]);
            System.out.println(run(seed, command));
        }
    }

    void submit(String name, double score) {
        String url = "https://atijm8gwti.execute-api.ap-northeast-1.amazonaws.com/dev/update";
        String body = String.format("{\"id\":\"%s\",\"score\":%f}", name, score);
        try {
            HttpURLConnection x = (HttpURLConnection) new URL(url).openConnection();
            x.setRequestMethod("PUT");
            x.setDoOutput(true);
            x.connect();
            try (OutputStream output = x.getOutputStream()) {
                output.write(body.getBytes());
            }
            x.disconnect();
            if (x.getResponseCode() != 200) throw new RuntimeException();
        } catch (Exception e) {
            System.out.println(url);
            System.out.println(body);
            throw new RuntimeException(e);
        }
    }

    double run(long seed, String command) {
        Process process = null;
        try {
            Input input = new Input(seed);

            process = Runtime.getRuntime().exec(command);
            OutputStream out = process.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            out.write(input.toString().getBytes());
            out.flush();

            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = in.readLine();
                if (line == null) break;
                sb.append(line).append("\n");
            }
            Output output = new Output(input, sb.toString());
            return output.getScore();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (process != null) process.destroy();
        }
    }

    void usage() {
        StringBuilder sb = new StringBuilder();
        sb.append("required args\n");
        sb.append("submit 'run command' 'your id'\n");
        sb.append("or\n");
        sb.append("'run command' 'seed(long int)'\n");
        sb.append("or\n");
        sb.append("input 'seed(long int)'\n");
        System.out.print(sb.toString());
    }

    void tr(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
