package com.example.navigationdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.findNavController
import kotlin.reflect.KClass

/**
 * Time:2021/6/30 09:58
 * Author:
 * Description:
 */
class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.to_step_one).setOnClickListener {
            stepTo(FlowStepFragment::class)
        }
    }

//    private fun stepTo(kClass: KClass<out Fragment>) {
//        val navController = findNavController()
//        val finalDestinationId = kClass.hashCode()
//        val destination = FragmentNavigatorDestinationBuilder(
//            navController.navigatorProvider.getNavigator(FragmentNavigator::class.java),
//            finalDestinationId,
//            kClass
//        ).build()
//        if (destination.id != navController.graph.findNode(finalDestinationId)?.id) {
//            navController.graph.addDestination(destination)
//        }
//        navController.navigate(
//            finalDestinationId,
//            Bundle(),
//            NavOptions.Builder().build(),
//            null
//        )
//    }
}