package edu.quinnipiac.edu.ser210.RecpieFinderApp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import edu.quinnipiac.edu.ser210.RecpieFinderApp.databinding.FragmentOptionsBinding
import edu.quinnipiac.edu.ser210.RecpieFinderApp.databinding.FragmentRecipeDetailsBinding


class OptionsFragment : Fragment() {

    private var _binding: FragmentOptionsBinding? = null
    private val binding get() = _binding !!
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this using view binding fragment
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.nightButton.setOnClickListener() {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        }

        binding.lightButton.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }

        return view

    }
}