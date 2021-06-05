package com.flannery.customview.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.flannery.customview.R
import com.flannery.customview.canlendarscroll.CanlenderScrollActivity
import com.flannery.customview.touch.InterruptEventActivity
import com.flannery.customview.touchevent.TouchEventActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInterceptTouch.setOnClickListener {
            startActivity(Intent(activity, InterruptEventActivity::class.java))
        }

        touchevent.setOnClickListener {
            startActivity(Intent(activity, TouchEventActivity::class.java))
        }

        calendar.setOnClickListener {
            startActivity(Intent(activity, CanlenderScrollActivity::class.java))
        }
    }
}