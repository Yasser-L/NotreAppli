package com.united.massi.yasser.notreappli;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class bdHelper extends SQLiteOpenHelper {

    public static final String TABLE_WILAYA_NAME = "wilaya";
    public static final String TABLE_WILAYA_CODE = "code_wilaya";
    public static final String TABLE_WILAYA_LIBELLE = "libelle_wilaya";
    public static final String TABLE_WILAYA_FAVORIS = "favoris_wilaya";
    public static final String TABLE_WILAYA_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_WILAYA_NAME + "(" +
            TABLE_WILAYA_CODE + " VARCHAR(5) PRIMARY KEY," +
            TABLE_WILAYA_LIBELLE + " VARCHAR(18)," +
            TABLE_WILAYA_FAVORIS + " VARCHAR(1))";
    public static final String TABLE_GET_WILAYA_DATA = "SELECT * FROM " + TABLE_WILAYA_NAME;
    public static final String TABLE_BUREAU_NAME = "bureau";
    public static final String TABLE_BUREAU_CODE = "code_bureau";
    public static final String TABLE_BUREAU_LIBELLE = "libelle_bureau";

    //Constantes
    public static final String TABLE_BUREAU_FAVORIS = "favoris_bureau";
    public static final String TABLE_BUREAU_CODE_WILAYA = "code_wilaya";
    public static final String TABLE_BUREAU_ADRESSE = "adresse_bureau";
    public static final String TABLE_BUREAU_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_BUREAU_NAME + "(" +
            TABLE_BUREAU_CODE + " VARCHAR(5) PRIMARY KEY ," +
            TABLE_BUREAU_LIBELLE + " VARCHAR(68)," +
            TABLE_BUREAU_FAVORIS + " VARCHAR(1)," +
            TABLE_BUREAU_CODE_WILAYA + " VARCHAR(5)," +
            TABLE_BUREAU_ADRESSE + " VARCHAR(68))";
    public static final String TABLE_GET_BUREAU_DATA = "SELECT * FROM " + TABLE_BUREAU_NAME;
    public static final String TABLE_LOCALITE_NAME = "localite";
    public static final String TABLE_LOCALITE_ID = "id_localite";
    public static final String TABLE_LOCALITE_LIBELLE = "libelle_localite";
    public static final String TABLE_LOCALITE_FAVORIS = "favoris_localite";
    public static final String TABLE_LOCALITE_CODE = "code_postal";
    public static final String TABLE_LOCALITE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_LOCALITE_NAME + "(" +
            TABLE_LOCALITE_ID + " VARCHAR(5) PRIMARY KEY," +
            TABLE_LOCALITE_LIBELLE + " VARCHAR(68)," +
            TABLE_LOCALITE_FAVORIS + " VARCHAR(1)," +
            TABLE_LOCALITE_CODE + " VARCHAR(5))";
    public static final String TABLE_GET_LOCALITE_DATA = "SELECT * FROM " + TABLE_LOCALITE_NAME;
    public Cursor curseur;
    public Cursor curseur_WILAYA;
    public Cursor curseur_BUREAU;
    public Cursor curseur_LOCALITE;
    public Context Mycontext;
    public String TABLE_INSERT = "rien";
    public String TABLE_WILAYA_INSERT = "rien";
    public String TABLE_BUREAU_INSERT = "rien";
    public String TABLE_LOCALITE_INSERT = "rien";


    //Constructeur
    public bdHelper(Context context) {

        super(context, "db", null, 1);
        Mycontext = context;
    }

    //Recuperation des toutes les wilaya
    public Cursor get_ALL_WILAYA(SQLiteDatabase DB) {
        curseur = DB.rawQuery(TABLE_GET_WILAYA_DATA, null);
        return curseur;
    }

    //Recuperation des tout les bureaux
    public Cursor get_ALL_BUREAU(SQLiteDatabase DB) {
        curseur = DB.rawQuery(TABLE_GET_BUREAU_DATA, null);
        return curseur;
    }

    //Recuperation des toutes les localité
    public Cursor get_ALL_LOCALITE(SQLiteDatabase DB) {
        curseur = DB.rawQuery(TABLE_GET_LOCALITE_DATA, null);
        return curseur;
    }

    //    modifier favoris dans la base de données
    public void AlterFavorite(SQLiteDatabase DB, String codePostal, String fav, int table) {
        String REQUETE = "";
        switch (table) {
            case 1:
                REQUETE = "UPDATE " + TABLE_WILAYA_NAME + " SET " + TABLE_WILAYA_FAVORIS + " = \"" + fav + "\" WHERE " + TABLE_WILAYA_CODE + " = \"" + codePostal + "\"";
                break;
            case 2:
                REQUETE = "UPDATE " + TABLE_BUREAU_NAME + " SET " + TABLE_BUREAU_FAVORIS + " = \"" + fav + "\" WHERE " + TABLE_BUREAU_CODE + " = \"" + codePostal + "\"";
                break;
            case 3:
                REQUETE = "UPDATE " + TABLE_LOCALITE_NAME + " SET " + TABLE_LOCALITE_FAVORIS + " = \"" + fav + "\" WHERE " + TABLE_LOCALITE_ID + " = \"" + codePostal + "\"";
                break;
        }
//        Toast.makeText(Mycontext,REQUETE, Toast.LENGTH_LONG).show();
        DB.execSQL(REQUETE);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {

        String data_wilaya = Mycontext.getString(R.string.data_wilaya);
        String data_bureau = Mycontext.getString(R.string.data_bureau);
        String data_localite = Mycontext.getString(R.string.data_localite);

        TABLE_WILAYA_INSERT = "INSERT INTO " + TABLE_WILAYA_NAME + " VALUES " + data_wilaya;
        TABLE_BUREAU_INSERT = "INSERT INTO " + TABLE_BUREAU_NAME + " VALUES " + data_bureau;
        TABLE_LOCALITE_INSERT = "INSERT INTO " + TABLE_LOCALITE_NAME + " VALUES " + data_localite;

        try {
            db.execSQL(TABLE_WILAYA_CREATE);
            db.execSQL(TABLE_LOCALITE_CREATE);
            db.execSQL(TABLE_BUREAU_CREATE);
        } catch (SQLException e) {
            Log.d("SQL Exec", "Fail");
            Toast.makeText(Mycontext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        curseur_WILAYA = db.rawQuery(TABLE_GET_WILAYA_DATA, null);
        curseur_BUREAU = db.rawQuery(TABLE_GET_BUREAU_DATA, null);
        curseur_LOCALITE = db.rawQuery(TABLE_GET_LOCALITE_DATA, null);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
