package other;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by tetiana.sviatska on 7/23/2015.
 */
public class LanguageDependencies {

    enum Languages{
        EN, RU, UK, FR, DE, PL
    }

    private static String language;

    public static String getLanguage(){
        for (Languages lang : Languages.values()){
            if(lang.toString().equals(System.getProperty("language").toUpperCase()))
                return lang.toString().toLowerCase();}
        return "en";
        }

    public static DateTimeFormatter getDateTimeFormatter(){
        language = getLanguage();
        String pattern = "";
        switch (language){
            case "en":
                pattern = "MMM d, yyyy h:mm:ss a";
                break;
            case "uk":
                pattern = "d LLL yyyy HH:mm:ss";
                break;
            case "fr":
                pattern = "d MMM yyyy HH:mm:ss";
                break;
            case "pl":
                pattern = "yyyy-MM-dd HH:mm:ss";
                break;
            case "ru":case"de":
                pattern = "dd.MM.yyyy HH:mm:ss";
                break;
        }
        return DateTimeFormatter.ofPattern(pattern, Locale.forLanguageTag(language));
    }
}


