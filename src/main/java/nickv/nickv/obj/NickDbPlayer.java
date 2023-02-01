package nickv.nickv.obj;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class NickDbPlayer {
    private final long points;
    private final OfflinePlayer offlinePlayer;

    public NickDbPlayer(UUID uuid, long points) {
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        this.points = points;
    }

    public long getPoints() {
        return points;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }
}
