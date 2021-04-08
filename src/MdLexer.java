import java.io.BufferedReader;
import java.io.IOException;

public class MdLexer {
    public enum Token {
        TITLE,
        EOF
    }
    private Token curToken;
    private final BufferedReader reader;
    private String curLine = "";
    private Title currTitle;
    private boolean hadSharp;
    private boolean isCode = false;

    public MdLexer(final BufferedReader reader) {
        this.reader = reader;
    }

    public Token getToken() {
        return curToken;
    }

    public Title getTitle() {
        return currTitle;
    }

    public void nextToken() throws IOException {
        if (Token.EOF.equals(curToken)) {
            throw new IllegalStateException("nextToken call after EOF token");
        }

        while (curLine != null) {
            currTitle = new Title(curLine);
            curLine = reader.readLine();
            if (curLine != null && curLine.startsWith("```")) {
                isCode = !isCode;
            }
            if (isCode) {
                continue;
            }
            if (!currTitle.getValue().isEmpty() && curLine != null && !curLine.isEmpty()) {
                if (curLine.chars().allMatch(c -> c == '=')) {
                    curToken = Token.TITLE;
                    currTitle = new Title(currTitle.getOriginal(), 1);
                    hadSharp = curLine.startsWith("#");
                    return;
                } else if (curLine.chars().allMatch(c -> c == '-')) {
                    curToken = Token.TITLE;
                    currTitle = new Title(currTitle.getOriginal(), 2);
                    hadSharp = curLine.startsWith("#");
                    return;
                }
            }
            if (hadSharp) {
                curToken = Token.TITLE;
                hadSharp = curLine != null && curLine.startsWith("#");
                return;
            } else if (curLine != null && curLine.startsWith("#")) {
                hadSharp = true;
            }
        }

        curToken = Token.EOF;
    }
}
