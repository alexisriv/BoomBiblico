package com.sixelasavir.www.boombiblico;

import android.app.Application;

import com.sixelasavir.www.boombiblico.greendao.model.DaoMaster;
import com.sixelasavir.www.boombiblico.greendao.model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by alexis on 05/09/17.
 */

public class AppBoomBiblico extends Application {

    public static final boolean ENCRYPTED = true;

    private DaoSession daoSession;

    private static final String DB_ENCRYPTED_BOOM_BIBLICO = "db-encrypted-boom-biblico";
    private static final String DB_BOOM_BIBLICO = "db-boom-biblico";

    private static final String password = "conexion.biblica";

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? DB_ENCRYPTED_BOOM_BIBLICO: DB_BOOM_BIBLICO);
        Database database = ENCRYPTED ? helper.getEncryptedReadableDb(password):helper.getWritableDb();
        daoSession = new DaoMaster(database).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
