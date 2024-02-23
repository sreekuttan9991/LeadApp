package com.cm.leadapp.ui.action


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cm.kbslead.databinding.FragmentTimelineBinding
import com.cm.leadapp.ui.adapter.TimelineAdapter
import com.lriccardo.timelineview.TimelineDecorator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimelineFragment : Fragment() {

    private var _binding: FragmentTimelineBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val timelineViewModel: TimelineViewModel by viewModels()

    private lateinit var timelineAdapter: TimelineAdapter

    private lateinit var leadId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
        leadId = arguments?.getString("lead_id").toString()
        setupList()
        setupObserver()
        timelineViewModel.getTimeline(leadId)
        return binding.root
    }

    private fun setupObserver() {
        timelineViewModel.timelineData.observe(viewLifecycleOwner) {
            timelineAdapter = TimelineAdapter(it)
            binding.apply {
                rvTimeline.visibility = View.VISIBLE
                progressTimeline.visibility = View.GONE
                rvTimeline.adapter = timelineAdapter
            }
        }
    }

    private fun setupList() {
        binding.rvTimeline.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(
                TimelineDecorator(
                    indicatorSize = 20f,
                    indicatorColor = Color.parseColor("#FFF50057"),
                    lineColor = Color.parseColor("#FF9C27B0"),
                    lineWidth = 8f,
                    position = TimelineDecorator.Position.Left,
                    padding = 48f
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}