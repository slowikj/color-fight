package com.example.colorfight.ui.statistics

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import com.example.colorfight.R
import com.example.colorfight.data.color.model.colorcounts.ColorCounts
import com.example.colorfight.ui.base.BaseFragment
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.statistics_fragment_layout.*
import javax.inject.Inject

class StatisticsFragment : BaseFragment(), StatisticsContract.View {

	override val layoutId: Int
		get() = R.layout.statistics_fragment_layout

	@Inject
	lateinit var presenter: StatisticsContract.Presenter<StatisticsContract.View>

	override fun attachPresenter() {
		presenter.attach(this)
	}

	override fun detachPresenter() {
		presenter.detach()
	}

	override fun onAttach(context: Context?) {
		super.onAttach(context)
		fragmentComponent.inject(this)
	}

	override fun onStart() {
		super.onStart()
        presenter.requestStatistics()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setUpGraphView()
	}

	private fun setUpGraphView() {
		graphView.gridLabelRenderer.isHorizontalLabelsVisible = true
		graphView.viewport.isYAxisBoundsManual = true
		graphView.viewport.setMinY(0.toDouble())

		graphView.viewport.isXAxisBoundsManual = true
		graphView.viewport.setMinX(0.toDouble())
		graphView.viewport.setMaxX(4.toDouble())

		graphView.title = getString(R.string.previous_day_stats)
	}

	override fun onNetworkError() {
		Toast.makeText(context, R.string.network_error_occurred, Toast.LENGTH_LONG)
			.show()
	}

	override fun setPreviousColors(colorCounts: ColorCounts) {
		val barCounts = arrayOf(0, colorCounts.red, colorCounts.green, colorCounts.blue, 0)
			.map { it.toDouble() }
		val dataPoints = Array(barCounts.size) {
			DataPoint(it.toDouble(), barCounts[it])
		}
		val barColors = arrayOf(R.color.white, R.color.red, R.color.green, R.color.blue, R.color.white)
			.map { getColor(context!!, it) }
		setBarPlot(graphView, dataPoints, barColors, barCounts)
	}

	private fun setBarPlot(
		graphView: GraphView,
		dataPoints: Array<DataPoint>,
		barColors: List<Int>,
		barCounts: List<Double>
	) {
		setLabelsFormatter(getBarNames(barCounts))

		val barPlot = BarGraphSeries(dataPoints)
		barPlot.setValueDependentColor { barColors[it.x.toInt()] }
		barPlot.isDrawValuesOnTop = false

		graphView.viewport.setMaxY(barCounts.max()!!.toDouble() + 1)
		graphView.addSeries(barPlot)
	}

	private fun setLabelsFormatter(barNames: Array<String>) {
		val labelsFormatter = StaticLabelsFormatter(graphView)
		labelsFormatter.setHorizontalLabels(barNames)
		graphView.gridLabelRenderer.labelFormatter = labelsFormatter
	}

	private fun getBarNames(barCounts: List<Double>): Array<String> {
		return (listOf("") +
				listOf("red", "green", "blue").zip(barCounts) { color, count -> "$color (${count.toLong()})" } +
				listOf(""))
			.toTypedArray()
	}
}