package com.rick.thridweekapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.rick.thridweekapp.databinding.ActivityMainBinding
import com.rick.thridweekapp.ui.adapter.HomePageAdapter
import com.rick.thridweekapp.ui.page.contact.ContactFragment
import com.rick.thridweekapp.ui.page.weather.WeatherFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        initView()
    }

    private fun initView() {
        binding.vpHome.getChildAt(0).let {
            if (it is RecyclerView) {
                it.overScrollMode = View.OVER_SCROLL_NEVER
            }
        }
        val fragments = listOf(WeatherFragment(), ContactFragment())
        binding.vpHome.adapter = HomePageAdapter(fragments, supportFragmentManager, lifecycle)
        binding.run {
            navView.setOnItemSelectedListener {
                vpHome.currentItem = if (it.itemId == R.id.navigation_home) 0 else 1
                true
            }

            vpHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    navView.menu.getItem(position).isChecked = true
                    binding.toolBar.title = if (position == 0) "天气" else "通讯录"
                }
            })
        }
    }
}