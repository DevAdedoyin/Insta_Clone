package com.example.instaclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity {

    private Button btnLoginUser;
    private Button switch_to_signup_page;
    private  EditText userName;
    private EditText userPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        setTitle("Login");

        btnLoginUser = findViewById(R.id.btnLogin);
        switch_to_signup_page = findViewById(R.id.btnToSignUpPage);
        userName = findViewById(R.id.edtUsernameLogin);
        userPassword = findViewById(R.id.edtPasswordLogin);

        userPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_DOWN){
                    btnLoginUser.callOnClick();
                }
                return false;
            }
        });

        if (ParseUser.getCurrentUser() != null){
            //ParseUser.getCurrentUser().logOut();
            switch_to_index();
        }

        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().equals("") || userPassword.getText().toString().equals("")){
                    FancyToast.makeText(Login.this, "Username and Passwordn Required", FancyToast.LENGTH_SHORT, FancyToast.INFO, true);
                }else {
                ProgressDialog progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Login " + userName.getText().toString());
                progressDialog.show();
                ParseUser.logInInBackground(userName.getText().toString(), userPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null){
                            FancyToast.makeText(Login.this, user.getUsername() + " Logged in Successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            switch_to_index();
                        } else {
                            FancyToast.makeText(Login.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                        ProgressDialog progressDialog = new ProgressDialog(Login.this);
                        progressDialog.dismiss();
                    }
                });
                }
            }
        });

        switch_to_signup_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToSignup = new Intent(Login.this, SignUp.class);
                startActivity(switchToSignup);
            }
        });
    }

    public void hideKeyboard(View view){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void switch_to_index(){
        Intent intent = new Intent(Login.this, Index.class);
        startActivity(intent);
        finish();
    }
}