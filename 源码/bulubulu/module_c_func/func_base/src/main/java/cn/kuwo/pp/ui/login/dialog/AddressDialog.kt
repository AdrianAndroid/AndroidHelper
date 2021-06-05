package cn.kuwo.pp.ui.login.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.kuwo.common.dialog.BaseBottomDialog
import cn.kuwo.pp.R
import cn.kuwo.pp.http.bean.user.ProvinceBean
import kotlinx.android.synthetic.main.dialog_select_address.*

class AddressDialog : BaseBottomDialog() {
    var onSelectListener: ((String, String) -> Unit)? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_select_address, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val address = cn.kuwo.pp.util.AddressUtil.getAddress()
        wpProvince.data = address
        wpCity.data = address[0].child
        wpProvince.setOnItemSelectedListener { _, data, position ->
            wpCity.data = (data as ProvinceBean).child
        }
        tvConfirm.setOnClickListener {
            onSelectListener?.invoke(wpProvince.data[wpProvince.currentItemPosition].toString(), wpCity.data[wpCity.currentItemPosition].toString())
            dismiss()
        }
        tvCancel.setOnClickListener {
            dismiss()
        }
    }
}