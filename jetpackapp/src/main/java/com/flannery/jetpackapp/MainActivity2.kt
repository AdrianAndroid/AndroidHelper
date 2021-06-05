//package com.flannery.jetpackapp
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
////import androidx.lifecycleDefaultLifecycleObserver
////import androidx.lifecycle.Lifecycle
////import androidx.lifecycle.LifecycleEventObserver
////import androidx.lifecycle.LifecycleOwner
//
//class MainActivity2 : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//
//
//    /*
//      public enum Event {
//        /**
//         * Constant for onCreate event of the {@link LifecycleOwner}.
//         */
//        ON_CREATE,
//        /**
//         * Constant for onStart event of the {@link LifecycleOwner}.
//         */
//        ON_START,
//        /**
//         * Constant for onResume event of the {@link LifecycleOwner}.
//         */
//        ON_RESUME,
//        /**
//         * Constant for onPause event of the {@link LifecycleOwner}.
//         */
//        ON_PAUSE,
//        /**
//         * Constant for onStop event of the {@link LifecycleOwner}.
//         */
//        ON_STOP,
//        /**
//         * Constant for onDestroy event of the {@link LifecycleOwner}.
//         */
//        ON_DESTROY,
//        /**
//         * An {@link Event Event} constant that can be used to match all events.
//         */
//        ON_ANY
//    }
//     */
//
//    /*
//    public enum State {
//        //当处于 DESTROYED 状态时，Lifecycle 将不会发布其它 Event 值
//        //当 Activity 即将回调 onDestory 时则处于此状态
//        DESTROYED,
//        //已初始化的状态。例如，当 Activity 的构造函数已完成，但还未回调 onCreate 时则处于此状态
//        INITIALIZED,
//        CREATED,
//        STARTED,
//        RESUMED;
//
//        //如果当前状态大于入参值 state 时，则返回 true
//        public boolean isAtLeast(@NonNull State state) {
//            return compareTo(state) >= 0;
//        }
//    }
//     */
//
//    fun test() {
////        object : LifecycleEventObserver {
////            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
////                when (event) {
////                    Lifecycle.Event.ON_CREATE -> TODO()
////                    Lifecycle.Event.ON_START -> TODO()
////                    Lifecycle.Event.ON_RESUME -> TODO()
////                    Lifecycle.Event.ON_PAUSE -> TODO()
////                    Lifecycle.Event.ON_STOP -> TODO()
////                    Lifecycle.Event.ON_DESTROY -> TODO()
////                    Lifecycle.Event.ON_ANY -> TODO()
////                }
////            }
////        })
////
////    }
////
////}
////        lifecycle.removeObserver(null);
////        lifecycle.currentState
////        lifecycle.addObserver(object : DefaultLifecycleObserver {
////            override fun onCreate(owner: LifecycleOwner) {
////                super.onCreate(owner)
////            }
////
////            override fun onResume(owner: LifecycleOwner) {
////                super.onResume(owner)
////            }
////
////            override fun onDestroy(owner: LifecycleOwner) {
////                super.onDestroy(owner)
////            }
////        })
//}
//
//}