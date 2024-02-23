package com.cm.leadapp.util

import com.cm.leadapp.data.responsemodel.ContactDetails
import com.cm.leadapp.data.uimodel.Info


class InfoUiMapper {
    fun getBasicInfo(contactDetails: ContactDetails?): ArrayList<Info> {
        return ArrayList<Info>().apply {
            add(Info("Full Name", contactDetails?.clientName))
            add(Info("Phone", contactDetails?.clientPhone))
            add(Info("Email", contactDetails?.clientEmail))
            add(Info("Source", contactDetails?.source))
            add(Info("Type", contactDetails?.type))
            add(Info("Status", contactDetails?.status))
            add(Info("Feedback", contactDetails?.feedBack))
            add(
                Info(
                    "Address",
                    contactDetails?.country + ", " + contactDetails?.state + ", " + contactDetails?.city
                )
            )
        }
    }

    fun getFollowupInfo(contactDetails: ContactDetails?): ArrayList<Info> {
        return ArrayList<Info>().apply {
            add(Info("Product", contactDetails?.product))
            add(Info("Cost", contactDetails?.cost))
            add(Info("Brand Name", contactDetails?.brandName))
            add(Info("Invoice No", contactDetails?.invoiceNo))
            add(Info("Invoice Value", contactDetails?.invoiceValue))
            add(Info("Models", contactDetails?.models))
            add(Info("Reason", contactDetails?.reason))
            add(Info("Final Status", contactDetails?.finalStatus))
        }
    }
}