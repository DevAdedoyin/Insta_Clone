package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;
import java.util.PriorityQueue;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private Button btnSave;
    private TextView nameEdtTxt;
    private TextView ageEdtTxt;
    private TextView clubEdtTxt;
    private TextView getFootballerDetail;
    private Button getAll;
    private String getAllFootballers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.saveBtn);
        nameEdtTxt = findViewById(R.id.fNameEdt);
        ageEdtTxt = findViewById(R.id.fAgeEdt);
        clubEdtTxt = findViewById(R.id.fClubEdt);
        getFootballerDetail = findViewById(R.id.getDetailText);
        getAll = findViewById(R.id.getAll);

        btnSave.setOnClickListener(SignUp.this);

        getFootballerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Footballer");
                parseQuery.getInBackground("X7hvXoLFZm", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        getFootballerDetail.setText("Name: " + object.get("Name") + " \n" + "Club: " + object.get("Club") + " \n" + "Age: " + object.get("Age"));
                    }
                });

            }
        });

        getAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Footballer");
                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null){
                            for (ParseObject footBaller : objects){
                                getAllFootballers = getAllFootballers + footBaller.get("Name") + " \n";
                            }
                            FancyToast.makeText(SignUp.this, getAllFootballers,  FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        } else {
                            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

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