package com.example.mcsdkdemoapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.mcsdkdemoapp.util.SplashFragment

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        mcHandler?.triggerStartEvent()


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SplashFragment.newInstance())
                .commitNow()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.splash_screen, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.add_user->{
                mcHandler?.triggerStartAddUserEvent()
        }
            R.id.delete_user->{
                mcHandler?.triggerStartDeleteUserEvent()
            }
    }
    return  super.onOptionsItemSelected(item)}

}