package cn.kuwo.common.dialog

import android.content.DialogInterface
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.kuwo.common.R
import kotlinx.android.synthetic.main.dialog_option.*


/**
 * Created by shihc on 2017/7/17.
 * 底部弹出统一格式的对话框基类，方便快速创建相同风格的对话框
 */

open class BaseOptionDialog : BaseBottomDialog() {

    private lateinit var mOptionAdapter: DialogOptionAdapter

    private var mOnOptionClickListener: ((View, Int) -> Unit)? = null

    protected lateinit var mItems: List<OptionItem>

    private val title: String? = ""

    //是否显示标题
    var isShowTitle: Boolean = false

    /**
     * 是否显示取消按钮
     * @return
     */
    var isShowCancel: Boolean
        get() = false
        set(show) {
            tv_cancel.visibility = if (show) View.VISIBLE else View.GONE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mItems = arguments?.getParcelableArrayList("items") ?: ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_option, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_options.layoutManager =
            LinearLayoutManager(context)
//        rv_options.addItemDecoration(
//            HorizontalDividerItemDecoration.Builder(context).margin(
//                SizeUtils.dp2px(15f)
//            ).drawable(R.drawable.dialog_option_divider).build()
//        )
        rv_options.setHasFixedSize(true)
        mOptionAdapter = DialogOptionAdapter(mItems)
        rv_options.adapter = mOptionAdapter
        mOptionAdapter.setOnItemClickListener { _, view, position ->
            dismiss()
            if (mOptionAdapter.getItem(position)?.isEnable != false) {
                mOnOptionClickListener?.invoke(view, position)
            }
        }
        tv_title.visibility = if (isShowTitle) View.VISIBLE else View.GONE
        tv_title.text = title
        tv_cancel.visibility = if (isShowCancel) View.VISIBLE else View.GONE
        tv_cancel.setOnClickListener { dismiss() }
    }

    fun setTitle(title: CharSequence) {
        tv_title.text = title
    }

    fun getOptionItem(position: Int): OptionItem? {
        if (position >= 0 && position < mItems.size) {
            return mItems[position]
        }
        return null
    }

    fun setOnOptionClickListener(onOptionClickListener: ((View, Int) -> Unit)?) {
        mOnOptionClickListener = onOptionClickListener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        setOnOptionClickListener(null)
    }


    companion object {
        fun newInstance(items: List<OptionItem>): BaseOptionDialog {
            val args = Bundle()
            args.putParcelableArrayList("items", ArrayList(items))
            val fragment = BaseOptionDialog()
            fragment.arguments = args
            return fragment
        }
    }
}
