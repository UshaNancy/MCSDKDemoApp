package com.example.mcsdkdemoapp

import android.content.Context
import com.kobil.wrapper.Future
import com.kobil.wrapper.SynchronousEventHandler
import com.kobil.wrapper.events.EventFrameworkEvent
import com.kobil.wrapper.events.StatusType

class MCWrapper(val context: Context, val mcListener: MCWrapperListener) :
    SynchronousEventHandler(context), MCWrapperInterface {

    override fun postEvent(p0: EventFrameworkEvent): Future {
        return super.postEvent(p0)
    }

    override fun suspendMasterController() {
        super.suspendMasterController()
    }

    override fun forwardEvent(p0: EventFrameworkEvent) {
        super<SynchronousEventHandler>.forwardEvent(p0)
    }

    override fun resumeMasterController(): StatusType {
        return super.resumeMasterController()
    }

    override fun executeEvent(event: EventFrameworkEvent) {
      event.let { mcListener.executeEvent(it) }
    }

    interface MCWrapperListener {
        fun executeEvent(event: EventFrameworkEvent)
    }

}