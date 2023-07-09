package com.myfitness.ui.splash.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.myfitness.R
import com.myfitness.databinding.FragmentSplashBinding
import com.myfitness.ui.booking_list.fragment.BookingListFragment


class SplashFragment : Fragment() {

    private var fragmentSplashBinding: FragmentSplashBinding? = null
    private var handler: Handler? = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSplashBinding.inflate(inflater, container, false)
        fragmentSplashBinding = binding

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        handler?.postDelayed(Runnable {
            val userDetailFragment = BookingListFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, userDetailFragment)
            transaction.commitNow()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacksAndMessages(null)
    }
}