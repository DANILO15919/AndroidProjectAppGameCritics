package com.example.it322020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email, password;
    Button loginButton;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.loginEmail);
        password = (EditText)  findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(this);
    }

    public void Register(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }

    @Override
    public void onClick(View view) {
            LogIn();
    }

    private void LogIn() {
        String stringEmail = email.getText().toString().trim();
        String stringPassword = password.getText().toString().trim();

        if(stringEmail.isEmpty())
        {
            email.setError("You did register before huh?");
            email.requestFocus();
            return;
        }
        if(stringPassword.isEmpty())
        {
            password.setError("Just go and register come on");
            password.requestFocus();
            return;
        }
      mAuth.signInWithEmailAndPassword(stringEmail,stringPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Login failed, check your username or password!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}