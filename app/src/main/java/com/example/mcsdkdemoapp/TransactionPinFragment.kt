package com.example.mcsdkdemoapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.mcsdkdemoapp.databinding.ConfirmTransactionFragmentBinding
import com.example.mcsdkdemoapp.databinding.TransactionPinFragmentBinding
import com.example.mcsdkdemoapp.ui.home.HomeFragment
import com.kobil.wrapper.events.*

class TransactionPinFragment : BaseFragment() {

    companion object {
        fun newInstance() = TransactionPinFragment()
    }

    private lateinit var viewModel: TransactionPinViewModel

    private lateinit var binding: TransactionPinFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.transaction_pin_fragment, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransactionPinViewModel::class.java)
        // TODO: Use the ViewModel

        binding.confirmPin.setOnClickListener(this)
        binding.cancelPin.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){

            R.id.confirm_pin->{
                mcHandler?.triggerTransPinEvent(ConfirmationType.OK,binding.transPin.text.toString());

            }
            R.id.cancel_pin->{
                mcHandler?.triggerTransPinEvent(ConfirmationType.CANCEL,"");

            }
        }
    }

    override fun onEventReceived(eventFrameworkEvent: EventFrameworkEvent) {
        super.onEventReceived(eventFrameworkEvent)

        when(eventFrameworkEvent){

            is ProvidePinResultEvent ->{

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