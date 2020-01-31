package com.example.instaclone;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class Index extends AppCompatActivity {

    private TextView welcomeText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_activity);
        welcomeText = findViewById(R.id.indexView);

        welcomeText.setText("Welcome " + ParseUser.getCurrentUser().get("username"));
    }
}
