package com.ns.greg.architecturecomponentsdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ns.greg.architecturecomponentsdemo.activities.room.RoomActivity

/**
 * @author gregho
 * @since 2018/6/4
 */
class DemoActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_demo)
    findViewById<View>(R.id.room_demo_btn).setOnClickListener {
      startActivity(Intent(this, RoomActivity::class.java))
    }
  }
}