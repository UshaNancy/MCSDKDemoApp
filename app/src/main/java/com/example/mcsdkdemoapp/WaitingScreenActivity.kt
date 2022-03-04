package com.example.mcsdkdemoapp

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.mcsdkdemoapp.databinding.ActivityWaitingScreenBinding
import com.example.mcsdkdemoapp.ui.activationuserid.ActivationUserIdFragment
import com.kobil.wrapper.events.StatusType

class WaitingScreenActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding:ActivityWaitingScreenBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var currentUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_waiting_screen)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_change_pin, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        binding.navView.setNavigationItemSelectedListener(this)



        if(AppConstants.UPDATE_STATUS == "UPDATE_AVAILABLE") {
            showAlertDialog(this,
                "Update Available",
                "",
                DialogInterface.OnClickListener { dialog, which ->

                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        mcHandler?.triggerUpdateEvent()
                    }
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        dialog.dismiss()
                    }
                })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.waiting_screen, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
           /* R.id.delete_user->{
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Delete User?")
                dialogBuilder.setPositiveButton("yes",
                    DialogInterface.OnClickListener { dialog, whichButton -> mcHandler?.triggerStartDeleteUserEvent()})
                dialogBuilder.setNegativeButton("no",{dialog, which -> dialog.dismiss() })
                val b = dialogBuilder.create()
                b.show()}
*/
R.id.logout ->{
    mcHandler?.triggerRestartEvent(null)
}


        }
        return  super.onOptionsItemSelected(item)}



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.add_user -> {
                // mcHandler?.triggerStartAddUserEvent()
                /*    val host = NavHostFragment.create(R.navigation.mobile_navigation)
                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment,host).setPrimaryNavigationFragment(host).commit()
*/
                /*  AppConstants.UserLoggedIN = true
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, ActivationUserIdFragment.newInstance())
                    .commitNow()*/
            }


            R.id.drawer_change_pin -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, ChangePinFragment.newInstance())
                    .commitNow()
            }
        }
       drawerLayout .closeDrawer(GravityCompat.START)
        return true
    }

}