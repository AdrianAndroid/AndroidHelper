package cn.kuwo.pp.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import cn.kuwo.common.app.App
import cn.kuwo.common.base.BaseFragment
import cn.kuwo.pp.R

class LoginManagerFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loginmanager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 加载最基本的Fragment
        val isBind = arguments?.getBoolean("isBind", false) ?: false
        // 初始化Fragment
        childFragmentManager.beginTransaction()
            .add(
                R.id.loginContainer,
                LoginFragment.newInstanceLogin(isBind),
                LoginFragment::class.simpleName
            )
            .commit()

        val model = ViewModelProvider(this).get(LoginOpModel::class.java)

        model.back.observe(viewLifecycleOwner, Observer {
            if (App.DEBUG) Log.i("LoginManagerFragment", "back")
            // 回退
            popFragment()
        })
        model.veriftyCode.observe(viewLifecycleOwner, Observer {
            if (App.DEBUG) Log.i("LoginManagerFragment", "veriftyCode")
            // 验证码
            skipFragment(VerifyCodeFragment.newInstance(it.mobile, it.isBind, it.inviteCode))
        })
        model.gender.observe(viewLifecycleOwner, Observer {
            if (App.DEBUG) Log.i("LoginManagerFragment", "gender")
            // 选择性别
            skipFragment(ChooseGenderFragment.newInstance())
        })
        model.profile.observe(viewLifecycleOwner, Observer {
            if (App.DEBUG) Log.i("LoginManagerFragment", "profile")
            // 个人信息
            skipFragment(EditProfileFragment.newInstance(it))
        })
        model.closeLoginManager.observe(viewLifecycleOwner, Observer {
            if (App.DEBUG) Log.i("LoginManagerFragment", "closeLoginManager")
            // 退出
            pop()
        })
    }


    private fun popFragment() {
        if ((childFragmentManager.fragments.first() is LoginFragment)) {
            pop()
        } else {
            childFragmentManager.popBackStack()
        }
//        if ((childFragmentManager.fragments.size > 1)) {
//            childFragmentManager.popBackStack() // 弹出
//        } else {
//            pop() //退出这个界面
//        }
    }

    private fun skipFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_right_in,
                R.anim.slide_left_out,
                R.anim.slide_left_in,
                R.anim.slide_right_out
            )
            .replace(R.id.loginContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

//    override fun onBackPressedSupport(): Boolean {
//        ViewModelProvider(viewModelStoreOwner).get(LoginOpModel::class.java)
//            .back.postValue(true)
//        return true
//    }

    override fun onBackBtnClicked(view: View?) {
        super.onBackBtnClicked(view)
    }


    override fun getPrintClass(): Class<*> {
        return javaClass
    }
}

data class Verfity(val mobile: String, val isBind: Boolean, val inviteCode:String?=null)

class LoginOpModel : ViewModel() {
    val back: MutableLiveData<Boolean> = MutableLiveData() //回退
    val veriftyCode: MutableLiveData<Verfity> = MutableLiveData() //验证码
    val gender: MutableLiveData<Boolean> = MutableLiveData() //选择性别
    val profile: MutableLiveData<Int> = MutableLiveData() //配置信息
    val closeLoginManager: MutableLiveData<Boolean> = MutableLiveData()

}
