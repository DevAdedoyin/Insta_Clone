package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.PriorityQueue;


public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private Button btnSave;
    private TextView nameEdtTxt;
    private TextView ageEdtTxt;
    private TextView clubEdtTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.saveBtn);
        nameEdtTxt = findViewById(R.id.fNameEdt);
        ageEdtTxt = findViewById(R.id.fAgeEdt);
        clubEdtTxt = findViewById(R.id.fClubEdt);

        btnSave.setOnClickListener(SignUp.this);

    }

    @Override
    public void onClick(View v) {
        try {
            final ParseObject parseObject = new ParseObject("Footballer");
            parseObject.put("Name", nameEdtTxt.getText().toString());
            parseObject.put("Age", Integer.parseInt(ageEdtTxt.getText().toString()));
            parseObject.put("Club", clubEdtTxt.getText().toString());

            parseObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUp.this, parseObject.get("Name") + " profile is saved", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    } else {
                        FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }
            });
        } catch (Exception e){
            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
        }
    }
}
