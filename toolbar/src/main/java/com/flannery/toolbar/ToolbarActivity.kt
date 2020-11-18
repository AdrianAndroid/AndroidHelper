package com.flannery.toolbar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.Toolbar

/**
 * https://www.jianshu.com/p/834baf3c8f0b
 * https://www.jianshu.com/p/834baf3c8f0b
 */
class ToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val toolbar2 = findViewById<Toolbar>(R.id.toolbar2)
            val menuview2 = toolbar2.findViewById<ActionMenuView>(R.id.menuview2)
            menuInflater.inflate(R.menu.menu_toolbar_left_2, menuview2.menu)
            //点击事件
            menuview2.setOnMenuItemClickListener {
                false
            }
        }


        val toolbar3 = findViewById<Toolbar>(R.id.toolbar3)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Toast.makeText(this, "menu", Toast.LENGTH_SHORT).show()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, "item->"+item, Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }
}