package com.example.mcsdkdemoapp

import android.app.usage.UsageEvents
import com.kobil.wrapper.Future
import com.kobil.wrapper.events.EventFrameworkEvent
import com.kobil.wrapper.events.StatusType

interface MCWrapperInterface {

    fun forwardEvent(event:EventFrameworkEvent)
    fun postEvent(event:EventFrameworkEvent):Future
    fun executeEvent(event: EventFrameworkEvent)

    fun resumeMasterController():StatusType
    fun suspendMasterController()

}
