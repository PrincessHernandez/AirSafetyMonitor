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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    LineGraphSeries graphSeries;

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

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Hourly Data");

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
                DataPoint[] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;
                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren()){
                    PointValue pointValue = myDataSnapshot.getValue(PointValue.class);
                    dp[index] = new DataPoint(pointValue.getxValue(),pointValue.getyValue());
                    index++;
                }
                graphSeries.resetData(dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
