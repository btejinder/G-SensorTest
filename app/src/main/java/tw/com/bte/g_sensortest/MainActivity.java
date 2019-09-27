package tw.com.bte.g_sensortest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView tv_x, tv_y, tv_z;
    private  LevelView levelView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        levelView = (LevelView)findViewById(R.id.view);
        tv_x = findViewById(R.id.tv_px);
        tv_y = findViewById(R.id.tv_py);
        tv_z = findViewById(R.id.tv_pz);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensor != null)
        sensorManager.registerListener(mSensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(sensor !=null)
        sensorManager.unregisterListener(mSensorEventListener);
    }

    /**
     * 角度变更后显示到界面
     * @param rollAngle
     * @param pitchAngle
     * @param azimuth
     */
    private void onAngleChanged(float rollAngle, float pitchAngle, float azimuth){

        levelView.setAngle(rollAngle, pitchAngle);

    }

    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            DecimalFormat df = new DecimalFormat("#,###.#####");
            tv_x.setText(df.format(event.values[0]).toString());
            tv_y.setText(df.format(event.values[1]).toString());
            tv_z.setText(df.format(event.values[2]).toString());

            levelView.setPosition(event.values[0]*100, event.values[1]*100);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
