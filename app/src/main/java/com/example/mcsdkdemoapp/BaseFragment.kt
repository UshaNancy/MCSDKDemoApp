package com.example.mcsdkdemoapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kobil.mcwmpgettingstarted.extensions.contents
import com.kobil.wrapper.events.EventFrameworkEvent

open class BaseFragment: Fragment(), View.OnClickListener, MCEventObserver {

    protected var mcHandler: MCHandler? = null

    lateinit var baseActivity: BaseActivity
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mcHandler = MCHandler.getInstance(baseActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    // <editor-fold desc="Life Cycle">
    override fun onAttach(context: Context) {
        super.onAttach(context)


        activity?.let {
            baseActivity = it as BaseActivity
        }
    }
    override fun onClick(v: View?) {

    }

    override fun onEventReceived(eventFrameworkEvent: EventFrameworkEvent) {

    }

    override fun onStart() {
        super.onStart()

       mcHandler?.addMCEventObserver(this)
    }

    /**
     * Here we are removing callback to MC events.
     */
    override fun onStop() {
        super.onStop()

        mcHandler?.removeMCEventObserver(this)
    }
    open fun showAlertDialog(
        context: Context?,
        title: String?,
        msg: String?,
        listener: DialogInterface.OnClickListener?
    ) {
        val builder: androidx.appcompat.app.AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(
            this!!.requireContext()
        )
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton("OK", listener)
        //builder.setNegativeButton("Cancel", listener)
        builder.setCancelable(false)
        builder.create()
        builder.show()

    }

    fun fragmentChange(fragment:Fragment){

    }

}