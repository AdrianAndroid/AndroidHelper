package cn.kuwo.pp.http;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.kuwo.networker.exception.ApiException;
import cn.kuwo.networker.exception.ReLoginException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static cn.kuwo.networker.exception.ErrorCode.ERROR_NETWORK_ERROR;
import static cn.kuwo.networker.exception.ErrorCode.ERROR_NETWORK_TIMEOUT;
import static cn.kuwo.networker.exception.ErrorCode.ERROR_UN_KNOW;
import static cn.kuwo.networker.exception.ErrorCode.ERROR_UN_LOGIN;

public abstract class CustomObserver<T> implements Observer<T> {

    /**
     * 观察者和被观察者连接到一起时触发回调，即发生在订阅的过程中，可在该接口中作请求之前的处理
     *
     * @param d
     */
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {
        onCompleteOrError();
    }

    /**
     * onComplete 或者 onError 被调用，都会触发该方法
     */
    public void onCompleteOrError() {

    }

    @Override
    public abstract void onNext(T t);

    @Override
    public void onError(Throwable e) {
        ApiException exception = null;
        if (e == null) {
            exception = new ApiException(null, ERROR_UN_KNOW, "请求失败，请稍后重试");
        } else if (e instanceof ApiException) {
            exception = (ApiException) e;
        } else if (e instanceof ReLoginException) {
            exception = new ApiException(null, ERROR_UN_LOGIN, "请求失败，请稍后重试！");
        } else if (e instanceof ConnectException) {
            exception = new ApiException(null, ERROR_NETWORK_ERROR, "网络受到神秘力量干扰");
        } else if (e instanceof UnknownHostException) {
            exception = new ApiException(null, ERROR_NETWORK_ERROR, "网络受到神秘力量干扰");
        } else if (e instanceof SocketTimeoutException) {
            exception = new ApiException(null, ERROR_NETWORK_TIMEOUT, "网络受到神秘力量干扰");
        } else {
            exception = new ApiException(null, ERROR_UN_KNOW, e.getMessage());
        }
        _onError(exception);
        onCompleteOrError();
    }

    public abstract void _onError(ApiException e);
}
