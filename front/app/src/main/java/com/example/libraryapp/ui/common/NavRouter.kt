package com.example.libraryapp.ui.common

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.fragment.findNavController
import org.koin.core.component.KoinComponent

interface NavRouter {
    fun getCurrentDestination(): NavDestination?
    fun setGraph(@NavigationRes graphResId: Int)
    fun onUpButtonTapped(): Boolean
    fun popBackStack(): Boolean
    fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean): Boolean
    fun navigate(directions: NavDirections, navOptions: NavOptions? = null)
    fun navigate(directions: NavDirections, navExtras: Navigator.Extras)
    fun navigate(
        @IdRes resId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null,
        navExtras: Navigator.Extras? = null
    )
}

open class NavRouterImpl : NavRouter, KoinComponent {
    private lateinit var navController: NavController

    fun setupNavController(navController: NavController) {
        this.navController = navController
    }

    override fun getCurrentDestination(): NavDestination? = navController.currentDestination

    override fun setGraph(@NavigationRes graphResId: Int) = navController.setGraph(graphResId)

    override fun onUpButtonTapped() = navController.navigateUp()

    override fun popBackStack() = navController.popBackStack()

    override fun popBackStack(destinationId: Int, inclusive: Boolean) =
        navController.popBackStack(destinationId, inclusive)

    override fun navigate(directions: NavDirections, navOptions: NavOptions?) =
        navController.navigate(directions, navOptions)

    override fun navigate(directions: NavDirections, navExtras: Navigator.Extras) {
        navController.navigate(directions, navExtras)
    }

    override fun navigate(
        resId: Int,
        args: Bundle?,
        navOptions: NavOptions?,
        navExtras: Navigator.Extras?
    ) =
        navController.navigate(resId, args, navOptions, navExtras)
}

class FragmentNavRouter(fragment: Fragment) : NavRouterImpl() {
    init {
        setupNavController(fragment.findNavController())
    }
}