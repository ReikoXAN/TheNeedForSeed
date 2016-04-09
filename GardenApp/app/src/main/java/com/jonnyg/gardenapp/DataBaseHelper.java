package com.jonnyg.gardenapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by reiko_000 on 28/01/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static  final String DataBASE_NAME = "SeedSave.db";

    private static  final String TABLE_NAME = "seed_table";
    //public static  final String COL_1 = "ID";
    private static  final String COL_2 = "SNAME";
    private static  final String COL_3 = "STYPE";
    private static  final String COL_4 = "SAMOUNT";

    //private static  final String DataBASE_NAME = "Test3.db";
    private static final String TABLE_IMAGE = "images";
    private static final String COULUMN_IMGID = "_id";
    private static final String COULUMN_IMAGE = "image";

    private static  final String TEST_TABLE = "test_table";
    private static  final String COL_T1 = "ID";
    private static  final String COL_T2 = "SEEDID";
    private static  final String COL_T3 = "SEEDTYPE";

    private static final String TEST_TABLE2 = "test_table2";
    private static final String CTT1 = "ttid";
    private static final String CTT2 = "ttname";

    private static final String TESTer = "tester";
    private static final String tt = "TTID";

    private static final int Version = 1;
    private static Context myContext;

    /*private static final String Create_Test_Table = "create table "
            + TEST_TABLE + "(" + COL_T1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_T2
            + " INTEGER," + COL_T3 + " TEXT" +");";*/

    /*private static final String Create_Test_Table2 = "create table "
            + TEST_TABLE2 + "(" + CTT1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + CTT2
            + " TEXT," +");";*/

    /*String name, SQLiteDatabase.CursorFactory factory, int version*/
    public DataBaseHelper(Context context) {
        super(context, DataBASE_NAME, null, Version);
        //SQLiteDatabase db = this.getWritableDatabase();
        myContext = context;
    }
    public static void insertToast(){
        Toast.makeText(myContext,"inserted",Toast.LENGTH_SHORT).show();
    }

    public static void noInsertToast(){
        Toast.makeText(myContext,"Not inserted",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table  " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SNAME TEXT, STYPE TEXT,SAMOUNT INTEGER)");
        db.execSQL("create table  " + TEST_TABLE2 + " (ttid INTEGER PRIMARY KEY AUTOINCREMENT, ttname TEXT)");
        db.execSQL("create table  " + TABLE_IMAGE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, image BLOB)");

        //Create_Test_Table = ("create table " + TEST_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SEEDID TEXT, SEEDTYPE TEXT)");
        /*String query = "CREATE TABLE " + TEST_TABLE2 + "(" +
                CTT1 + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                CTT2 + " TEXT " +
                ");";
        db.execSQL(query);
        db.execSQL(Create_Test_Table);*/
        //db.execSQL(Create_Test_Table2);
    }

   /* public void onOpen(SQLiteDatabase db){
        db.execSQL(Create_Test_Table);
        db.execSQL(Create_Test_Table2);
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TEST_TABLE2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TEST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TESTer);
        onCreate(db);
    }
    /*public boolean insertData(String sname, String stype, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, sname);
        contentValues.put(COL_3, stype);
        contentValues.put(COL_4, amount);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }*/

    public void addSeed(Seed_Table seed){
        ContentValues values = new ContentValues();
        values.put(COL_2, seed.get_seedName());
        values.put(COL_3, seed.get_seedType());
        values.put(COL_4, seed.get_amount());
        SQLiteDatabase db = getWritableDatabase();
        long rowInserted = db.insert(TABLE_NAME, null, values);
        if(rowInserted != -1) {
            insertToast();
        }
        else{
            noInsertToast();
        }
        db.close();
    }

   /* public boolean insertTest(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentTestValues = new ContentValues();
        contentTestValues.put(tt,id);
        long testResult = db.insert(TESTer,null,contentTestValues);
        if(testResult == -1){
            return false;
        }
        else
            return true;
    }*/

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select ID AS _id,SNAME,STYPE,SAMOUNT from " + TABLE_NAME,null);
        /*if(res!=null){
            res.moveToFirst();
        }*/
        return  res;
    }

    public  Cursor getName(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor myC = db.rawQuery("select SNAME from " + TABLE_NAME,null);

        return  myC;
    }

//    public Cursor getName(SQLiteDatabase db){
//        Cursor cursor;
//        String[] myStringArray = {}
//         return cursor;
//    }

    public Cursor listInfo(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] myStringArray = {COL_2,COL_3,COL_4};
        Cursor cursor = db.query(TABLE_NAME,myStringArray,null,null,null,null,null);
        return  cursor;
    }

    public boolean insertData(byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COULUMN_IMAGE, image);
        long result = db.insert(TABLE_IMAGE,null,cv);
        if(result == -1){
            return  false;
        }
        else{
            return true;
        }
    }
}
