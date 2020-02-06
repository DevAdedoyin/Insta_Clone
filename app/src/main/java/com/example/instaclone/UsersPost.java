package com.example.instaclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPost extends AppCompatActivity {

    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);

        mLinearLayout = findViewById(R.id.linearLayout);

        Intent receivedIntentObject = getIntent();
        final String reeivedUserName = receivedIntentObject.getStringExtra("username");
        FancyToast.makeText(this, reeivedUserName, Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

        setTitle(reeivedUserName + "'s Posts");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username", reeivedUserName);
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

         parseQuery.findInBackground(new FindCallback<ParseObject>() {
             @Override
             public void done(List<ParseObject> objects, ParseException e) {
                 if (objects.size() > 0 && e == null){
                     for (final ParseObject post : objects){
                         final TextView postDescription = new TextView(UsersPost.this);
                         postDescription.setText(post.get("image_des") + "");
                         ParseFile postPicture = (ParseFile) post.get("picture");
                         postPicture.getDataInBackground(new GetDataCallback() {
                             @Override
                             public void done(byte[] data, ParseException e) {
                                 if (data != null && e == null){

                                     Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                     ImageView postImageView = new ImageView(UsersPost.this);
                                     LinearLayout.LayoutParams imageView_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                     imageView_params.setMargins(5, 5, 5, 5);
                                     postImageView.setLayoutParams(imageView_params);
                                     postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                     postImageView.setImageBitmap(bitmap);

                                     LinearLayout.LayoutParams desc_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                     desc_params.setMargins(5, 5, 5, 5);
                                     postDescription.setLayoutParams(desc_params);
                                     postDescription.setGravity(Gravity.CENTER);
                                     postDescription.setBackgroundColor(Color.RED);
                                     postDescription.setTextColor(Color.WHITE);
                                     postDescription.setTextSize(30f);

                                     mLinearLayout.addView(postImageView);
                                     mLinearLayout.addView(postDescription);
                                 }
                             }
                         });
                     }
                 }else {
                     FancyToast.makeText(UsersPost.this, reeivedUserName + " have no post", Toast.LENGTH_SHORT, FancyToast.INFO, true).show();
                     finish();
                 }
                 progressDialog.dismiss();
             }
         });
    }
}
