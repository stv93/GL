package other;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

/**
 * Created by tetiana.sviatska on 7/14/2015.
 */
public class API {

    private final String authEncrypted;

    private API(@NotNull String authEncrypted) {
        this.authEncrypted = new String(Base64.getEncoder().encode(authEncrypted.getBytes()));
    }

    @NotNull
    public static API getAPIUsingDefaultCredentials() {
        return new API("admin:ab4f051e680095cb9a5d655dc48aa15c");
    }

    @NotNull
    public static API getAPI(@NotNull String login, @NotNull String password) {
        return new API(String.format("%s:%s", login, password));
    }

    @NotNull
    public JSONObject request(@NotNull String pageURL) {
        HttpURLConnection connection = null;
        String result = "";
        try {
            final URL url = new URL(pageURL + "/api/json?pretty=true");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", "Basic " + authEncrypted);
            connection.connect();
            try (Scanner scanner = new Scanner(connection.getInputStream())) {
                while (scanner.hasNextLine()) {
                    result += scanner.nextLine();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result.isEmpty() ? new JSONObject() : new JSONObject(result);
    }

   /* public static String getTimeStamp(String pageURL){
        JSONObject jo = getJsonObject(pageURL);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");
        return Instant.ofEpochMilli((long)jo.get("timestamp")).atZone(ZoneId.systemDefault()).format(formatter);
    }*/
}
