package nickv.nickv.db;

import nickv.nickv.NickDB;
import nickv.nickv.log.Log;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.OptionalLong;

public class PointsManager {
    private final Connection connection;

    public PointsManager() throws SQLException {
        connection = NickDB.getPlugin(NickDB.class).getDataSource().getConnection();
    }

    public boolean addPoints(Player player, long amount) {
        Log.info("LENGTH ----> " + player.getUniqueId().toString().length());
        try (Connection conn = connection; PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO player_points(uuid, points) VALUES(?, ?) ON DUPLICATE KEY UPDATE points = points + ?;")) {
            stmt.setString(1, player.getUniqueId().toString());
            stmt.setLong(2, amount);
            stmt.setLong(3, amount);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            Log.SQLError("SQLException in method addPoints()!", e);
        }
        return false;
    }

    /* PreparedStatement: An object that represents a precompiled SQL statement. A SQL statement is precompiled
       and stored within a PreparedStatement object.

       This object can then be used to efficiently execute this statement multiple times.
     */
    public OptionalLong getPoints(Player player) {
        String statement = "select points from player_points where uuid = ?;";
        try (var conn = connection; PreparedStatement stmt = conn.prepareStatement(statement)) {
            stmt.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return OptionalLong.of(resultSet.getLong("points"));
            }
            return OptionalLong.of(0);
        } catch (SQLException e) {
            Log.SQLError("SQLException in method getPoints()!", e);
            return OptionalLong.empty();
        }
    }
}
