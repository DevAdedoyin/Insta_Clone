package com.example.instaclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class RegisterUser extends AppCompatActivity {

    private TextView loginPassword, loginUsername, signupUsername, signupPassword;
    private Button btnLogin, btnSignup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user_activity);

        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);
        loginPassword = findViewById(R.id.loginPassword);
        loginUsername = findViewById(R.id.loginUsername);
        signupUsername = findViewById(R.id.signupUsername);
        signupPassword = findViewById(R.id.signupPassword);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser parseUser = new ParseUser();
                parseUser.setUsername(signupUsername.getText().toString());
                parseUser.setPassword(signupPassword.getText().toString());
                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            FancyToast.makeText(RegisterUser.this, parseUser.get("username") +" Signed in successfully>", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            Intent intent = new Intent(RegisterUser.this, Welcome.class);
                            startActivity(intent);
                        } else{
                            FancyToast.makeText(RegisterUser.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(loginUsername.getText().toString(), loginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null){
                            FancyToast.makeText(RegisterUser.this, user.get("username") + " Logged in Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            Intent intent = new Intent(RegisterUser.this, Welcome.class);
                            startActivity(intent);
                        }else{
                            FancyToast.makeText(RegisterUser.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });
    }
}