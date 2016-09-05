package practiceapp.shockdetector;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Sensor shockdetector;
    SensorManager shockmanager;
    TextView eventView0, dateView0, eventView1, dateView1, eventView2, dateView2, eventView3,
             dateView3, eventView4, dateView4;
    public static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shockmanager=(SensorManager)getSystemService(SENSOR_SERVICE);
        // NOTE:  The detector needs TYPE_LINEAR_ACCELERATION to detect shocks.  However, there is
        // some testing benefit to using TYPE_ACCELEROMETER instead since it always generates a
        // value (because of gravity).  Therefore, comment out one of the next two lines.
        shockdetector=shockmanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // shockdetector=shockmanager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        shockmanager.registerListener(this, shockdetector, SensorManager.SENSOR_DELAY_NORMAL);
        eventView0=(TextView)findViewById(R.id.textView3);  // Would like to find some more
        dateView0=(TextView)findViewById(R.id.textView8);  // ...
        eventView1=(TextView)findViewById(R.id.textView4);  // ...
        dateView1=(TextView)findViewById(R.id.textView9);  // ...
        eventView2=(TextView)findViewById(R.id.textView5);  // ...
        dateView2=(TextView)findViewById(R.id.textView10); // ...
        eventView3=(TextView)findViewById(R.id.textView6);  // ...
        dateView3=(TextView)findViewById(R.id.textView11); // ...
        eventView4=(TextView)findViewById(R.id.textView7);  // ...
        dateView4=(TextView)findViewById(R.id.textView12); // elegant way of handling this output.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float shock;
        Date shockdate = new  Date();
        String[] shocktxt = new String[5];  // Small array size for concept; storage array(s) can
        String[] datetxt = new String[5];  // be made as large as desired.

         if((event.values[0]>5)||(event.values[1]>5)||(event.values[2]>5)) {
             shock = event.values[0];

             if (event.values[1] > shock) {
                 shock = event.values[1];
             }

             if (event.values[2] > shock) {
                 shock = event.values[2];
             }

             shocktxt[count] = Float.toString(shock);
             datetxt[count] = shockdate.toString();
             switch (count) {

                 case 0:                                     // Again, would like to find something
                     eventView0.setText(shocktxt[count]);
                     dateView0.setText(datetxt[count]);
                     break;

                 case 1:                                    // more elegant.
                     eventView1.setText(shocktxt[count]);
                     dateView1.setText(datetxt[count]);
                     break;

                 case 2:                                    // Especially if the storage array(s)
                     eventView2.setText(shocktxt[count]);
                     dateView2.setText(datetxt[count]);
                     break;

                 case 3:                                    // get larger, this will become
                     eventView3.setText(shocktxt[count]);
                     dateView3.setText(datetxt[count]);
                     break;

                 case 4:                                    // cumbersome.
                     eventView4.setText(shocktxt[count]);
                     dateView4.setText(datetxt[count]);
                     break;
             }

             if (count<4){  // If storage array(s) are increased to size n, make this n-1.
                 count++;   // Saves first n-1 events, but allows unlimited overwrite of last event.
             }
         }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // Not used.
    }
}