package com.cm.leadapp.ui.leaddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cm.kbslead.databinding.FragmentFollowupHistoryBinding
import com.cm.leadapp.ui.adapter.FollowupHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowupHistoryFragment : Fragment() {

    private var _binding: FragmentFollowupHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PagerViewModel by activityViewModels()

    private lateinit var followupHistoryAdapter: FollowupHistoryAdapter

    companion object {
        fun newInstance() = FollowupHistoryFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowupHistoryBinding.inflate(inflater, container, false)
        setupList()
        setupObserver()
        return binding.root
    }

    private fun setupObserver() {
        viewModel.followupHistoryList.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { hisList ->
                followupHistoryAdapter = FollowupHistoryAdapter(hisList)
                binding.rvFollowupHistory.adapter = followupHistoryAdapter
            }
        }
    }

    private fun setupList() {
        binding.rvFollowupHistory.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}