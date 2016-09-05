package com.example.lg.detector;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Sensor shockdetector;
    SensorManager shockmanager;
    @BindView(R.id.eventTime)
    TextView mTimeLabel;
    @BindView(R.id.eventTime2)
    TextView mTimeLabel2;
    @BindView(R.id.eventTime3)
    TextView mTimeLabel3;
    @BindView(R.id.eventTime4)
    TextView mTimeLabel4;
    public static int count;
    protected static boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        shockmanager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // NOTE:  The detector needs TYPE_LINEAR_ACCELERATION to detect shocks.  However, there is
        // some testing benefit to using TYPE_ACCELEROMETER instead since it always generates a
        // value (because of gravity).  Therefore, comment out one of the next two lines.
        shockdetector = shockmanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // shockdetector=shockmanager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        shockmanager.registerListener(this, shockdetector, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onResume() {
        super.onResume();
        setVisible(true);

    }


    @Override
    public void onPause() {
        super.onPause();
        setVisible(false);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate
        double[] gravity = new double[3];        //gravity values (phone senses in x,y,z ways)
        double[] linear_acceleration = new double[3]; // sensing accelaration
        final double alpha = 0.8;          // (normal gravity value)
        int count = 0;
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        while (isVisible) {
            if (linear_acceleration[0] > 0 ||
                    linear_acceleration[1] > 0 ||
                    linear_acceleration[2] > 0 && count == 0) {
                mTimeLabel.setText(getFormmatedTime());
                count++;
            }
            if (linear_acceleration[0] > 0 ||
                    linear_acceleration[1] > 0 ||
                    linear_acceleration[2] > 0 && count == 1) {
                mTimeLabel2.setText(getFormmatedTime());
                count++;
            }
            if (linear_acceleration[0] > 0 ||
                    linear_acceleration[1] > 0 ||
                    linear_acceleration[2] > 0 && count == 2) {
                mTimeLabel3.setText(getFormmatedTime());
                count++;
            }
            if (linear_acceleration[0] > 0 ||
                    linear_acceleration[1] > 0 ||
                    linear_acceleration[2] > 0 && count == 3) {
                mTimeLabel4.setText(getFormmatedTime());
                count++;
            }

        }
    }
    public String getFormmatedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        Time dateTime = new Time(Time.getCurrentTimezone());
        dateTime.setToNow();
        String timeString = formatter.format(dateTime);
        return timeString;
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
