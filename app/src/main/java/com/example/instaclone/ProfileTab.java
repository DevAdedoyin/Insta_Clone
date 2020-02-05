package com.example.instaclone;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText mProfileName, mAge, mHobby, mHouseAdress;
    private Button mUpdateProfile;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        mProfileName = view.findViewById(R.id.profile_name);
        mAge = view.findViewById(R.id.age);
        mHobby = view.findViewById(R.id.hobby);
        mHouseAdress = view.findViewById(R.id.home_address);

        mUpdateProfile = view.findViewById(R.id.btnUpdateProfile);

        final ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser.get("profileName") == null) {
            mProfileName.setText("");
        } else {
            mProfileName.setText(parseUser.get("profileName") + "");
        }
        if (parseUser.get("age") == null) {
            mAge.setText("");
        } else {
            mAge.setText(parseUser.get("age") + "");
        }
        if (parseUser.get("hobby") == null) {
            mHobby.setText("");
        } else {
            mHobby.setText(parseUser.get("hobby") + "");
        }
        if (parseUser.get("houseAddress") == null) {
            mHouseAdress.setText("");
        } else {
            mHouseAdress.setText(parseUser.get("houseAddress") + "");
        }
        mUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName", mProfileName.getText() + "");
                parseUser.put("age", mAge.getText() + "");
                parseUser.put("hobby", mHobby.getText() + "");
                parseUser.put("houseAddress", mHouseAdress.getText() + "");

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(getContext(), "Profile Updated", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
                        } else {
                            FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });
        return view;
    }
}
