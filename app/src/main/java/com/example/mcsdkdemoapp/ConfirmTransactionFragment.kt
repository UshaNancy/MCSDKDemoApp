package com.example.mcsdkdemoapp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mcsdkdemoapp.databinding.ConfirmTransactionFragmentBinding
import com.example.mcsdkdemoapp.ui.home.HomeFragment
import com.kobil.wrapper.events.ConfirmationType
import com.kobil.wrapper.events.DisplayConfirmationRequestEvent
import com.kobil.wrapper.events.EventFrameworkEvent
import com.kobil.wrapper.events.TransactionEndEvent


class ConfirmTransactionFragment : BaseFragment() {

    private  lateinit var  displayMessage : String
    private var  timer : Int = 0
    companion object {
        fun newInstance(transactionInfo:String, transactionTimer:Int) = ConfirmTransactionFragment().apply {
            arguments = Bundle().apply {
                putString("TRANS_INFO", transactionInfo)
                putInt("TRANS_TIMER",transactionTimer)
            }
        }
    }

    private lateinit var viewModel: ConfirmTransactionViewModel
    private lateinit var binding:ConfirmTransactionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.confirm_transaction_fragment, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConfirmTransactionViewModel::class.java)
        displayMessage = arguments?.getString("TRANS_INFO").toString()
        timer = arguments?.getInt("TRANS_TIMER")!!

        var timeInMilliSeconds:Long = (timer *1000).toLong()
        object : CountDownTimer(timeInMilliSeconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.transTimer.setText("seconds remaining: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                binding.transTimer.setText("0")
            }
        }.start()

        binding.displayMessage.text = displayMessage
        binding.transAccept.setOnClickListener(this)
        binding.transDecline.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){

            R.id.trans_accept->{
               mcHandler?. triggerDisplayConfirmationEvent(ConfirmationType.OK,displayMessage)

            }
            R.id.trans_decline->{
                mcHandler?. triggerDisplayConfirmationEvent(ConfirmationType.CANCEL,displayMessage)

            }
        }
    }

    override fun onEventReceived(eventFrameworkEvent: EventFrameworkEvent) {
        super.onEventReceived(eventFrameworkEvent)

        when(eventFrameworkEvent){

            is DisplayConfirmationRequestEvent->{

            }
            is TransactionEndEvent ->{

                activity?. runOnUiThread {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(
                            R.id.nav_host_fragment,
                            HomeFragment.newInstance()
                        )
                        ?.commit()
                }

                }
        }

        }
    }
