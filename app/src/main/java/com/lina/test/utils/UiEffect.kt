package com.lina.test.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

interface UiEffect

@Composable
@SuppressWarnings("kotlin:S100", "kotlin:S107")
fun <T : UiEffect> CollectEffect(effect: Flow<T>, collector: FlowCollector<T>) {
    val scope = rememberCoroutineScope()
    SideEffect {
        scope.launch {
            effect.collect(collector)
        }
    }
}