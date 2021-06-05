package cn.kuwo.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;

import cn.kuwo.common.util.L;
import io.reactivex.subjects.BehaviorSubject;

public class FullScreenDialog extends Dialog {
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    public FullScreenDialog(Activity context, int themeResId){
        super(context, themeResId);
        L.m(getClass());
        setOwnerActivity(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        L.m(getClass());
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        onSetContentView();
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        L.m(getClass());
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    protected void onSetContentView(){
        L.m(getClass());
    }

    @Override
    protected void onStop(){
        L.m(getClass());
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

}
