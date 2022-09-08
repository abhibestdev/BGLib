package us.blockgame.lib.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StringUtil {

    //Method to check if a string is contained within an array regardless of capitalization
    public static boolean contains(String[] array, String compare) {
        List<String> list = new ArrayList<>();
        Arrays.stream(array).forEach(s -> list.add(s.toLowerCase()));
        return list.contains(compare.toLowerCase());
    }

    //Method to join strings together
    public static String join(List<String> pieces, String separator) {
        StringBuilder buffer = new StringBuilder();
        Iterator iter = pieces.iterator();

        while (iter.hasNext()) {
            buffer.append((String) iter.next());
            if (iter.hasNext()) {
                buffer.append(separator);
            }
        }

        return buffer.toString();
    }

    //Build string from arguments
    public static String buildString(String[] args, int start) {
        return String.join(" ", Arrays.copyOfRange(args, start, args.length));
    }
}
