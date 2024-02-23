package com.cm.leadapp.ui.common

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cm.kbslead.databinding.FragmentChangeStatusBinding
import com.cm.leadapp.data.responsemodel.FinalStatus
import com.cm.leadapp.data.responsemodel.Status
import com.cm.leadapp.ui.action.LeadActionsActivity
import com.cm.leadapp.ui.adapter.StatusAdapter
import com.cm.leadapp.util.LoadingDialog
import com.cm.leadapp.util.StatusType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeStatusFragment : Fragment() {

    private var _binding: FragmentChangeStatusBinding? = null
    private val binding get() = _binding!!

    private val changeStatusViewModel: ChangeStatusViewModel by viewModels()

    private lateinit var statusAdapter: StatusAdapter
    private lateinit var finalStatusAdapter: StatusAdapter

    private lateinit var statusList: ArrayList<Status>
    private lateinit var finalStatusList: ArrayList<FinalStatus>

    private lateinit var loadingDialog: LoadingDialog

    private var statusId: String = "1"
    private var finalStatusId: String = ""
    private lateinit var leadId: String
    private var invoiceNumber: String = ""
    private var invoiceValue: String = ""
    private var models: String = ""
    private var brandName: String = ""
    private var reason: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeStatusBinding.inflate(inflater, container, false)
        leadId = arguments?.getString("lead_id").toString()
        loadingDialog = LoadingDialog(requireActivity())
        setupObserver()
        setupSpinnerListener()
        setupClickListener()
        changeStatusViewModel.getStatusData()
        return binding.root
    }

    private fun setupClickListener() {
        binding.btnChangeStatus.setOnClickListener {
            loadingDialog.startLoadingDialog()
            binding.apply {
                invoiceNumber = etInvoiceNumber.text.toString()
                invoiceValue = etInvoiceValue.text.toString()
                models = etModels.text.toString()
                reason = etReason.text.toString()
                brandName = etBrandName.text.toString()
            }
            changeStatusViewModel.changeStatus(
                leadId,
                statusId,
                finalStatusId,
                invoiceNumber,
                models,
                invoiceValue,
                brandName,
                reason
            )
        }
    }

    private fun setupSpinnerListener() {
        binding.spinStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                statusId = statusList[position].id.toString()

                if (statusId == "2") {
                    binding.apply {
                        tvLabelFinalStatus.visibility = View.VISIBLE
                        relFinalStatus.visibility = View.VISIBLE
                        spinFinalStatus.adapter = finalStatusAdapter
                    }
                } else {
                    showOriginalView()
                }
            }
        }

        binding.spinFinalStatus.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    finalStatusId = finalStatusList[position].id.toString()

                    when (finalStatusId) {
                        "1" -> {
                            showGlenPurchaseView()
                        }

                        "2" -> {
                            showOtherBrandPurchaseView()
                        }

                        else -> {
                            showReasonView()
                        }
                    }
                }
            }
    }

    private fun showReasonView() {
        binding.apply {
            linReason.visibility = View.VISIBLE
            goneView(linGlenPurchase)
            goneView(linOtherBrandPurchase)
        }
    }

    private fun showOtherBrandPurchaseView() {
        binding.apply {
            linOtherBrandPurchase.visibility = View.VISIBLE
            linReason.visibility = View.VISIBLE
            goneView(linGlenPurchase)
        }
    }

    private fun showGlenPurchaseView() {
        binding.apply {
            linGlenPurchase.visibility = View.VISIBLE
            goneView(linReason)
            goneView(linOtherBrandPurchase)
        }
    }

    private fun showOriginalView() {
        binding.apply {
            goneView(tvLabelFinalStatus)
            goneView(relFinalStatus)
            goneView(linGlenPurchase)
            goneView(linOtherBrandPurchase)
            goneView(linReason)
        }
    }

    private fun goneView(view: View) {
        if (view.isVisible)
            view.isVisible = false
    }

    private fun setupObserver() {
        changeStatusViewModel.statusData.observe(viewLifecycleOwner) {
            binding.progressChangeStatus.visibility = View.GONE
            binding.spinStatus.visibility = View.VISIBLE

            statusList = it.status
            finalStatusList = it.finalStatus

            statusAdapter = StatusAdapter(requireContext(), it, StatusType.STATUS)
            finalStatusAdapter = StatusAdapter(requireContext(), it, StatusType.FINAL_STATUS)

            binding.spinStatus.adapter = statusAdapter
        }

        changeStatusViewModel.changeStatusResponse.observe(viewLifecycleOwner) {
            loadingDialog.dismissDialog()
            showAlert(it.status, it.message)
        }
    }

    private fun showAlert(status: String?, message: String?) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(status?.uppercase())
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton("OK") { dialog, which ->
            (requireActivity() as LeadActionsActivity).finish()
        }
        builder.show()
    }
}