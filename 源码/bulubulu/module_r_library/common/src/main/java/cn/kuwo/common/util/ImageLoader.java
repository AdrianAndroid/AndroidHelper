package cn.kuwo.common.util;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

import cn.kuwo.common.R;
import cn.kuwo.common.app.App;
import cn.kuwo.common.utilscode.UtilsCode;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by shihc on 2016/11/3.
 */

public class ImageLoader {

    private static int CROSS_FADE_DURATION = 300;

    /**
     * 显示方形图像
     *
     * @param imageView 要显示的imageview
     * @param url       图片下载地址
     */
    public static void showImage(ImageView imageView, String url) {
        showImage(imageView, url, R.drawable.image_rounded_placeholder, null);
    }

    /**
     * 显示正方形图像,并可以设置占位图
     *
     * @param imageView     要显示的imageview
     * @param url           图片下载地址
     * @param placeholderId 占位图
     */
    public static void showImage(ImageView imageView, String url, int placeholderId) {
        showImage(imageView, url, placeholderId, null);
    }

    /**
     * 显示正方形图像,并可以设置占位图
     *
     * @param imageView     要显示的imageview
     * @param url           图片下载地址
     * @param placeholderId 占位图
     * @param width         图片宽度
     * @param height        图片高度
     */
    public static void showImage(ImageView imageView, String url, int placeholderId, int width, int height) {
        try {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .override(width, height)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontTransform()
                    .placeholder(placeholderId)
                    .error(null);
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示正方形图像,并可以设置占位图,失败图
     *
     * @param imageView     要显示的imageview
     * @param url           图片下载地址
     * @param placeholderId 占位图
     * @param errorDrawable 失败图
     */
    public static void showStaticImage(ImageView imageView, String url, int placeholderId, Drawable errorDrawable) {
        try {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(placeholderId)
                    .error(errorDrawable);
            Glide.with(imageView.getContext())
                    .asBitmap()
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示正方形图像,并可以设置占位图,失败图
     *
     * @param imageView     要显示的imageview
     * @param url           图片下载地址
     * @param placeholderId 占位图
     * @param errorDrawable 失败图
     */
    public static void showImage(ImageView imageView, String url, int placeholderId, Drawable errorDrawable) {
        try {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(placeholderId)
                    .error(errorDrawable);
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_DURATION)) //淡入
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static void showCenterInsideImage(ImageView imageView, String url) {
        try {
            RequestOptions options = new RequestOptions()
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(imageView.getContext()).load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static void showDefaultScaleTypeImage(ImageView imageView, String url) {
        try {
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(imageView.getContext()).load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static void showGif(GifImageView imageView, String url) {
        showGif(imageView, url, R.drawable.image_placeholder);
    }

    public static void showGif(GifImageView imageView, String url, int placeholderId) {
        imageView.setTag(R.id.image_url, url);
        CustomViewTarget viewTarget = new CustomViewTarget<GifImageView, Drawable>(imageView) {
            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {

            }

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (this.view.getTag(R.id.image_url) == null || url != this.view.getTag(R.id.image_url)) {
                    return;
                }
                try {
                    if (resource instanceof GifDrawable) {
                        pl.droidsonroids.gif.GifDrawable gifFromBytes;
                        gifFromBytes = new pl.droidsonroids.gif.GifDrawable(((GifDrawable) resource).getBuffer());
                        if (gifFromBytes != null) {
                            this.view.setImageDrawable(gifFromBytes);
                            gifFromBytes.start();
                        } else {
                            this.view.setImageDrawable(resource);
                        }
                    } else {
                        this.view.setImageDrawable(resource);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (OutOfMemoryError error) {
                    error.printStackTrace();
                }
            }

            @Override
            protected void onResourceCleared(@Nullable Drawable placeholder) {

            }

            @Override
            protected void onResourceLoading(@Nullable Drawable placeholder) {
                super.onResourceLoading(placeholder);
                imageView.setImageDrawable(placeholder);
            }
        };

        RequestOptions options = new RequestOptions()
                .placeholder(placeholderId)
                .centerCrop()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(imageView.getContext()).asDrawable()
                .load(url)
                .apply(options)
                .into(viewTarget);
    }

    public static void preload(String url) {
        try {
            RequestOptions options = new RequestOptions()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(App.getInstance())// 提前缓存
                    .load(url)
                    .apply(options)
                    .preload();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示圆角图像,并可以设置占位图,失败图
     *
     * @param imageView     要显示的imageview
     * @param url           图片下载地址
     * @param placeholderId 占位图
     */
    public static void showRoundedCornersImage(ImageView imageView, String url, int placeholderId) {
        showRoundedCornersImage(imageView, url, placeholderId, 8f);
    }

    /**
     * 显示圆角图像,并可以设置占位图,失败图
     *
     * @param imageView     要显示的imageview
     * @param url           图片下载地址
     * @param placeholderId 占位图
     */
    public static void showRoundedCornersImage(ImageView imageView, String url, int placeholderId, float roundedCorners) {
        if (placeholderId <= 0) {
            placeholderId = R.drawable.image_rounded_placeholder;
        }
        try {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(placeholderId)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transforms(new CenterCrop(), new RoundedCorners(UtilsCode.INSTANCE.dp2px(roundedCorners)));
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示自定义圆角位置图像,并可以设置占位图,失败图
     *
     * @param imageView     要显示的imageview
     * @param url           图片下载地址
     * @param placeholderId 占位图
     */
    public static void showRoundedCornersImage(ImageView imageView, String url, int placeholderId, float leftTopCorner, float leftBottomCorner,
                                               float rightTopCorner, float rightBottomCorner) {
        if (placeholderId <= 0) {
            placeholderId = R.drawable.image_rounded_placeholder;
        }
        try {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(placeholderId)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transforms(new CenterCrop(), new CustomRoundedCorners(UtilsCode.INSTANCE.dp2px(leftTopCorner),
                            UtilsCode.INSTANCE.dp2px(leftBottomCorner),
                            UtilsCode.INSTANCE.dp2px(rightTopCorner),
                            UtilsCode.INSTANCE.dp2px(rightBottomCorner)
                    ));
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }


    /**
     * 显示圆角图像,并可以设置占位图,失败图
     *
     * @param imageView 要显示的imageview
     * @param url       图片下载地址
     */
    public static void showRoundedCornersImage(ImageView imageView, String url) {
        showRoundedCornersImage(imageView, url, R.drawable.image_rounded_placeholder);
    }

    /**
     * 显示圆形图片
     *
     * @param url 带不带域名都可以
     */
    public static void showCircleImage(ImageView imageView, String url) {
        showCircleImage(imageView, url, 0);
    }

    /**
     * 显示圆形图片
     *
     * @param placeholder placeholder<=0,使用默认用户头像
     */
    public static void showCircleImage(ImageView imageView, String url, int placeholder) {
        if (imageView == null) {
            Log.e(ImageLoader.class.getSimpleName(), "imageView must not null");
            return;
        }
        if (App.DEBUG) L.m(ImageLoader.class, imageView.getContext().getClass().getCanonicalName());
        if (App.DEBUG) L.loopStackTrace(ImageLoader.class.getSimpleName());
        try {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(placeholder)
                    .circleCrop();
            Glide.with(imageView.getContext()) // 使用Fragment的生命周期
                    .load(url)
                    .apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_DURATION)) //淡入
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示圆形图片
     *
     * @param placeholder placeholder<=0,使用默认用户头像
     */
    public static void showCircleImage(ImageView imageView, Uri url, int placeholder) {
        if (imageView == null) {
            Log.e(ImageLoader.class.getSimpleName(), "imageView must not null");
            return;
        }
        if (App.DEBUG) L.m(ImageLoader.class, imageView.getContext().getClass().getCanonicalName());
        if (App.DEBUG) L.loopStackTrace(ImageLoader.class.getSimpleName());
        try {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(placeholder)
                    .circleCrop();
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_DURATION)) //淡入
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示圆形图片
     *
     * @param resourceId 资源id
     */
    public static void showCircleImage(ImageView imageView, int resourceId) {
        try {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .circleCrop();
            if (App.DEBUG)
                L.m(ImageLoader.class, imageView.getContext().getClass().getCanonicalName());
            if (App.DEBUG) L.loopStackTrace(ImageLoader.class.getSimpleName());
            Glide.with(imageView.getContext())
                    .load(resourceId)
                    .apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_DURATION)) //淡入
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static void loadUrlInBlur(ImageView imageView, String url, int placeholder) {
        loadUrlInBlur(imageView, url, placeholder, 5, 5);
    }

    /**
     * 高斯模糊后显示
     */
    public static void loadUrlInBlur(ImageView imageView, String url, int placeholder, int radius, int sample) {
        try {
            if (App.DEBUG)
                L.m(ImageLoader.class, imageView.getContext().getClass().getCanonicalName());
            if (App.DEBUG) L.loopStackTrace(ImageLoader.class.getSimpleName());
            RequestOptions options = new RequestOptions()
                    .placeholder(placeholder)
                    .disallowHardwareConfig()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .transforms(new MyBlurTransformation(radius, sample), new CenterCrop());
            Glide.with(imageView.getContext())
                    .asDrawable()
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception e) {
        } catch (OutOfMemoryError error) {
        }
    }

    /**
     * 高斯模糊后显示
     */
    public static void loadResInBlur(ImageView imageView, int resId) {
        try {
            Glide.with(imageView.getContext()).load(resId)
                    .apply(new RequestOptions().transform(new MyBlurTransformation(25, 10)))
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError error) {
        }
    }
}
