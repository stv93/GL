package other;

import java.util.Random;

/**
 * Created by tetiana.sviatska on 7/6/2015.
 */
public abstract class RandomForPages {

    private static final String characterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&()_-+={[}];',.";
    private static final String invalidCharacters = "/?<>\\:*|\"";
    private static Random random = new Random();

    public static String randomString(int length) {
        StringBuilder yourString = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            yourString.append(characterSet.charAt(random.nextInt(characterSet.length())));
        return yourString.toString();
    }

    public static String incorrectRandomUsername(){
        StringBuilder yourString = new StringBuilder(5);
        for(int i = 0; i < 5; i++)
            yourString.append(invalidCharacters.charAt(random.nextInt(invalidCharacters.length())));
        return yourString.toString();
    }
}
