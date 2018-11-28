package com.air.safety.monitor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

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

    PieChartView pieChartView_pm, pieChartView_voc, pieChartView_co2, pieChartView_co;
    List<SliceValue> pieData_pm, pieData_voc, pieData_co2, pieData_co;
    PieChartData pieChartData_pm, pieChartData_voc, pieChartData_co2, pieChartData_co;

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

// ----------------- PM
        pieChartView_pm = myView.findViewById(R.id.chart_pm);
        pieData_pm = new ArrayList<>();
        pieData_pm.add(new SliceValue(15, Color.BLUE));
        pieData_pm.add(new SliceValue(10, Color.RED));

        pieChartData_pm = new PieChartData(pieData_pm);
        pieChartData_pm.setHasCenterCircle(true).setCenterText1("PM").setCenterText1FontSize(25);
        pieChartView_pm.setPieChartData(pieChartData_pm);

// ----------------- VOC
        pieChartView_voc = myView.findViewById(R.id.chart_voc);
        pieData_voc = new ArrayList<>();
        pieData_voc.add(new SliceValue(25, Color.BLUE));
        pieData_voc.add(new SliceValue(10, Color.RED));

        pieChartData_voc = new PieChartData(pieData_voc);
        pieChartData_voc.setHasCenterCircle(true).setCenterText1("VOC").setCenterText1FontSize(25);
        pieChartView_voc.setPieChartData(pieChartData_voc);

// ----------------- CO2
        pieChartView_co2 = myView.findViewById(R.id.chart_co2);
        pieData_co2 = new ArrayList<>();
        pieData_co2.add(new SliceValue(25, Color.BLUE));
        pieData_co2.add(new SliceValue(60, Color.RED));

        pieChartData_co2 = new PieChartData(pieData_co2);
        pieChartData_co2.setHasCenterCircle(true).setCenterText1("CO2").setCenterText1FontSize(25);
        pieChartView_co2.setPieChartData(pieChartData_co2);


// ----------------- CO
        pieChartView_co = myView.findViewById(R.id.chart_co);
        pieData_co = new ArrayList<>();
        pieData_co.add(new SliceValue(22, Color.BLUE));
        pieData_co.add(new SliceValue(78, Color.RED));

        pieChartData_co = new PieChartData(pieData_co);
        pieChartData_co.setHasCenterCircle(true).setCenterText1("CO").setCenterText1FontSize(25);
        pieChartView_co.setPieChartData(pieChartData_co);

        return myView;
    }


}
