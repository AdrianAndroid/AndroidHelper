/**
 * 
 */
package cn.kuwo.pp.ui.web;

import android.app.Activity;

import com.flannery.kuwowebview.KwWebView;


/**
 * @author 李建衡：jianheng.li@kuwo.cn
 *
 */
public interface KwWebWindowInterface{
	Activity getActivity_WebWindow();
	KwWebView getWebView_WebWindow();
	void setTitle_WebWindow(final String title);
	void setTitleColor_WebWindow(final String colorStr);
	void setResume_Reload(final boolean breload);
	void onWebError_WebWindow();
	void webCommand_WebWindow(final String cmd);

    void openNewWebFragment(String url, String title, String psrc);
}
