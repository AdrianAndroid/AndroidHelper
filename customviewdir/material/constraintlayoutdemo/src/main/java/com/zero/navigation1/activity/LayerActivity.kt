package com.zero.navigation1.activity

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zero.navigation1.databinding.LayoutLayerBinding
import kotlin.math.sin

class LayerActivity : AppCompatActivity() {

    private val binding by lazy {
        LayoutLayerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button8.setOnClickListener {

            val anim = ValueAnimator.ofFloat(0f, 360f)
            anim.duration = 300
            anim.addUpdateListener { animation ->
                val angle = animation.animatedValue as Float
                binding.layer.rotation = angle
                binding.layer.scaleX = 1 + (180 - Math.abs(angle - 180)) / 20f
                binding.layer.scaleY = 1 + (180 - Math.abs(angle - 180)) / 20f


                var shift_x = 500 * sin(Math.toRadians((angle * 5).toDouble())).toFloat()
                var shift_y = 500 * sin(Math.toRadians((angle * 7).toDouble())).toFloat()
                binding.layer.translationX = shift_x
                binding.layer.translationY = shift_y
            }
            anim.duration = 4000
            anim.start()
        }


    }
}
