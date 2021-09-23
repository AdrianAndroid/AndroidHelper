package com.tencent.qcloud.uikit.business.chat.view.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tencent.qcloud.uikit.R;
import com.tencent.qcloud.uikit.common.BaseFragment;

import cn.kuwo.common.utilscode.UtilsCode;


public class ChatGiftFragment extends BaseFragment implements View.OnClickListener {
    public static interface IChatGiftListener {
        void IChatGiftListener_onClick(int id);
    }

    public void setListener(IChatGiftListener l) {
        listener = l;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.chat_bottom_gift_layout, container, false);

        ImageView v = (ImageView)baseView.findViewById(R.id.gift1);
        v.setTag(84);
        v.setOnClickListener(this);

        v = (ImageView)baseView.findViewById(R.id.gift2);
        v.setTag(85);
        v.setOnClickListener(this);

        v = (ImageView)baseView.findViewById(R.id.gift3);
        v.setTag(86);
        v.setOnClickListener(this);

        giftNumText = (TextView)baseView.findViewById(R.id.tvGiftNum);

        setGiftNum(giftNum);

        return baseView;
    }

    @Override
    public void onClick(View v) {
        int id = (Integer)v.getTag();

        int num = 0;
        if (id == 84) {
            num = 1;
        } else if (id == 85) {
            num = 3;
        } else if (id == 86) {
            num = 5;
        }
        if (num > giftNum) {
            UtilsCode.INSTANCE.showLong("爱意告急～需要补给(⊙ω⊙)～");
            return;
        }
        setGiftNum(giftNum - num);

        if (listener != null) {
            listener.IChatGiftListener_onClick(id);
        }
    }

    public void setGiftNum(int num) {
        giftNum = num;
        if (giftNumText != null) {
            if (giftNum > 0) {
                giftNumText.setText("我的爱意：" + num);
            } else {
                giftNumText.setText("爱意告急～需要补给(⊙ω⊙)～");
            }
        }
    }

    private IChatGiftListener listener;
    private TextView giftNumText;
    private int giftNum;
}
