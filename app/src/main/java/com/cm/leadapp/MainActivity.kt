package com.cm.leadapp

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cm.kbslead.R
import com.cm.kbslead.databinding.ActivityMainBinding
import com.cm.leadapp.data.pref.MySharedPref
import com.cm.leadapp.data.request.AddCallLogRequest
import com.cm.leadapp.data.request.LeadCallLog
import com.cm.leadapp.data.responsemodel.SaleContactData
import com.cm.leadapp.ui.addlead.AddNewLeadActivity
import com.cm.leadapp.util.GenUtils
import com.cm.leadapp.util.LoadingDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    private val permission = arrayOf(Manifest.permission.READ_CALL_LOG)
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var pref: MySharedPref

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                readCallLog()
            }
        }

    private lateinit var loadingDialog: LoadingDialog

    private lateinit var callList: ArrayList<LeadCallLog>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        loadingDialog = LoadingDialog(this)
        val navView: BottomNavigationView = binding.navView
        navController =
            binding.navHostFragmentActivityMain.getFragment<NavHostFragment>().navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard,
                R.id.navigation_followup,
                R.id.navigation_missed_followup,
                R.id.navigation_leads,
                R.id.navigation_closed_leads
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddNewLeadActivity::class.java))
        }

        if (!GenUtils.hasPermissions(this, permission)) {
            permReqLauncher.launch(
                permission
            )
        }
        observeData()
    }

    private fun observeData() {
        viewModel.apply {
            salesContactsResp.observe(this@MainActivity) {
                if (it.data.isNotEmpty()) {
                    if (callList.isNotEmpty()) {
                        val phoneNUmberList = extractNumbers(it.data, callList)
                        if (phoneNUmberList.isNotEmpty()) {
                            val addCallLogRequest = AddCallLogRequest(
                                data_array = phoneNUmberList,
                                sales_id = pref.saleId
                            )
                            syncCallHistory(addCallLogRequest)
                        } else {
                            loadingDialog.dismissDialog()
                            showAlert(getString(R.string.alert), getString(R.string.no_data_sync))
                        }
                    } else {
                        loadingDialog.dismissDialog()
                        showAlert(getString(R.string.alert), getString(R.string.no_data_sync))
                    }
                } else {
                    loadingDialog.dismissDialog()
                    showAlert(getString(R.string.alert), getString(R.string.no_data_sync))
                }
            }
            syncCallsResp.observe(this@MainActivity) {
                loadingDialog.dismissDialog()
                if (it.status.equals("success")) {
                    pref.lastSyncTime = GenUtils.getTimeInMillis(it.data?.lastSync!!)
                }
                showAlert(it.status, it.message)
            }
        }
    }

    private fun extractNumbers(
        saleContactList: ArrayList<SaleContactData>,
        callList: ArrayList<LeadCallLog>
    ): ArrayList<com.cm.leadapp.data.request.CallLog> {
        val contactsToSync = arrayListOf<com.cm.leadapp.data.request.CallLog>()
        for (scd in saleContactList) {
            for (lcl in callList) {
                if (lcl.phoneNumber.contains(scd.userPhone.toString())) {
                    contactsToSync.add(
                        com.cm.leadapp.data.request.CallLog(
                            date = GenUtils.getDate(lcl.date.toLong(), "dd-MM-yyyy"),
                            duration = lcl.duration,
                            lead_id = scd.leadId!!,
                            phone = scd.userPhone!!,
                            time = GenUtils.getDate(lcl.date.toLong(), "HH:mm")
                        )
                    )
                }
                if(!scd.parentPhone.isNullOrEmpty() || !scd.parentPhone.isNullOrBlank()) {
                    if(lcl.phoneNumber.contains(scd.parentPhone!!)) {
                        contactsToSync.add(
                            com.cm.leadapp.data.request.CallLog(
                                date = GenUtils.getDate(lcl.date.toLong(), "dd-MM-yyyy"),
                                duration = lcl.duration,
                                lead_id = scd.leadId!!,
                                phone = scd.parentPhone!!,
                                time = GenUtils.getDate(lcl.date.toLong(), "HH:mm")
                            )
                        )
                    }
                }
            }
        }
        return contactsToSync
    }

    private fun readCallLog() {
        val calendar = Calendar.getInstance()
        val currentDateTime = calendar.timeInMillis
        callList = arrayListOf()
        val beforeDateTime = if (pref.lastSyncTime == 0L) {
            calendar.add(Calendar.HOUR, -12)
            calendar.timeInMillis
        } else {
            pref.lastSyncTime
        }
        val numberCol = CallLog.Calls.NUMBER
        val durationCol = CallLog.Calls.DURATION
        val typeCol = CallLog.Calls.TYPE // 1 - Incoming, 2 - Outgoing, 3 - Missed
        val dateCol = CallLog.Calls.DATE

        val projection = arrayOf(numberCol, durationCol, typeCol, dateCol)
        val SELECT = CallLog.Calls.DATE + ">?" + " AND " + CallLog.Calls.DATE + "<?"
        val cursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            projection,
            SELECT,
            arrayOf(beforeDateTime.toString(), currentDateTime.toString()),
            CallLog.Calls.DEFAULT_SORT_ORDER
        )

        val numberColIdx = cursor!!.getColumnIndex(numberCol)
        val durationColIdx = cursor.getColumnIndex(durationCol)
        val typeColIdx = cursor.getColumnIndex(typeCol)
        val dateColIdx = cursor.getColumnIndex(dateCol)

        while (cursor.moveToNext()) {
            val number = cursor.getString(numberColIdx)
            val duration = cursor.getString(durationColIdx)
            val type = cursor.getString(typeColIdx)
            val date = cursor.getString(dateColIdx)
            if (type.equals("2")) {
                callList.add(LeadCallLog(number, duration, date))
            }
            Log.d("MainActivity", "$number $duration $type $date")
        }
        cursor.close()
    }

    private fun showAlert(status: String?, message: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(status?.uppercase())
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
                true
            }

            R.id.iv_sync -> {
                if (GenUtils.hasPermissions(this, permission)) {
                    loadingDialog.startLoadingDialog()
                    readCallLog()
                    viewModel.getSalesContactList()
                } else {
                    permReqLauncher.launch(
                        permission
                    )
                }
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}