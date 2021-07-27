package com.fannery.gradledownload

import android.os.Bundle
import android.util.Log

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resourceEntryName = resources.getResourceEntryName(R.string.app_name)
        Log.e("MainActivity", resourceEntryName);
        val resourceTypeName = resources.getResourceTypeName(R.string.app_name)
        Log.e("MainActivity", resourceTypeName);
    }

    var isDefaultSkin = true
    var hashMap: HashMap<String, String> = HashMap()

    fun getString2(resId: Int): String {
        if (isDefaultSkin) {
            resources.getString(resId)
        }
        val resourceEntryName = resources.getResourceEntryName(resId)
        val s = hashMap[resourceEntryName]
        return if (s == null) return resources.getString(resId) else s
    }


}