package cn.kuwo.common.util;

import com.tencent.mmkv.MMKV;

/**
 * @author shihc
 */
public class MMKVUtil {
    /**
     * 多进程mmkv实例
     * @return
     */
    public static MMKV getDefaultMultiProcess() {
        return MMKV.mmkvWithID("defaultMultiProcess", MMKV.MULTI_PROCESS_MODE);
    }
}
