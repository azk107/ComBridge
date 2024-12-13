package com.example.combridge.beranda

import SliderAdapter
import SliderItem
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.combridge.R
import java.util.Timer
import java.util.TimerTask

class BerandaFragment : Fragment() {

    private lateinit var viewPagerSlider: ViewPager2
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_beranda, container, false)

        val sliderItems = listOf(
            SliderItem("https://assets.loket.com/neo/production/images/banner/20240612224339_6669c22b2f4f6.jpg"),
            SliderItem("https://mediapijar.com/wp-content/uploads/2021/06/Foto-Lifestyle-Bahasa-Isyarat.jpg"),
            SliderItem("https://static.wixstatic.com/media/f75d6a_e4420e8a987f4c828ae353c4d854b648~mv2.png/v1/fill/w_454,h_341,fp_0.50_0.50,q_95,enc_auto/f75d6a_e4420e8a987f4c828ae353c4d854b648~mv2.png"),
            SliderItem("https://static.wixstatic.com/media/f75d6a_2f65de1f6c2343caa9229235d17f5648~mv2.png/v1/fill/w_454,h_341,fp_0.50_0.50,q_95,enc_auto/f75d6a_2f65de1f6c2343caa9229235d17f5648~mv2.png"),
            SliderItem("https://minikino.org/wp-content/uploads/2023/03/Seminar-Tuli-banner-web.jpg")
        )
        viewPagerSlider = view.findViewById(R.id.viewPagerSlider)
        viewPagerSlider.adapter = SliderAdapter(sliderItems)

        startAutoSlider(sliderItems.size, 5000)

        val buttonHuruf = view.findViewById<View>(R.id.iconHuruf)
        buttonHuruf.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragmentHuruf())
                .addToBackStack(null)
                .commit()
        }

        val buttonKata = view.findViewById<View>(R.id.iconKata)
        buttonKata.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, KataFragment())
                .addToBackStack(null)
                .commit()
        }

        val buttonAngka = view.findViewById<View>(R.id.iconAngka)
        buttonAngka.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AngkaFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun startAutoSlider(itemCount: Int, interval: Long) {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    if (currentPage == itemCount) {
                        currentPage = 0
                    }
                    viewPagerSlider.setCurrentItem(currentPage++, true)
                }
            }
        }, interval, interval)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}