package nickv.nickv.log;

import nickv.nickv.NickDB;

import java.sql.SQLException;
import java.util.logging.Level;

public class Log {
    /**
     * Pretty logging of a {@link SQLException} with the plugin logger on a {@link Level#SEVERE} level.
     *
     * @param message message to log. What went wrong.
     * @param ex      exception to log
     */
    public static void SQLError(String message, SQLException ex) {
        SQLError(Level.SEVERE, message, ex);
    }

    /**
     * Pretty logging of a {@link SQLException} with the plugin logger.
     *
     * @param level   logging level of error. A {@link Level} lower than {@link Level#INFO} will be changed to {@link
     *                Level#INFO}
     * @param message message to log. What went wrong.
     * @param ex      exception to log
     */
    protected static void SQLError(Level level, String message, SQLException ex) {
        if (level.intValue() < Level.INFO.intValue()) {
            level = Level.INFO;
        }

        NickDB.getPlugin(NickDB.class).getLogger().log(
                level, String.format("%s%nMessage: %s%nCode: %s%nState: %s",
                        message, ex.getMessage(), ex.getErrorCode(), ex.getSQLState()), ex);
    }
}
