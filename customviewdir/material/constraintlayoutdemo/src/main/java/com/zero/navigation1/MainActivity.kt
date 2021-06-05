package com.zero.navigation1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.zero.navigation1.activity.LayerActivity
import com.zero.navigation1.databinding.ConstraintKeyframe1Binding
import com.zero.navigation1.databinding.ConstraintMotion1Binding
import com.zero.navigation1.databinding.ScrollviewBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ScrollviewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        startActivity(Intent(MainActivity@this,LayerActivity::class.java))

//        binding.root.postDelayed({binding.motionLayout.transitionToEnd()},1000)

//        binding.button.setOnClickListener {
//            val constraintSet = ConstraintSet()
//            constraintSet.load(MainActivity@this,R.layout.constraint_keyframe_2)
//            TransitionManager.beginDelayedTransition(binding.root as ConstraintLayout)
//            constraintSet.applyTo(binding.root as ConstraintLayout)
//        }

    }
}
