package com.air.safety.monitor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CurrentDataFragment extends Fragment{

    View myView;

    EditText pmVal, vocVal, co2Val, coVal;
    Button btnCData;
    FirebaseDatabase database;
    DatabaseReference ref;

    FirebaseUser authData = FirebaseAuth.getInstance().getCurrentUser() ;

    PieChartView pieChartView[] = new PieChartView[4];
    List<SliceValue> pieData[] = new List[4];
    PieChartData pieChartData[] = new PieChartData[4];
    String gas_n[] = {"PM", "VOC", "CO2", "CO"};

    private static final String TAG = "tag me";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.current_data_layout, container, false);

        pmVal = (EditText) myView.findViewById(R.id.pmVal);
        vocVal = (EditText) myView.findViewById(R.id.vocVal);
        co2Val = (EditText) myView.findViewById(R.id.co2Val);
        coVal = (EditText) myView.findViewById(R.id.coVal);
        btnCData = (Button) myView.findViewById(R.id.btn_current_data);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference(authData.getUid());

        pieChartView[0] = myView.findViewById(R.id.chart_pm);
        pieChartView[1] = myView.findViewById(R.id.chart_voc);
        pieChartView[2] = myView.findViewById(R.id.chart_co2);
        pieChartView[3] = myView.findViewById(R.id.chart_co);

        setListeners();

        return myView;
    }

    private void setListeners() {
        btnCData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ref.push().getKey();

                int pm = Integer.parseInt(pmVal.getText().toString());
                int voc = Integer.parseInt(vocVal.getText().toString());
                int co2 = Integer.parseInt(co2Val.getText().toString());
                int co = Integer.parseInt(coVal.getText().toString());

                CurrentValue currentValue = new CurrentValue(pm, voc, co2, co);
                ref.child(id).setValue(currentValue);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren()){
                    CurrentValue currentValue = myDataSnapshot.getValue(CurrentValue.class);
                    int ValArr[] = {currentValue.getPmValue(),currentValue.getVocValue(),currentValue.getCo2Value(),currentValue.getCoValue()};

                    for (int i=0; i < 4; i++){
                        pieData[i] = new ArrayList<>();
                        pieData[i].add(new SliceValue(ValArr[i], Color.RED));
                        pieData[i].add(new SliceValue(100-ValArr[i], Color.LTGRAY));

                        pieChartData[i] = new PieChartData(pieData[i]);
                        //pieChartData[i].setHasLabels(true).setValueLabelTextSize(14);
                        pieChartData[i].setHasCenterCircle(true).setCenterText1(gas_n[i]+": " + Integer.toString(ValArr[i])+"%").setCenterText1FontSize(25);
                        pieChartView[i].setPieChartData(pieChartData[i]);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

