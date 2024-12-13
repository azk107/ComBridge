package com.example.combridge.pengaturan

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.combridge.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val developerList: LinearLayout = findViewById(R.id.developer_list)

        val developers = listOf(
            Developer("Hendri Yunus Wijaya", R.drawable.dev1),
            Developer("Fajar Aulia", R.drawable.dev2),
            Developer("I Putu Galih Bhramanta Yudarsana", R.drawable.dev3),
            Developer("Taufik Raihandani", R.drawable.dev4),
            Developer("Azka Amalia", R.drawable.dev5),
            Developer("Zainal Ilmi", R.drawable.dev6),
            Developer("Muhammad Azimi", R.drawable.dev7)
        )

        for (developer in developers) {
            val developerView = LayoutInflater.from(this).inflate(R.layout.item_developer, developerList, false)

            val developerImage = developerView.findViewById<ImageView>(R.id.developer_image)
            val developerName = developerView.findViewById<TextView>(R.id.developer_name)

            developerImage.setImageResource(developer.imageRes)
            developerName.text = developer.name

            developerList.addView(developerView)
        }
    }
}

data class Developer(val name: String, val imageRes: Int)