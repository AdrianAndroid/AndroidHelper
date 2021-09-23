package cn.kuwo.pp.ui.discover.view;

import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import cn.kuwo.common.dialog.BaseOptionDialog;
import cn.kuwo.common.dialog.OptionItem;
import cn.kuwo.pp.R;

public class BallMoreDialog extends BaseOptionDialog {

    public interface BallMoreDialogListener{
        void onClickIndex(int index);
    }

    private BallMoreDialogListener mMoreDialogListener;

    public static BallMoreDialog newInstance(){
        BallMoreDialog dialog = new BallMoreDialog();
        return dialog;
    }

    public void setDialogListener(BallMoreDialogListener listener){
        mMoreDialogListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setShowTitle(false);

        ArrayList<OptionItem> items = new ArrayList<>();
        items.add(new OptionItem(getString(R.string.ball_more_share)));
        items.add(new OptionItem(getString(R.string.ball_more_report)));

        mItems = items;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setShowCancel(true);
        setOnOptionClickListener((view, position) -> {
            if(mMoreDialogListener != null){
                mMoreDialogListener.onClickIndex(position);
            }
            return null;
        });
    }
}
