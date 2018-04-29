package com.united.massi.yasser.notreappli;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        //Recuperer liste des wilayas bureau et localité
        bdHelper createDB = new bdHelper(getApplicationContext());
        SQLiteDatabase DB = createDB.getWritableDatabase();
        createDB.onCreate(DB);
        Cursor result_WILAYA = createDB.curseur_WILAYA;
        Cursor result_BUREAU = createDB.curseur_BUREAU;
        Cursor result_LOCALITE = createDB.curseur_LOCALITE;

//    vérifier si les tables sont vides pour les remplir
        if (result_WILAYA.getCount() == 0) {
            DB.execSQL(createDB.TABLE_WILAYA_INSERT);
        }
        if (result_BUREAU.getCount() == 0) {
            DB.execSQL(createDB.TABLE_BUREAU_INSERT);
        }
        if (result_LOCALITE.getCount() == 0) {
            DB.execSQL(createDB.TABLE_LOCALITE_INSERT);
        }

        Toast.makeText(getApplicationContext(), String.valueOf(result_WILAYA.getCount()) + " " + String.valueOf(result_BUREAU.getCount()) + " " + String.valueOf(result_LOCALITE.getCount()), Toast.LENGTH_SHORT).show();

        //Personnaliser Toolbar
        customizeActionBar();

        //Mise en place de Recyclerview
        final RecyclerView liste = findViewById(R.id.liste_wilaya);
        liste.setLayoutManager(new LinearLayoutManager(this));
        final MyAdapter myAdapter = new MyAdapter(getApplicationContext());
        liste.setAdapter(myAdapter);


        //Mise en place d'Autocomplete
        ArrayList<String> listeSearch = new ArrayList<>();
        ArrayAdapter adapterSearch = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listeSearch);
        AutoCompleteTextView barreSearch = findViewById(R.id.barreSearch);
        barreSearch.setAdapter(adapterSearch);
        barreSearch.setThreshold(1);

        //Recuperer liste des resultats d'Autocomplete

        for (result_WILAYA.moveToFirst(); !result_WILAYA.isAfterLast(); result_WILAYA.moveToNext()) {
//            listeSearch.add("cod : "+ result_WILAYA.getString(0));
            listeSearch.add("wil : " + result_WILAYA.getString(1));
        }
        for (result_BUREAU.moveToFirst(); !result_BUREAU.isAfterLast(); result_BUREAU.moveToNext()) {
            listeSearch.add("cod : " + result_BUREAU.getString(0));
            listeSearch.add("bur : " + result_BUREAU.getString(1));
        }
        for (result_LOCALITE.moveToFirst(); !result_LOCALITE.isAfterLast(); result_LOCALITE.moveToNext()) {
            listeSearch.add("loc : " + result_LOCALITE.getString(1));
        }

    }


    //Personnaliser Toolbar
    private void customizeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View customNav = LayoutInflater.from(this).inflate(R.layout.tool_bar_layout, null); // layout which contains your button.

        actionBar.setCustomView(customNav, params);
    }


}
