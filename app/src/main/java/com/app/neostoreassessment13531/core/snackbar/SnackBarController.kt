package com.app.neostoreassessment13531.core.snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

object SnackBarController {
    private val _events = Channel<SnackBarData>()
    val events = _events.receiveAsFlow()

    private val scope by lazy { CoroutineScope(Dispatchers.Main) }
    fun showMessage(snackBarData: SnackBarData){
        scope.launch {
            _events.send(snackBarData)
        }
    }
}