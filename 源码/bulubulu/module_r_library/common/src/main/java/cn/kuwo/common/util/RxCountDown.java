package cn.kuwo.common.util;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RxCountDown {

    public static Observable<Integer> countDown(int times) {
        return countDown(times, 1000);
    }

    /**
     * rxJava实现的倒计时
     *
     * @param times  倒计时次数
     * @param period 倒计时间隔
     * @return
     */
    public static Observable<Integer> countDown(int times, int period) {
        if (times < 0) times = 0;
        final int countTime = times;
        return Observable.interval(0, period, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(increaseTime -> countTime - increaseTime.intValue())
                .take(countTime + 1);

    }
}