package com.cm.leadapp.ui.leaddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cm.leadapp.databinding.FragmentFollowupInfoBinding
import com.cm.leadapp.ui.adapter.InfoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowupInfoFragment : Fragment() {

    private var _binding: FragmentFollowupInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PagerViewModel by activityViewModels()

    private lateinit var infoAdapter: InfoAdapter

    companion object {
        fun newInstance() = FollowupInfoFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowupInfoBinding.inflate(inflater, container, false)

        setupList()
        setupObserver()

        return binding.root
    }

    private fun setupObserver() {
        viewModel.followupInfoData.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let { infoList ->
                infoAdapter = InfoAdapter(infoList)

                binding.rvFollowupInfo.adapter = infoAdapter
            }
        }
    }

    private fun setupList() {

        binding.rvFollowupInfo.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}