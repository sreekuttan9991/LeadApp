package com.cm.leadapp.ui.leaddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cm.leadapp.databinding.FragmentBasicInfoBinding
import com.cm.leadapp.ui.adapter.InfoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasicInfoFragment : Fragment() {


    private var _binding: FragmentBasicInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var leadId: String? = null

    private lateinit var infoAdapter: InfoAdapter

    companion object {


        fun newInstance(leadId: String) = BasicInfoFragment().apply {

            arguments = Bundle().apply {
                putString("lead_id", leadId)
            }
        }
    }

    private val viewModel: PagerViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            leadId = it.getString("lead_id")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBasicInfoBinding.inflate(inflater, container, false)

        setupList()
        setupObserver()

        viewModel.viewLeadDetails(leadId!!)

        return binding.root
    }

    private fun setupList() {
        binding.rvBasicInfo.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun setupObserver() {
        viewModel.basicInfoData.observe(viewLifecycleOwner) {
            binding.progressInfo.visibility = View.GONE

            it.getContentIfNotHandled()?.let { infoList ->

                infoAdapter = InfoAdapter(infoList)

                binding.rvBasicInfo.adapter = infoAdapter

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}