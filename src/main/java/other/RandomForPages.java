package other;

import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by tetiana.sviatska on 7/6/2015.
 */
public class RandomForPages {

    private static final String INVALID_CHAR_SET = "?*|\"";
    private static Random random = new Random();
    private RandomForPages() {
    }

    public static String randomString(int length) {
        if(length < 0){
            throw new StringIndexOutOfBoundsException();
        }
        return random.ints((int) '0', (int) 'z')
                .filter(ch -> Character.isDigit(ch) || Character.isAlphabetic(ch))
                .limit(length)
                .mapToObj(ch -> Character.toString((char) ch))
                .collect(Collectors.joining());
    }

    public static String randomStringWithInvalidSymbols() {
        int j=5;
        StringBuilder yourString = new StringBuilder(j);
        for (int i = 0; i < j; i++)
            yourString.append(INVALID_CHAR_SET.charAt(random.nextInt(INVALID_CHAR_SET.length())));
        return randomString(10) + yourString.toString();
    }
}
