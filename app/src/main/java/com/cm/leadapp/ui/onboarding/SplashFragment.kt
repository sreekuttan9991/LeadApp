package com.cm.leadapp.ui.onboarding

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cm.kbslead.R
import com.cm.kbslead.databinding.FragmentSplashBinding
import com.cm.leadapp.MainActivity
import com.cm.leadapp.data.pref.MySharedPref
import com.cm.leadapp.util.GenUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var pref: MySharedPref

    private val permission = arrayOf(Manifest.permission.READ_CALL_LOG)

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                navigate()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        if (GenUtils.hasPermissions(requireActivity() as Context, permission)) {
            navigate()
        } else {
            permReqLauncher.launch(
                permission
            )
        }
        return binding.root
    }

    private fun navigate() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (TextUtils.isEmpty(pref.saleId))
                findNavController().navigate(R.id.action_splash_to_email)
            else {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            }
        }, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}