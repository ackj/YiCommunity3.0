package cn.itsite.amain.s1.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by ilike on 15/10/8.
 */
public class ImageUtils {
    public static byte[] compressBitmapToArray(String imagePath,int reqWidth,int quality){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = decodeSampledBitmapFromResource(imagePath, reqWidth);
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality , baos);
        byte[] result = baos.toByteArray();
        bitmap.recycle();
        return result;
    }
    public static Bitmap decodeSampledBitmapFromResource(String imgPath, int reqWidth) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath,options);
        //Log.v("hehe","out Mime type : " + options.outMimeType);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        //options.outMimeType = thumbOutMimeType;
        //Log.v("hehe","Out Mime Type : " + options.outMimeType);
        return BitmapFactory.decodeFile(imgPath, options);
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth) {
        // Raw height and width of image
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (width > reqWidth) {

            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
//        return 1;
    }




    /** 保存bitmap为PNG **/
    public static boolean bitmap2Png(Bitmap bitmap , String path){
        FileOutputStream out = null;
        Bitmap newBitmap = null;
        boolean flag = true;
        try {
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            if(bitmap.getConfig()!=null){
                config = bitmap.getConfig();
            }
            newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            out = new FileOutputStream(path);

            newBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bitmap.recycle();
            assert newBitmap != null;
            newBitmap.recycle();
        }catch (Exception e){
            e.getStackTrace();
        }
        return flag;
    }

    public static Bitmap zoomImage(Bitmap bgimage) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = 256f / width;
        float scaleHeight = 256f / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
