package com.cm.leadapp.ui.leads

import android.app.AlertDialog
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
import com.cm.kbslead.R
import com.cm.kbslead.databinding.FragmentLeadsBinding
import com.cm.leadapp.data.responsemodel.LeadData
import com.cm.leadapp.ui.action.LeadActionsActivity
import com.cm.leadapp.ui.adapter.LeadsAdapter
import com.cm.leadapp.ui.leaddetails.LeadDetailsActivity
import com.cm.leadapp.util.GenUtils
import com.cm.leadapp.util.LoadingDialog
import com.cm.leadapp.util.OnLeadsItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeadsFragment : Fragment(), OnLeadsItemClickListener {

    private var _binding: FragmentLeadsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val leadsViewModel: LeadsViewModel by viewModels()
    private lateinit var leadsAdapter: LeadsAdapter
    private lateinit var loadingDialog: LoadingDialog
    private var statusId = ""
    private var customerCategory = ""
    private var keyWord = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeadsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupObserver()
        setupSearch()
        setupSwipeRefresh()
        leadsViewModel.setCurrentRequest(statusId, customerCategory, keyWord)
        return binding.root
    }

    private fun setupObserver() {
        leadsViewModel.apply {
            data.observe(viewLifecycleOwner) {
                binding.progressLeads.visibility = View.GONE
                binding.rvLeads.visibility = View.VISIBLE
                leadsAdapter.submitData(lifecycle, it)
                if (binding.swipeRefresh.isRefreshing)
                    binding.swipeRefresh.isRefreshing = false
            }

            trashLeadResp.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { resp ->
                    loadingDialog.dismissDialog()
                    showAlert(resp.status, resp.message)
                }
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.apply {
                rvLeads.visibility = View.GONE
                progressLeads.visibility = View.VISIBLE
            }
            leadsAdapter.refresh()
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
                    leadsViewModel.setCurrentRequest(statusId, customerCategory, keyWord)
                    leadsAdapter.refresh()
                }
            })
        }
    }

    private fun setupRecyclerView() {
        leadsAdapter = LeadsAdapter(this)
        binding.rvLeads.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = leadsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(lead: LeadData?, view: View?) {
        val bundle: Bundle by lazy {
            Bundle().apply {
                putString("lead_id", lead?.leadId)
            }
        }
        val actionIntent: Intent by lazy {
            Intent(requireActivity(), LeadActionsActivity::class.java)
        }
        when (view?.id) {
            R.id.iv_call -> GenUtils.callPhone(context, lead?.phone)
            R.id.iv_whatsapp -> GenUtils.callWhatsApp(context, lead?.phone)
            R.id.iv_edit -> {
                bundle.putString("go_to", getString(R.string.change_status))
                actionIntent.putExtras(bundle).run {
                    requireActivity().startActivity(this)
                }
            }

            R.id.iv_add_followup -> {
                bundle.putString("go_to", getString(R.string.add_followup))
                actionIntent.putExtras(bundle).run {
                    requireActivity().startActivity(this)
                }
            }

            R.id.iv_timeline -> {
                bundle.putString("go_to", getString(R.string.lead_activity_timeline))
                actionIntent.putExtras(bundle).run {
                    requireActivity().startActivity(this)
                }
            }

            R.id.iv_change_agent -> {
                bundle.putString("go_to", getString(R.string.change_agent))
                actionIntent.putExtras(bundle).run {
                    requireActivity().startActivity(this)
                }
            }

            R.id.iv_view -> {
                val intent = Intent(requireActivity(), LeadDetailsActivity::class.java).apply {
                    putExtras(bundle)
                }
                requireActivity().startActivity(intent)
            }
        }
    }

    private fun moveToTrash(leadId: String?) {
        loadingDialog = LoadingDialog(requireActivity())
        loadingDialog.startLoadingDialog()
        leadsViewModel.moveLeadToTrash(leadId!!)
    }

    private fun showAlert(status: String?, message: String?) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(status?.uppercase())
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
            leadsAdapter.refresh()
        }
        builder.show()
    }
}