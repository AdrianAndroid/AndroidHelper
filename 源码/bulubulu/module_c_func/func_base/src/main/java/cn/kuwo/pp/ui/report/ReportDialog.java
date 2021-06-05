package cn.kuwo.pp.ui.report;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.kuwo.common.dialog.BaseOptionDialog;
import cn.kuwo.common.dialog.DialogUtils;
import cn.kuwo.common.dialog.OptionItem;
import cn.kuwo.common.event.ReportResultEvent;
import cn.kuwo.common.utilscode.UtilsCode;
import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.pp.R;
import cn.kuwo.pp.http.CustomObserver;
import cn.kuwo.pp.http.RetrofitClient;
import cn.kuwo.pp.manager.UserInfoManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ReportDialog extends BaseOptionDialog {
    public static final String TYPE_REPORT_VOICE = "1";
    public static final String TYPE_REPORT_USER = "2";


    private String mDigest;
    private String mTargetItemId;
    private String mChatList = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setShowTitle(true);
        mDigest = getArguments().getString("mDigest");
        mTargetItemId = getArguments().getString("mTargetItemId");
        ArrayList<OptionItem> items = new ArrayList<>();
        items.add(new OptionItem(getString(R.string.report_res_one)));
        items.add(new OptionItem(getString(R.string.report_res_two)));
        items.add(new OptionItem(getString(R.string.report_res_three)));
        items.add(new OptionItem(getString(R.string.report_res_other)));
        mItems = items;
    }

    public static ReportDialog newInstance(String mDigest, String mTargetItemId) {
        Bundle args = new Bundle();
        args.putString("mDigest", mDigest);
        args.putString("mTargetItemId", mTargetItemId);
        ReportDialog fragment = new ReportDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public ReportDialog setChatList(String chatList) {
        this.mChatList = chatList;
        return this;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setShowCancel(true);
        setTitle("打小报告，我觉得这条内容不太合适");
        setOnOptionClickListener((view, position) -> {
            if (position == 3) {
                fillReasonDialog();
            } else {
                innerSendReason(String.valueOf(position + 1), mItems.get(position).getTitle());
            }
            return null;
        });
    }

    private void fillReasonDialog() {
        DialogUtils.INSTANCE.showReportDialog(new Function1<CharSequence, Unit>() {
            @Override
            public Unit invoke(CharSequence charSequence) {
                innerSendReason("4", charSequence.toString());
                return null;
            }
        }, this);

//        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
//                .backgroundColorRes(R.color.colorWindowBackground)
//                .dividerColorRes(R.color.colorHighlight)
//                .positiveColorRes(R.color.colorHighlight)
//                .negativeColorRes(R.color.colorHighlight)
//                .inputRange(4, 500)
//                .titleColorRes(R.color.colorHighlight)
//                .title(R.string.input)
//                .positiveText(R.string.input_report_send)
//                .negativeText(R.string.cancel_title)
//                .inputType(InputType.TYPE_CLASS_TEXT)
//                .input(R.string.input_hint, 0, false, (dlg, input) -> {
//                    innerSendReason("4", input.toString());
//                }).
//                        build();
//        dialog.getInputEditText().setHintTextColor(Color.GRAY);
//        dialog.getInputEditText().setTextColor(getResources().getColor(R.color.colorSubTitleColor));
//        dialog.show();
    }

    private void innerSendReason(String digest, String reason) {
        if (TYPE_REPORT_USER.equals(mDigest)) {
            Map<String, RequestBody> postParams = buildParams(digest, reason);
            RetrofitClient.getInstance().execute(
                    RetrofitClient.getApiService().postReportChat(postParams),
                    new CustomObserver() {
                        @Override
                        public void onNext(Object o) {
                            UtilsCode.INSTANCE.showShort("感谢你的举报！我们会尽快审核处理！");
                            EventBus.getDefault().post(ReportResultEvent.buildReportEvent(mTargetItemId, true));
                        }

                        @Override
                        public void _onError(ApiException e) {
                            UtilsCode.INSTANCE.showShort(e.getMessage());
                        }
                    }
            );
        } else if (TYPE_REPORT_VOICE.equals(mDigest)) {
            if (TextUtils.isEmpty(mTargetItemId)) {
                return;
            }
            RetrofitClient.getInstance().executeWithoutBaseBean(RetrofitClient.getApiService().sendReportReason(
                    digest, mTargetItemId, reason),
                    new CustomObserver<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody body) {
                            UtilsCode.INSTANCE.showShort("感谢你的举报！我们会尽快审核处理！");
                            EventBus.getDefault().post(ReportResultEvent.buildReportEvent(mTargetItemId, true));
                        }

                        @Override
                        public void _onError(ApiException e) {
                            e.printStackTrace();
                            UtilsCode.INSTANCE.showShort("" + e.getMessage());
                        }
                    });
        }
    }

    private Map<String, RequestBody> buildParams(String digest, String reason) {
        Map<String, RequestBody> params = new HashMap<>(9);
        params.put("users", RequestBody.create(MediaType.parse("text/plain"), mTargetItemId));
        params.put("userf", RequestBody.create(MediaType.parse("text/plain"), UserInfoManager.INSTANCE.getUid()));
        params.put("devId", RequestBody.create(MediaType.parse("text/plain"), UtilsCode.INSTANCE.getAndroidID()));
        params.put("deviceModel", RequestBody.create(MediaType.parse("text/plain"), UtilsCode.INSTANCE.getModel()));
        params.put("source", RequestBody.create(MediaType.parse("text/plain"), "1"));
        params.put("platform", RequestBody.create(MediaType.parse("text/plain"), "ar"));
        params.put("reportReasonId", RequestBody.create(MediaType.parse("text/plain"), digest));
        params.put("reportReason", RequestBody.create(MediaType.parse("text/plain"), reason));
        params.put("chatContent", RequestBody.create(MediaType.parse("text/plain"), mChatList));
        return params;
    }

}
