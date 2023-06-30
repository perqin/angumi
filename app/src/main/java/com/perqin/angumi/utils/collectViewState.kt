package com.perqin.angumi.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun <T> Fragment.collectViewState(state: StateFlow<T>, collector: FlowCollector<T>) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            state.collect(collector)
        }
    }
}
