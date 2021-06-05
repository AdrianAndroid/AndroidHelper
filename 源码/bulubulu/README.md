### 项目介绍
```
    app：主业务module
    common：框架基础，集成各种必须库，脱离业务，方便移植
    player：音频播放module，不依赖其他module，除播放相关接口外，其他module不要调用该module的其他接口
    video：视频播放module，依赖player
    socialsdk：通过submodule方式引入的第三方登录和分享库
```

### 开源库

* 各种Utils -> [AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/README-CN.md)
* UI框架 -> [Fragmentation](https://github.com/YoKeyword/Fragmentation/blob/master/README_CN.md)
* fragment滑动返回 -> [fragmentation_swipeback](https://github.com/YoKeyword/Fragmentation)
* RecyclerView的adapter -> [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
* RecyclerView的分割线 -> [FlexibleDivider](https://github.com/yqritc/RecyclerView-FlexibleDivider)
* 对话框 -> [material_dialogs](https://github.com/afollestad/material-dialogs)
* 模块间通讯 -> [eventbus](https://github.com/greenrobot/EventBus)
* 网络框架 -> [retrofit](https://github.com/square/retrofit)
* 图片加载框架（ImageLoader） -> [glide](https://github.com/bumptech/glide)
* Rx -> [rxjava](https://github.com/ReactiveX/RxJava)，[RxAndroid](https://github.com/ReactiveX/RxAndroid)
* 动态权限申请 -> [permissionsdispatcher](https://github.com/permissions-dispatcher/PermissionsDispatcher)
* Android可伸缩布局 -> [flexbox-layout](https://github.com/google/flexbox-layout)
* 图片压缩 -> [Luban(鲁班)](https://github.com/Curzibn/Luban)
* 解决Rx内存泄露 -> [RxLifecycle](https://github.com/trello/RxLifecycle)
* Material风格控件 -> [material](https://github.com/rey5137/Material/wiki)
* 小红点显示 -> [badgeview](https://github.com/qstumn/BadgeView)
* 沉浸式状态栏 -> [ImmersionBar](https://github.com/gyf-dev/ImmersionBar)
* 视频缓存框架 -> [AndroidVideoCache](https://github.com/danikula/AndroidVideoCache/blob/master/README.md)
* 下载进度监听 -> [ProgressManager](https://github.com/JessYanCoding/ProgressManager/blob/master/README-zh.md)
* 打渠道包 -> [packer-ng](https://github.com/mcxiaoke/packer-ng-plugin)
* Android键盘面板冲突 布局闪动处理方案 -> [JKeyboardPanelSwitch](https://github.com/Jacksgong/JKeyboardPanelSwitch/blob/master/FULLSCREEN_TUTORIAL_zh.md)
* 高性能 key-value 组件 -> [MMKV](https://github.com/Tencent/MMKV/wiki/android_setup_cn)
* 视频播放器 -> [JiaoZiVideoPlayer](https://github.com/lipangit/JiaoZiVideoPlayer)
* 图片选择 -> [PictureSelector](https://github.com/Andrew-Shi/PictureSelector)
* 导航指示器 -> [MagicIndicator](https://github.com/hackware1993/MagicIndicator)

### 架构思想
1 单Activity+多Fragment方式
2 EventBus尽量以MainActivity为主（避免混乱问题）
3 多Fragment通信使用ViewModel中LiveData的监听
4

### 具体使用

```
    图片显示：通过ImageLoader类来显示图片或gif
    网络请求：通过RetrofitFactory获取Retrofit实例发送请求
    loading对话框：直接调用BaseFragment或BaseActivity中的接口
    Log：com.blankj.utilcode.util.LogUtils
    Toast：com.blankj.utilcode.util.ToastUtils
    Button背景色：通过android:backgroundTint设置
    RV代替VP：使用 PagerSnapHelper 实现
    提示对话框使用cn.kuwo.common.dialog.AlertDialog
    底部弹出对话框继承cn.kuwo.common.dialog.BaseBottomDialog
    SpannableStringBuilder 封装类 cn.kuwo.common.util.Spanny，用于一个TextView中显示不同样式的文字
    多状态布局使用 cn.kuwo.common.view.MultipleStatusView
```
```
    沉浸式状态栏：
        1、布局中使用CustomToolbar作为顶部Toolbar，并且id为@+id/toolbar，BaseFragment会自动处理
        2、不满足情况1的，重写BaseFragment的 getStatusBarOffsetView() 方法，返回需要向下偏移状态栏高度的view即可
        3、目前不要再布局中设置状态栏高度进行适配，各个手机状态栏高度不一致，布局适配会出现误差
```
```
    java代码获取版本号：ChannelUtil.getChannel(this)

    java脚本验证渠道信息：
    java -jar tools/packer-ng-2.0.1.jar verify ./build/apks/xxx.apk

    Python脚本读取渠道：
    python tools/packer-ng-v2.py ./build/apks/xxx.apk
```
### 打包发版
```
    1、修改 cn.kuwo.boom.http.ConstantUrls.IS_DEBUG = false
    2、确认版本号是否升级，确认数据库版本是否需要升级
    3、执行 resguardRelease 命令打渠道包，渠道包输出位置：/Boom/build/archives
    4、渠道包上传至 smb://172.17.60.121/boom/发版/android/xxx
    5、mapping文件上传至 smb://172.17.60.121/boom/发版/android/xxx
    6、让运维上传apk，生产下载地址
    7、bugly配置应用内升级（可省略）
    8、渠道发版
```



