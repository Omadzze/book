package com.omaddev.read.OnBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.omaddev.read.R

class OnBoardingFragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root: ViewGroup = inflater.inflate(R.layout.fragment_onboarding1, container, false) as ViewGroup

        return root
    }
}