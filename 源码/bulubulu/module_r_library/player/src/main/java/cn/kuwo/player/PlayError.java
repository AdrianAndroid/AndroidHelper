package cn.kuwo.player;

public enum PlayError {
    ERROR_UNKNOWN,                  //未指定错误
    ERROR_PREPARE,                   //异步请求数据源错误
    IO_ERROR,
    FILENOTEXIST,
    NO_NETWORK,
    DECODE_FAILE
}
