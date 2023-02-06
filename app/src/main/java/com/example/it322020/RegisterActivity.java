package com.example.it322020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import pl.droidsonroids.gif.GifImageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
private EditText regUsername, regPassword, regEmail, regNumber;
private Button regButton;
private FirebaseAuth mAuth;
private GifImageView progressBar, ErrorGif, Idle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.robotLoadingGif);
        progressBar.setVisibility(View.INVISIBLE);
        ErrorGif = findViewById(R.id.policemanGifError);
        ErrorGif.setVisibility(View.INVISIBLE);
        Idle = findViewById(R.id.fishIdleGif);
        Idle.setVisibility(View.VISIBLE);

        regUsername = findViewById(R.id.registerUsername);
        regPassword = findViewById(R.id.registerPassword);
        regEmail = findViewById(R.id.registerEmail);
        regNumber = findViewById(R.id.registerNumber);

        regButton = (Button) findViewById(R.id.btnRegister);
        regButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        registerUser();
    }

    void registerUser() {
        String stringUsername = regUsername.getText().toString().trim();
        String stringPassword = regPassword.getText().toString().trim();
        String stringEmail = regEmail.getText().toString().trim();
        String stringNumber = regNumber.getText().toString().trim();

        if (stringUsername.isEmpty()) {
            ErrorGif.setVisibility(View.VISIBLE);
            Idle.setVisibility(View.INVISIBLE);
            regUsername.setError("You are nameless huh?");
            regUsername.requestFocus();
            return;
        }
        if (stringPassword.isEmpty()) {
            ErrorGif.setVisibility(View.VISIBLE);
            Idle.setVisibility(View.INVISIBLE);
            regPassword.setError("You don't want me getting into your account");
            regPassword.requestFocus();
            return;
        }
        if (stringPassword.length()<6) {
            ErrorGif.setVisibility(View.VISIBLE);
            Idle.setVisibility(View.INVISIBLE);
            regPassword.setError("You can do longer than that come on");
            regPassword.requestFocus();
            return;
        }
        if (stringEmail.isEmpty()) {
            ErrorGif.setVisibility(View.VISIBLE);
            Idle.setVisibility(View.INVISIBLE);
            regEmail.setError("Just give me your instagram email, its save (pinky promise)");
            regEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(stringEmail).matches()) {
            ErrorGif.setVisibility(View.VISIBLE);
            Idle.setVisibility(View.INVISIBLE);
            regEmail.setError("You know how to write an email address right?");
            regEmail.requestFocus();
            return;
        }
        if (stringNumber.isEmpty()) {
            ErrorGif.setVisibility(View.VISIBLE);
            Idle.setVisibility(View.INVISIBLE);
            regNumber.setError("You are not a pretty girl soo you cant avoid this one");
            regNumber.requestFocus();
            return;
        }
        ErrorGif.setVisibility(View.INVISIBLE);
        Idle.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(stringEmail, stringPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    User user = new User(stringUsername,stringPassword,stringEmail,stringNumber);
                    FirebaseDatabase.getInstance("https://gamecritics-bc259-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this,"User Registered",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this,"Registration failed",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"Registration failed, use another email preferably from your mom ;)",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}