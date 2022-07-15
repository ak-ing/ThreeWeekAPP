package com.rick.thridweekapp.ui.page.weather

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rick.thridweekapp.R
import com.rick.thridweekapp.bean.CityBean
import com.rick.thridweekapp.databinding.DialogLocationBinding
import com.rick.thridweekapp.databinding.FragmentWeatherBinding
import com.rick.thridweekapp.ui.adapter.CitySelectAdapter
import com.rick.thridweekapp.ui.adapter.WeatherAdapter

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var mState: WeatherViewModel
    private val mAdapter = WeatherAdapter()
    private val cityDialog by lazy { BottomSheetDialog(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mState = ViewModelProvider(this).get(WeatherViewModel::class.java)
        //读取城市数据
        readCityList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_replace, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_location) {
            cityDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        binding.tvTips.requestFocus()
    }

    /**
     * 读取城市列表
     */
    private fun readCityList() {
        val sp = requireContext().getSharedPreferences("city", Context.MODE_PRIVATE)
        if (sp.getBoolean("hasCity", false)) {
            mState.readCityFromSp(sp)
        } else {
            mState.readCityFromExel(resources.openRawResource(R.raw.citycode))
        }
    }

    /**
     * 初始化观察者
     */
    private fun initObserver() {
        mState.cityListLd.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                mState.requestWeatherToday(it[0].code)
                mState.requestWeatherSixDay(it[0].code)
                mState.saveCityList(it, requireContext().getSharedPreferences("city", Context.MODE_PRIVATE))
                initCityDialog(it)
            }
        }

        mState.weatherSixDay.observe(viewLifecycleOwner) {
            if (it != null) {
                mAdapter.setData(it.data)
            }
            binding.tvTips.requestFocus()
        }
    }

    /**
     * 构造城市选择器
     */
    private fun initCityDialog(list: List<CityBean>) {
        val inflate = DialogLocationBinding.inflate(layoutInflater)
        cityDialog.setContentView(inflate.root)
        val adapter = CitySelectAdapter()
        inflate.rvCity.adapter = adapter
        inflate.rvCity.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener {
            mState.requestWeatherToday(it.code)
            mState.requestWeatherSixDay(it.code)
            cityDialog.dismiss()
        }
        adapter.submitList(list)
    }

    /**
     * 初始化View
     */
    private fun initView() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = mState
        binding.executePendingBindings()
        binding.rvDate.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvDate.adapter = mAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}