package com.air.safety.monitor;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HourlyDataFragment extends Fragment{
    View myView;
    EditText xVal, yVal;
    Button btnInsert;
    FirebaseDatabase database;
    DatabaseReference ref;
    GraphView graphView;
    LineGraphSeries graphSeries, graphSeries2, graphSeries3, graphSeries4;

    FirebaseUser authData = FirebaseAuth.getInstance().getCurrentUser() ;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    // HH:mm:ss

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.hourly_data_layout, container, false);

        xVal = (EditText) myView.findViewById(R.id.xVal);
        yVal = (EditText) myView.findViewById(R.id.yVal);
        btnInsert = (Button) myView.findViewById(R.id.btn_insert);
        graphView = (GraphView) myView.findViewById(R.id.graph);

        graphSeries=new LineGraphSeries();
        graphView.addSeries(graphSeries);

        graphSeries2=new LineGraphSeries();
        graphView.addSeries(graphSeries2);

        graphSeries3=new LineGraphSeries();
        graphView.addSeries(graphSeries3);

        graphSeries4=new LineGraphSeries();
        graphView.addSeries(graphSeries4);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference(authData.getUid());

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()
        {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX) {
                    return  sdf.format(new Date((long) value));
                }
                return super.formatLabel(value, isValueX);
            }
        });

        setListeners();

        return myView;
    }

    private void setListeners() {
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ref.push().getKey();

                int x = Integer.parseInt(xVal.getText().toString());
                int y = Integer.parseInt(yVal.getText().toString());

                PointValue pointValue = new PointValue(x,y);

                ref.child(id).setValue(pointValue);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint[][] dp = new DataPoint[4][(int) dataSnapshot.getChildrenCount()];
                //DataPoint[] dp_voc = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                //DataPoint[] dp_co2 = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                //DataPoint[] dp_co = new DataPoint[(int) dataSnapshot.getChildrenCount()];

                int index = 0;
                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren()){
                    CurrentValue currentValue = myDataSnapshot.getValue(CurrentValue.class);
                    int ValArr[] = {currentValue.getPmValue(),currentValue.getVocValue(),currentValue.getCo2Value(),currentValue.getCoValue()};

                    for (int i=0; i < 4; i++) {
                        dp[i][index] = new DataPoint(currentValue.getDate(),ValArr[i]);
                    }
                    //dp_pm[index] = new DataPoint(currentValue.getDate(),currentValue.getPmValue());
                    //dp_voc[index] = new DataPoint(currentValue.getDate(),currentValue.getVocValue());
                    //dp_co2[index] = new DataPoint(currentValue.getDate(),currentValue.getCo2Value());
                    //dp_co[index] = new DataPoint(currentValue.getDate(),currentValue.getCoValue());
                    index++;
                }
                graphSeries.resetData(dp[0]);
                graphSeries2.resetData(dp[1]);
                graphSeries3.resetData(dp[2]);
                graphSeries4.resetData(dp[3]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
