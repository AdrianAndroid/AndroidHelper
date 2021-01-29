package com.flannery.androidhelper.login.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.flannery.androidhelper.R

class LoginManagerFragment : Fragment() {

    companion object {
        fun newInstance() = LoginManagerFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment_manager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        if (childFragmentManager != null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.manager_container_id, LoginFragment.newInstance())
                .commitNow()
        }
    }

}