package com.cm.leadapp.ui.action

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
import com.cm.kbslead.databinding.FragmentDialogSearchAgentBinding
import com.cm.leadapp.data.responsemodel.Agent
import com.cm.leadapp.ui.adapter.AgentListAdapter
import com.cm.leadapp.util.OnAgentListItemClickListener
import com.cm.leadapp.util.OnChangeAgentDialogDismissListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAgentDialogFragment : DialogFragment(), OnAgentListItemClickListener {

    private var _binding: FragmentDialogSearchAgentBinding? = null
    private val binding get() = _binding!!
    private lateinit var agentListAdapter: AgentListAdapter

    private lateinit var selectedAgent: Agent

    private val changeAgentViewModel: ChangeAgentViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private var dialogDismissListener: OnChangeAgentDialogDismissListener? = null

    companion object {
        fun newInstance() = SearchAgentDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogSearchAgentBinding.inflate(inflater, container, false)
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
        changeAgentViewModel.agentList.observe(viewLifecycleOwner) {
            setupList(it)
        }
    }

    private fun setupList(agentList: ArrayList<Agent>) {
        agentListAdapter = AgentListAdapter(this)
        agentListAdapter.setData(agentList)
        binding.rvAgentList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = agentListAdapter
        }
    }

    fun setDismissListener(dismissListener: OnChangeAgentDialogDismissListener) {
        this.dialogDismissListener = dismissListener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (this::selectedAgent.isInitialized)
            dialogDismissListener?.onDismissed(selectedAgent)
    }

    private fun setupSearchListener() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                agentListAdapter.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    override fun onItemClick(agent: Agent) {
        selectedAgent = agent
        dismiss()
    }
}