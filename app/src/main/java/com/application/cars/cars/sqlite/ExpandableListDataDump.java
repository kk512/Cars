package com.application.cars.cars.sqlite;

import android.content.Context;

import com.application.cars.cars.Model.Car;
import com.application.cars.cars.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kailash on 04-11-2017.
 */

public class ExpandableListDataDump {

    static Context mContext;
    static ArrayList<Long> userId;
    static ArrayList<Car> cars;
    public static HashMap<String, List<Car>> getData(Context context) {
        mContext = context;
        HashMap<String, List<Car>> expandableListDetail = new HashMap<String, List<Car>>();
        DBHelper dbHelper = DBHelper.getDatabase(mContext);
        userId = dbHelper.getDistinctUser();
        for (Long id: userId) {
            cars = dbHelper.getCarDetailsForUser(id);
            expandableListDetail.put(dbHelper.getUser(id),cars);
        }
        return expandableListDetail;
    }
}
