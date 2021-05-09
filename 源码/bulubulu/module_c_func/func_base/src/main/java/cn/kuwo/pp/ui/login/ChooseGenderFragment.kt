package cn.kuwo.pp.ui.login

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import cn.kuwo.common.base.BaseFragment
import cn.kuwo.common.util.SP
import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.pp.R
import cn.kuwo.pp.manager.UserInfoManager
import com.elbbbird.android.analytics.AnalyticsUtils
import com.elbbbird.android.analytics.UmengEventId
import kotlinx.android.synthetic.main.fragment_choose_gender.*


class ChooseGenderFragment : BaseFragment() {
    private var colorSelected = Color.parseColor("#FFE43F")
    private var colorNormal = Color.WHITE

    companion object {
        fun newInstance(): ChooseGenderFragment {
            val args = Bundle()
            val fragment = ChooseGenderFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_gender, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableToolbar(R.id.toolbar, "", false)
        ViewCompat.setBackgroundTintMode(ivConfirm, PorterDuff.Mode.DARKEN)
        tvMale.setOnClickListener {
            tvMale.toggle()
            if (tvMale.isChecked) {
                tvFemale.isChecked = false
                changeUi(maleChecked = true, femaleChecked = false)
            } else {
                changeUi(maleChecked = false, femaleChecked = tvFemale.isChecked)
            }
        }
        tvFemale.setOnClickListener {
            tvFemale.toggle()
            if (tvFemale.isChecked) {
                tvMale.isChecked = false
                changeUi(maleChecked = false, femaleChecked = true)
            } else {
                changeUi(maleChecked = tvMale.isChecked, femaleChecked = false)
            }
        }
        ivConfirm.setOnClickListener {
            if (tvMale.isChecked) {
                SP.encode("gender", 1)
                startProfile()
            } else if (tvFemale.isChecked) {
                SP.encode("gender", 2)
                startProfile()
            } else {
                UtilsCode.showShort("请选择性别")
            }
        }
    }

    private fun startProfile() {
        ViewModelProvider(parentViewModelStoreOwner).get(LoginOpModel::class.java)
            .profile.postValue(0)
    }

    override fun onStop() {
        super.onStop()

        if(!UserInfoManager.isLogin){
            AnalyticsUtils.onEvent(activity, UmengEventId.NO_LOGIN_CHOOSE, "")
        }
    }

    private fun changeUi(maleChecked: Boolean, femaleChecked: Boolean) {
        var color: Int
        if (maleChecked && !femaleChecked) {//选男
            tvMale.setCompoundDrawablesWithIntrinsicBounds(
                0,
                R.drawable.choose_gender_male_select,
                0,
                0
            )
            tvFemale.setCompoundDrawablesWithIntrinsicBounds(
                0,
                R.drawable.choose_gender_female_select_male,
                0,
                0
            )
            ViewCompat.setBackgroundTintList(ivConfirm, ColorStateList.valueOf(colorSelected))
            color = colorSelected
        } else if (!maleChecked && femaleChecked) {//选女
            tvMale.setCompoundDrawablesWithIntrinsicBounds(
                0,
                R.drawable.choose_gender_male_select_female,
                0,
                0
            )
            tvFemale.setCompoundDrawablesWithIntrinsicBounds(
                0,
                R.drawable.choose_gender_female_select,
                0,
                0
            )
            ViewCompat.setBackgroundTintList(ivConfirm, ColorStateList.valueOf(colorSelected))
            color = colorSelected
        } else {//都不选

            tvMale.setCompoundDrawablesWithIntrinsicBounds(
                0,
                R.drawable.choose_gender_male_normal,
                0,
                0
            )
            tvFemale.setCompoundDrawablesWithIntrinsicBounds(
                0,
                R.drawable.choose_gender_female_normal,
                0,
                0
            )
            ViewCompat.setBackgroundTintList(
                ivConfirm,
                ColorStateList.valueOf(resources.getColor(R.color.white_alpha_50))
            )
            color = colorNormal
        }
        tvMale.setTextColor(color)
        tvFemale.setTextColor(color)
        tvWelcome.setTextColor(color)
        tvSelectGender.setTextColor(color)
    }

    override fun onBackPressedSupport(): Boolean {
        ViewModelProvider(parentViewModelStoreOwner).get(LoginOpModel::class.java)
            .closeLoginManager.postValue(true)
        return true
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
    }

    override fun getPrintClass(): Class<*> {
        return javaClass
    }
}
