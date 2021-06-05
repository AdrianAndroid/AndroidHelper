package cn.kuwo.pp.ui.login.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import cn.kuwo.common.dialog.BaseBottomDialog
import cn.kuwo.common.utilscode.UtilsCode
import cn.kuwo.pp.R
import kotlinx.android.synthetic.main.dialog_select_birthday.*
import java.text.SimpleDateFormat
import java.util.*

class BirthdayDialog : BaseBottomDialog() {
    companion object {
        fun newInstance(birthday: String?): BirthdayDialog {
            val args = Bundle()
            args.putString("birthday", birthday)
            val fragment = BirthdayDialog()
            fragment.arguments = args
            return fragment
        }
    }

    var onSelectListener: ((Date) -> Unit)? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_select_birthday, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        wheelPicker.visibleItemCount = 5
        wheelPicker.itemSpace = UtilsCode.dp2px(30F)
        wheelPicker.setAtmospheric(true)
        wheelPicker.itemTextColor = resources.getColor(R.color.colorBirthdaySub)
        wheelPicker.selectedItemTextColor = resources.getColor(R.color.colorBirthday)
        wheelPicker.itemTextSize = UtilsCode.sp2px(20F)
        wheelPicker.textViewYear.visibility = View.GONE
        wheelPicker.textViewMonth.visibility = View.GONE
        wheelPicker.textViewDay.visibility = View.GONE
        (wheelPicker.wheelDayPicker.layoutParams as LinearLayout.LayoutParams).weight = 1F
        (wheelPicker.wheelMonthPicker.layoutParams as LinearLayout.LayoutParams).weight = 1F
        (wheelPicker.wheelYearPicker.layoutParams as LinearLayout.LayoutParams).weight = 1F
        var birthday = arguments?.getString("birthday")
        if (birthday.isNullOrEmpty()) {
            birthday = "1995-01-01"
        }
        val calendar = Calendar.getInstance()
        calendar.time = UtilsCode.string2Date(birthday, SimpleDateFormat("yyyy-MM-dd"))
        wheelPicker.setYearAndMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
        wheelPicker.selectedDay = calendar.get(Calendar.DAY_OF_MONTH)
        tvConfirm.setOnClickListener {
            onSelectListener?.invoke(wheelPicker.currentDate)
            dismiss()
        }
        tvCancel.setOnClickListener {
            dismiss()
        }
    }
}