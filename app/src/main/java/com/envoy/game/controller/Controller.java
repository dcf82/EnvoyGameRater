package com.envoy.game.controller;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.preference.PreferenceManager;
import android.util.Log;

import com.envoy.game.greendao.DaoMaster;
import com.envoy.game.greendao.DaoSession;

/**
 * @author David Castillo Fuentes <dcf82@hotmail.com>
 * Application instance, it loads the app database
 * and the ORM greenDAO engine
 */
public class Controller extends Application {
    private static String LOG = Controller.class.getName();

    private static Controller thiz;

    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private SharedPreferences mOIProfiles;

    public void onCreate() {
        super.onCreate();

        // App Context
        thiz = this;

        // Load Database
        openSQLiteDatabase();

        // Load App Profiles
        mOIProfiles = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public Resources getAppResources() {
        return thiz.getResources();
    }

    public static Controller getApp() {
        return thiz;
    }


    public SQLiteDatabase openSQLiteDatabase() throws SQLiteException {
        if (database == null) {
            database = new DaoMaster.DevOpenHelper(this, Definitions.DATABASE_NAME,
                    null).getWritableDatabase();
        } else if (!database.isOpen()) {
            database = new DaoMaster.DevOpenHelper(this, Definitions.DATABASE_NAME,
                    null).getWritableDatabase();
        }
        return database;
    }

    public DaoSession getDAOSession() {
        DaoMaster dm = getDAOMaster();
        if (daoSession == null) {
            daoSession = dm.newSession();
        }
        return daoSession;
    }

    public DaoMaster getDAOMaster() {
        SQLiteDatabase d = openSQLiteDatabase();
        if (daoMaster == null) {
            daoMaster = new DaoMaster(d);
        } else if (daoMaster.getDatabase() != d) {
            daoMaster = new DaoMaster(d);
        }
        return daoMaster;
    }

    public SQLiteDatabase getDB() {
        return openSQLiteDatabase();
    }

    public void closeDB() {
        try {
            database.close();
        } catch (Exception e) {
            Log.i(LOG, "error when try to close the db(" + e + ")");
        } finally {
            database = null;
            daoMaster = null;
            daoSession = null;
        }
    }

    public SharedPreferences getOIProfiles() {
        return mOIProfiles;
    }
}
