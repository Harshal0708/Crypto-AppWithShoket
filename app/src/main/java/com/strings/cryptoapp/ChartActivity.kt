package com.strings.cryptoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.strings.cryptoapp.domain.model.AirQualityData
import com.strings.cryptoapp.ui.airqualitymain.AirQualityMainViewModel
import com.strings.cryptoapp.ui.airqualitymain.ChartViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChartActivity : AppCompatActivity() {

    private val viewModel: ChartViewModel by viewModels()
    var city: String? = null
    private var dataPoint = mutableListOf<Double>()
    var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        city = intent.getStringExtra("city")

        println("$city")
        supportActionBar?.hide()


//        val chart = findViewById<AAChartView>(R.id.chart)


        lifecycleScope.launch {
            viewModel.airQualityDataList.collectLatest {
                it?.let {

                    getCityDataPoints(it.airQualityDataList)
//                    val chartModel = AAChartModel()
//                        .chartType(AAChartType.Line)
//                        .title("Air Quality Data")
//                        .dataLabelsEnabled(true)
//                        .series(
//                            arrayOf(
//                                AASeriesElement()
//                                    .name(city)
//                                    .data(dataPoint.toTypedArray())
//                            )
//                        )
//
//                    chart.aa_drawChartWithChartModel(chartModel)

                }

            }

        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.connectToSocket()
    }

    override fun onStop() {
        super.onStop()
        viewModel.disconnect()
    }

    private fun getCityDataPoints(data: List<AirQualityData>) {
        val a = emptyArray<Any>()
        for (points in data) {
            if (points.city.equals(city)) {
                dataPoint.add(i, (Math.round(points.aqi * 100) / 100.0))
                i++
            }
        }

    }

}
