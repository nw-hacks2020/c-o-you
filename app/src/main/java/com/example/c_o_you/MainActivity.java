package com.example.c_o_you;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    public static final int REQUEST_CODE = 1234;
    public static final int CAMERA_REQUEST_CODE = 9090;

    @BindView(R.id.take_photo)
    ImageButton mTakePhoto;

    @BindView(R.id.iconlayout)
    View foodIconlayout;

    @BindView(R.id.preview)
    ImageView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        startBgAnimation();
    }

    private void startBgAnimation() {
        foodIconlayout.setRotation(315f);
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                                                                      R.animator.foodiconscroll);
        set.setTarget(foodIconlayout);
        set.start();
    }


    @OnClick(R.id.take_photo)
    void onTakePhoto() {
        if (ContextCompat.checkSelfPermission(this,
                                              Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
        } else {
            Log.i(TAG, "onTakePhoto: have perm");
            // We have the permission
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionsResult: " + requestCode);
        Log.i(TAG, "onRequestPermissionsResult: " + Arrays.toString(grantResults));
        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onTakePhoto();
        } else {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            assert extras != null;
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Toast.makeText(this, "got image", Toast.LENGTH_SHORT).show();

            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap rotatedBitmap = Bitmap.createBitmap(imageBitmap,
                                                       0,
                                                       0,
                                                       imageBitmap.getWidth(),
                                                       imageBitmap.getHeight(),
                                                       matrix,
                                                       true);

            preview.setVisibility(View.VISIBLE);
            preview.setImageBitmap(rotatedBitmap);
            // Do the OCR
            FirebaseVisionImage firebaseImage = FirebaseVisionImage.fromBitmap(rotatedBitmap);
            FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                                                                  .getOnDeviceTextRecognizer();
            detector.processImage(firebaseImage).addOnSuccessListener(firebaseVisionText -> {
                Toast.makeText(MainActivity.this, "OCR done", Toast.LENGTH_SHORT).show();
                String resulttext = firebaseVisionText.getText();
                Log.i(TAG, "onActivityResult: " + firebaseVisionText.getText());
//                Paint paint = new Paint();
//                paint.setStyle(Paint.Style.STROKE);
//                paint.setFilterBitmap(true);
//                paint.setDither(true);
//                paint.setStrokeWidth(20f);
//                paint.setColor(0x1b03a3);
//
//                Canvas c = new Canvas(rotatedBitmap);
//                for (FirebaseVisionText.TextBlock textblock : firebaseVisionText.getTextBlocks()) {
//                    Log.i(TAG, "onActivityResult: drawing rect");
//                    c.drawRect(textblock.getBoundingBox(), paint);
//                }
//                preview.draw(c);
            });
        }
    }
}
