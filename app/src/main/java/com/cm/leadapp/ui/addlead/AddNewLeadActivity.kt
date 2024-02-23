package com.cm.leadapp.ui.addlead

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.cm.kbslead.R
import com.cm.kbslead.databinding.ActivityAddNewLeadBinding
import com.cm.leadapp.data.responsemodel.Country
import com.cm.leadapp.data.responsemodel.CustomerType
import com.cm.leadapp.data.responsemodel.District
import com.cm.leadapp.data.responsemodel.Products
import com.cm.leadapp.data.responsemodel.Source
import com.cm.leadapp.data.responsemodel.State
import com.cm.leadapp.ui.adapter.GeneralItemAdapter
import com.cm.leadapp.util.GenUtils
import com.cm.leadapp.util.GeneralItem
import com.cm.leadapp.util.LoadingDialog
import com.cm.leadapp.util.OnSelectCountryDialogDismissListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar


@AndroidEntryPoint
class AddNewLeadActivity : AppCompatActivity(), OnSelectCountryDialogDismissListener {

    private lateinit var binding: ActivityAddNewLeadBinding

    private val viewModel: AddNewLeadViewModel by viewModels()

    private var name = ""
    private var phone = ""
    private var phone1 = ""
    private var email = ""
    private var followupDate = ""
    private var productId = ""
    private var touchDate = ""
    private var cost = ""
    private var sourceId = ""
    private var feedbackId = ""
    private var customerCategoryId = ""
    private var countryId = "99"
    private var stateId = ""
    private var districtId = ""
    private var city = ""

    private lateinit var stateAdapter: GeneralItemAdapter
    private lateinit var districtAdapter: GeneralItemAdapter
    private lateinit var sourceAdapter: GeneralItemAdapter
    private lateinit var productAdapter: GeneralItemAdapter
    private lateinit var customerAdapter: GeneralItemAdapter

    private lateinit var stateArrList: ArrayList<State>
    private lateinit var districtArrList: ArrayList<District>
    private lateinit var sourceArrList: ArrayList<Source>
    private lateinit var productArrList: ArrayList<Products>
    private lateinit var customerArrList: ArrayList<CustomerType>

    private lateinit var loadingDialog: LoadingDialog

    private lateinit var calendar: Calendar

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNewLeadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar

        actionBar?.apply {
            title = getString(R.string.new_lead)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        loadingDialog = LoadingDialog(this)
        calendar = Calendar.getInstance()

        setupListeners()
        setupSpinners()
        setDateAndTime()
        setupObservers()
        collectFlow()
        viewModel.getGeneralList()
    }

    private fun setDateAndTime() {
        mYear = calendar.get(Calendar.YEAR)
        mMonth = calendar.get(Calendar.MONTH)
        mDay = calendar.get(Calendar.DAY_OF_MONTH)
        mHour = calendar.get(Calendar.HOUR_OF_DAY)
        mMinute = calendar.get(Calendar.MINUTE)

        val date = GenUtils.getConvertedDate(calendar, mYear, mMonth, mDay, pattern = "dd/MM/yyyy")

        binding.etLeadTouchDate.setText(date)
        binding.etFollowupDate.setText(date)
        followupDate = GenUtils.getFollowupDate(calendar, mYear, mMonth, mDay, mHour, mMinute)

        val time = GenUtils.getConvertedTime(calendar, mHour, mMinute)

        binding.etFollowupTime.setText(time)
    }

    private fun setupSpinners() {
        binding.apply {
            spinState.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    stateId = stateArrList[position].id.toString()

                    if (binding.progressDistrict.visibility != View.VISIBLE)
                        binding.progressDistrict.visibility = View.VISIBLE

                    viewModel.getDistricts(stateId)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

            spinDistrict.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    districtId = districtArrList[position].id.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

            spinSource.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    sourceId = sourceArrList[position].id.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

            spinProducts.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    productId = productArrList[position].id.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }


            spinType.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    customerCategoryId = customerArrList[position].id.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            countryList.observe(this@AddNewLeadActivity) {
                binding.apply {

                    progressNewLead.visibility = View.GONE
                    scrollView.visibility = View.VISIBLE

                    tvSelectedCountry.text = getString(R.string.india)

                    if (binding.progressState.visibility != View.VISIBLE)
                        binding.progressState.visibility = View.VISIBLE

                    viewModel.getStates(countryId)
                }
            }

            customerTypeList.observe(this@AddNewLeadActivity) {
                customerArrList = it
                customerAdapter = GeneralItemAdapter(
                    this@AddNewLeadActivity,
                    customerArrList,
                    GeneralItem.CUSTOMER
                )
                binding.spinType.adapter = customerAdapter
            }

            sourceList.observe(this@AddNewLeadActivity) {

                sourceArrList = it
                sourceAdapter =
                    GeneralItemAdapter(this@AddNewLeadActivity, sourceArrList, GeneralItem.SOURCE)
                binding.spinSource.adapter = sourceAdapter
            }

            productList.observe(this@AddNewLeadActivity) {

                productArrList = it
                productAdapter =
                    GeneralItemAdapter(this@AddNewLeadActivity, productArrList, GeneralItem.PRODUCT)
                binding.spinProducts.adapter = productAdapter
            }

            stateList.observe(this@AddNewLeadActivity) {

                if (binding.progressState.visibility == View.VISIBLE)
                    binding.progressState.visibility = View.INVISIBLE

                stateArrList = it
                stateAdapter =
                    GeneralItemAdapter(this@AddNewLeadActivity, stateArrList, GeneralItem.STATE)

                binding.spinState.adapter = stateAdapter
                binding.spinState.setSelection(getPreferredStatePosition("KERALA"))
            }

            districtList.observe(this@AddNewLeadActivity) {

                if (binding.progressDistrict.visibility == View.VISIBLE)
                    binding.progressDistrict.visibility = View.INVISIBLE

                districtArrList = it
                districtAdapter = GeneralItemAdapter(
                    this@AddNewLeadActivity,
                    districtArrList,
                    GeneralItem.DISTRICT
                )
                binding.spinDistrict.adapter = districtAdapter

            }

            addNewLeadResponse.observe(this@AddNewLeadActivity) {

                println(it.message)

                loadingDialog.dismissDialog()

                showAlert(it.status, it.message)
            }
        }
    }

    private fun getPreferredStatePosition(state: String): Int {
        for (i in 0 until stateArrList.size) {
            if (state.lowercase() == stateArrList[i].name?.lowercase())
                return i
        }
        return 0
    }

    private fun showAlert(status: String?, message: String?) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(status?.uppercase())
        builder.setMessage(message)

        builder.setPositiveButton("OK") { _, _ ->
            finish()
        }
        builder.show()
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            viewModel.isSubmitEnabled.collect { value ->
                activateButtonClick(value)
            }
        }
    }

    private fun activateButtonClick(value: Boolean) {
        binding.btnSave.apply {

            isClickable = value
            alpha = when (value) {
                true -> 1f
                false -> 0.3f
            }
        }
    }

    private fun setupListeners() {
        binding.apply {

            etCustomerName.addTextChangedListener {
                viewModel.setCustomerName(it.toString())
            }

            etCustomerPhone.addTextChangedListener {
                viewModel.setCustomerPhone(it.toString())
            }
            etCity.addTextChangedListener {
                viewModel.setCity(it.toString())
            }

            tvSelectedCountry.setOnClickListener {
                val dialog = SearchCountryDialogFragment.newInstance()

                dialog.setDismissListener(this@AddNewLeadActivity)

                dialog.show(supportFragmentManager, "select_country")
            }

            ivCalendar.setOnClickListener {
                launchDatePicker(etLeadTouchDate.id)
            }

            ivFollowupCalendar.setOnClickListener {
                launchDatePicker(etFollowupDate.id)
            }

            ivFollowupClock.setOnClickListener {
                launchTimePicker()
            }

            btnSave.setOnClickListener {
                loadingDialog.startLoadingDialog()

                name = etCustomerName.text.toString()
                phone = etCustomerPhone.text.toString()
                phone1 = etParentPhone.text.toString()
                email = etCustomerEmail.text.toString()
                touchDate = etLeadTouchDate.text.toString()
                cost = etCost.text.toString()
                city = etCity.text.toString()


                viewModel.addNewLead(
                    name,
                    phone,
                    phone1,
                    email,
                    followupDate,
                    productId,
                    cost,
                    touchDate,
                    sourceId,
                    feedbackId,
                    customerCategoryId,
                    countryId,
                    stateId,
                    districtId,
                    city
                )
            }
        }
    }

    private fun launchTimePicker() {
        mHour = calendar.get(Calendar.HOUR_OF_DAY)
        mMinute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            this@AddNewLeadActivity,
            { _, hourOfDay, minute ->

                mHour = hourOfDay
                mMinute = minute

                val time = GenUtils.getConvertedTime(calendar, mHour, mMinute)

                binding.etFollowupTime.setText(time)

            }, mHour, mMinute, false
        ).show()

    }

    private fun launchDatePicker(id: Int) {

        mYear = calendar.get(Calendar.YEAR)
        mMonth = calendar.get(Calendar.MONTH)
        mDay = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            this@AddNewLeadActivity,
            { _, year, month, day ->

                mYear = year
                mMonth = month
                mDay = day

                val date =
                    GenUtils.getConvertedDate(calendar, mYear, mMonth, mDay, pattern = "dd/MM/yyyy")

                if (id == binding.etFollowupDate.id) {
                    binding.etFollowupDate.setText(date)

                    followupDate =
                        GenUtils.getFollowupDate(calendar, mYear, mMonth, mDay, mHour, mMinute)
                } else
                    binding.etLeadTouchDate.setText(date)
            },
            mYear,
            mMonth,
            mDay
        ).show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onDismissed(country: Country) {
        countryId = country.id.toString()
        binding.tvSelectedCountry.text = country.name

        if (binding.progressState.visibility != View.VISIBLE)
            binding.progressState.visibility = View.VISIBLE

        viewModel.getStates(countryId)
    }
}