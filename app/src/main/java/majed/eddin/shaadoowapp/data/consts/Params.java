package majed.eddin.shaadoowapp.data.consts;

import java.util.NavigableMap;
import java.util.TreeMap;

public class Params {

    public static final String DATA = "data";
    public static final String Message = "message";
    public static final String PAGE = "page";

    public static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }
}