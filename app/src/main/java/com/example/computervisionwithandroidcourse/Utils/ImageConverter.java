package com.example.computervisionwithandroidcourse.Utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;

import androidx.camera.core.ImageProxy;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;


public class ImageConverter {

    public static Bitmap imageToBitmap(ImageProxy image) {
        @SuppressLint("UnsafeExperimentalUsageError") byte[] bytes = imageToByteArray(image.getImage());
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    private static byte[] imageToByteArray(Image image) {
        byte[] data = null;
        if (image.getFormat() == ImageFormat.JPEG) {
            Image.Plane[] planes = image.getPlanes();
            ByteBuffer buffer = planes[0].getBuffer();
            data = new byte[buffer.capacity()];
            buffer.get(data);
            return data;
        } else if (image.getFormat() == ImageFormat.YUV_420_888) {
            data = NV21toJPEG(
                    YUV_420_888toNV21(image),
                    image.getWidth(), image.getHeight());
        }
        return data;
    }

    private static byte[] YUV_420_888toNV21(Image image) {
        byte[] nv21;
        ByteBuffer yBuffer = image.getPlanes()[0].getBuffer();
        ByteBuffer vuBuffer = image.getPlanes()[2].getBuffer();

        int ySize = yBuffer.remaining();
        int vuSize = vuBuffer.remaining();

        nv21 = new byte[ySize + vuSize];

        yBuffer.get(nv21, 0, ySize);
        vuBuffer.get(nv21, ySize, vuSize);

        return nv21;
    }

    private static byte[] NV21toJPEG(byte[] nv21, int width, int height) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        YuvImage yuv = new YuvImage(nv21, ImageFormat.NV21, width, height, null);
        yuv.compressToJpeg(new Rect(0, 0, width, height), 50, out);
        return out.toByteArray();
    }
}
