package cn.kuwo.common.base;


import com.afollestad.materialdialogs.MaterialDialog;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * MVP中view的基类，只适用于Fragment
 */
public interface BaseView extends LifecycleProvider<FragmentEvent>  {
    public void showLoadingDialog();
    public void showLoadingDialog(String title);
    public void showLoadingDialog(int titleId);
    public void dismissLoadingDialog();
    public void showEmptyView(CharSequence content);
    public void showErrorView(int errorCode, String error);
}
