package com.air.safety.monitor;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mSend, mProceed;
    private EditText et_lng, et_lat;
    private static final String TAG = "test";
    FirebaseUser authData = FirebaseAuth.getInstance().getCurrentUser() ;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(authData.getUid());

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSend = (Button) findViewById(R.id.btn_send);
        mProceed = (Button) findViewById(R.id.btn_proceed);
        et_lng = (EditText)findViewById(R.id.et_longitude);
        et_lat = (EditText)findViewById(R.id.et_latitude);


        mSend.setOnClickListener(this);

        mProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new MapsFragment())
                        .commit();
            }
        });
    }

    // Saves user longitude and latitude
    private void sendCoordinates(){
        double latitude= Double.parseDouble(et_lat.getText().toString().trim());
        double longitude= Double.parseDouble(et_lng.getText().toString().trim());
        Coordinates userInformation=new Coordinates(latitude,longitude);
        myRef.child("coordinates").setValue(userInformation);
        Toast.makeText(this,"Sent",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
//        if(v==mProceed){
//            finish();
//        }
        if (v==mSend){
            sendCoordinates();
            et_lng.getText().clear();
            et_lat.getText().clear();
        }
    }
}
