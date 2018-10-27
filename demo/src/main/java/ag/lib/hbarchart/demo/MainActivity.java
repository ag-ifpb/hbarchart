package ag.lib.hbarchart.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ag.lib.hbarchart.HDoubleBarChart;

public class MainActivity extends AppCompatActivity {
    HDoubleBarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        chart = findViewById(R.id.hchart);
        chart.setMaxValue(100);
        chart.setPreviousValue(30);
    }

    public void changePrevColor(View v){
        chart.setPreviousBarColor("#cccccc");
        chart.setPreviousValue(200);
    }

    public void changeCurrColor(View v){
        chart.setCurrentBarColor("#000000");
        chart.setCurrentValue(50);
    }
}
