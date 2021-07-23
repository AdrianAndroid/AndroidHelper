package com.joyy.routermapproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.imooc.router.annotations.Destination
import com.joyy.routermapproject.R

@Destination(
        url = "router://ScrollingFragment",
        description = "ScrollingFragment"
)
class ScrollingFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scrolling, container, false)
    }
}