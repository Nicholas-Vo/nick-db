package nickv.nickv.listener;

import nickv.nickv.NickDB;
import nickv.nickv.db.PointsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.sql.SQLException;

public class PlayerBreakBlock implements Listener {
    private PointsManager manager;


    public PlayerBreakBlock(NickDB plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        try {
            manager = new PointsManager();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        manager.addPoints(e.getPlayer(), 1);
        e.getPlayer().sendMessage("You have " + manager.getPoints(e.getPlayer()) + " points.");
    }

}
