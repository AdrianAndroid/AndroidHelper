package com.example.navigationdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.findNavController
import kotlin.reflect.KClass

/**
 * Time:2021/7/12 11:02
 * Author:
 * Description:
 */
fun Fragment.stepTo(kClass: KClass<out Fragment>, bundle: Bundle = Bundle()) {
    val navController = findNavController()
    val finalDestinationId = kClass.hashCode()
    val destination = FragmentNavigatorDestinationBuilder(
        navController.navigatorProvider.getNavigator(FragmentNavigator::class.java),
        finalDestinationId,
        kClass
    ).build()
    if (destination.id != navController.graph.findNode(finalDestinationId)?.id) {
        navController.graph.addDestination(destination)
    }
    navController.navigate(
        finalDestinationId,
        bundle,
        NavOptions.Builder().build(),
        null
    )
}

fun Fragment.popBack() {
    findNavController().popBackStack()
}