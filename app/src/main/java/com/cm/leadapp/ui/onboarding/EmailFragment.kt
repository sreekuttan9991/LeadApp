package com.cm.leadapp.ui.onboarding

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.cm.leadapp.R
import com.cm.leadapp.databinding.FragmentEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailFragment : Fragment() {

    private var _binding: FragmentEmailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val viewModel: OnboardingViewModel by hiltNavGraphViewModels(R.id.login_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailBinding.inflate(inflater, container, false)
        setupButtonClick()
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.loginResponse.observe(viewLifecycleOwner) { resp ->
            if(resp?.status.equals("success")){
                binding.loadingIndicator.visibility = View.GONE
                findNavController().navigate(R.id.navigation_otp)
            }else{
                binding.loadingIndicator.visibility = View.GONE
                Toast.makeText(context, resp?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupButtonClick() {
        binding.btnProceed.setOnClickListener {
            if (Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
                binding.loadingIndicator.visibility = View.VISIBLE
                viewModel.getUserData(binding.etEmail.text.toString())
            } else
                Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}