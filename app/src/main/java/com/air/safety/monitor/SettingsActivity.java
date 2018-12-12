package com.air.safety.monitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SettingsActivity extends AppCompatActivity {

    private Button mRead, mWrite;
    private static final String TAG = "test";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("data test");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner mLanguage = (Spinner) findViewById(R.id.spLanguage);
        final TextView mTextView = (TextView) findViewById(R.id.textView);

        ArrayAdapter mAdapter = new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_option));
        mLanguage.setAdapter(mAdapter);

        SharedPreferences.Editor editor = getPreferences(0).edit();

        if (LocaleHelper.getLanguage(SettingsActivity.this).equalsIgnoreCase("en")) {
            mLanguage.setSelection(mAdapter.getPosition("English"));
        } else if (LocaleHelper.getLanguage(SettingsActivity.this).equalsIgnoreCase("fr")) {
            mLanguage.setSelection(mAdapter.getPosition("French"));
        }


        int selectedPosition = mLanguage.getSelectedItemPosition();
        editor.putInt("spinnerSelection", selectedPosition);
        editor.commit();

        mLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Context context;
                Resources resources;
                switch (i) {
                    case 0:
                        context = LocaleHelper.setLocale(SettingsActivity.this, "en");
                        resources = context.getResources();
                        mTextView.setText(resources.getString(R.string.text_translation));
                        break;
                    case 1:
                        context = LocaleHelper.setLocale(SettingsActivity.this, "fr");
                        resources = context.getResources();
                        mTextView.setText(resources.getString(R.string.text_translation));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
}
