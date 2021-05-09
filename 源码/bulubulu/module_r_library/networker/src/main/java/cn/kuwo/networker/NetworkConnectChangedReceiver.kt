package cn.kuwo.networker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Parcelable
import cn.kuwo.common.app.App
import cn.kuwo.common.event.ConnectStatusEvent
import cn.kuwo.common.util.L
import org.greenrobot.eventbus.EventBus

/**
 * 监听网络变化
 * 监听网络的改变状态，只有在用户操作网络连接开关（wifi，mobile）的时候接收广播，
 * 然后对相应的界面进行相应的操作， 并将状态保存在我们的APP里面
 * 在MainActivity里面注册****
 * https://blog.csdn.net/u010161303/article/details/88847836
 * https://blog.csdn.net/gdutxiaoxu/article/details/53008266
 *
 * @Subscribe(threadMode = ThreadMode.MAIN)
 * protected void onConnected(ConnectStatusEvent event) {
 *         if (BuildConfig.DEBUG) L.m(getPrintClass(), event.toString());
 * }
 */
class NetworkConnectChangedReceiver : BroadcastReceiver() {

    //@Subscribe(threadMode = ThreadMode.MAIN)
    //protected void onConnected(ConnectStatusEvent event) {
    //        if (BuildConfig.DEBUG) L.m(getPrintClass(), event.toString());
    //}
//    IntentFilter filter = new IntentFilter();
//    filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//    filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
//    filter.addAction("android.net.wifi.STATE_CHANGE");
//    registerReceiver(mNetworkChangeListener,filter);

    companion object {
        private val receiver = NetworkConnectChangedReceiver()

        // 注册
        fun registerConnectState() {
            val filter = IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            filter.addAction("android.net.wifi.STATE_CHANGE");

            //LocalBroadcastUtils.register(receiver, filter) // 监听网络变化
            App.getInstance().registerReceiver(receiver, filter)
        }

        // 取消注册
        fun unRegisterConnectState() {
            App.getInstance().unregisterReceiver(receiver);
        }

    }

    private fun getPrintClass(): Class<*> {
        return javaClass
    }

    /**
     * WifiManager.WIFI_STATE_CHANGED_ACTION
     * 这个监听wifi的打开与关闭，与wifi的连接无关
     * WifiManager.NETWORK_STATE_CHANGED_ACTION：
     * 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
     * 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
     * ConnectivityManager.CONNECTIVITY_ACTION
     * 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适。
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        if (BuildConfig.DEBUG) L.m(getPrintClass(), "/n")
        // 这个监听wifi的打开与关闭，与wifi的连接无关
        if (WifiManager.WIFI_STATE_CHANGED_ACTION == intent?.action) {
            if (BuildConfig.DEBUG) L.m(getPrintClass(), "WifiManager.WIFI_STATE_CHANGED_ACTION")
            val wifiState: Int = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            //if (BuildConfig.DEBUG) L.m(getPrintClass(), "wifiState" + wifiState);
            when (wifiState) {
                WifiManager.WIFI_STATE_DISABLED -> { // wifi已经关闭
                    if (BuildConfig.DEBUG) L.m(getPrintClass(), "WifiManager.WIFI_STATE_DISABLED")
                    setEnablaWifi(false);
                }
                WifiManager.WIFI_STATE_DISABLING -> {
                    if (BuildConfig.DEBUG) L.m(getPrintClass(), "WifiManager.WIFI_STATE_DISABLING")
                }
                WifiManager.WIFI_STATE_ENABLING -> {
                    if (BuildConfig.DEBUG) L.m(getPrintClass(), "WifiManager.WIFI_STATE_ENABLING")
                }
                WifiManager.WIFI_STATE_ENABLED -> { // wifi已经打开
                    if (BuildConfig.DEBUG) L.m(getPrintClass(), "WifiManager.WIFI_STATE_ENABLED")
                    setEnablaWifi(true);
                }
                WifiManager.WIFI_STATE_UNKNOWN -> {
                    if (BuildConfig.DEBUG) L.m(getPrintClass(), "WifiManager.WIFI_STATE_UNKNOWN")
                }
                else -> {
                    if (BuildConfig.DEBUG) L.m(getPrintClass(), "WifiManager---OTHER")
                }
            }
        }
        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager
        // .WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，
        // 当然刚打开wifi肯定还没有连接到有效的无线
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION == intent?.action) {
            if (BuildConfig.DEBUG) L.m(getPrintClass(), "WifiManager.NETWORK_STATE_CHANGED_ACTION")
            val parcelableExtra: Parcelable =
                intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO)
            val networkInfo: NetworkInfo = parcelableExtra as NetworkInfo
            val state: NetworkInfo.State = networkInfo.getState();
            val isConnected: Boolean = state == NetworkInfo.State.CONNECTED;// 当然，这边可以更精确的确定状态
            if (BuildConfig.DEBUG) L.m(getPrintClass(), "isConnected=$isConnected")
            if (isConnected) {
                if (BuildConfig.DEBUG) L.m(getPrintClass(), "isCollected = true")
                setWifi(true);
            } else {
                if (BuildConfig.DEBUG) L.m(getPrintClass(), "isCollected = false")
                setWifi(false);
            }
        }
        // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
        // 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
        // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent?.action) {
            if (BuildConfig.DEBUG) L.m(getPrintClass(), "onnectivityManager.CONNECTIVITY_ACTION")
            val manager: ConnectivityManager? =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            //if (BuildConfig.DEBUG) L.m(getPrintClass(), "CONNECTIVITY_ACTION");

            val activeNetwork: NetworkInfo? = manager?.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.isConnected) {
                    if (BuildConfig.DEBUG) L.m(getPrintClass(), "activeNetwork.isConnected = true")
                    if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                        if (BuildConfig.DEBUG) L.m(
                            getPrintClass(),
                            "ConnectivityManager.TYPE_WIFI"
                        );
                        setWifi(true)
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        // connected to the mobile provider's data plan
                        if (BuildConfig.DEBUG) L.m(
                            getPrintClass(),
                            "ConnectivityManager.TYPE_MOBILE"
                        )
                        setMobile(true);
                    }
                } else {
                    if (BuildConfig.DEBUG) L.m(getPrintClass(), "当前没有网络连接，请确保你已经打开网络 ");
                }


                if (BuildConfig.DEBUG) {
                    L.m(getPrintClass(), "info.getTypeName()" + activeNetwork.typeName);
                    L.m(getPrintClass(), "getSubtypeName()" + activeNetwork.subtypeName);
                    L.m(getPrintClass(), "getState()" + activeNetwork.state);
                    L.m(getPrintClass(), "getDetailedState()" + activeNetwork.detailedState.name);
                    L.m(getPrintClass(), "getDetailedState()" + activeNetwork.extraInfo);
                    L.m(getPrintClass(), "getType()" + activeNetwork.type);
                }
                setConnected(true)
            } else {   // not connected to the internet
                if (BuildConfig.DEBUG) {
                    L.m(getPrintClass(), "当前没有网络连接，请确保你已经打开网络 ");
                }
                setWifi(false);
                setMobile(false);
                setConnected(false);

            }


        }
    }

    private fun test() {
        ///ConnectivityManager().registerDefaultNetworkCallback(object :Call)
    }


//    private var isConnected = false
//    private var isMobile = false
//    private var isWifi = false
//    private var isEnableWifi = true

    private fun setConnected(b: Boolean) {
        if (BuildConfig.DEBUG) L.m(getPrintClass(), "b=$b")
        EventBus.getDefault().post(ConnectStatusEvent())
    }

    private fun setMobile(b: Boolean) {
        if (BuildConfig.DEBUG) L.m(getPrintClass(), "b=$b")
        //EventBus.getDefault().post(ConnectStatusEvent())
    }

    private fun setWifi(b: Boolean) {
        if (BuildConfig.DEBUG) L.m(getPrintClass(), "b=$b")
        //EventBus.getDefault().post(ConnectStatusEvent())
    }

    private fun setEnablaWifi(b: Boolean) {
        if (BuildConfig.DEBUG) L.m(getPrintClass(), "b=$b")
        //EventBus.getDefault().post(ConnectStatusEvent())
    }

    /**
     * // 未连接网络之前
     * onReceive(NetworkConnectChangedReceiver.kt:67)
     * onReceive(NetworkConnectChangedReceiver.kt:120)onnectivityManager.CONNECTIVITY_ACTION,
     * onReceive(NetworkConnectChangedReceiver.kt:158)当前没有网络连接，请确保你已经打开网络 ,
     * setWifi(NetworkConnectChangedReceiver.kt:188)b=false,
     * setMobile(NetworkConnectChangedReceiver.kt:182)b=false,
     * setConnected(NetworkConnectChangedReceiver.kt:176)b=false,
     *
     * onReceive(NetworkConnectChangedReceiver.kt:67)
     * onReceive(NetworkConnectChangedReceiver.kt:70)WifiManager.WIFI_STATE_CHANGED_ACTION,
     * onReceive(NetworkConnectChangedReceiver.kt:72)wifiState3,
     * onReceive(NetworkConnectChangedReceiver.kt:85)WifiManager.WIFI_STATE_ENABLED,
     * setEnablaWifi(NetworkConnectChangedReceiver.kt:194)b=true,
     *
     * onReceive(NetworkConnectChangedReceiver.kt:67)
     * onReceive(NetworkConnectChangedReceiver.kt:101)WifiManager.NETWORK_STATE_CHANGED_ACTION,
     * onReceive(NetworkConnectChangedReceiver.kt:107)isConnected=false,
     * onReceive(NetworkConnectChangedReceiver.kt:112)isCollected = false,
     * setWifi(NetworkConnectChangedReceiver.kt:188)b=false,
     *
     * // 连接网络之后
     * onReceive(NetworkConnectChangedReceiver.kt:67)
     * onReceive(NetworkConnectChangedReceiver.kt:101)WifiManager.NETWORK_STATE_CHANGED_ACTION,
     * onReceive(NetworkConnectChangedReceiver.kt:107)isConnected=false,
     * onReceive(NetworkConnectChangedReceiver.kt:112)isCollected = false,
     * setWifi(NetworkConnectChangedReceiver.kt:188)b=false,
     *
     * onReceive(NetworkConnectChangedReceiver.kt:67)
     * onReceive(NetworkConnectChangedReceiver.kt:101)WifiManager.NETWORK_STATE_CHANGED_ACTION,
     * onReceive(NetworkConnectChangedReceiver.kt:107)isConnected=false,
     * onReceive(NetworkConnectChangedReceiver.kt:112)isCollected = false,
     * setWifi(NetworkConnectChangedReceiver.kt:188)b=false,
     *
     * onReceive(NetworkConnectChangedReceiver.kt:67)
     * onReceive(NetworkConnectChangedReceiver.kt:101)WifiManager.NETWORK_STATE_CHANGED_ACTION,
     * onReceive(NetworkConnectChangedReceiver.kt:107)isConnected=false,
     * onReceive(NetworkConnectChangedReceiver.kt:112)isCollected = false,
     * setWifi(NetworkConnectChangedReceiver.kt:188)b=false,
     *
     * onReceive(NetworkConnectChangedReceiver.kt:67)
     * onReceive(NetworkConnectChangedReceiver.kt:101)WifiManager.NETWORK_STATE_CHANGED_ACTION,
     * onReceive(NetworkConnectChangedReceiver.kt:107)isConnected=false,
     * onReceive(NetworkConnectChangedReceiver.kt:112)isCollected = false,
     * setWifi(NetworkConnectChangedReceiver.kt:188)b=false,
     *
     * onReceive(NetworkConnectChangedReceiver.kt:67)
     * onReceive(NetworkConnectChangedReceiver.kt:101)WifiManager.NETWORK_STATE_CHANGED_ACTION,
     * onReceive(NetworkConnectChangedReceiver.kt:107)isConnected=false,
     * onReceive(NetworkConnectChangedReceiver.kt:112)isCollected = false,
     * setWifi(NetworkConnectChangedReceiver.kt:188)b=false,
     *
     * onReceive(NetworkConnectChangedReceiver.kt:67)
     * onReceive(NetworkConnectChangedReceiver.kt:101)WifiManager.NETWORK_STATE_CHANGED_ACTION,
     * onReceive(NetworkConnectChangedReceiver.kt:107)isConnected=false,
     * onReceive(NetworkConnectChangedReceiver.kt:112)isCollected = false,
     * setWifi(NetworkConnectChangedReceiver.kt:188)b=false,
     *
     * onReceive(NetworkConnectChangedReceiver.kt:67)
     * onReceive(NetworkConnectChangedReceiver.kt:101)WifiManager.NETWORK_STATE_CHANGED_ACTION,
     * onReceive(NetworkConnectChangedReceiver.kt:107)isConnected=false,
     * onReceive(NetworkConnectChangedReceiver.kt:112)isCollected = false,
     * setWifi(NetworkConnectChangedReceiver.kt:188)b=false,
     *
     * onReceive(NetworkConnectChangedReceiver.kt:67)
     * onReceive(NetworkConnectChangedReceiver.kt:101)WifiManager.NETWORK_STATE_CHANGED_ACTION,
     * onReceive(NetworkConnectChangedReceiver.kt:107)isConnected=true,
     * onReceive(NetworkConnectChangedReceiver.kt:109)isCollected = true,
     * setWifi(NetworkConnectChangedReceiver.kt:188)b=true,
     *
     * onReceive(NetworkConnectChangedReceiver.kt:67)
     * onReceive(NetworkConnectChangedReceiver.kt:101)WifiManager.NETWORK_STATE_CHANGED_ACTION,
     * onReceive(NetworkConnectChangedReceiver.kt:107)isConnected=true,
     * onReceive(NetworkConnectChangedReceiver.kt:109)isCollected = true,
     * setWifi(NetworkConnectChangedReceiver.kt:188)b=true,
     *
     * onReceive(NetworkConnectChangedReceiver.kt:67)
     * onReceive(NetworkConnectChangedReceiver.kt:120)onnectivityManager.CONNECTIVITY_ACTION,
     * onReceive(NetworkConnectChangedReceiver.kt:128)activeNetwork.isConnected = true,
     * onReceive(NetworkConnectChangedReceiver.kt:130)ConnectivityManager.TYPE_WIFI,
     * setWifi(NetworkConnectChangedReceiver.kt:188)b=true,
     * onReceive(NetworkConnectChangedReceiver.kt:149)info.getTypeName()WIFI,
     * onReceive(NetworkConnectChangedReceiver.kt:150)getSubtypeName(),
     * onReceive(NetworkConnectChangedReceiver.kt:151)getState()CONNECTED,
     * onReceive(NetworkConnectChangedReceiver.kt:152)getDetailedState()CONNECTED,
     * onReceive(NetworkConnectChangedReceiver.kt:153)getDetailedState()"JD-F",
     * onReceive(NetworkConnectChangedReceiver.kt:154)getType()1,
     */
}
