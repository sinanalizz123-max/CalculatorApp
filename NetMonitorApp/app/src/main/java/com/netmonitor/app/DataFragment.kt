package com.netmonitor.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DataFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.frag_data, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerData)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        recycler.adapter = DataAdapter(
            listOf(
                AppUsage("YouTube", "35%", "2.3 GB"),
                AppUsage("Instagram", "22%", "1.4 GB"),
                AppUsage("Chrome", "18%", "1.2 GB"),
                AppUsage("WhatsApp", "12%", "780 MB"),
                AppUsage("Spotify", "8%", "520 MB"),
                AppUsage("Gmail", "5%", "325 MB")
            )
        )

        return view
    }
}
