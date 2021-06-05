package com.zero.navigation1.activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.zero.navigation1.activity.CircularRevealHelperActivity
import com.zero.navigation1.activity.FlyInHelperActivity
import com.zero.navigation1.activity.LayerActivity

class MainFragment : ListFragment() {
    private lateinit var arrayAdapter: ArrayAdapter<String>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val array = arrayOf(
                "LayerActivity",
                "CircularRevealHelperActivity",
                "FlyInHelperActivity"
        )
        arrayAdapter = ArrayAdapter(activity!!, R.layout.simple_list_item_1, array)
        listAdapter = arrayAdapter
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) { // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id)
        val item = arrayAdapter.getItem(position)
        Toast.makeText(activity, item, Toast.LENGTH_LONG).show()
        when (position) {
            0 -> {
              startActivity(Intent(activity,LayerActivity::class.java))
            }
            1 -> {
                startActivity(Intent(activity, CircularRevealHelperActivity::class.java))
            }
            2 -> {
                startActivity(Intent(activity, FlyInHelperActivity::class.java))
            }
            3 -> {
            }
            else -> {
            }
        }
    }

    companion object {
        fun newIntance(): Fragment {
            return MainFragment()
        }
    }
}