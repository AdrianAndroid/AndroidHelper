package cn.kuwo.common.recyclerview

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.kuwo.common.BuildConfig
import java.lang.RuntimeException

class EndlessRecyclerOnScrollListener(
    val preloadNum: Int = 1/*预加载的页数*/,
    var onLoadNextPage: ((RecyclerView) -> Unit)
) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        Log.d("EndlessRecycler", "onScrolled")

        if (recyclerView.layoutManager is LinearLayoutManager) {
            val llm: LinearLayoutManager =
                recyclerView.layoutManager as LinearLayoutManager;

            if (llm.orientation == RecyclerView.HORIZONTAL) {
                // 水平滑动
                if (dx > 0 // 向上滑动
                    && llm.childCount > 0 // 可见条目够数
                    && llm.findLastVisibleItemPosition() >= llm.itemCount - preloadNum /*到了可加载的范围*/
                ) {
                    // 加载下一页
                    onLoadNextPage.invoke(recyclerView)
                }
            } else {
                throw RuntimeException("还不支持这样的操作")
            }

        } else {
            throw RuntimeException("还不支持其他的LayoutManager")
        }
    }


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        Log.d("EndlessRecycler", "onScrollStateChanged")
    }

}