package com.cm.leadapp.ui.leaddetails

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.cm.kbslead.R
import com.cm.kbslead.databinding.ActivityLeadDetailsBinding
import com.cm.leadapp.ui.adapter.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LeadDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeadDetailsBinding

    private lateinit var leadId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeadDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        leadId = intent.getStringExtra("lead_id")!!
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            title = getString(R.string.lead_details)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        setupViewPager()
    }

    private fun setupViewPager() {
        val pagerAdapter = PagerAdapter(this).also {
            it.addFragment(BasicInfoFragment.newInstance(leadId), getString(R.string.basic_info))
            it.addFragment(FollowupInfoFragment.newInstance(), getString(R.string.followup_info))
            it.addFragment(
                FollowupHistoryFragment.newInstance(),
                getString(R.string.followup_history)
            )
        }
        binding.apply {
            viewPager.apply {
                adapter = pagerAdapter
                currentItem = 0
            }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = pagerAdapter.getTabTitle(position)
            }.attach()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else super.onOptionsItemSelected(item)
    }
}