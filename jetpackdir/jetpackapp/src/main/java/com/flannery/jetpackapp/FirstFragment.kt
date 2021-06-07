//package com.flannery.jetpackapp
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.Fragment
//
///**
// * Created by TanJiaJun on 2019-12-22.
// */
//class FirstFragment : Fragment(), FirstHandlers {
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? = null;
//
//    override fun onNavigateToSecondFragmentClick(view: View) {
//        //(activity as MainActivity).addFragment(SecondFragment(), FRAGMENT_TAG_SECOND)
//    }
////        DataBindingUtil.inflate<FragmentFirstBinding>(
////            inflater,
////            R.layout.fragment_first,
////            container,
////            false
////        )
////            .also { binding ->
////                // 调用了setLifecycleOwner方法，下面会分析
////                binding.lifecycleOwner = this
////                // 使MainViewModel与视图绑定
////                binding.viewModel = activity?.let {
////                    ViewModelProviders.of(it)[MainViewModel::class.java]
////                }
////                binding.handlers = this
////            }
////            .root
//
////    // 点击Button导航到SecondFragment
////    override fun onNavigateToSecondFragmentClick(view: View) {
//    //(activity as MainActivity).addFragment(SecondFragment(), FRAGMENT_TAG_SECOND)
////    }
//
//}
//
//interface FirstHandlers {
//
//    fun onNavigateToSecondFragmentClick(view: View)
//
//}
//
//
//
///*
///**
// * Created by TanJiaJun on 2019-12-22.
// */
//class SecondFragment : Fragment(), SecondHandlers {
//
//    private var viewModel: MainViewModel? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel = activity?.let { ViewModelProviders.of(it)[MainViewModel::class.java] }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? =
//        DataBindingUtil.inflate<FragmentSecondBinding>(
//            inflater,
//            R.layout.fragment_second,
//            container,
//            false
//        )
//            .also {
//                it.handlers = this
//            }
//            .root
//
//    // 点击第一个Button调用MainViewModel中的changeFirstContent方法，使FirstFragment中的第一个TextView的文本从”第一个文本“变成”第一个文本已改变“
//    override fun onChangeFirstContentClick(view: View) {
//        viewModel?.changeFirstContent(getString(R.string.first_content_changed))
//    }
//
//    // 点击第二个Button调用MainViewModel中的changeSecondContent方法，使FirstFragment中的第二个TextView的文本从”第二个文本“变为”第二个文本已改变“
//    override fun onChangeSecondContentClick(view: View) {
//        viewModel?.changeSecondContent(getString(R.string.second_content_changed))
//    }
//
//}
//
//interface SecondHandlers {
//
//    fun onChangeFirstContentClick(view: View)
//
//    fun onChangeSecondContentClick(view: View)
//
//}
// */