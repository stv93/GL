package other;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by tetiana.sviatska on 7/24/2015.
 */
public class Source {

    private static Properties properties = null;

    public static String getValue(String key) {
        if (properties == null) {
            properties = new Properties();
            try (BufferedReader reader = Files.newBufferedReader(getPropertyFilePath(), Charset.forName("UTF-8"))) {
                properties.load(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(key);
    }

    private static Path getPropertyFilePath() {
        return Paths.get("resources", String.format("%sLanguage.properties", LanguageDependencies.getLanguage()));
    }
}