package com.envoy.game.utilities;

import com.envoy.game.controller.Controller;
import com.envoy.game.greendao.Game;

import java.util.List;

/**
 * @author David Castillo Fuentes <dcf82@hotmail.com>
 * Utility class to manage the persistance in a SQLite database
 */
public class DatabaseUtilities {

    public static long saveGame(Game game) {
        return Controller.getApp().getDAOSession().getGameDao().insert(game);
    }

    public static void updateGame(Game game) {
        Controller.getApp().getDAOSession().getGameDao().update(game);
    }

    public static List<Game> getGames() {
        return Controller.getApp().getDAOSession().getGameDao().loadAll();
    }
}
