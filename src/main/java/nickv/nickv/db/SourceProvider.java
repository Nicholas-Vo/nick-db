package nickv.nickv.db;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.bukkit.plugin.Plugin;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SourceProvider {
    public static DataSource initialize(Plugin plugin, Database db) throws SQLException {
        MysqlDataSource source = new MysqlConnectionPoolDataSource();

        source.setServerName(db.getHost());
        source.setPassword(db.getPassword());
        source.setPortNumber(db.getPort());
        source.setDatabaseName(db.getDatabase());
        source.setUser(db.getUser());

        try (Connection conn = source.getConnection()) {
            if (!conn.isValid(1000)) {
                throw new SQLException("Could not establish database connection.");
            }
        }

        if (plugin != null) {
            plugin.getLogger().info("§2Database connection established.");
        }

        return source;
    }
}
