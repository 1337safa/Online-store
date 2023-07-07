package com.safronov_original_app_online_store.presentation.activity.communication_with_activity.bottom_nav_view

import androidx.fragment.app.Fragment

fun Fragment.requireCommunicationWithBottomNavView(): CommunicationWithBottomNavView? {
    return if (requireActivity() is CommunicationWithBottomNavView) { requireActivity() as CommunicationWithBottomNavView } else { return null }
}

interface CommunicationWithBottomNavView {

    fun showBadgeForCartGraph(number: Int)
    fun hideBadgeForCartGraph()

}