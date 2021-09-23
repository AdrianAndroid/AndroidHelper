# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep class com.lsjwzh.widget.recyclerviewpager.**
-dontwarn com.lsjwzh.widget.recyclerviewpager.**

-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}
-keep class com.tencent.stat.* {*;}
-keep class com.tencent.mid.* {*;}
-keep class com.tencent.** {*;}

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep public class android.content.FileProvider {*;}

#eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}

# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-assumenosideeffects class com.blankj.utilcode.util.LogUtils{
    public static *** v(...);
    public static *** a(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** log(...);
}

# Gson
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

-keep class cn.kuwo.pp.event.** { *; }
-dontwarn cn.kuwo.pp.event.**

-keep class cn.kuwo.pp.http.bean.** { *; }
-dontwarn cn.kuwo.pp.http.bean.**

-keep class cn.kuwo.common.event.** { *; }
-dontwarn cn.kuwo.common.event.**

#ijk播放器
-keep class org.ijkplayer.** { *; }
-keep class cn.kuwo.service.remote.kwplayer.**{*;}
-dontwarn org.ijkplayer.**

-keep class cn.kuwo.player.bean.** { *; }
-dontwarn cn.kuwo.player.bean.**

-keep class com.elbbbird.android.socialsdk.otto.** { *; }
-dontwarn com.elbbbird.android.socialsdk.otto.**

-keep class cn.kuwo.service.Quality{*;}
# For retrolambda
-dontwarn java.lang.invoke.*

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
#greendao3.2.0,此是针对3.2.0，如果是之前的，可能需要更换下包名
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static java.lang.String TABLENAME;
    public static void dropTable(org.greenrobot.greendao.database.Database, boolean);
    public static void createTable(org.greenrobot.greendao.database.Database, boolean);
}
-keep class **$Properties

-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.infer.**

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.**{*;}

-keep class com.blankj.utilcode.** { *; }
-keepclassmembers class com.blankj.utilcode.** { *; }
-dontwarn com.blankj.utilcode.**

-keepclassmembers class cc.ninty.chat.ui.fragment.ScriptFragment$JavaScriptInterface {
public *;
}
-keepclassmembers class cn.kuwo.service.DownloadProxy.Quality {*;}
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class cn.kuwo.pp.R$*{
public static final int *;
}

-keep public class cn.kuwo.service.remote.downloader.DownloadCore {
  public *;
}
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class cn.kuwo.boom.push.MiPushMessageReceiver {*;}
-keep class cn.kuwo.boom.push.PushMessageBean {*;}

-keep class org.devio.takephoto.** { *; }
-dontwarn org.devio.takephoto.**

-keep class com.darsh.multipleimageselect.** { *; }
-dontwarn com.darsh.multipleimageselect.**

-keep class com.soundcloud.android.crop.** { *; }
-dontwarn com.soundcloud.android.crop.**

-keep class me.jessyan.progressmanager.** { *; }
-keep interface me.jessyan.progressmanager.** { *; }

-keep class com.sina.weibo.sdk.** { *; }

-keep public class cn.kuwo.bulubulu.wxapi.WXEntryActivity
-keep public class cn.kuwo.bulubulu.wxapi.WXPayEntryActivity
#视频播放器
-keep class cn.jzvd.** { *; }
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
}
#七牛云存储相关
-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}
-ignorewarnings
#华为推送
-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}
-keep class com.huawei.android.hms.agent.**{*;}
# 请将 com.tencent.qcloud.uipojo.thirdpush.HUAWEIPushReceiver 改成您 App 中定义的完整类名
-keep class cn.kuwo.pp.thirdpush.HUAWEIPushReceiver {*;}
# 请将 com.tencent.qcloud.uipojo.thirdpus.VIVOPushMessageReceiverImpl 改成您 App 中定义的完整类名
# vivo 推送
-dontwarn com.vivo.push.**
-keep class com.vivo.push.**{*; }
-keep class com.vivo.vms.**{*; }
-keep class cn.kuwo.pp.thirdpush.VIVOPushMessageReceiverImpl{*;}
# 请将 com.tencent.qcloud.uipojo.thirdpush.XiaomiMsgReceiver 改成您 App 中定义的完整类名
-keep class cn.kuwo.pp.thirdpush.XiaomiMsgReceiver {*;}
# 如果编译使用的 Android 版本是23，添加这个可以防止一个误报的 warning 导致无法成功编译
-dontwarn com.xiaomi.push.**
# 请将 com.tencent.qcloud.uipojo.thirdpush.MEIZUPushReceiver 改成您 App 中定义的完整类名
-keep class cn.kuwo.pp.thirdpush.MEIZUPushReceiver {*;}


-keep class cn.kuwo.pp.manager.FriendList.FriendListItem {*;}
-keep class cn.kuwo.pp.manager.FriendList.FriendListManager.** {*;}
