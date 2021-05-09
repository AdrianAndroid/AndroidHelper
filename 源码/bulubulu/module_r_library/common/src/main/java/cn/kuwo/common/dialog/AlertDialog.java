//package cn.kuwo.common.dialog;
//
//import android.app.Activity;
//import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.TextView;
//
//import com.afollestad.materialdialogs.MaterialDialog;
//
//import cn.kuwo.common.R;
//import cn.kuwo.common.utilscode.UtilsCode;
//
//
///**
// * Created by shihc on 2016/11/14.
// * 两个按钮的确认对话框
// */
//
//public class AlertDialog extends MaterialDialog {
//
//    protected AlertDialog(Builder builder) {
//        super(builder);
//    }
//
//    /**
//     * 获得确认对话框实例，不是单例！
//     *
//     * @param activity 上下文，dialog显示需要依附在activity
//     * @return
//     */
//    public static MaterialDialog getInstance(Activity activity, String title, String content, final SingleButtonCallback positiveCallback, final SingleButtonCallback negativeCallback) {
//        return getInstance(activity, title, content, "确定", "取消", positiveCallback, negativeCallback);
//    }
//
//    /**
//     * 获得确认对话框实例，不是单例！
//     *
//     * @param activity 上下文，dialog显示需要依附在activity
//     * @return
//     */
//    public static MaterialDialog getInstance(Activity activity, String title, String content, final SingleButtonCallback positiveCallback) {
//        return getInstance(activity, title, content, positiveCallback, null);
//    }
//
//    /**
//     * 获得确认对话框实例，不是单例！
//     *
//     * @param activity 上下文，dialog显示需要依附在activity
//     * @return
//     */
//    public static MaterialDialog getInstance(Activity activity, String title, String content, String positiveText, String negativeText, final SingleButtonCallback positiveCallback) {
//        return getInstance(activity, title, content, positiveText, negativeText, positiveCallback, null);
//    }
//
//    /**
//     * 获得确认对话框实例，不是单例！
//     *
//     * @param activity 上下文，dialog显示需要依附在activity
//     * @return
//     */
//    public static MaterialDialog getInstance(Activity activity, String title, String content, String positiveText, String negativeText, SingleButtonCallback positiveCallback, SingleButtonCallback negativeCallback) {
//        if (activity == null) {
//            return null;
//        }
//        int color = activity.getResources().getColor(R.color.colorTitleColor);
//        Builder builder = new Builder(activity)
//                .backgroundColor(Color.WHITE)
//                .content(content)
//                .positiveText(positiveText)
//                .negativeText(negativeText)
//                .positiveColor(color)
//                .negativeColor(color)
//                .contentColor(color)
//                .onPositive(positiveCallback != null ? positiveCallback : (SingleButtonCallback) (dialog, which) -> {
//                })
//                .onNegative(negativeCallback != null ? negativeCallback : (SingleButtonCallback) (dialog, which) -> {
//                });
//        if (!UtilsCode.INSTANCE.isEmpty(title)) {
//            builder.title(title);
//        }
//        return new AlertDialog(builder);
//    }
//
//    @Override
//    public void onDetachedFromWindow() {
//        if (getBuilder() != null) {
//            getBuilder().onPositive(null).onNegative(null);
//        }
//        super.onDetachedFromWindow();
//    }
//
//    /**
//     * 获得确认对话框实例，不是单例！
//     *
//     * @param activity 上下文，dialog显示需要依附在activity
//     * @return
//     */
//    public static MaterialDialog getSingleBtnInstance(Activity activity, String content, String btnTitle, final View.OnClickListener positiveCallback) {
//        View view = LayoutInflater.from(activity).inflate(R.layout.single_button_dialog, null);
//        TextView contentView = ((TextView) view.findViewById(R.id.dlg_content));
//        contentView.setText(content);
//        contentView.setTextColor(activity.getResources().getColor(R.color.colorAlertDialogMain));
//        TextView tvButton = (TextView) view.findViewById(R.id.dlg_positive);
//        tvButton.setText(btnTitle);
//        tvButton.setTextColor(activity.getResources().getColor(R.color.colorAlertDialogMain));
//        tvButton.setOnClickListener(positiveCallback);
//        Builder builder = new Builder(activity)
//                .backgroundColorRes(R.color.dialog_bg)
//                .customView(view, false);
//
//        return new AlertDialog(builder);
//    }
//
//    /**
//     * 获得隐私对话框实例，不是单例！
//     *
//     * @param activity 上下文，dialog显示需要依附在activity
//     * @return
//     */
//    public static MaterialDialog getPrivacyBtnInstance(Activity activity, final View.OnClickListener positiveCallback, final View.OnClickListener negativeCallback) {
//        View view = LayoutInflater.from(activity).inflate(R.layout.privacy_button_dialog, null);
//        TextView positiveButton = (TextView) view.findViewById(R.id.dlg_positive);
//        positiveButton.setOnClickListener(positiveCallback);
//        TextView negativeButton = (TextView) view.findViewById(R.id.dlg_negative);
//        negativeButton.setOnClickListener(negativeCallback);
//        Builder builder = new Builder(activity)
//                .backgroundColorRes(R.color.dialog_bg)
//                .customView(view, false)
//                .cancelable(false);
//
//        return new AlertDialog(builder);
//    }
//
//    public static MaterialDialog getSingleBtnInstanceWhite(Activity activity, String content, String btnTitle, final View.OnClickListener positiveCallback) {
//        View view = LayoutInflater.from(activity).inflate(R.layout.single_button_dialog_white, null);
//        TextView contentView = ((TextView) view.findViewById(R.id.dlg_content));
//        contentView.setText(content);
//        TextView tvButton = (TextView) view.findViewById(R.id.dlg_positive);
//        tvButton.setText(btnTitle);
//        tvButton.setOnClickListener(positiveCallback);
//        Builder builder = new Builder(activity)
//                .backgroundColorRes(R.color.dialog_bg)
//                .customView(view, false)
//                .cancelable(false);
//
//        return new AlertDialog(builder);
//    }
//}
