package com.flannery.multilanguage.confict4

import android.content.Context
import android.view.ViewConfiguration
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.ln

/**
 * Time:2021/7/1 19:07
 * Author:
 * Description:
 */
class FlingHelper(context: Context) {

    private val DECELERATION_RATE = (ln(0.78) / ln(0.9)).toFloat()
    private val mFlingFriction = ViewConfiguration.getScrollFriction()
    private var mPhysicalCoeff: Float =
        context.resources.displayMetrics.density * 160.0f * 386.0878f * 0.84f

//    fun FlingHelper(context: Context) {
//        mPhysicalCoeff = context.resources.displayMetrics.density * 160.0f * 386.0878f * 0.84f
//    }

    private fun getSplineDeceleration(i: Int): Double {
        return ln(
            (0.35f * abs(i).toFloat() / (mFlingFriction * mPhysicalCoeff)).toDouble()
        )
    }

    private fun getSplineDecelerationByDistance(d: Double): Double {
        return (DECELERATION_RATE.toDouble() - 1.0) * ln(d / (mFlingFriction * mPhysicalCoeff).toDouble()) / DECELERATION_RATE.toDouble()
    }

    fun getSplineFlingDistance(i: Int): Double {
        return exp(getSplineDeceleration(i) * (DECELERATION_RATE.toDouble() / (DECELERATION_RATE.toDouble() - 1.0))) * (mFlingFriction * mPhysicalCoeff).toDouble()
    }

    fun getVelocityByDistance(d: Double): Int {
        return abs((exp(getSplineDecelerationByDistance(d)) * mFlingFriction.toDouble() * mPhysicalCoeff.toDouble() / 0.3499999940395355).toInt())
    }

}