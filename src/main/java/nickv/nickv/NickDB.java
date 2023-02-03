package nickv.nickv;

import nickv.nickv.db.Database;
import nickv.nickv.db.SourceProvider;
import nickv.nickv.listener.PlayerBreakBlock;
import nickv.nickv.log.Log;
import org.bukkit.plugin.java.JavaPlugin;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public final class NickDB extends JavaPlugin {
    private DataSource dataSource; // A factory for connections to the physical data source

    @Override
    public void onEnable() {
        Log.info("NickDB starting up...");
        try {
            dataSource = SourceProvider.initialize(this, new Database());
        } catch (SQLException e) {
            Log.info("Could not establish database connection!\n" + e);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            Database.initialize(this, dataSource);
        } catch (SQLException | IOException e) {
            Log.info("Could not initialize database.\n" + e);
            getServer().getPluginManager().disablePlugin(this);
        }

        new PlayerBreakBlock(this);
    }

    @Override
    public void onDisable() {
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
