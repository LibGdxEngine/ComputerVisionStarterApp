package com.example.computervisionwithandroidcourse;

import android.graphics.Bitmap;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ImageProcessing {

    public interface Listener{
        void onNewFrame(Bitmap newFrame);
        void onProcess(Bitmap processedBitmap);
    }
    private Listener listener;

    public ImageProcessing(Listener listener) {
        this.listener = listener;
    }

    public void process(Bitmap bitmap){
        //write image processing code here
        if(bitmap==null)
            return;

        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap, mat);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);
        Utils.matToBitmap(mat, bitmap);
        this.listener.onProcess(bitmap);
    }

}
