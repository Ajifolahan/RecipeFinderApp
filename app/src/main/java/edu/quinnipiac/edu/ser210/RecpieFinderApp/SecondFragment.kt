package edu.quinnipiac.edu.ser210.RecpieFinderApp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import edu.quinnipiac.edu.ser210.RecpieFinderApp.databinding.FragmentSecondBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding !!

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_second, container, false)

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        val view = binding.root

        //val food = view.findViewById<EditText>(R.id.FoodName)
        val search = binding.search

        search.setOnClickListener {
            //navigate to the next screen------COMMENT OUT
            val navController = view.findNavController()
            val message = binding.FoodName.text.toString()
            val apiInterface = APiInterface.create().getRecipes(message)
            //navigates to the action ID
            if(message.equals("")){
                //if the username text is empty, show a toast
                Toast.makeText(context, "Please enter a valid food name to proceed", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context,"Getting Response", Toast.LENGTH_SHORT).show()
                val action = SecondFragmentDirections.actionSecondFragmentToRecipeResult(message)
                view.findNavController().navigate(action)
            }

        }

        return view
    }
}



