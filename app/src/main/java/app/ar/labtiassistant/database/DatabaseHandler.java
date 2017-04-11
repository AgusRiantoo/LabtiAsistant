package app.ar.labtiassistant.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import app.ar.labtiassistant.models.Jadwal;
import app.ar.labtiassistant.models.Transaksi;

/**
 * Created by ghost on 31/03/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper{
    private static final String TAG = SQLiteOpenHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "assistent_labti";

    private static final String TABLE_JADWAL = "jadwal";
    private static final String TABLE_TRANSAKSI = "transaksi";

    private static final String KEY_ID = "id";
    private static final String KEY_HARI = "hari";
    private static final String KEY_RUANG = "ruang";
    private static final String KEY_JOB = "job";
    private static final String KEY_KELAS = "kelas";
    private static final String KEY_SHIFT = "shift";
    private static final String KEY_JAM = "jam";
    private static final String KEY_MATA_PRAKTIKUM = "mata_praktikum";
    private static final String KEY_TANGGAL = "tanggal";
    private static final String KEY_DATE = "date";
    private static final String KEY_TINGKAT = "tingkat";
    private static final String KEY_UPAH = "upah";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_JADWAL = "CREATE TABLE "+TABLE_JADWAL+"("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_HARI + " TEXT,"
                + KEY_RUANG + " TEXT,"
                + KEY_JOB + " TEXT,"
                + KEY_KELAS + " TEXT,"
                + KEY_SHIFT + " TEXT,"
                + KEY_JAM + " TEXT,"
                + KEY_MATA_PRAKTIKUM + " TEXT)";
        db.execSQL(CREATE_TABLE_JADWAL);

        String CREATE_TABLE_TRANSAKSI= "CREATE TABLE "+TABLE_TRANSAKSI+"("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_JOB + " TEXT,"
                + KEY_TINGKAT + " TEXT,"
                + KEY_SHIFT + " TEXT,"
                + KEY_UPAH + " TEXT,"
                + KEY_TANGGAL + " TEXT,"
                + KEY_DATE + " TEXT)";
        db.execSQL(CREATE_TABLE_TRANSAKSI);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JADWAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSAKSI);

        // Create tables again
        onCreate(db);
    }

//    public void addJadwal(String hari, String ruang, String job, String kelas, String shift, String matprak) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_HARI, hari);
//        values.put(KEY_RUANG, ruang);
//        values.put(KEY_JOB, job);
//        values.put(KEY_KELAS, kelas);
//        values.put(KEY_SHIFT, shift);
//        values.put(KEY_MATA_PRAKTIKUM, matprak);
//        db.insert(TABLE_JADWAL, null, values);
//
//        db.close();
//
//        Log.d(TAG, "Berhasil menambahkan jadwal");
//    }

    public boolean addJadwal(Jadwal jadwal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HARI, jadwal.get_hari());
        values.put(KEY_RUANG, jadwal.get_ruang());
        values.put(KEY_JOB, jadwal.get_job());
        values.put(KEY_KELAS, jadwal.get_kelas());
        values.put(KEY_SHIFT, jadwal.get_shift());
        values.put(KEY_JAM, jadwal.get_jam());
        values.put(KEY_MATA_PRAKTIKUM, jadwal.get_mata_praktikum());

        db.insert(TABLE_JADWAL, null, values);

        db.close();
        Log.d(TAG, "Berhasil menambahkan jadwal");

        return true;
    }

    public Jadwal getJadwal(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_JADWAL, new String[]{
                        KEY_ID, KEY_HARI, KEY_JOB, KEY_KELAS, KEY_SHIFT, KEY_JAM, KEY_MATA_PRAKTIKUM},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Jadwal jadwal = new Jadwal(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(6)
            );
            return jadwal;
        }else {
            return null;
        }
    }

    public List<Jadwal> getAllJadwal(String day){
        String sq;
        if (day.equals("null")){
            sq = "SELECT * FROM "+ TABLE_JADWAL;
        }else {
            sq = "SELECT * FROM "+ TABLE_JADWAL +" WHERE "+KEY_HARI+" LIKE '%"+day+"%'";
        }
        try {
            List<Jadwal> jadwalList = new ArrayList<>();

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(sq, null);
            if (cursor.moveToFirst()){
                do {
                    Jadwal jadwal = new Jadwal();
                    jadwal.set_id(Integer.parseInt(cursor.getString(0)));
                    jadwal.set_hari(cursor.getString(1));
                    jadwal.set_ruang(cursor.getString(2));
                    jadwal.set_job(cursor.getString(3));
                    jadwal.set_kelas(cursor.getString(4));
                    jadwal.set_shift(cursor.getString(5));
                    jadwal.set_jam(cursor.getString(6));
                    jadwal.set_mata_praktikum(cursor.getString(7));
                    jadwalList.add(jadwal);
                }while (cursor.moveToNext());
            }
            return jadwalList;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean addTransaksi(Transaksi t) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_JOB, t.get_job());
        values.put(KEY_TINGKAT, t.get_tingkat());
        values.put(KEY_SHIFT, t.get_shift());
        values.put(KEY_UPAH, t.get_upah());
        values.put(KEY_TANGGAL, t.get_tanggal());
        values.put(KEY_DATE, t.get_date());

        db.insert(TABLE_TRANSAKSI, null, values);

        db.close();
        Log.d(TAG, "Berhasil menambahkan transaksi");

        return true;
    }

    public List<Transaksi> getAllTransaksi(String date){
        List<Transaksi> transaksiList = new ArrayList<>();
        String sq;
        if (date.equals("null")){
            sq = "SELECT * FROM "+ TABLE_TRANSAKSI + " ORDER BY id DESC;";
        }else{
//            sq = "SELECT * FROM "+ TABLE_TRANSAKSI + " WHERE date("+ KEY_DATE +") BETWEEN "+date;
            sq = "SELECT * FROM " + TABLE_TRANSAKSI + " WHERE "+ date;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sq,null);

        if (cursor.moveToFirst()){
            do {
                Transaksi t = new Transaksi();
                    t.set_id(Integer.parseInt(cursor.getString(0)));
                    t.set_job(cursor.getString(1));
                    t.set_tingkat(cursor.getString(2));
                    t.set_shift(cursor.getString(3));
                    t.set_upah(cursor.getString(4));
                    t.set_tanggal(cursor.getString(5));
                    t.set_date(cursor.getString(6));
                transaksiList.add(t);
            }while (cursor.moveToNext());
        }
        return transaksiList;
    }

    public void deleteJadwal(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_JADWAL+" WHERE id = "+id);
    }

    public void deleteTransaksi(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_TRANSAKSI+" WHERE id = "+id);
    }




}
