/* Copyright Urban Airship and Contributors */

package com.urbanairship.debug.push

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.urbanairship.debug.R
import com.urbanairship.debug.ServiceLocator
import com.urbanairship.debug.databinding.UaFragmentPushDetailsBinding
import com.urbanairship.debug.extensions.setupToolbarWithNavController

/**
 * PushItem detail fragment.
 */
class PushDetailsFragment : Fragment() {

    companion object {
        const val ARGUMENT_PUSH_ID = "pushId"
    }

    private lateinit var binding: UaFragmentPushDetailsBinding

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, ViewModelFactory(context!!, arguments?.getString(ARGUMENT_PUSH_ID)!!)).get(PushDetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.binding = UaFragmentPushDetailsBinding.inflate(inflater, container, false)
        with(binding) {
            viewModel = this@PushDetailsFragment.viewModel
            lifecycleOwner = this@PushDetailsFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarWithNavController(R.id.toolbar)
    }

    internal class ViewModelFactory(private val context: Context, private val eventId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PushDetailsViewModel(ServiceLocator.shared(context).getPushRepository(), eventId) as T
        }
    }
}
