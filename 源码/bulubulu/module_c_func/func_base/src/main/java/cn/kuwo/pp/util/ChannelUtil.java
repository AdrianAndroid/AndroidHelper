//package cn.kuwo.pp.util;
//
//import android.content.Context;
//import android.content.pm.ApplicationInfo;
//
//import com.mcxiaoke.packer.common.PackerCommon;
//
//import java.io.File;
//import java.io.IOException;
//
//import cn.kuwo.common.app.App;
//
///**
// * User: mcxiaoke
// * Date: 15/11/23
// * Time: 13:12
// */
//public final class ChannelUtil {
//    private static final String TAG = "PackerNg";
//    private static final String EMPTY_STRING = "";
//    private static String sCachedChannel = null;
//
//    public static String getChannel(final File file) {
//        if (sCachedChannel != null) {
//            return sCachedChannel;
//        }
//        try {
//            sCachedChannel = PackerCommon.readChannel(file);
//        } catch (Exception e) {
//            sCachedChannel = EMPTY_STRING;
//        }
//        if (sCachedChannel == null){
//            sCachedChannel = "kuwo";
//        }
//        return sCachedChannel;
//    }
//
//    public static String getChannel(final Context context) {
//        if (sCachedChannel != null){
//            return sCachedChannel;
//        }
//        try {
//            sCachedChannel = getChannelOrThrow(context);
//        } catch (Exception e) {
//            sCachedChannel = EMPTY_STRING;
//        }
//        if (sCachedChannel == null){
//            sCachedChannel = "kuwo";
//        }
//        return sCachedChannel;
//    }
//
//    public static synchronized String getChannelOrThrow(final Context context)
//            throws IOException {
//        return BuildConfig.CHANNEL;
////        final ApplicationInfo info = context.getApplicationInfo();
////        return PackerCommon.readChannel(new File(info.sourceDir));
//    }
//
//}
