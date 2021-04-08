import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class MdParser {
    private static class TitleNode {
        private final String name;
        private final TitleNode parent;
        private final List<TitleNode> children = new ArrayList<>();
        private final int rank;

        private TitleNode(final Title title, final TitleNode parent) {
            this.rank = title.getRank();
            this.name = title.getValue();
            this.parent = parent;
        }
    }

    private static final Pattern dumpPunctuation = Pattern.compile("[^\\p{javaLetterOrDigit}\\- ]");
    private static final Pattern space = Pattern.compile("[ \t]");
    private static final Set<String> links = new HashSet<>();
    private static String nameToTag(final String name) {
        String modify = name;
        modify = dumpPunctuation.matcher(modify.toLowerCase()).replaceAll("");
        modify = space.matcher(modify).replaceAll("-");
        if (links.contains(modify)) {
            int num = 1;
            while (links.contains(modify + "-" + num)) {
                ++num;
            }
            modify = modify + "-" + num;
        }
        links.add(modify);
        return modify;
    }

    private static String toString(final TitleNode node, final int tab) {
        final StringBuilder builder = new StringBuilder();
        int num = 0;
        for (final TitleNode currNode : node.children) {
            builder.append(" ".repeat(tab * 4)).append(++num).append(". ")
                    .append("[").append(currNode.name).append("]")
                    .append("(#").append(nameToTag(currNode.name)).append(")")
                    .append(System.lineSeparator());
            builder.append(toString(currNode, tab + 1));
        }

        return builder.toString();
    }

    public static String parse(final BufferedReader reader) throws IOException {
        links.clear();
        final MdLexer lexer = new MdLexer(reader);

        final TitleNode toc = new TitleNode(new Title("TOC"), null);
        TitleNode curr;
        TitleNode prev = toc;
        while (!MdLexer.Token.EOF.equals(lexer.getToken())) {
            lexer.nextToken();
            if (lexer.getToken().equals(MdLexer.Token.TITLE)) {
                curr = new TitleNode(lexer.getTitle(), prev);
                if (curr.rank <= prev.rank) {
                    while (prev.parent != null && prev.rank >= curr.rank) {
                        prev = prev.parent;
                    }
                }
                prev.children.add(curr);
                prev = curr;
            }
        }

        return toString(toc, 0);
    }
}
