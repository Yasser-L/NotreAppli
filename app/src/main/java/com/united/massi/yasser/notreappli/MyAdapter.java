package com.united.massi.yasser.notreappli;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    SQLiteDatabase DB;
    bdHelper helper;
    //    private List<String> wilayas;
    private Cursor wilayas;

    public MyAdapter(Context Mcontext) {
        helper = new bdHelper(Mcontext);
        DB = helper.getWritableDatabase();
        wilayas = helper.get_ALL_WILAYA(DB);
        context = Mcontext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.wilaya_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return wilayas.getCount();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.display(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView codeP;
        private final TextView icone;
        private final TextView badgeW;
        private final TextView badgeB;
        private final TextView badgeL;
        private final FloatingActionButton btn;
        private final CheckBox checkB;
        private final ImageView imageFav;
        private final LinearLayout layout_wilaya;
        int indexColor = 0;

        public MyViewHolder(final View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            codeP = itemView.findViewById(R.id.codePostal);
            icone = itemView.findViewById(R.id.icoWilaya);
            btn = itemView.findViewById(R.id.btn);
            badgeB = itemView.findViewById(R.id.badgeB);
            badgeL = itemView.findViewById(R.id.badgeL);
            badgeW = itemView.findViewById(R.id.badgeW);
            checkB = itemView.findViewById(R.id.checkBoxid);
            imageFav = itemView.findViewById(R.id.imageFav);
            layout_wilaya = itemView.findViewById(R.id.layout_wilaya);


        }


        public void display(final int p) {

            int tabCouleur[] = new int[11];
            tabCouleur[1] = context.getResources().getColor(R.color.col1);
            tabCouleur[2] = context.getResources().getColor(R.color.col2);
            tabCouleur[3] = context.getResources().getColor(R.color.col3);
            tabCouleur[4] = context.getResources().getColor(R.color.col4);
            tabCouleur[5] = context.getResources().getColor(R.color.col5);
            tabCouleur[6] = context.getResources().getColor(R.color.col6);
            tabCouleur[7] = context.getResources().getColor(R.color.col7);
            tabCouleur[8] = context.getResources().getColor(R.color.col8);
            tabCouleur[9] = context.getResources().getColor(R.color.col9);
            tabCouleur[10] = context.getResources().getColor(R.color.col10);
            Random generateur = new Random();
            indexColor = generateur.nextInt(10) + 1;
            wilayas.moveToPosition(p);
            name.setText(wilayas.getString(1));
            btn.setBackgroundTintList(ColorStateList.valueOf(tabCouleur[indexColor]));

            codeP.setText("Code postal : " + wilayas.getString(0));

            badgeL.setVisibility(View.GONE);
            badgeB.setVisibility(View.GONE);
            icone.setText(wilayas.getString(1).substring(0, 1));

            btn.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    goto_bureau(p);
                }
            });
            layout_wilaya.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goto_bureau(p);
                }
            });


//        vérifier si une wilaya est favorite
            switch (wilayas.getString(2)) {
                case "0":
                    image_Off();
                    break;
                case "1":
                    image_On();
                    break;
            }

            imageFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    wilayas.moveToPosition(p);
                    if (checkB.isChecked()) {
                        image_Off();
                        helper.AlterFavorite(DB, wilayas.getString(0), "0", 1);

                    } else {
                        image_On();
                        helper.AlterFavorite(DB, wilayas.getString(0), "1", 1);
                    }
//                    rafraichier la base de données
                    wilayas = helper.get_ALL_WILAYA(DB);
                }
            });

        }

        public void image_Off() {
            imageFav.setImageResource(R.drawable.star_off_s);
            checkB.setChecked(false);
        }

        public void image_On() {
            imageFav.setImageResource(R.drawable.star_on_s);
            checkB.setChecked(true);
        }

        public void goto_bureau(int p) {
            wilayas.moveToPosition(p);
            Intent activite_bureau = new Intent(context, Activity_bureau.class);
            activite_bureau.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activite_bureau.putExtra("code", wilayas.getString(0));
            context.startActivity(activite_bureau);
        }
    }

}

