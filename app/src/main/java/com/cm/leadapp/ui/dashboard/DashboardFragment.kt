package com.cm.leadapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.cm.leadapp.R
import com.cm.leadapp.ui.onboarding.OnboardingActivity
import com.cm.leadapp.data.pref.MySharedPref
import com.cm.leadapp.databinding.FragmentDashboardBinding
import com.cm.leadapp.ui.adapter.StatisticsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val dashboardViewModel: DashboardViewModel by viewModels()

    private lateinit var statsAdapter: StatisticsAdapter

    @Inject
    lateinit var pref: MySharedPref

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        setupView()
        setupObserver()
        setupClick()
        dashboardViewModel.getUserData(pref.saleId)
        return binding.root
    }

    private fun setupClick() {
        binding.linLogout.setOnClickListener {
            pref.saleId = ""
            startActivity(Intent(requireActivity(), OnboardingActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun setupObserver() {
        dashboardViewModel.statsData.observe(viewLifecycleOwner) {
            binding.rvStats.visibility = View.VISIBLE
            binding.progressDashboard.visibility = View.GONE
            statsAdapter = StatisticsAdapter(requireActivity(), it)
            binding.rvStats.adapter = statsAdapter
        }
    }

    private fun setupView() {
        binding.apply {
            rvStats.apply {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
            }
            tvGreet.text = getString(R.string.greet, pref.userName)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}