package com.example.it322020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.it322020.Adapters.MainRecyclerAdapter;
import com.example.it322020.Listeners.OnGameClickAListener;
import com.example.it322020.Listeners.OnSearchAPIListener;
import com.example.it322020.Models.SearchAPIResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements OnGameClickAListener {

    Button logout;
    FirebaseAuth mAuth;

    SearchView searchView;
    RecyclerView recyclerViewMain;
    MainRecyclerAdapter adapter;
    RequestManager manager;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       logout = (Button) findViewById(R.id.profile_button);

        searchView = findViewById(R.id.search);
        recyclerViewMain = findViewById(R.id.recycler);
        dialog = new ProgressDialog(this);

        manager = new RequestManager(this);

        manager.searchGames(listener," ");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                manager.searchGames(listener,query);
                dialog.setTitle("Proccessing");
                dialog.show();
                    return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final OnSearchAPIListener listener = new OnSearchAPIListener() {
        @Override
        public void onResponse(SearchAPIResponse response) {
            dialog.dismiss();
            if (response==null)
            {
                Toast.makeText(MainActivity.this,"No data",Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(response);
        }


        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
            return;
        }
    };

    private void showResult(SearchAPIResponse response) {
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(new GridLayoutManager( MainActivity.this,1));
        adapter = new MainRecyclerAdapter(this,response.getGames(),this);
        recyclerViewMain.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this,"You need to log out to go back to login",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onGameClicked(String slur) {
        startActivity(new Intent(MainActivity.this,DetailsActivity.class).putExtra("slur",slur));
    }

    public void LogOut(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }
}