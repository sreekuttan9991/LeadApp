package com.cm.leadapp.ui.action

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cm.leadapp.data.responsemodel.FollowType
import com.cm.leadapp.databinding.FragmentAddFollowupBinding
import com.cm.leadapp.ui.adapter.StatusAdapter
import com.cm.leadapp.util.GenUtils
import com.cm.leadapp.util.LoadingDialog
import com.cm.leadapp.util.StatusType
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class AddFollowupFragment : Fragment() {

    private var _binding: FragmentAddFollowupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val addFollowupViewModel: AddFollowupViewModel by viewModels()

    private lateinit var calendar: Calendar

    private lateinit var statusAdapter: StatusAdapter

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0
    private var type: String = "1"
    private lateinit var leadId: String
    private lateinit var followupDate: String
    private lateinit var remarks: String
    private lateinit var statusList: ArrayList<FollowType>
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFollowupBinding.inflate(inflater, container, false)

        leadId = arguments?.getString("lead_id").toString()

        loadingDialog = LoadingDialog(requireActivity())
        calendar = Calendar.getInstance()

        setupObserver()
        setupClickListeners()
        getStatusList()
        setupSpinnerListener()

        return binding.root
    }

    private fun setupSpinnerListener() {
        binding.spinType.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                type = statusList[position].id.toString()
            }
        }
    }

    private fun getStatusList() {
        addFollowupViewModel.getStatusData()
    }

    private fun setupClickListeners() {
        binding.apply {
            ivCalendar.setOnClickListener {
                launchDatePicker()
            }

            ivClock.setOnClickListener {
                launchTimePicker()
            }

            btnSave.setOnClickListener {
                addFollowupViewModel.validateInputs(
                    etDate.text.toString(),
                    etTime.text.toString(),
                    etRemark.text.toString()
                )
            }
        }
    }

    private fun launchTimePicker() {
        mHour = calendar.get(Calendar.HOUR_OF_DAY)
        mMinute = calendar.get(Calendar.MINUTE)
        TimePickerDialog(
            requireActivity(),
            { _, hourOfDay, minute ->
                mHour = hourOfDay
                mMinute = minute
                val time = GenUtils.getConvertedTime(calendar, mHour, mMinute)
                binding.etTime.setText(time)

            }, mHour, mMinute, false
        ).show()
    }

    private fun launchDatePicker() {
        mYear = calendar.get(Calendar.YEAR)
        mMonth = calendar.get(Calendar.MONTH)
        mDay = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireActivity(),
            { _, year, month, day ->
                mYear = year
                mMonth = month
                mDay = day
                val date = GenUtils.getConvertedDate(calendar, mYear, mMonth, mDay)
                binding.etDate.setText(date)
            },
            mYear,
            mMonth,
            mDay
        ).show()
    }

    private fun setupObserver() {
        addFollowupViewModel.apply {
            statusData.observe(viewLifecycleOwner) {
                statusList = it.followType
                statusAdapter = StatusAdapter(requireContext(), it, StatusType.FOLLOWUP)
                binding.progressType.visibility = View.GONE
                binding.spinType.apply {
                    visibility = View.VISIBLE
                    adapter = statusAdapter
                }
            }

            validationMessage.observe(viewLifecycleOwner) {
                if (!TextUtils.isEmpty(it!!)) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                } else {
                    showLoading()
                    followupDate =
                        GenUtils.getFollowupDate(calendar, mYear, mMonth, mDay, mHour, mMinute)
                    remarks = binding.etRemark.text.toString()
                    addFollowupViewModel.addFollowup(followupDate, type, leadId, remarks)
                }
            }
            addFollowupResponse.observe(viewLifecycleOwner) {
                dismissLoading()
                showAlert(it.status, it.message)
            }
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showLoading() {

        loadingDialog.startLoadingDialog()

    }

    private fun dismissLoading() {

        loadingDialog.dismissDialog()

    }


}