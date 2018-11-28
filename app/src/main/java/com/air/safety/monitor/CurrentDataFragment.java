package com.air.safety.monitor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class CurrentDataFragment extends Fragment{

    View myView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.current_data_layout, container, false);


// ----------------- PM
        PieChartView pieChartView_pm = myView.findViewById(R.id.chart_pm);
        List<SliceValue> pieData_pm = new ArrayList<>();
        pieData_pm.add(new SliceValue(15, Color.BLUE));
        pieData_pm.add(new SliceValue(10, Color.RED));

        PieChartData pieChartData_pm = new PieChartData(pieData_pm);
        pieChartData_pm.setHasCenterCircle(true).setCenterText1("PM").setCenterText1FontSize(25);
        pieChartView_pm.setPieChartData(pieChartData_pm);

// ----------------- VOC
        PieChartView pieChartView_voc = myView.findViewById(R.id.chart_voc);
        List<SliceValue> pieData_voc = new ArrayList<>();
        pieData_voc.add(new SliceValue(25, Color.BLUE));
        pieData_voc.add(new SliceValue(10, Color.RED));

        PieChartData pieChartData_voc = new PieChartData(pieData_voc);
        pieChartData_voc.setHasCenterCircle(true).setCenterText1("VOC").setCenterText1FontSize(25);
        pieChartView_voc.setPieChartData(pieChartData_voc);

// ----------------- CO2
        PieChartView pieChartView_co2 = myView.findViewById(R.id.chart_co2);
        List<SliceValue> pieData_co2 = new ArrayList<>();
        pieData_co2.add(new SliceValue(25, Color.BLUE));
        pieData_co2.add(new SliceValue(60, Color.RED));

        PieChartData pieChartData_co2 = new PieChartData(pieData_co2);
        pieChartData_co2.setHasCenterCircle(true).setCenterText1("CO2").setCenterText1FontSize(25);
        pieChartView_co2.setPieChartData(pieChartData_co2);


// ----------------- CO
        PieChartView pieChartView_co = myView.findViewById(R.id.chart_co);
        List<SliceValue> pieData_co = new ArrayList<>();
        pieData_co.add(new SliceValue(22, Color.BLUE));
        pieData_co.add(new SliceValue(78, Color.RED));

        PieChartData pieChartData_co = new PieChartData(pieData_co);
        pieChartData_co.setHasCenterCircle(true).setCenterText1("CO").setCenterText1FontSize(25);
        pieChartView_co.setPieChartData(pieChartData_co);

        return myView;
    }


}
