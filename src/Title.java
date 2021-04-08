import java.util.regex.Pattern;

public class Title {
    private final String original;
    private final String value;
    private final int rank;

    private static final Pattern link = Pattern.compile("\\[([^\\[\\]]*)\\](\\[[^\\[\\]]*\\]|\\([^\\(\\)]*\\))");
    public Title(final String value) {
        int pos = 0;
        while (pos < value.length() && value.charAt(pos) == '#') {
            ++pos;
        }

        this.original = value;
        this.value = link.matcher(value.substring(pos).trim()).replaceAll("$1");
        this.rank = pos;
    }

    public Title(final String value, final int rank) {
        this.original = value;
        this.value = link.matcher(value).replaceAll( "$1");
        this.rank = rank;
    }

    public String getOriginal() {
        return original;
    }

    public String getValue() {
        return value;
    }

    public int getRank() {
        return rank;
    }
}
