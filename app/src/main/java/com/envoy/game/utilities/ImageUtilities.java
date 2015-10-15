package com.envoy.game.utilities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;

import com.envoy.game.controller.Controller;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author David Castillo Fuentes <dcf82@hotmail.com>
 * Utility class to manage Bitmap processing
 */
public class ImageUtilities {

    public static Bitmap resizeBitmap(Intent uriImage, int dstWidth, int dstHeight) {
        Bitmap resizedBitmap = null;

        try {
            InputStream in = processImage(uriImage);

            // decode image size (decode metadata only, not the whole image)
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);

            in.close();
            in = null;

            in = processImage(uriImage);

            // save width and height
            int inWidth = options.outWidth;
            int inHeight = options.outHeight;

            // decode full image pre-resized
            //in = new FileInputStream(pathOfInputImage);
            options = new BitmapFactory.Options();

            // calc rought re-size (this is no exact resize)
            options.inSampleSize = Math.max(inWidth/dstWidth, inHeight/dstHeight);
            options.inJustDecodeBounds = false;

            // decode full image
            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

            in.close();
            in = null;

            Matrix m = new Matrix();
            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
            RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
            float[] values = new float[9];
            m.getValues(values);

            // resize bitmap
            resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]),
                    (int) (roughBitmap.getHeight() * values[4]), true);

            String imagePath = uriImage.getData().getPath();
            resizedBitmap = ExifUtil.rotateBitmap(imagePath, resizedBitmap);

        } catch (Exception e) {
            Log.e("Image", e.getMessage(), e);
        }
        return resizedBitmap;
    }

    public static InputStream processImage(Intent uriImage) {
        InputStream imageStream = null;
        try {
            imageStream = Controller.getApp().getContentResolver().openInputStream(uriImage.getData());
        } catch (FileNotFoundException fex) {
            fex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return imageStream;
    }

    public static byte[] getByteArray(Bitmap bitmap) {
        if (bitmap!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }

    public static Bitmap getBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}
