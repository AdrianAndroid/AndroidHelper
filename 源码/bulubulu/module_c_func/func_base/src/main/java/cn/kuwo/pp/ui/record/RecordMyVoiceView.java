package cn.kuwo.pp.ui.record;

import cn.kuwo.common.base.BaseView;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.GuideBean;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;

public interface RecordMyVoiceView extends BaseView {
    void onGuideListSuccess(BaseListData<GuideBean> data);
    void onGuideListFailed(int code,String msg);

    void onRecordStart(String filePath);
    void onRecordSuccess(String mp3File,String wavFile,boolean isUserStop);
    void onRecordFailed(int code,String msg);

    void onUploadStart();
    void onUploadProcess(long percent, long total);
    void onUploadFailed(int code, String msg);
    void onUploadSuccess(VoiceInfo voiceInfo);
}
