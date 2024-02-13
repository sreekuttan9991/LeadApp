package com.cm.leadapp.ui.missed

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.cm.leadapp.ui.leaddetails.LeadDetailsActivity
import com.cm.leadapp.R
import com.cm.leadapp.data.responsemodel.FollowupData
import com.cm.leadapp.databinding.FragmentMissedFollowupBinding
import com.cm.leadapp.ui.action.LeadActionsActivity
import com.cm.leadapp.ui.adapter.MissedFollowupsAdapter
import com.cm.leadapp.util.GenUtils
import com.cm.leadapp.util.LoadingDialog
import com.cm.leadapp.util.OnFollowupsItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MissedFollowupFragment : Fragment(), OnFollowupsItemClickListener {

    private var _binding: FragmentMissedFollowupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val missedFollowupViewModel: MissedFollowupViewModel by viewModels()

    private lateinit var missedFollowupsAdapter: MissedFollowupsAdapter
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadingDialog = LoadingDialog(requireActivity())
        _binding = FragmentMissedFollowupBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupObserver()
        setupSwipeRefresh()
        getMissedFollowups()
        return binding.root
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun getMissedFollowups() {
        missedFollowupViewModel.getMissedFollowups().observe(viewLifecycleOwner) {
            binding.progressMissedFollowups.visibility = View.GONE
            binding.rvMissedFollowups.visibility = View.VISIBLE
            missedFollowupsAdapter.submitData(lifecycle, it)
            if (binding.swipeRefresh.isRefreshing)
                binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.apply {
                rvMissedFollowups.visibility = View.GONE
                progressMissedFollowups.visibility = View.VISIBLE
            }
            missedFollowupsAdapter.refresh()
        }
    }

    private fun setupObserver() {
        missedFollowupViewModel.markCompleteResp.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { resp ->
                dismissLoading()
                showAlert(resp.status, resp.message)
            }
        }
    }

    private fun setupRecyclerView() {
        missedFollowupsAdapter = MissedFollowupsAdapter(this)
        binding.rvMissedFollowups.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = missedFollowupsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(followup: FollowupData?, view: View?) {
        val bundle: Bundle by lazy {
            Bundle().apply {
                putString("lead_id", followup?.leadId)
            }
        }
        val actionIntent: Intent by lazy {
            Intent(requireActivity(), LeadActionsActivity::class.java)
        }

        when (view?.id) {
            R.id.iv_call -> GenUtils.callPhone(context, followup?.phone)
            R.id.iv_whatsapp -> GenUtils.callWhatsApp(context, followup?.phone)
            R.id.iv_edit -> {
                bundle.putString("go_to", getString(R.string.change_status))
                actionIntent.putExtras(bundle).run {
                    requireActivity().startActivity(this)
                }
            }

            R.id.iv_mark_completed -> {
                if ((view as ImageView).tag.equals("not_completed")) {
                    showLoading()
                    missedFollowupViewModel.markAsCompleted(followup?.followUpId!!)
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

    private fun showLoading() {
        loadingDialog.startLoadingDialog()
    }

    private fun dismissLoading() {
        loadingDialog.dismissDialog()
    }

    private fun showAlert(status: String?, message: String?) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(status?.uppercase())
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
            missedFollowupsAdapter.refresh()
        }
        builder.show()
    }
}