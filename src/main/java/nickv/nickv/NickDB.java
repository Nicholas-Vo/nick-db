package nickv.nickv;

import nickv.nickv.db.Database;
import nickv.nickv.db.SourceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

public final class NickDB extends JavaPlugin {
    private DataSource dataSource; // A factory for connections to the physical data source

    @Override
    public void onEnable() {
        try {
            dataSource = SourceProvider.initialize(this, new Database());
        } catch (SQLException e) {
            getLogger().log(Level.SEVERE, "Could not establish database connection!", e);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            Database.initialize(this, dataSource);
        } catch (SQLException | IOException e) {
            getLogger().log(Level.SEVERE, "Could not initialize database.", e);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
