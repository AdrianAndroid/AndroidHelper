package com.joyy.routermapproject

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.imooc.gradle.router.runtime.Router
import com.imooc.router.annotations.Destination
import com.imooc.router.mapping.RouterPath
import java.lang.reflect.Field
import java.lang.reflect.Modifier

@Destination(
        url = "router://SecondActivity",
        description = "SecondActivity"
)
class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        findViewById<TextView>(R.id.textView).apply {
            setText(Router.toString())
        }


        getAllFieldsList(RouterPath::class.java)?.forEach {
            if (Modifier.isStatic(it.modifiers)) {
                Log.i("[SecondActivity]", "${it.name} : ${it.get(Router::class.java)}")
            }
        }
    }

    // https://blog.csdn.net/wangjun5159/article/details/79289244
    // https://blog.csdn.net/GWQ_CY/article/details/106117611
    private fun getAllFieldsList(cls: Class<*>?): List<Field> {
//        Validate.isTrue(cls != null, "The class must not be null")
        val allFields: MutableList<Field> = ArrayList<Field>()
        var currentClass = cls
        while (currentClass != null) {
            val declaredFields: Array<Field> = currentClass.declaredFields
            for (field in declaredFields) {
                allFields.add(field)
            }
            currentClass = currentClass.superclass
        }
        return allFields
    }
}