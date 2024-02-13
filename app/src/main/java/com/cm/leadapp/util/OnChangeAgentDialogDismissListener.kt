package com.cm.leadapp.util

import com.cm.leadapp.data.responsemodel.Agent

interface OnChangeAgentDialogDismissListener {

    fun onDismissed(agent: Agent)
}