package com.example.android.noteapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextView text_singinTv, forgetTv;
    EditText email_logEt, password_logEt;
    ImageView image_close_login;
    Button loginButon;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String email, password;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, home_pages.class);
            startActivity(intent);
        }

        email_logEt = findViewById(R.id.email_logEt);
        password_logEt = findViewById(R.id.password_logEt);
        loginButon = findViewById(R.id.loginButon);
        image_close_login = findViewById(R.id.image_close_login);

        findViewById(R.id.forgetTv).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Forgot_passwordActivity.class);
                startActivity(intent);

            }
        });


        image_close_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });
        loginButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("".equals(email_logEt.getText().toString().trim())) {
                    email_logEt.setError("worng email");
                    return;
                }
                if (password_logEt.getText().toString().trim().equals("")) {
                    password_logEt.setError("worng password");
                    return;
                }

                if (isNetworkAvailable()) {
                    doSignIn(email_logEt.getText().toString(), password_logEt.getText().toString());
                }else{
                    Toast.makeText(LoginActivity.this, "please, check internet connection.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SingupActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void doSignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Success Login.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, home_pages.class);
                            startActivity(intent);


                        } else {
//                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            try {
                                task.getResult();
                                Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                            }catch (Exception e){
                                Toast.makeText(LoginActivity.this, e.getMessage().split(":")[1], Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
    }
}





