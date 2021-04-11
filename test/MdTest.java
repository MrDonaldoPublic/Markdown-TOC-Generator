import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MdTest {
    private final PrintStream out = System.out;
    private final ByteArrayOutputStream outputInfo = new ByteArrayOutputStream();
    private final String TEST_DIR = "test/resources/";

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputInfo));
    }

    @AfterEach
    public void teardown() {
        System.setOut(out);
    }

    private void runTest(final String name) throws IOException {
        final String fileName = TEST_DIR + name;
        runTest(fileName, name + ".md_expected");
    }

    private void runTest(final String name, final String expect) throws IOException {
        setup();
        final String fileName = name + ".md";
        final String[] args = {fileName};
        Generator.main(args);
        final String res = outputInfo.toString();
        final File expected = new File(TEST_DIR + expect);
        final String expectation = Files.readString(expected.toPath())
                + System.lineSeparator()
                + Files.readString(Path.of(fileName));
        assertEquals((expectation).trim(), res.trim());
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

    @Test
    public void code() throws IOException {
        runTest("code");
    }

    @Test
    public void README() throws IOException {
        runTest("README", "README.md_expected");
    }
}
