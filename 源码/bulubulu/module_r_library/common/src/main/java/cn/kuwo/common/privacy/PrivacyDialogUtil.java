package cn.kuwo.common.privacy;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.tencent.mmkv.MMKV;

import cn.kuwo.common.R;
import cn.kuwo.common.dialog.DialogUtils;
import cn.kuwo.common.util.SP;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class PrivacyDialogUtil {
    //    private static MaterialDialog mDialog;
//    private static MaterialDialog mConfirmDialog;
    private static String IS_SHOW_PRIVACY = "IS_SHOW_PRIVACY";


//    public static void showDialog(Activity activity, View.OnClickListener protocolListener, View.OnClickListener privacyListener) {
//        boolean showed = MMKV.defaultMMKV().decodeBool(IS_SHOW_PRIVACY, false);
//        if (showed) {
//            return;
//        }
//
//        mDialog = AlertDialog.getPrivacyBtnInstance(activity, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean showDialog = true;
//                MMKV.defaultMMKV().encode(IS_SHOW_PRIVACY, showDialog);
//
//                mDialog.dismiss();
//            }
//        }, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//
//                mConfirmDialog = AlertDialog.getSingleBtnInstanceWhite(activity, "您需同意本提示方可使用本软件", "我知道了", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mConfirmDialog.dismiss();
//                        showDialog(activity, protocolListener, privacyListener);
//                    }
//                });
//                mConfirmDialog.show();
//            }
//        });
//
//        TextView textView = (TextView) mDialog.findViewById(R.id.dlg_content);
//        textView.setText("为了更好的保障您的个人权益，在您使用我们的产品前，请务必审慎阅读并充分理解");
//
//        SpannableString userString1 = new SpannableString("《用户协议》");
//        userString1.setSpan(new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                if (protocolListener != null) {
//                    protocolListener.onClick(widget);
//                }
//            }
//
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//                ds.setColor(Color.parseColor("#417FB3"));
//            }
//        }, 0, userString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        textView.append(userString1);
//
//        textView.append("和");
//
//        SpannableString privacyString1 = new SpannableString("《隐私政策》");
//        privacyString1.setSpan(new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                if (privacyListener != null) {
//                    privacyListener.onClick(widget);
//                }
//            }
//
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//                ds.setColor(Color.parseColor("#417FB3"));
//            }
//        }, 0, privacyString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        textView.append(privacyString1);
//
//        textView.append("的各条款，包括但不限于：\n" +
//                "1、为了向您提供注册登录、聊天、推荐用户、发表动态等基本功能，我们会收集、使用必要的信息；\n" +
//                "2、基于您的授权，我们可能会根据不同的功能获取您的不同权限，涉及位置、存储、相册等权限，您有权拒绝或取消授权；\n" +
//                "3、您可以随时在设置中访问、更正您的个人信息。\n" +
//                "您可以通过阅读完整版");
//
//        SpannableString userString2 = new SpannableString("《用户协议》");
//        userString2.setSpan(new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                if (protocolListener != null) {
//                    protocolListener.onClick(widget);
//                }
//            }
//
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//                ds.setColor(Color.parseColor("#417FB3"));
//            }
//        }, 0, userString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        textView.append(userString2);
//
//        textView.append("和");
//
//        SpannableString privacyString2 = new SpannableString("《隐私政策》");
//        privacyString2.setSpan(new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                if (privacyListener != null) {
//                    privacyListener.onClick(widget);
//                }
//            }
//
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//                ds.setColor(Color.parseColor("#417FB3"));
//            }
//        }, 0, privacyString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        textView.append(privacyString2);
//
//        textView.append("了解详细信息，如您同意，请点击“我知道了”开始我们的服务");
//
//        textView.setMovementMethod(LinkMovementMethod.getInstance());
//
//        mDialog.show();
//    }

    // 是接受隐私协议
    public static boolean acceptPrivacy() {
        return SP.getMMKV().decodeBool(IS_SHOW_PRIVACY, false);
    }

    public static void showPrivacy(View stub, View.OnClickListener protocolListener, View.OnClickListener privacyListener) {
        final MMKV mmkv = SP.getMMKV(); // 只需要一个实例就好了


        boolean showed = mmkv.decodeBool(IS_SHOW_PRIVACY, false);
        if (showed && stub instanceof ViewStub) {
            return;
        }


        View privacy_button_dialog = ((ViewStub) stub).inflate();
        // 不同意按钮 dlg_negative
        View dlg_negative = privacy_button_dialog.findViewById(R.id.dlg_negative);
        // 同意按钮 dlg_positive
        View dlg_positive = privacy_button_dialog.findViewById(R.id.dlg_positive);


        privacy_button_dialog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true; // 消费掉触摸事件
            }
        });

        // 按钮的点击事件
        dlg_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mmkv.encode(IS_SHOW_PRIVACY, true);
                stub.setVisibility(View.GONE);
            }
        });
        dlg_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stub.setVisibility(View.GONE);

//                mConfirmDialog = AlertDialog.getSingleBtnInstanceWhite(
//                        UtilsCode.INSTANCE.getTopActivity()
//                        , "您需同意本提示方可使用本软件"
//                        , "我知道了"
//                        , new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if (mConfirmDialog != null) {
//                                    mConfirmDialog.dismiss();
//                                    mConfirmDialog = null;
//                                }
//                                //showDialog(activity, protocolListener, privacyListener);
//                                stub.setVisibility(View.VISIBLE);
//                            }
//                        });
//                mConfirmDialog.show();

                DialogUtils.INSTANCE.showPrivicyDialog(new Function1<Boolean, Unit>() {
                    @Override
                    public Unit invoke(Boolean aBoolean) {
                        stub.setVisibility(View.VISIBLE);
                        return null;
                    }
                }, null);
            }
        });


//        mDialog = AlertDialog.getPrivacyBtnInstance(activity, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean showDialog = true;
//                mmkv.encode(IS_SHOW_PRIVACY, showDialog);
//
//                mDialog.dismiss();
//            }
//        }, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//
//                mConfirmDialog = AlertDialog.getSingleBtnInstanceWhite(activity, "您需同意本提示方可使用本软件", "我知道了", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mConfirmDialog.dismiss();
//                        showDialog(activity, protocolListener, privacyListener);
//                    }
//                });
//                mConfirmDialog.show();
//            }
//        });

        TextView textView = (TextView) privacy_button_dialog.findViewById(R.id.dlg_content);
        textView.setText("为了更好的保障您的个人权益，在您使用我们的产品前，请务必审慎阅读并充分理解");

        SpannableString userString1 = new SpannableString("《用户协议》");
        userString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (protocolListener != null) {
                    protocolListener.onClick(widget);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.parseColor("#417FB3"));
            }
        }, 0, userString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(userString1);

        textView.append("和");

        SpannableString privacyString1 = new SpannableString("《隐私政策》");
        privacyString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (privacyListener != null) {
                    privacyListener.onClick(widget);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.parseColor("#417FB3"));
            }
        }, 0, privacyString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(privacyString1);

        textView.append("的各条款，包括但不限于：\n" +
                "1、为了向您提供注册登录、聊天、推荐用户、发表动态等基本功能，我们会收集、使用必要的信息；\n" +
                "2、基于您的授权，我们可能会根据不同的功能获取您的不同权限，涉及位置、存储、相册等权限，您有权拒绝或取消授权；\n" +
                "3、您可以随时在设置中访问、更正您的个人信息。\n" +
                "您可以通过阅读完整版");

        SpannableString userString2 = new SpannableString("《用户协议》");
        userString2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (protocolListener != null) {
                    protocolListener.onClick(widget);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.parseColor("#417FB3"));
            }
        }, 0, userString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(userString2);

        textView.append("和");

        SpannableString privacyString2 = new SpannableString("《隐私政策》");
        privacyString2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (privacyListener != null) {
                    privacyListener.onClick(widget);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.parseColor("#417FB3"));
            }
        }, 0, privacyString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(privacyString2);

        textView.append("了解详细信息，如您同意，请点击“我知道了”开始我们的服务");

        textView.setMovementMethod(LinkMovementMethod.getInstance());

//        mDialog.show();
    }
}
