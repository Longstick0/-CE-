package kr.ac.kpu.foodtukorea

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        supportActionBar?.setIcon(R.drawable.restaurant2)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val intent = intent

        val text1 = intent.getStringExtra("text1")
        val text2 = intent.getStringExtra("text2")
        val text3 = intent.getStringExtra("text3")
        val text4 = intent.getStringExtra("text4")
        val text5 = intent.getStringExtra("text5")
        val text6 = intent.getStringExtra("text6")

        val button = findViewById<Button>(R.id.button)
        val text11 = findViewById<TextView>(R.id.text1)
        val text22 = findViewById<TextView>(R.id.text2)
        val text33 = findViewById<TextView>(R.id.text3)
        val text44 = findViewById<TextView>(R.id.text4)
        val text55 = findViewById<TextView>(R.id.text5)
        val text66 = findViewById<TextView>(R.id.text6)

        text11.text =  text1
        text22.text =  text2
        text33.text =  text3
        text44.text =  text4
        text55.text =  text5
        text66.text =  text6

        /*
        intent.adapter = object : Intent.DefaultViewAdapter(this){
            override fun getView(intent: Intent): CharSequence {
                text = intent.getStringExtra() // infoWindow에 getMarker() -> 연결된 마커
                val row = text!!.tag as Row? // getTag() -> 필요한 row 정보 얻어옴
                // 정보창에 음식점이름, 주소(도로명x)표시
                return row!!.entrpsNm.toString() + "\n" + row!!.refineLotnoAddr.toString()
            }
        }
        */

        button.setOnClickListener {
            finish()
        }
    }

}