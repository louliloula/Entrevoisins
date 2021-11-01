package com.openclassrooms.entrevoisins;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteNeighbourActivity extends AppCompatActivity {


    @BindView(R.id.first_img)
    ImageView avatar;
    @BindView(R.id.name_neighbourg)
    TextView name;
    @BindView(R.id.cellphone)
    ImageView cellphone;
    @BindView(R.id.social_img)
    ImageView social_img;
    @BindView(R.id.text_location)
    TextView text_loc;
    @BindView(R.id.text_cellphone)
    TextView text_cell;
    @BindView(R.id.social_text)
    TextView social_txt;
    @BindView(R.id.location)
    ImageView loc;
    @BindView(R.id.about_me)
    TextView about;
    @BindView(R.id.text_about)
    TextView text_about;
    @BindView(R.id.favorite_float)
    FloatingActionButton favorite;





    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private Neighbour neighbour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_neighbour);

        ButterKnife.bind(this);

        Intent intent =getIntent();
        long id = getIntent().getLongExtra("monidentifiant",0);
        mApiService = DI.getNeighbourApiService();
        mNeighbours = mApiService.getNeighbours();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        for (Neighbour n : mNeighbours) {
            if (n.getId() == id)
                neighbour = n;
        }

            Log.i("monidentifiant",neighbour.toString());

        Glide.with(avatar.getContext())
                .load(neighbour.getAvatarUrl())
                .centerCrop()
                .into(avatar);




        this.name.setText(neighbour.getName());
        this.text_loc.setText(neighbour.getAddress());
        this.text_about.setText(neighbour.getAboutMe());
        this.text_cell.setText(neighbour.getPhoneNumber());
        this.social_txt.setText(neighbour.getName());



        if (neighbour.isFavorite()) {
            favorite.setImageResource(R.drawable.ic_favorit_n);
        } else {
            favorite.setImageResource(R.drawable.ic_star_white_24dp);
        }

        favorite.setOnClickListener(new View.OnClickListener()


        {
            @Override
            public void onClick(View v)


            {


                if (neighbour.isFavorite()) {
                    favorite.setImageResource(R.drawable.ic_star_white_24dp);

                    neighbour.setFavorite(false);


                } else {
                    favorite.setImageResource(R.drawable.ic_favorit_n);

                    neighbour.setFavorite(true);


                }

                mApiService.deleteNeighbour(neighbour);



                mApiService.createNeighbour(neighbour);
            }
        });

    }
}

