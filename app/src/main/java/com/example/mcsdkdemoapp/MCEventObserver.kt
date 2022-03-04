package com.example.mcsdkdemoapp

import com.kobil.wrapper.events.EventFrameworkEvent

interface MCEventObserver {
    fun onEventReceived(eventFrameworkEvent: EventFrameworkEvent)

}
