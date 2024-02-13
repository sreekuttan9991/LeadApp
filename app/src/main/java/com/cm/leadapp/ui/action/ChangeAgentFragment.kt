package com.cm.leadapp.ui.action

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cm.leadapp.data.responsemodel.Agent
import com.cm.leadapp.data.pref.MySharedPref
import com.cm.leadapp.databinding.FragmentChangeAgentBinding
import com.cm.leadapp.util.LoadingDialog
import com.cm.leadapp.util.OnChangeAgentDialogDismissListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChangeAgentFragment : Fragment(), OnChangeAgentDialogDismissListener {

    private var _binding: FragmentChangeAgentBinding? = null
    private val binding get() = _binding!!

    private val changeAgentViewModel: ChangeAgentViewModel by viewModels()

    private lateinit var loadingDialog: LoadingDialog

    private lateinit var saleId: String
    private lateinit var leadId: String
    private lateinit var agent: Agent

    @Inject
    lateinit var pref: MySharedPref

    private lateinit var agentList: ArrayList<Agent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeAgentBinding.inflate(inflater, container, false)
        leadId = arguments?.getString("lead_id").toString()
        saleId = pref.saleId
        loadingDialog = LoadingDialog(requireActivity())
        setupObserver()
        setupClickListener()
        changeAgentViewModel.getAgentList()
        return binding.root
    }

    private fun setupClickListener() {
        binding.apply {
            tvSelectedAgent.setOnClickListener {

                val dialog = SearchAgentDialogFragment.newInstance()

                dialog.setDismissListener(this@ChangeAgentFragment)

                dialog.show(childFragmentManager, "change_agent")
            }
            btnChangeAgent.setOnClickListener {
                loadingDialog.startLoadingDialog()
                changeAgentViewModel.changeAgent(saleId, leadId)
            }
        }
    }

    private fun setupObserver() {
        changeAgentViewModel.agentList.observe(viewLifecycleOwner) {
            it?.let {
                agentList = it
                binding.apply {
                    progressChangeAgent.visibility = View.GONE
                    tvSelectedAgent.visibility = View.VISIBLE
                    tvSelectedAgent.text = pref.userName
                }
            }
        }
        changeAgentViewModel.changeAgentResponse.observe(viewLifecycleOwner) {
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

    override fun onDismissed(agent: Agent) {
        this.agent = agent
        binding.tvSelectedAgent.text = agent.name
        saleId = agent.id!!
    }
}