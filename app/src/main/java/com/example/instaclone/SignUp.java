package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity{

    private Button btnSignupUser;
    private Button switch_to_Login_page;
    private EditText userEmail;
    private  EditText userName;
    private EditText userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        setTitle("Signup");

        btnSignupUser = findViewById(R.id.btnSignup);
        switch_to_Login_page = findViewById(R.id.btnToLoginPage);
        userEmail = findViewById(R.id.edtEmail);
        userName = findViewById(R.id.edtUsernameSignup);
        userPassword = findViewById(R.id.edtPasswordSignup);

        userPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    btnSignupUser.callOnClick();
                }
                return false;
            }
        });

        if (ParseUser.getCurrentUser() != null){
            //ParseUser.getCurrentUser().logOut();
            switch_to_index();
        }

        btnSignupUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEmail.getText().toString().equals("") || userName.getText().toString().equals("") || userPassword.getText().toString().equals("")){
                    FancyToast.makeText(SignUp.this, "Email, Username and Password Required", FancyToast.LENGTH_SHORT, FancyToast.INFO, true);
                } else {
                final ParseUser signupParse = new ParseUser();
                signupParse.setEmail(userEmail.getText().toString());
                signupParse.setUsername(userName.getText().toString());
                signupParse.setPassword(userPassword.getText().toString());
                ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("Registering " + userName.getText().toString());
                progressDialog.show();
                signupParse.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            FancyToast.makeText(SignUp.this, signupParse.getUsername() + " Registered Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            switch_to_index();
                        }else {
                            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                        ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                        progressDialog.dismiss();
                    }
                });
                }
            }
        });

        switch_to_Login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToLogin = new Intent(SignUp.this, Login.class);
                startActivity(switchToLogin);

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
        Intent intent = new Intent(SignUp.this, Index.class);
        startActivity(intent);

    }

}