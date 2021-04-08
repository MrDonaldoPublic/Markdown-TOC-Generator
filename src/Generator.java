import java.io.*;
import java.nio.file.Files;

public class Generator {
    public static void main(final String[] args) {
        if (args == null || args.length != 1) {
            System.err.println("Usage: Generator <md file path>");
            return;
        }

        final File file = new File(args[0]);
        if (!file.exists()) {
            System.err.println("Couldn't find " + file.getPath());
            return;
        }
        if (!file.canRead() || !file.getName().endsWith(".md") && !file.getName().endsWith(".markdown")) {
            System.err.println("Given not md file " + file.getName());
            return;
        }

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            final String toc = MdParser.parse(reader);
            if (!toc.isEmpty()) {
                System.out.println(toc);
            }
            System.out.println(Files.readString(file.toPath()));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
