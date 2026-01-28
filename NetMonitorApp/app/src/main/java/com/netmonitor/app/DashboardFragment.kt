package com.netmonitor.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.frag_dashboard, container, false)

        // SIM 1
        val sim1Recycler = view.findViewById<RecyclerView>(R.id.recyclerSim1)
        sim1Recycler.layoutManager = LinearLayoutManager(requireContext())
        sim1Recycler.adapter =
            DashboardAdapter(DashboardDataProvider.getSim1Metrics())

        // SIM 2
        val sim2Recycler = view.findViewById<RecyclerView>(R.id.recyclerSim2)
        sim2Recycler.layoutManager = LinearLayoutManager(requireContext())
        sim2Recycler.adapter =
            DashboardAdapter(DashboardDataProvider.getSim2Metrics())

        return view
    }
}
