package com.ferdinandsilva.android;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.content.Intent;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

import DYNAMIC_IMPORT_OF_R;

public class OCRActivity extends Activity {

    private SurfaceView cameraView;
    private TextRecognizer textRecognizer;
    private CameraSource cameraSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocr_activity);

        cameraView = (SurfaceView)findViewById(R.id.camera_view);
        textRecognizer = new TextRecognizer.Builder(this).build();
        cameraSource = new CameraSource.Builder(this, textRecognizer).setRequestedPreviewSize(640, 480).build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {

                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }

        });

        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray<TextBlock> txtBlocks = detections.getDetectedItems();

                if(txtBlocks.size() != 0) {
                    showData(txtBlocks.valueAt(0).getValue());
                }
            }
        });
    }

    private void showData(final String dt) {
        runOnUiThread (new Thread(new Runnable() {
            public void run() {

                Intent output = new Intent();
                output.putExtra("text", dt);
                setResult(RESULT_OK, output);
                finish();

            }
        }));
    }

}
