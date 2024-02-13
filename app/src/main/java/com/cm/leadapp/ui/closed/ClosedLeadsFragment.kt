package com.cm.leadapp.ui.closed

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cm.leadapp.ui.leaddetails.LeadDetailsActivity
import com.cm.leadapp.R
import com.cm.leadapp.data.responsemodel.ClosedLeadData
import com.cm.leadapp.databinding.FragmentClosedLeadsBinding
import com.cm.leadapp.ui.action.LeadActionsActivity
import com.cm.leadapp.ui.adapter.ClosedLeadsAdapter
import com.cm.leadapp.util.OnClosedLeadsItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClosedLeadsFragment : Fragment(), OnClosedLeadsItemClickListener {

    private var _binding: FragmentClosedLeadsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val closedLeadsViewModel: ClosedLeadsViewModel by viewModels()

    private lateinit var closedLeadsAdapter: ClosedLeadsAdapter

    private var statusId = ""
    private var customerCategory = ""
    private var keyWord = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClosedLeadsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupObserver()
        setupSwipeRefresh()
        setupSearch()
        closedLeadsViewModel.setCurrentRequest(statusId, customerCategory, keyWord)
        return binding.root
    }

    private fun setupObserver() {
        closedLeadsViewModel.data.observe(viewLifecycleOwner) {
            binding.progressLeads.visibility = View.GONE
            binding.rvLeads.visibility = View.VISIBLE
            closedLeadsAdapter.submitData(lifecycle, it)
            if (binding.swipeRefresh.isRefreshing)
                binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setupSearch() {
        binding.apply {
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    keyWord = p0.toString()
                    closedLeadsViewModel.setCurrentRequest(statusId, customerCategory, keyWord)
                    closedLeadsAdapter.refresh()
                }
            })
        }
    }

    private fun setupRecyclerView() {
        closedLeadsAdapter = ClosedLeadsAdapter(this)
        binding.rvLeads.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = closedLeadsAdapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.apply {
                rvLeads.visibility = View.GONE
                progressLeads.visibility = View.VISIBLE
            }
            closedLeadsAdapter.refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(lead: ClosedLeadData?, view: View?) {
        val bundle: Bundle by lazy {
            Bundle().apply {
                putString("lead_id", lead?.leadId)
            }
        }
        val actionIntent: Intent by lazy {
            Intent(requireActivity(), LeadActionsActivity::class.java)
        }
        when (view?.id) {
            R.id.iv_view -> {
                val intent = Intent(requireActivity(), LeadDetailsActivity::class.java).apply {
                    putExtras(bundle)
                }
                requireActivity().startActivity(intent)
            }

            R.id.iv_timeline -> {
                bundle.putString("go_to", getString(R.string.lead_activity_timeline))
                actionIntent.putExtras(bundle).run {
                    requireActivity().startActivity(this)
                }
            }
        }
    }
}