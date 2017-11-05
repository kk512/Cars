package com.application.cars.cars.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.application.cars.cars.Model.Car;
import com.application.cars.cars.Model.User;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "cars";
    private static final int VERSION = 1;
    private static DBHelper backend;

    // -------------------------------- TABLE NAMES -----------------------------

    private static String TABEL_USER = "USER_NAME";
    private static String TABEL_CAR_DETAILS = "CAR_DETAILS";

    // -------------------------------- COLUMN NAMES -----------------------------

    private static String USER_COLUMN_ID = "USER_ID";
    private static String USER_COLUMN_NAME = "USER_NAME";

    private static String CAR_COLUMN_ID = "CAR_ID";
    private static String CAR_COLUMN_COMPANY = "COMPANY_NAME";
    private static String CAR_COLUMN_MODEL = "MODEL";
    private static String CAR_COLUMN_REG_NO = "REGISTRATION_NUMBER";
    private static String CAR_COLUMN_USER_CAR = "CAR_OF_USER";

    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static DBHelper getDatabase(Context ctx) {
        if (backend == null) {
            backend = new DBHelper(ctx);
        }
        return backend;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TABLE_USER = "CREATE TABLE " + TABEL_USER + " ("
                    + USER_COLUMN_ID + " LONG PRIMARY KEY,"
                    + USER_COLUMN_NAME + " TEXT)";
            String CREATE_TABLE_CAR_DETAILS = "CREATE TABLE " + TABEL_CAR_DETAILS + " ("
                    + CAR_COLUMN_COMPANY + " TEXT,"
                    + CAR_COLUMN_MODEL + " TEXT,"
                    + CAR_COLUMN_REG_NO + " TEXT,"
                    + CAR_COLUMN_USER_CAR + " LONG,"
                    + "FOREIGN KEY(" + CAR_COLUMN_USER_CAR + ") REFERENCES " + USER_COLUMN_NAME + "(" + USER_COLUMN_ID + "))";

            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_CAR_DETAILS);
        } catch (Exception ex) {
            Log.d("Logs", "" + ex.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.beginTransaction();
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABEL_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABEL_CAR_DETAILS);
            db.setTransactionSuccessful();
        } catch (Exception ex) {

        } finally {
            db.endTransaction();
        }
        this.onCreate(db);
    }

    public long insertUserDetails(User user) {
        long rows = 0;
        SQLiteDatabase dbase = getWritableDatabase();
        String query = "INSERT INTO " + TABEL_USER + "("
                + USER_COLUMN_ID + ","
                + USER_COLUMN_NAME + ")VALUES(?,?)";
        SQLiteStatement statmnt = dbase.compileStatement(query);
        try {
            dbase.beginTransaction();
            statmnt.bindLong(1, user.getUserId());
            statmnt.bindString(2, user.getUserName());
            rows = statmnt.executeInsert();
            dbase.setTransactionSuccessful();
            statmnt.clearBindings();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.endTransaction();
        }
        return rows;
    }

    public long insertCarDetails(Car car) {
        long rows = 0;
        SQLiteDatabase dbase = getWritableDatabase();
        String query = "INSERT INTO " + TABEL_CAR_DETAILS + "("
                + CAR_COLUMN_COMPANY + ","
                + CAR_COLUMN_MODEL + ","
                + CAR_COLUMN_REG_NO + ","
                + CAR_COLUMN_USER_CAR + ")VALUES(?,?,?,?)";
        SQLiteStatement statmnt = dbase.compileStatement(query);
        try {
            dbase.beginTransaction();
            dbase.execSQL("PRAGMA foreign_keys=ON");
            statmnt.bindString(1, car.getCaompanyName());
            statmnt.bindString(2, car.getModel());
            statmnt.bindString(3, car.getRegNo());
            statmnt.bindLong(4, car.get_userId());
            rows = statmnt.executeInsert();
            dbase.setTransactionSuccessful();
            statmnt.clearBindings();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbase.endTransaction();
        }
        return rows;
    }

    public ArrayList<Long> getDistinctUser() {
        ArrayList<Long> list = new ArrayList<>();
        String query = "select DISTINCT " + USER_COLUMN_ID + " from " + TABEL_USER;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                db.beginTransaction();
                try {

                    list.add(cursor.getLong(0));
                    db.setTransactionSuccessful();
                } catch (SQLException e) {
                    e.printStackTrace();
                    db.endTransaction();
                } finally {
                    db.endTransaction();
                }
            } while (cursor.moveToNext());

        }
        return list;
    }

    public String getUser(long userId) {
        String userNAame = null;
        String query = "select " + USER_COLUMN_NAME + " from " + TABEL_USER + " where "
                + USER_COLUMN_ID + " = '" + userId + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                db.beginTransaction();
                try {

                    userNAame = cursor.getString(0);
                    db.setTransactionSuccessful();
                } catch (SQLException e) {
                    e.printStackTrace();
                    db.endTransaction();
                } finally {
                    db.endTransaction();
                }
            } while (cursor.moveToNext());

        }
        return userNAame;
    }

    public ArrayList<Car> getCarDetailsForUser(long userId) {
        ArrayList<Car> list = new ArrayList<>();
        Car car = null;
        String query = "select * " + " from " + TABEL_CAR_DETAILS + " where "
                + CAR_COLUMN_USER_CAR + " = '" + userId + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                db.beginTransaction();
                try {
                    car = new Car();
                    car.setCaompanyName(cursor.getString(0));
                    car.setModel(cursor.getString(1));
                    car.setRegNo(cursor.getString(2));
                    list.add(car);
                    db.setTransactionSuccessful();
                } catch (SQLException e) {
                    e.printStackTrace();
                    db.endTransaction();
                } finally {
                    db.endTransaction();
                }
            } while (cursor.moveToNext());

        }
        return list;
    }
}
