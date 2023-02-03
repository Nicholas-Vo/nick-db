package nickv.nickv.util;

import nickv.nickv.log.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
    final static String credentialsFile = "db-credentials";

    /**
     * Avoids committing database password to GitHub
     *
     * @return The password to the database!
     */
    public static String getDatabasePassword() {
        InputStream aURL = Utils.class.getClassLoader().getResourceAsStream(credentialsFile);

        String password = null;
        try (InputStream aStream = aURL) {
            var reader = new BufferedReader(new InputStreamReader(aStream));
            password = reader.readLine();
        } catch (IOException ignored) {
            Log.info("Ran into an error obtaining the password from the db-credentials.txt file.");
        }

        return password;
    }
}
