package com.dao.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.envoy.game.greendao");

        schema.enableKeepSectionsByDefault();
        schema.enableActiveEntitiesByDefault();

        addGamesTable(schema);

        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }

    public static void addGamesTable(Schema schema) {
        Entity gameTable = schema.addEntity("Game");
        gameTable.addIdProperty().autoincrement();
        gameTable.addStringProperty("mGameName");
        gameTable.addStringProperty("mConsoleName");
        gameTable.addByteArrayProperty("mGameImage");
        gameTable.addBooleanProperty("mGameFinished");
        gameTable.addIntProperty("mStarsNumber");
    }
}
