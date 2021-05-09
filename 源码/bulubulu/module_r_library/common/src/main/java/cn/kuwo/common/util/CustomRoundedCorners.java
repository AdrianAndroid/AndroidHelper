package cn.kuwo.common.util;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Synthetic;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class CustomRoundedCorners extends BitmapTransformation {
    private static final String ID = "cn.kuwo.common.util.CustomRoundedCorners";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);
    // See #738.
    private static final Set<String> MODELS_REQUIRING_BITMAP_LOCK =
            new HashSet<>(
                    Arrays.asList(
                            // Moto X gen 2
                            "XT1085",
                            "XT1092",
                            "XT1093",
                            "XT1094",
                            "XT1095",
                            "XT1096",
                            "XT1097",
                            "XT1098",
                            // Moto G gen 1
                            "XT1031",
                            "XT1028",
                            "XT937C",
                            "XT1032",
                            "XT1008",
                            "XT1033",
                            "XT1035",
                            "XT1034",
                            "XT939G",
                            "XT1039",
                            "XT1040",
                            "XT1042",
                            "XT1045",
                            // Moto G gen 2
                            "XT1063",
                            "XT1064",
                            "XT1068",
                            "XT1069",
                            "XT1072",
                            "XT1077",
                            "XT1078",
                            "XT1079"
                    )
            );
    private static final Lock BITMAP_DRAWABLE_LOCK =
            MODELS_REQUIRING_BITMAP_LOCK.contains(Build.MODEL)
                    ? new ReentrantLock() : new NoLock();

    private final int mLeftTopCorner, mLeftBottomCorner, mRightTopCorner, mRightBottomCorner;
    private RectF mRect;

    public CustomRoundedCorners(int leftTopCorner, int leftBottomCorner, int rightTopCorner
            , int rightBottomCorner) {
        mLeftTopCorner = leftTopCorner;
        mLeftBottomCorner = leftBottomCorner;
        mRightTopCorner = rightTopCorner;
        mRightBottomCorner = rightBottomCorner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomRoundedCorners that = (CustomRoundedCorners) o;

        if (mLeftTopCorner != that.mLeftTopCorner) return false;
        if (mLeftBottomCorner != that.mLeftBottomCorner) return false;
        if (mRightTopCorner != that.mRightTopCorner) return false;
        if (mRightBottomCorner != that.mRightBottomCorner) return false;
        return mRect != null ? mRect.equals(that.mRect) : that.mRect == null;
    }

    @Override
    public int hashCode() {
        int result = mLeftTopCorner;
        result = 31 * result + mLeftBottomCorner;
        result = 31 * result + mRightTopCorner;
        result = 31 * result + mRightBottomCorner;
        result = 31 * result + (mRect != null ? mRect.hashCode() : 0);
        return result;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return roundedCorners(pool, toTransform, mLeftTopCorner, mLeftBottomCorner, mRightTopCorner, mRightBottomCorner);
    }

    private Bitmap roundedCorners(BitmapPool pool, Bitmap inBitmap, int leftTopCorner, int leftBottomCorner, int rightTopCorner, int rightBottomCorner) {

        // Alpha is required for this transformation.
        Bitmap.Config safeConfig = getAlphaSafeConfig(inBitmap);
        Bitmap toTransform = getAlphaSafeBitmap(pool, inBitmap);
        Bitmap result = pool.get(toTransform.getWidth(), toTransform.getHeight(), safeConfig);

        result.setHasAlpha(true);

        BitmapShader shader = new BitmapShader(toTransform, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        mRect = new RectF(0, 0, result.getWidth(), result.getHeight());
        Path cornerPath = new Path();
        cornerPath.addRoundRect(mRect, new float[]{leftTopCorner, leftTopCorner,
                rightTopCorner, rightTopCorner,
                rightBottomCorner, rightBottomCorner,
                rightBottomCorner, leftBottomCorner}, Path.Direction.CCW);
        BITMAP_DRAWABLE_LOCK.lock();
        try {
            Canvas canvas = new Canvas(result);
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            canvas.drawPath(cornerPath, paint);
            clear(canvas);
        } finally {
            BITMAP_DRAWABLE_LOCK.unlock();
        }

        if (!toTransform.equals(inBitmap)) {
            pool.put(toTransform);
        }

        return result;
    }

    // Avoids warnings in M+.
    private static void clear(Canvas canvas) {
        canvas.setBitmap(null);
    }

    private static Bitmap getAlphaSafeBitmap(
            @NonNull BitmapPool pool, @NonNull Bitmap maybeAlphaSafe) {
        Bitmap.Config safeConfig = getAlphaSafeConfig(maybeAlphaSafe);
        if (safeConfig.equals(maybeAlphaSafe.getConfig())) {
            return maybeAlphaSafe;
        }

        Bitmap argbBitmap =
                pool.get(maybeAlphaSafe.getWidth(), maybeAlphaSafe.getHeight(), safeConfig);
        new Canvas(argbBitmap).drawBitmap(maybeAlphaSafe, 0 /*left*/, 0 /*top*/, null /*paint*/);

        // We now own this Bitmap. It's our responsibility to replace it in the pool outside this method
        // when we're finished with it.
        return argbBitmap;
    }

    private static Bitmap.Config getAlphaSafeConfig(@NonNull Bitmap inBitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Avoid short circuiting the sdk check.
            if (Bitmap.Config.RGBA_F16.equals(inBitmap.getConfig())) { // NOPMD
                return Bitmap.Config.RGBA_F16;
            }
        }

        return Bitmap.Config.ARGB_8888;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);

        byte[] radiusData = ByteBuffer.allocate(16).putInt(mLeftTopCorner).putInt(mLeftBottomCorner)
                .putInt(mRightTopCorner).putInt(mRightBottomCorner).array();
        messageDigest.update(radiusData);
    }


    private static final class NoLock implements Lock {

        @Synthetic
        NoLock() {
        }

        @Override
        public void lock() {
            // do nothing
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            // do nothing
        }

        @Override
        public boolean tryLock() {
            return true;
        }

        @Override
        public boolean tryLock(long time, @NonNull TimeUnit unit) throws InterruptedException {
            return true;
        }

        @Override
        public void unlock() {
            // do nothing
        }

        @NonNull
        @Override
        public Condition newCondition() {
            throw new UnsupportedOperationException("Should not be called");
        }
    }
}
