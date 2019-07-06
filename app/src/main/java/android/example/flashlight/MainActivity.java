package android.example.flashlight;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
   Button btn1;
   ImageView iv;
   private static final int Camera_Code=101;
   boolean flashLightStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=findViewById(R.id.btn);
        iv=findViewById(R.id.select);
        final boolean hasCameraFlash=getPackageManager().hasSystemFeature(getPackageManager().FEATURE_CAMERA_FLASH);
        final boolean isEnable = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
//        btn1.setEnabled(!isEnable);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEnable)
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},Camera_Code);
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (hasCameraFlash) {
                        if (flashLightStatus) {
                            flashLightOff();
                        } else {
                            flashLightOn();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No Flash Light Available", Toast.LENGTH_SHORT).show();
                    }
            }
        });


    }
    private void flashLightOff(){
        CameraManager cm=(CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try{
            String cameraId=cm.getCameraIdList()[0];
            cm.setTorchMode(cameraId,false);
            flashLightStatus=false;
            iv.setImageResource(R.drawable.off1);
        }
        catch(CameraAccessException e){}
    }
    private void flashLightOn(){
        CameraManager cm=(CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try{
            String cameraId=cm.getCameraIdList()[0];
            cm.setTorchMode(cameraId,true);
            flashLightStatus=true;
            iv.setImageResource(R.drawable.on1);
        }
        catch(CameraAccessException e){}

    }

}
