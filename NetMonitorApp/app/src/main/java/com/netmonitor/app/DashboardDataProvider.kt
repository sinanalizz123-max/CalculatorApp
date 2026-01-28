package com.netmonitor.app

object DashboardDataProvider {

    fun getSim1Metrics(): List<DashboardMetric> = listOf(
        DashboardMetric(
            label = "Signal Strength (RSRP)",
            value = "-85 dBm",
            status = MetricStatus.GOOD,
            trend = MetricTrend.UP
        ),
        DashboardMetric(
            label = "Signal Quality (RSRQ)",
            value = "-10 dB",
            status = MetricStatus.NORMAL
        ),
        DashboardMetric(
            label = "Noise Ratio (SINR)",
            value = "15 dB",
            status = MetricStatus.GOOD
        ),
        DashboardMetric(
            label = "PCI (Cell ID)",
            value = "342",
            status = MetricStatus.GOOD
        ),
        DashboardMetric(
            label = "Band / CA",
            value = "B40 + B3",
            status = MetricStatus.VERY_GOOD,
            trend = MetricTrend.UP
        )
    )

    fun getSim2Metrics(): List<DashboardMetric> = listOf(
        DashboardMetric(
            label = "Signal Strength (RSRP)",
            value = "-92 dBm",
            status = MetricStatus.NORMAL
        ),
        DashboardMetric(
            label = "Signal Quality (RSRQ)",
            value = "-12 dB",
            status = MetricStatus.WEAK,
            trend = MetricTrend.DOWN
        ),
        DashboardMetric(
            label = "Noise Ratio (SINR)",
            value = "8 dB",
            status = MetricStatus.WEAK
        ),
        DashboardMetric(
            label = "PCI (Cell ID)",
            value = "128",
            status = MetricStatus.NORMAL
        ),
        DashboardMetric(
            label = "Band / CA",
            value = "n78",
            status = MetricStatus.VERY_GOOD
        )
    )
}
