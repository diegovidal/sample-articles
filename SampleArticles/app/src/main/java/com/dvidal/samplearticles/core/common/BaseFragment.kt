package com.dvidal.samplearticles.core.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.dvidal.samplearticles.MyApplication

/**
 * @author diegovidal on 14/12/18.
 */
abstract class BaseFragment: Fragment() {

    internal val component by lazy { (activity?.application as MyApplication).appComponent.activityComponent().build() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(layoutRes(), container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        injectDagger()
    }

    @LayoutRes
    internal abstract fun layoutRes(): Int

    internal abstract fun injectDagger()
}