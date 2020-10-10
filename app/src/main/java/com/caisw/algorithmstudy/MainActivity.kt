package com.caisw.algorithmstudy

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val installedApplication = packageManager.getInstalledApplications(0)
        var imageView: ImageView
        for (applicationInfo in installedApplication) {
            imageView = ImageView(this)
            imageView.setImageDrawable(applicationInfo.loadIcon(packageManager))
            linearLayout.addView(
                imageView,
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120)
            )
        }

    }
}
