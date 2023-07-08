package com.mw.example.composenavigation.graph

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow


object EventBusNavigation {
    private val bus = MutableSharedFlow<NavigationEvent>()

    suspend fun send(event: NavigationEvent) {
        bus.emit(event)
    }

    fun receiver(): Flow<NavigationEvent> = bus
}

sealed class NavigationEvent {
    data class Event(val event: String): NavigationEvent()
}