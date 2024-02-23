package com.cm.leadapp.ui.action

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cm.kbslead.R
import com.cm.kbslead.databinding.ActivityLeadActionsBinding
import com.cm.leadapp.ui.common.ChangeStatusFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeadActionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeadActionsBinding
    private lateinit var goTo: String
    private lateinit var leadId: String

    companion object {
        const val goToKey = "go_to"
        const val leadIdKey = "lead_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeadActionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            goTo = it.getStringExtra(goToKey) as String
            leadId = it.getStringExtra(leadIdKey) as String
        }

        val actionBar: ActionBar? = supportActionBar

        actionBar?.apply {
            title = goTo
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        val bundle = Bundle().apply {
            putString(leadIdKey, leadId)
        }
        showActionFragment(when (goTo) {
            getString(R.string.add_followup) -> {
                AddFollowupFragment().apply {
                    arguments = bundle
                }
            }

            getString(R.string.change_status) -> {
                ChangeStatusFragment().apply {
                    arguments = bundle
                }
            }

            getString(R.string.change_agent) -> {
                ChangeAgentFragment().apply {
                    arguments = bundle
                }
            }

            getString(R.string.lead_activity_timeline) -> {
                TimelineFragment().apply {
                    arguments = bundle
                }
            }

            else -> {
                Fragment()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else super.onOptionsItemSelected(item)
    }


    private fun showActionFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
            .commit()
    }
}