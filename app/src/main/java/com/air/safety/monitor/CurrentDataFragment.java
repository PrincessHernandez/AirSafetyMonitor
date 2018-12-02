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
    DatabaseReference ref, ref2;

    FirebaseUser authData = FirebaseAuth.getInstance().getCurrentUser() ;

    //PieChartView pieChartView_pm, pieChartView_voc, pieChartView_co2, pieChartView_co;
    PieChartView pieChartView[] = new PieChartView[4];
    //List<SliceValue> pieData_pm, pieData_voc, pieData_co2, pieData_co;
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
        ref = database.getReference("Current Data");
        ref2 = database.getReference(authData.getUid());

        pieChartView[0] = myView.findViewById(R.id.chart_pm);
        pieChartView[1] = myView.findViewById(R.id.chart_voc);
        pieChartView[2] = myView.findViewById(R.id.chart_co2);
        pieChartView[3] = myView.findViewById(R.id.chart_co);

        for (int i=0; i < 4; i++){

            pieData[i] = new ArrayList<>();
            pieData[i].add(new SliceValue(100));

            pieChartData[i] = new PieChartData(pieData[i]);
            pieChartData[i].setHasLabels(true).setValueLabelTextSize(14);
            pieChartData[i].setHasCenterCircle(true).setCenterText1(gas_n[i]).setCenterText1FontSize(25);
            pieChartView[i].setPieChartData(pieChartData[i]);
        }

        setListeners();

        return myView;
    }

    private void setListeners() {
        btnCData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ref2.push().getKey();

                int pm = Integer.parseInt(pmVal.getText().toString());
                int voc = Integer.parseInt(vocVal.getText().toString());
                int co2 = Integer.parseInt(co2Val.getText().toString());
                int co = Integer.parseInt(coVal.getText().toString());

                CurrentValue currentValue = new CurrentValue(pm, voc, co2, co);
                ref2.child(id).setValue(currentValue);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren()){
                    CurrentValue currentValue = myDataSnapshot.getValue(CurrentValue.class);
                    pieChartData[3].setHasCenterCircle(true).setCenterText1("CO: "+Integer.toString(currentValue.getCoValue()) +"%").setCenterText1FontSize(25);

                    pieData[0].clear();
                    pieData[0].add(new SliceValue(currentValue.getPmValue(), Color.RED));
                    pieData[0].add(new SliceValue(100-currentValue.getPmValue(), Color.LTGRAY));

                    pieData[1].clear();
                    pieData[1].add(new SliceValue(currentValue.getVocValue(), Color.RED));
                    pieData[1].add(new SliceValue(100-currentValue.getVocValue(), Color.LTGRAY));

                    pieData[2].clear();
                    pieData[2].add(new SliceValue(currentValue.getCo2Value(), Color.RED));
                    pieData[2].add(new SliceValue(100-currentValue.getCo2Value(), Color.LTGRAY));

                    pieData[3].clear();
                    pieData[3].add(new SliceValue(currentValue.getCoValue(), Color.RED));
                    pieData[3].add(new SliceValue(100-currentValue.getCoValue(), Color.LTGRAY));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

