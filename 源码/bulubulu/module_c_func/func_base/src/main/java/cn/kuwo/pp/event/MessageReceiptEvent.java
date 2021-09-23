package cn.kuwo.pp.event;

import com.tencent.imsdk.ext.message.TIMMessageReceipt;

import java.util.List;

public class MessageReceiptEvent {
    private List<TIMMessageReceipt> mList;

    public MessageReceiptEvent(List<TIMMessageReceipt> list){
        mList = list;
    }

    public List<TIMMessageReceipt> getList() {
        return mList;
    }
}
