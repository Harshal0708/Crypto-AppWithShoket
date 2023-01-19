package com.strings.cryptoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.strings.airqualityvisualizer.data.adapter.AirListClickListener
import com.strings.airqualityvisualizer.data.adapter.AirQualityAdapter
import com.strings.airqualityvisualizer.databinding.ActivityMain2Binding
import com.strings.airqualityvisualizer.ui.airqualitymain.AirQualityMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity2 : AppCompatActivity() {

    private val viewModel : AirQualityMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityMain2Binding = DataBindingUtil.setContentView(this,R.layout.activity_main2)

        val airAdapter = AirQualityAdapter(AirListClickListener {
            it ->viewModel.onNavigateToChart(it)
        })


        binding.airQualityList.adapter = airAdapter

        lifecycleScope.launch {
            viewModel.airQualityDataList.collectLatest {
                it?.let {
                    airAdapter.submitList(it.airQualityDataList)
                }
            }
        }

        lifecycleScope.launch{
            viewModel.navigateToChartEvent.collectLatest {
                val intent = Intent(applicationContext,ChartActivity::class.java).apply{
                    putExtra("city",it)
                }
                startActivity(intent)
            }
        }

        binding.lifecycleOwner = this



    }

    override fun onStart() {
        super.onStart()
        viewModel.connectToSocket()
        displayToast()
    }

    override fun onStop() {
        super.onStop()
        viewModel.disconnect()
    }

    private fun displayToast(){
        lifecycleScope.launch {
            viewModel.toastEvent.collectLatest {
                    message ->
                Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
            }
        }
    }


}