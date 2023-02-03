package nickv.nickv.db;

import nickv.nickv.log.Log;
import nickv.nickv.util.Utils;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.plugin.Plugin;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

public class Database implements ConfigurationSerializable {
    private final int port;
    private final String host;
    private final String database;
    private final String user;
    private final String password;

    public Database(Map<String, Object> map) {
        host = (String) map.getOrDefault("host", "localhost");
        port = (int) map.getOrDefault("port", "3306");
        database = (String) map.getOrDefault("database", "mysql");
        user = (String) map.getOrDefault("user", "root");
        password = (String) map.getOrDefault("password", Utils.getDatabasePassword());
    }

    public Database() {
        host = "localhost";
        port = 3306;
        database = "mysql";
        user = "root";
        password = Utils.getDatabasePassword();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("host", host);
        map.put("port", port);
        map.put("database", database);
        map.put("user", user);
        map.put("password", password);
        return map;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public static void initialize(Plugin plugin, DataSource dataSource) throws SQLException, IOException {
        String setup;
        try (InputStream in = Database.class.getClassLoader().getResourceAsStream("tablesetup.sql")) {
            setup = new String(in.readAllBytes());
        } catch (IOException exception) {
            plugin.getLogger().log(Level.SEVERE, "Could not read db setup file.", exception);
            throw exception;
        }
        // Mariadb can only handle a single query per statement. We need to split at ;.
        String[] queries = setup.split(";");
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            for (String query : queries) {
//                if (query.isBlank()) {
//                    continue;
//                }
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    plugin.getLogger().info(query);
                    stmt.execute();
                }
            }
            conn.commit();
        }
        Log.info("§2Database setup complete.");
    }
}
