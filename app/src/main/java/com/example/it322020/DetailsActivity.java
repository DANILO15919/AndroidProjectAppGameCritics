package com.example.it322020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it322020.Listeners.OnDetailsAPIListener;
import com.example.it322020.Listeners.OnGameClickAListener;
import com.example.it322020.Models.DetailAPIResponse;
import com.example.it322020.Models.SearchAPIResponse;
import com.squareup.picasso.Picasso;

import java.io.PipedInputStream;

public class DetailsActivity extends AppCompatActivity {

    TextView details_game_name, details_game_rating,details_game_description,details_game_rcount;
    ImageView details_game_image;
    RequestManager menager;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        details_game_name = findViewById(R.id.game_name_details);
        details_game_rating = findViewById(R.id.game_rating_details);
        details_game_description = findViewById(R.id.game_description_details);
        details_game_image = findViewById(R.id.game_image_details);
        details_game_rcount = findViewById(R.id.ratingCount);


        menager = new RequestManager(this);

        String slur = getIntent().getStringExtra("slur");

        menager.searchGamesDetails(listener,slur);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Wait a sec");
        dialog.show();

    }

    private OnDetailsAPIListener listener = new OnDetailsAPIListener() {
        @Override
        public void onResponse(DetailAPIResponse response) {
            dialog.dismiss();
            if (response==null)
            {
                Toast.makeText(DetailsActivity.this,"Error",Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(response);
        }


        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(DetailsActivity.this,"Error",Toast.LENGTH_SHORT).show();
            return;
        }
    };

    private void showResult(DetailAPIResponse response) {
        if (Integer.parseInt(response.getMetacritic())>70)
        {
            details_game_rating.setTextColor(Color.parseColor("#2FFF54"));
        }else if (Integer.parseInt(response.getMetacritic())<70&&Integer.parseInt(response.getMetacritic())>50)
        {
            details_game_rating.setTextColor(Color.parseColor("#FFD760"));
        }else if (Integer.parseInt(response.getMetacritic())<50){
            details_game_rating.setTextColor(Color.parseColor("#FF495F"));
        }
        details_game_name.setText(response.getName());
        details_game_rating.setText(response.getMetacritic());
        details_game_description.setText(response.getDescription_raw());
        details_game_rcount.setText(response.getRatings_count());
        Picasso.get().load(response.getBackground_image()).into(details_game_image);
    }
}