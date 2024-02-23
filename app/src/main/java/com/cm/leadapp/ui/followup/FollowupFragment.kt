package com.cm.leadapp.ui.followup

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cm.kbslead.R
import com.cm.kbslead.databinding.FragmentFollowupBinding
import com.cm.leadapp.data.responsemodel.FollowupData
import com.cm.leadapp.ui.action.LeadActionsActivity
import com.cm.leadapp.ui.adapter.UpComingFollowupsAdapter
import com.cm.leadapp.ui.leaddetails.LeadDetailsActivity
import com.cm.leadapp.util.GenUtils
import com.cm.leadapp.util.LoadingDialog
import com.cm.leadapp.util.OnFollowupsItemClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FollowupFragment : Fragment(), OnFollowupsItemClickListener {

    private var _binding: FragmentFollowupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val followupViewModel: FollowupViewModel by viewModels()

    private lateinit var upComingFollowupsAdapter: UpComingFollowupsAdapter

    private lateinit var loadingDialog: LoadingDialog
    private var followupType = "1"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadingDialog = LoadingDialog(requireActivity())
        _binding = FragmentFollowupBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupToggleButtonListener()
        setupSwipeRefresh()
        setupObserver()
        followupViewModel.setCurrentRequest(followupType)
        return binding.root
    }

    private fun setupToggleButtonListener() {
        binding.apply {
            toggleButton.setOnCheckedChangeListener { p0, optId ->
                rvUpcomingFollowups.visibility = View.GONE
                progressUpcomingFollowups.visibility = View.VISIBLE
                if (optId == R.id.btn_today_followups) {
                    followupType = "1"
                    btnTodayFollowups.setTextColor(resources.getColor(R.color.white, null))
                    btnAllFollowups.setTextColor(resources.getColor(R.color.black, null))
                } else {
                    followupType = "0"
                    btnTodayFollowups.setTextColor(resources.getColor(R.color.black, null))
                    btnAllFollowups.setTextColor(resources.getColor(R.color.white, null))
                }
                followupViewModel.setCurrentRequest(followupType)
                upComingFollowupsAdapter.refresh()
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.apply {
                rvUpcomingFollowups.visibility = View.GONE
                progressUpcomingFollowups.visibility = View.VISIBLE
            }
            upComingFollowupsAdapter.refresh()
        }
    }

    private fun setupObserver() {
        followupViewModel.apply {
            followupData.observe(viewLifecycleOwner) {
                binding.apply {
                    it?.let {
                        rvUpcomingFollowups.visibility = View.VISIBLE
                        progressUpcomingFollowups.visibility = View.GONE
                        upComingFollowupsAdapter.submitData(lifecycle, it)
                        if (swipeRefresh.isRefreshing)
                            swipeRefresh.isRefreshing = false
                    } ?: run {
                        rvUpcomingFollowups.visibility = View.GONE
                        progressUpcomingFollowups.visibility = View.GONE
                        tvNoData.visibility = View.VISIBLE
                    }
                }
            }
            markCompleteResp.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { resp ->
                    dismissLoading()
                    showAlert(resp.status, resp.message)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        upComingFollowupsAdapter = UpComingFollowupsAdapter(this)
        binding.rvUpcomingFollowups.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = upComingFollowupsAdapter
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
                    followupViewModel.markAsCompleted(followup?.followUpId!!)
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
            upComingFollowupsAdapter.refresh()
        }
        builder.show()
    }
}