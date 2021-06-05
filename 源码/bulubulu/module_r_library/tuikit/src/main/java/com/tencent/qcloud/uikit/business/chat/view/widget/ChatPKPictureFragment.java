package com.tencent.qcloud.uikit.business.chat.view.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.tencent.qcloud.uikit.R;
import com.tencent.qcloud.uikit.common.BaseFragment;

public class ChatPKPictureFragment extends BaseFragment implements View.OnClickListener{

    public interface IPKPictureListener{
        void IPKPictureListener_OnClick(int index);
    }

    private IPKPictureListener mListener;
    public void setListener(IPKPictureListener listener){
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.chat_bottom_pkpicture_layout, container, false);

        view.findViewById(R.id.ivPKInvite).setOnClickListener(this);
        view.findViewById(R.id.tvPKInvite).setOnClickListener(this);
        view.findViewById(R.id.ivPicture).setOnClickListener(this);
        view.findViewById(R.id.tvPicture).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v){
        if(mListener == null){
            return;
        }

        if(v.getId() == R.id.ivPKInvite || v.getId() == R.id.tvPKInvite){
            mListener.IPKPictureListener_OnClick(0);
        }else if(v.getId() == R.id.ivPicture || v.getId() == R.id.tvPicture){
            mListener.IPKPictureListener_OnClick(1);
        }
    }
}
