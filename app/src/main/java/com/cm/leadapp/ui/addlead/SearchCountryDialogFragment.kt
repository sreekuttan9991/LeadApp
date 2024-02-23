package com.cm.leadapp.ui.addlead

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cm.kbslead.databinding.FragmentDialogSearchCountryBinding
import com.cm.leadapp.data.responsemodel.Country
import com.cm.leadapp.ui.adapter.CountryListAdapter
import com.cm.leadapp.util.OnCountryListItemClickListener
import com.cm.leadapp.util.OnSelectCountryDialogDismissListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCountryDialogFragment : DialogFragment(), OnCountryListItemClickListener {

    private var _binding: FragmentDialogSearchCountryBinding? = null
    private val binding get() = _binding!!
    private lateinit var countryListAdapter: CountryListAdapter

    private lateinit var selectedCountry: Country

    private val viewModel: AddNewLeadViewModel by viewModels(ownerProducer = { requireActivity() })

    private var dialogDismissListener: OnSelectCountryDialogDismissListener? = null

    companion object {
        fun newInstance() = SearchCountryDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogSearchCountryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupSearchListener()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupObserver() {
        viewModel.countryList.observe(viewLifecycleOwner) {
            setupList(it)
        }
    }

    private fun setupList(countryList: ArrayList<Country>) {
        countryListAdapter = CountryListAdapter(this)
        countryListAdapter.setData(countryList)
        binding.rvCountryList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = countryListAdapter
        }
    }

    fun setDismissListener(dismissListener: OnSelectCountryDialogDismissListener) {
        this.dialogDismissListener = dismissListener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (this::selectedCountry.isInitialized)
            dialogDismissListener?.onDismissed(selectedCountry)
    }


    private fun setupSearchListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                countryListAdapter.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    override fun onItemClick(country: Country) {
        selectedCountry = country
        dismiss()
    }
}