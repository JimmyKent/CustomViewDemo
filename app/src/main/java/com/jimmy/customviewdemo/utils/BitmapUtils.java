package com.jimmy.customviewdemo.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.jimmy.customviewdemo.App;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ScrollView;

public class BitmapUtils {
    private static final String TAG = BitmapUtils.class.getSimpleName();

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 从Uri中读取出制定大小的bitmap
     *
     * @param context
     * @param uri
     * @param width
     * @param height
     * @return Bitmap
     * @Author Spartacus26
     */
    public static Bitmap decodeUriAsBitmap(Context context, Uri uri, int width, int height) {
        Bitmap bitmap = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, width, height);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        Log.d(TAG, "decodeUriAsBitmap bitmap:" + bitmap.getByteCount() / 1024 + "K");
        return bitmap;
    }

    /**
     * 从文件重读取出指定大小的bitmap
     *
     * @param filePath
     * @param width
     * @param height
     * @return Bitmap
     * @Author Spartacus26
     */
    public static Bitmap decodeFileAsBitmap(String filePath, int width, int height) {
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        Bitmap bitmap = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(filePath, options);
        //Bl.d(TAG, "decodeFileAsBitmap bitmap:" + bitmap.getByteCount() / 1024 + "K");
        return bitmap;
    }

    /**
     * 生成与原图同样大小的Bitmap，不作压缩
     *
     * @param path
     * @return Bitmap
     * @Author Spartacus26
     */
    public static Bitmap decodeFileAsBitmap(String path) {
        if (path == null) {
            return null;
        }
        Bitmap bitmap = null;

        try {
            bitmap = BitmapFactory.decodeFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap getImgFromAssets(Context context, String path) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(path);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 压缩bitmap之后，转byte[]
     *
     * @param bitmap
     * @param width
     * @param height
     * @param maxSize
     * @return byte[]
     * @Author Spartacus26
     */
    public static byte[] getByteFromCompressBitmap(Bitmap bitmap, int width, int height, int maxSize) {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (null == bitmap || bitmap.isRecycled()) {
            return bytes;
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // Compress by loop
        int options = 100;
        while (baos.toByteArray().length / 1024 > maxSize) {
            Log.d(TAG, "bytes.length:" + baos.toByteArray().length / 1024 + "K");
            // Clean up os
            baos.reset();
            // interval 5
            options -= 10;
            if (options <= 0) {
                break;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        Log.d(TAG, "compress finish bytes.length:" + baos.toByteArray().length / 1024 + "K");
        bytes = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 不压缩的将bitmap转byte[]
     *
     * @param bitmap
     * @return byte[]
     * @Author Spartacus26
     */
    public static byte[] getByteFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 算二次采样的大小
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 把view“拍照”成bitmap
     *
     * @param view
     * @return Bitmap
     * @Author Spartacus26
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }

    /**
     * 截取scrollview的屏幕
     *
     * @param scrollView
     * @return
     */
    public static Bitmap convertScrollViewToBitmap(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 缩放bitmap至合适屏幕：宽图和屏幕等宽，长图和屏幕等高
     *
     * @param bitmap
     * @param cWidth
     * @param cHeight
     * @return Bitmap
     * @Author Spartacus26
     */
    public static Bitmap scaleBitmapToMatchScreen(Bitmap bitmap, int cWidth, int cHeight) {
        Bitmap b = null;
        int bWidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();
        float wScale = (float) bWidth / cWidth;
        float hScale = (float) bHeight / cHeight;
        Matrix matrix = new Matrix();
        if (wScale < hScale) {//高图
            //长和宽等比缩放
            matrix.postScale(1 / hScale, 1 / hScale);
        } else {//宽图
            matrix.postScale(1 / wScale, 1 / wScale);
        }
        b = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return b;
    }
}
