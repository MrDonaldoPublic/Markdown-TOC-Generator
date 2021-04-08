import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MdTest {
    private final PrintStream out = System.out;
    private final ByteArrayOutputStream outputInfo = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputInfo));
    }

    @AfterEach
    public void teardown() {
        System.setOut(out);
    }

    private void runTest(final String name) throws IOException {
        setup();
        final String fileName = "test/resources/" + name + ".md";
        final String[] args = {fileName};
        Generator.main(args);
        final String res = outputInfo.toString();
        final File expected = new File(fileName + "_expected");
        assertEquals(Files.readString(expected.toPath()).trim(), res.trim());
        teardown();
    }

    @Test
    public void sample() throws IOException {
        runTest("sample");
    }

    @Test
    public void ordinal() throws IOException {
        runTest("ordinal");
    }

    @Test
    public void underline() throws IOException {
        runTest("underline");
    }

    @Test
    public void emphasis() throws IOException {
        runTest("emphasis");
    }

    @Test
    public void link() throws IOException {
        runTest("link_picture");
    }

    @Test
    public void repeat() throws IOException {
        runTest("repeat");
    }

    @Test
    public void bamboo() throws IOException {
        runTest("bamboo");
    }

    @Test
    public void complete() throws IOException {
        runTest("complete");
    }

    @Test
    public void surprise() throws IOException {
        runTest("surprise");
    }
}
