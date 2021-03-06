package com.foobnix.dao2;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RECENT_META".
*/
public class RecentMetaDao extends AbstractDao<RecentMeta, String> {

    public static final String TABLENAME = "RECENT_META";

    /**
     * Properties of entity RecentMeta.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Path = new Property(0, String.class, "path", true, "PATH");
        public final static Property Progress = new Property(1, Integer.class, "progress", false, "PROGRESS");
        public final static Property Time = new Property(2, Long.class, "time", false, "TIME");
    }


    public RecentMetaDao(DaoConfig config) {
        super(config);
    }
    
    public RecentMetaDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RECENT_META\" (" + //
                "\"PATH\" TEXT PRIMARY KEY NOT NULL ," + // 0: path
                "\"PROGRESS\" INTEGER," + // 1: progress
                "\"TIME\" INTEGER);"); // 2: time
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RECENT_META\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, RecentMeta entity) {
        stmt.clearBindings();
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(1, path);
        }
 
        Integer progress = entity.getProgress();
        if (progress != null) {
            stmt.bindLong(2, progress);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(3, time);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, RecentMeta entity) {
        stmt.clearBindings();
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(1, path);
        }
 
        Integer progress = entity.getProgress();
        if (progress != null) {
            stmt.bindLong(2, progress);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(3, time);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public RecentMeta readEntity(Cursor cursor, int offset) {
        RecentMeta entity = new RecentMeta( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // path
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // progress
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // time
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, RecentMeta entity, int offset) {
        entity.setPath(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setProgress(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setTime(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    @Override
    protected final String updateKeyAfterInsert(RecentMeta entity, long rowId) {
        return entity.getPath();
    }
    
    @Override
    public String getKey(RecentMeta entity) {
        if(entity != null) {
            return entity.getPath();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(RecentMeta entity) {
        return entity.getPath() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
