 package com.example.computervisionwithandroidcourse.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.example.computervisionwithandroidcourse.R;
import com.example.computervisionwithandroidcourse.UseCases.MarkerDetector.MarkerDetector;
import com.example.computervisionwithandroidcourse.UseCases.MarkerDetector.MarkerDetectorFactory;
import com.example.computervisionwithandroidcourse.UseCases.MarkerDetector.MarkerDetectorType;
import com.example.computervisionwithandroidcourse.Utils.ImageConverter;
import com.google.common.util.concurrent.ListenableFuture;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Point;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {
    //Background Thread
    private ExecutorService executor;
    //provide us with available device cameras
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    //width & height of current mobile device
    private int screenWidth , screenHeight;
    //the preview of cameraX frames
    private PreviewView previewView;

    //Initialize OpenCV loader before app is launched
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    //initialize all classes that use OpenCV here to ensure OpenCV is loaded before using this classes
                    Log.i("OpenCV", "OpenCV loaded successfully");
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            //TODO: check OpenCV version in here
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        previewView = findViewById(R.id.previewView);
        //choose single thread executor for simple image processing task
        executor = Executors.newSingleThreadExecutor();
        //provide us with available device cameras
        cameraProviderFuture = ProcessCameraProvider.getInstance(getApplicationContext());
        //if couldn't find a camera it will throw an error
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                ImageAnalysis cameraAnalysis = getCameraAnalysis();
                Preview cameraPreview = getCameraPreview();
                // Attach the viewfinder's surface provider to preview use case
                cameraPreview.setSurfaceProvider(previewView.getSurfaceProvider());
                cameraProvider.unbindAll();
                //camera is ready to use now and can start preview
                cameraProvider.bindToLifecycle((LifecycleOwner) this
                        , getCameraSelector()
                        , cameraAnalysis
                        , cameraPreview);
                // The analyzer can then be assigned to the instance
                cameraAnalysis.setAnalyzer(executor, image -> {
                    int rotationDegrees = image.getImageInfo().getRotationDegrees();
                    // insert your image processing code here.
                    //covert YUV image to bitmap image
                    Bitmap nextFrame = ImageConverter.imageToBitmap(image);
                    analyseImage(nextFrame);
                    System.out.println("ssss");
                    //we must close imageProxy after using it to avoid memory leak and app crashes
                    image.close();
                });
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));

    }

    /** Do computer vision stuff here **/
    private void analyseImage(Bitmap nextFrame) {
        //get Analyser type -> here we get analyser for colored marker
        MarkerDetector markerDetector = MarkerDetectorFactory.getImageAnalyser(MarkerDetectorType.DETECTOR_FOR_COLORED_MARKER);
        List<Point> points = markerDetector.analyseImageAndGetMarkersPoints(nextFrame);
        if(!points.isEmpty()){
            Point markerPoint = points.get(0);
            assert markerPoint != null;
            //use this point to draw whatever you want here using Drawer class
            Log.i("MAIN" , "x : " + markerPoint.x + " : y : " + markerPoint.y);
        }
    }

    public CameraSelector getCameraSelector() {
        //choose front or back camera
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        return cameraSelector;
    }

    public ImageAnalysis getCameraAnalysis() {
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetAspectRatio(getAspectRatio(screenWidth, screenHeight))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        return imageAnalysis;
    }

    public Preview getCameraPreview() {
        Preview preview =  new Preview.Builder()
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        return preview;
    }

    //get aspect ratio of device according to its width & height
    private int getAspectRatio(int width , int height){
        double aspectRatio = ((double) Math.max(width , height)) / Math.min(width , height);
        if ( (Math.abs(aspectRatio - AspectRatio.RATIO_4_3 )) <= (Math.abs(aspectRatio - AspectRatio.RATIO_16_9)) ){
            return AspectRatio.RATIO_4_3;
        }
        return AspectRatio.RATIO_16_9;
    }
}