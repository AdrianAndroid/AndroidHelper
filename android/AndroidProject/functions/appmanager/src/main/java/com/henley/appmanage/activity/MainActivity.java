package com.henley.appmanage.activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.henley.appmanage.R;
import com.henley.appmanage.adapter.AppInfoAdapter;
import com.henley.appmanage.adapter.FragmentAdapter;
import com.henley.appmanage.data.AppInfo;
import com.henley.appmanage.data.AppInfoManage;
import com.henley.appmanage.fragment.AppInfoFragment;
import com.henley.appmanage.listener.OnInstallStateChangedListener;
import com.henley.appmanage.receiver.InstallStateChangedReceiver;
import com.henley.appmanage.utils.AppManageHelper;
import com.henley.appmanage.utils.ComparatorByName;
import com.henley.appmanage.utils.ComparatorBySize;
import com.henley.appmanage.utils.NavigatorHelper;
import com.henley.appmanage.utils.PermissionHelper;
import com.henley.appmanage.utils.SearchViewHelper;
import com.henley.appmanage.utils.Utility;
import com.henley.appmanage.widget.LoadingDialog;
import com.henley.appmanage.widget.SimpleTextWatcher;
import com.henley.appmanage.widget.indicator.CommonNavigator;
import com.henley.appmanage.widget.indicator.MagicIndicator;
//import com.joyy.router.annotations.Destination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Destination(
//        url = "appmanager/MainActivity",
//        description = "MainActivity"
//)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_USAGE = 100;
    private static final String SP_NAME = "config_prefs";
    private static final String SORT_WHICH = "sort_which";
    private static final String[] TITLES = {"所有", "系统", "用户"};
    private static final String[] SORT_MENU_ITEMS = new String[]{"按名称升序", "按名称降序", "按大小升序", "按大小降序"};
    private ExecutorService mExecutorService;
    private AppInfoManage appInfoManage;
    private List<AppInfoFragment> fragments;
    private Dialog mProgressDialog;
    private InstallStateChangedReceiver mReceiver;
    private SharedPreferences mPreferences;
    private int curWhich;
    private int tempWhich;
    private AppManageHelper appManageHelper;
    private ListView listSearch;
    private AppInfoAdapter mAdapter;
    private EditText edtSeatch;
    private View searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        int length = TITLES.length;
        final List<String> titles = Arrays.asList(TITLES);

        final ViewPager viewPager = findViewById(R.id.view_pager);
        fragments = new ArrayList<>(length);
        for (int index = 0; index < length; index++) {
            fragments.add(new AppInfoFragment());
        }
        viewPager.setOffscreenPageLimit(length);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), titles, fragments));

        MagicIndicator magicIndicator = findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = NavigatorHelper.getCommonNavigator(this, viewPager, titles);
        magicIndicator.setNavigator(commonNavigator);
        magicIndicator.setupWithViewPager(viewPager);

        mPreferences = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        mProgressDialog = new LoadingDialog(this);

        initSearchView();
        registerReceiver();
        loadAppManageInfo();
        checkUsagePermission();
    }

    private void initSearchView() {
        FrameLayout contentView = (FrameLayout) getWindow().getDecorView();
        searchView = getLayoutInflater().inflate(R.layout.layout_search_appinfo, contentView, false);
        contentView.addView(searchView);
        int statusBarHeight = Utility.getStatusBarHeight(this);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) searchView.getLayoutParams();
        layoutParams.topMargin = statusBarHeight;
        searchView.setLayoutParams(layoutParams);
        searchView.setVisibility(View.INVISIBLE);
        edtSeatch = searchView.findViewById(R.id.search_edt);
        final View ivClean = searchView.findViewById(R.id.search_clean);
        ivClean.setVisibility(View.GONE);
        ivClean.setOnClickListener(this);
        searchView.findViewById(R.id.search_back).setOnClickListener(this);
        searchView.findViewById(R.id.search_cancel).setOnClickListener(this);
        edtSeatch.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    ivClean.setVisibility(View.VISIBLE);
                    startQuery(s.toString());
                } else {
                    ivClean.setVisibility(View.GONE);
                    updateSearchAppInfo(null);
                }
            }
        });
        listSearch = searchView.findViewById(R.id.search_listview);
    }

    private void registerReceiver() {
        mReceiver = new InstallStateChangedReceiver(new OnInstallStateChangedListener() {
            @Override
            public void onPackageAdded(String action, String packageName) {
                AppInfo appInfo = appManageHelper.getAppInfo(packageName);
                appInfoManage.putMapAppInfo(appInfo);
                appInfoManage.addAllAppInfo(appInfo);
                appInfoManage.addUserAppInfo(appInfo);
                updateAppManageInfo();
            }

            @Override
            public void onPackageReplaced(String action, String packageName) {

            }

            @Override
            public void onPackageRemoved(String action, String packageName) {
                AppInfo appInfo = appInfoManage.removeMapAppInfo(packageName);
                appInfoManage.removeAllAppInfo(appInfo);
                appInfoManage.removeUserAppInfo(appInfo);
                updateAppManageInfo();
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(mReceiver, intentFilter);
    }

    private boolean checkUsagePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !PermissionHelper.checkUsagePermission(this)) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("温馨提示")
                    .setMessage("允许" + Utility.getAppName(this) + "访问设备上其他应用的信息？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PermissionHelper.openUsagePermissionSetting(MainActivity.this, REQUEST_CODE_USAGE);
                        }
                    })
                    .create();
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            return false;
        }
        return true;
    }

    private void loadAppManageInfo() {
        if (mExecutorService == null) {
            mExecutorService = Executors.newSingleThreadExecutor();
        }
        if (appManageHelper == null) {
            appManageHelper = new AppManageHelper(this);
        }
        mProgressDialog.show();
        final long timeMillis = System.currentTimeMillis();
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                appInfoManage = appManageHelper.getAppManageInfo();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "耗时：" + (System.currentTimeMillis() - timeMillis) + "ms", Toast.LENGTH_SHORT).show();
                        updateAppManageInfo();
                    }
                });
            }
        });
    }

    private void updateAppManageInfo() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        if (appInfoManage != null) {
            fragments.get(0).updateAppInfo(appInfoManage.getAllAppList());
            fragments.get(1).updateAppInfo(appInfoManage.getSystemAppList());
            fragments.get(2).updateAppInfo(appInfoManage.getUserAppList());
            curWhich = mPreferences.getInt(SORT_WHICH, 0);
            startSortAppInfo();
        }
    }

    private void showSortMenuDialog() {
        tempWhich = curWhich;
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("排序")
                .setSingleChoiceItems(SORT_MENU_ITEMS, curWhich, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tempWhich = which;
                    }
                })
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (curWhich != tempWhich) {
                            curWhich = tempWhich;
                            mPreferences.edit()
                                    .putInt(SORT_WHICH, curWhich)
                                    .apply();
                            startSortAppInfo();
                        }
                    }
                })
                .create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void startSortAppInfo() {
        if (fragments != null && !fragments.isEmpty()) {
            for (AppInfoFragment fragment : fragments) {
                switch (curWhich) {
                    case 0:
                        fragment.sortByName(false); // 按名称排序(升序)
                        break;
                    case 1:
                        fragment.sortByName(true); // 按名称排序(降序)
                        break;
                    case 2:
                        fragment.sortBySize(false); // 按大小排序(升序)
                        break;
                    case 3:
                        fragment.sortBySize(true); // 按大小排序(降序)
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_search) {
            handleSearchViewState();
            return true;
        } else if (itemId == R.id.action_sort) {
            showSortMenuDialog();
            return true;
        } else if (item.getItemId() == R.id.action_refresh) {
            if (checkUsagePermission()) {
                loadAppManageInfo();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_USAGE) {
            if (PermissionHelper.checkUsagePermission(this)) {
                loadAppManageInfo();
            } else {
                Toast.makeText(this, "访问设备上其他应用的信息需要该权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }

    private void startQuery(String text) {
        if (appInfoManage != null && !appInfoManage.getAllAppList().isEmpty()) {
            List<AppInfo> resultList = new ArrayList<>();
            List<AppInfo> sourceList = appInfoManage.getAllAppList();
            for (AppInfo appInfo : sourceList) {
                if (appInfo.getAppName().contains(text) || appInfo.getPackageName().contains(text)) {
                    resultList.add(appInfo);
                }
            }
            switch (curWhich) {
                case 0:
                    Collections.sort(resultList, new ComparatorByName(false)); // 按名称排序(升序)
                    break;
                case 1:
                    Collections.sort(resultList, new ComparatorByName(true)); // 按名称排序(降序)
                    break;
                case 2:
                    Collections.sort(resultList, new ComparatorBySize(false)); // 按大小排序(升序)
                    break;
                case 3:
                    Collections.sort(resultList, new ComparatorBySize(true)); // 按大小排序(降序)
                    break;
                default:
                    break;
            }
            updateSearchAppInfo(resultList);
        }
    }

    public void updateSearchAppInfo(List<AppInfo> list) {
        if (mAdapter == null) {
            mAdapter = new AppInfoAdapter(list);
            listSearch.setAdapter(mAdapter);
        } else {
            mAdapter.refresh(list);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.search_back || id == R.id.search_cancel) {
            handleSearchViewState();
        } else if (id == R.id.search_clean) {
            if (edtSeatch != null) {
                edtSeatch.setText("");
            }
            updateSearchAppInfo(null);
        }
    }

    private void handleSearchViewState() {
        SearchViewHelper.handleSearchViewState(this, searchView, edtSeatch);
    }

    @Override
    public void onBackPressed() {
        if (searchView != null && searchView.getVisibility() == View.VISIBLE) {
            handleSearchViewState();
        } else {
            super.onBackPressed();
        }
    }

}