package com.tangcco170205_ftp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tangcco170205_ftp.bean.AllDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static int DBVERSON = 1;
    private static String TABLENAME = "alldata";
    private final SQLiteDatabase db;
    private IQueryAllData iQueryAllData;

    public DBHelper(Context context) {
        super(context, "alldata.db", null, DBVERSON);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table " + TABLENAME + "(_id integer primary key," +
                "title text not null," +
                "actor text not null," +
                "director text not null," +
                "category text not null," +
                "movid integer not null," +
                "pic text not null)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(List<AllDataBean> allDataBeanList) {
        ContentValues values;
        db.beginTransaction();
        try {
            for (int i = 0; i < allDataBeanList.size(); i++) {
                values = new ContentValues();
                values.put("title", allDataBeanList.get(i).getTitle());
                values.put("actor", allDataBeanList.get(i).getActor());
                values.put("director", allDataBeanList.get(i).getDirector());
                values.put("category", allDataBeanList.get(i).getCategory());
                values.put("movid", allDataBeanList.get(i).getMovid());
                values.put("pic", allDataBeanList.get(i).getPic());
                db.insert(TABLENAME, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public List<AllDataBean> queryAll() {
        List<AllDataBean> dataBeanList = new ArrayList<>();
        db.beginTransaction();
        try {
            Cursor cursor = db.query(TABLENAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                AllDataBean allDataBean = new AllDataBean();

                //cursor.getColumnCount()-->列数
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String columnName = cursor.getColumnName(i);
                    switch (columnName) {
                        case "title":
                            allDataBean.setTitle(cursor.getString(i));
                            break;
                        case "actor":
                            allDataBean.setActor(cursor.getString(i));
                            break;
                        case "director":
                            allDataBean.setDirector(cursor.getString(i));
                            break;
                        case "category":
                            allDataBean.setCategory(cursor.getString(i));
                            break;
                        case "movid":
                            allDataBean.setMovid(cursor.getInt(i));
                            break;
                        case "pic":
                            allDataBean.setPic(cursor.getString(i));
                            break;
                    }
                }
                dataBeanList.add(allDataBean);
//                iQueryAllData.queryAll(allDataBean);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return dataBeanList;
    }

    public List<AllDataBean> queryByXXX(String name) {
        List<AllDataBean> dataBeanList = new ArrayList<>();
        db.beginTransaction();
        try {
            String sql  = "select * from " + TABLENAME+
                    " where title like ? or actor like ? or director like ? or category like ?";
            String [] selectionArgs  = new String[]{"%" + name + "%",
                    "%" + name + "%",
                    "%" + name + "%",
                    "%" + name + "%"};
            Cursor cursor = db.rawQuery(sql, selectionArgs);            while (cursor.moveToNext()) {
                AllDataBean allDataBean = new AllDataBean();

                //cursor.getColumnCount()-->列数
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String columnName = cursor.getColumnName(i);
                    switch (columnName) {
                        case "title":
                            allDataBean.setTitle(cursor.getString(i));
                            break;
                        case "actor":
                            allDataBean.setActor(cursor.getString(i));
                            break;
                        case "director":
                            allDataBean.setDirector(cursor.getString(i));
                            break;
                        case "category":
                            allDataBean.setCategory(cursor.getString(i));
                            break;
                        case "movid":
                            allDataBean.setMovid(cursor.getInt(i));
                            break;
                        case "pic":
                            allDataBean.setPic(cursor.getString(i));
                            break;
                    }
                }
                dataBeanList.add(allDataBean);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return dataBeanList;
    }

    /**
     * 删除全部
     */
    public void deleteAll() {

        db.beginTransaction();
        try {
            db.delete(TABLENAME, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void setQueryAllData(IQueryAllData iQueryAllData) {
        this.iQueryAllData = iQueryAllData;
    }

    public interface IQueryAllData {
        void queryAll(AllDataBean allDataBean);
    }
}
