package other;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by tetiana.sviatska on 7/6/2015.
 */
public class RandomForPages {


    private static final String CHAR_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";/*#$%&+=;',
    !^()_-{[}].<>\/:*/
    private static final String INVALID_CHAR_SET = "?*|\"";
    private static Random random = new Random();
    private RandomForPages() {
    }

    public static String randomString2(int length) {
        return random.ints((int) '0', (int) 'z')
                .filter(ch -> Character.isDigit(ch) || Character.isAlphabetic(ch))
                .limit(length)
                .mapToObj(ch -> Character.toString((char) ch))
                .collect(Collectors.joining());
    }

    public static void main(String[] args) {
        System.out.println(randomString2(5));
    }

    public static String randomString(int length) {
        StringBuilder yourString = new StringBuilder(length);
        int number = CHAR_SET.length();
        for (int i = 0; i < length; i++)
            yourString.append(CHAR_SET.charAt(random.nextInt(number)));
        return yourString.toString();
    }

    public static String randomStringWithInvalidSymbols() {
        StringBuilder yourString = new StringBuilder(5);
        for (int i = 0; i < 5; i++)
            yourString.append(INVALID_CHAR_SET.charAt(random.nextInt(INVALID_CHAR_SET.length())));
        return randomString(10) + yourString.toString();
    }
}
