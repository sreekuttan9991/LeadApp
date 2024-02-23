package com.cm.leadapp.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.cm.kbslead.R
import com.cm.kbslead.databinding.FragmentOtpBinding
import com.cm.leadapp.MainActivity
import com.cm.leadapp.data.pref.MySharedPref
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OtpFragment : Fragment() {

    private var userName: String = ""
    private var userEmail = ""
    private var _binding: FragmentOtpBinding? = null

    private val viewModel: OnboardingViewModel by hiltNavGraphViewModels(R.id.login_navigation)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var otpReal: Int? = 0
    private var saleId = ""

    @Inject
    lateinit var pref: MySharedPref

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        setup()
        setupObserver()
        return binding.root
    }

    private fun setupObserver() {
        viewModel.apply {
            userOtp.observe(viewLifecycleOwner) {
                otpReal = it
                println("======== $it")
                binding.loadingIndicator.visibility = View.GONE
                Toast.makeText(context, "OTP sent.", Toast.LENGTH_SHORT).show()
            }
            saleId.observe(viewLifecycleOwner) {
                if (it != null) {
                    this@OtpFragment.saleId = it
                }
            }
            userName.observe(viewLifecycleOwner) {
                this@OtpFragment.userName = it!!
            }
            userEmail.observe(viewLifecycleOwner) {
                this@OtpFragment.userEmail = it!!
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    class GenericKeyEvent internal constructor(
        private val currentView: EditText,
        private val previousView: EditText?
    ) : View.OnKeyListener {
        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != R.id.et_otp1 && currentView.text!!.isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView!!.text = null
                previousView.requestFocus()
                return true
            }
            return false
        }
    }

    private fun setup() {
        binding.apply {
            etOtp1.addTextChangedListener(
                GenericTextWatcher(
                    etOtp1,
                    etOtp2
                )
            )
            etOtp2.addTextChangedListener(
                GenericTextWatcher(
                    etOtp2,
                    etOtp3
                )
            )
            etOtp3.addTextChangedListener(
                GenericTextWatcher(
                    etOtp3,
                    etOtp4
                )
            )
            etOtp4.addTextChangedListener(
                GenericTextWatcher(
                    etOtp4,
                    etOtp5
                )
            )
            etOtp5.addTextChangedListener(
                GenericTextWatcher(
                    etOtp5,
                    etOtp6
                )
            )
            etOtp6.addTextChangedListener(GenericTextWatcher(etOtp6, null))

            etOtp1.setOnKeyListener(GenericKeyEvent(etOtp1, null))
            etOtp2.setOnKeyListener(GenericKeyEvent(etOtp2, etOtp1))
            etOtp3.setOnKeyListener(GenericKeyEvent(etOtp3, etOtp2))
            etOtp4.setOnKeyListener(GenericKeyEvent(etOtp4, etOtp3))
            etOtp5.setOnKeyListener(GenericKeyEvent(etOtp5, etOtp4))
            etOtp6.setOnKeyListener(GenericKeyEvent(etOtp6, etOtp5))

            btnVerify.setOnClickListener {
                if (getOtpString() == "" + otpReal) {
                    pref.saleId = saleId
                    pref.userName = userName
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()
                } else {
                    Toast.makeText(context, "Invalid OTP.", Toast.LENGTH_SHORT).show()
                }
            }

            tvResend.setOnClickListener {
                binding.loadingIndicator.visibility = View.VISIBLE
                viewModel.getUserData(userEmail)
            }
        }
    }

    private fun getOtpString(): String {

        return binding.run {
            etOtp1.text.toString() +
                    etOtp2.text.toString() +
                    etOtp3.text.toString() +
                    etOtp4.text.toString() +
                    etOtp5.text.toString() +
                    etOtp6.text.toString()
        }
    }

    class GenericTextWatcher internal constructor(
        private val currentView: View,
        private val nextView: View?
    ) :
        TextWatcher {
        override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
            val text = editable.toString()
            when (currentView.id) {
                R.id.et_otp1 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.et_otp2 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.et_otp3 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.et_otp4 -> if (text.length == 1) nextView!!.requestFocus()
                R.id.et_otp5 -> if (text.length == 1) nextView!!.requestFocus()
            }
        }

        override fun beforeTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) { // TODO Auto-generated method stub
        }

        override fun onTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) { // TODO Auto-generated method stub
        }
    }
}