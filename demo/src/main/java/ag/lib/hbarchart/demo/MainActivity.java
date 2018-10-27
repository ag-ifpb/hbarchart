package ag.lib.hbarchart.demo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ag.lib.hbarchart.HDoubleBarChart;

public class MainActivity extends AppCompatActivity {
    HDoubleBarChart chart;
    boolean touch;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        chart = findViewById(R.id.hchart);
        chart.setMaxValue(100);
        chart.setPreviousValue(30);
    }

    public void changeColor(View v){
        if (touch){
            chart.resetCurrentBarColor();
            chart.resetPreviousBarColor();
        } else {
            chart.setPreviousBarColor("#cccccc");
            chart.setCurrentBarColor("#000000");
        }
        touch = !touch;
    }

    public void increase(View v){
        if (count < 100){
            count = count+5;
        } else {
            count = 0;
        }
        chart.setCurrentValue(count);
    }
}
