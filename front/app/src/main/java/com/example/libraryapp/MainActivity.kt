package com.example.libraryapp

import android.app.AlertDialog
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.libraryapp.databinding.ActivityMainBinding
import com.example.libraryapp.ui.common.SessionViewModel
import com.example.libraryapp.ui.common.extensions.themeColor
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val sessionViewModel: SessionViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionViewModel.load()
        sessionViewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()

        })

        sessionViewModel.sessionUser.observe(this, Observer {

            if (it != null) {
                updateHeaderUser(it.name)
                updateMenuByUserType(it.isAdmin)
            }
        })

        sessionViewModel.sessionOrgName.observe(this, Observer {
            if (it != null) updateHeaderOrg(it)
        })

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.catalog_fragment,
                R.id.reservations_fragment,
                R.id.apikeyManagement_fragment,
                R.id.inviteUser_fragment,
                R.id.manageBooks_fragment,
                R.id.returnBook_fragment
            ),
            binding.drawer
        )

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        navView.setCheckedItem(0)

        setSupportActionBar(binding.topAppBar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navView.setupWithNavController(navController)

        nav2.setNavigationItemSelectedListener { menuItem ->
            val builder = AlertDialog.Builder(this)
            builder.setMessage("¿Seguro que deseas cerrar sesión?")
                .setPositiveButton("Si") { _, id -> sessionViewModel.destroySession() }
                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            val alert = builder.create()
            alert.show()
            true
        }

        sessionViewModel.updatedSession.observe(this, Observer {
            navController.navigate(R.id.splash_fragment)
        })

        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                topAppBar.setBackgroundColor(themeColor(R.attr.colorPrimary))

                when (destination.id) {
                    R.id.splash_fragment, R.id.loginFragment, R.id.signupFragment, R.id.bookFragment -> {
                        showTopBar(false)
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                    }
                    R.id.addBookFragment, R.id.editBookFragment, R.id.bookReservationsFragment -> {
                        topAppBar.setBackgroundColor(themeColor(R.attr.colorSecondary))
                    }
                    // sets the initial checked item in the navigation drawer. Doing it from xml
                    // generates an issue of having two checked items the first time a destination is changed
                    R.id.catalog_fragment -> {
                        showTopBar(true)
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                        navView.setCheckedItem(R.id.catalog_fragment)
                    }
                    else -> {
                        showTopBar(true)
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_activity_main).navigateUp(
            appBarConfiguration
        )
    }

    fun updateMenuByUserType(isAdmin: Boolean) {
        navView.menu.clear()
        if (isAdmin)
            navView.inflateMenu(R.menu.admin_menu)
        else
            navView.inflateMenu(R.menu.student_menu)
    }

    fun showTopBar(visible: Boolean) {
        appBarLayout.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun updateHeaderUser(name: String) {
        val headerView: View = navView.getHeaderView(0)
        val navUser = headerView.findViewById<TextView>(R.id.tv_username_drawer)
        navUser.text = name
    }

    fun updateHeaderOrg(orgName: String) {
        val headerView: View = navView.getHeaderView(0)
        val navOrg = headerView.findViewById<TextView>(R.id.tv_organization_name_drawer)
        navOrg.text = orgName
    }

    fun showSnackbar(
        message: SpannableStringBuilder,
        isSuccess: Boolean? = null,
    ) {
        val snackbar = Snackbar.make(
            coordinatorLayout2,
            message,
            Snackbar.LENGTH_SHORT
        )

        snackbar.setBackgroundTint(
            resources.getColor(if (isSuccess == true) R.color.green_inactive else R.color.red_inactive)
        )
        snackbar.setTextColor(
            resources.getColor(if (isSuccess == true) R.color.green_on_inactive else R.color.red_active)

        )
        snackbar.show()
    }

    fun getCurrentNavigationFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
            ?.childFragmentManager
            ?.fragments
            ?.first()
    }
}
