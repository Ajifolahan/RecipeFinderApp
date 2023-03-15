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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        val food = view.findViewById<EditText>(R.id.FoodName)
        val search = view.findViewById<Button>(R.id.search)

        search.setOnClickListener {
            //navigate to the next screen------COMMENT OUT
            val navController = view.findNavController()
            val message = food.text.toString()
            val apiInterface = APiInterface.create().getRecipes(message)
            //navigates to the action ID
            if(message.equals("")){
                //if the username text is empty, show a toast
                Toast.makeText(context, "Please enter a valid food name to proceed", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context,"Getting Response", Toast.LENGTH_SHORT).show()
                if (apiInterface != null) {
                    apiInterface.enqueue(object : Callback<ArrayList<RecipeItem?>?> {
                        override fun onResponse(
                            call: Call<ArrayList<RecipeItem?>?>?,
                            response: Response<ArrayList<RecipeItem?>?>
                        ) {
                            if (response != null) {
                                Log.d("Main activity", response.message())
                                Log.d("Main activity", response.headers().toString())
                                Log.d("Main activity", response.body().toString())
                                //navigate to next screen-----COMMENT OUT
                                //val action = SecondFragmentDirections.actionIntroScreenToMainActivity()
                                //navController.navigate(action)
                            }
                        }

                        override fun onFailure(call: Call<ArrayList<RecipeItem?>?>, t: Throwable) {
                            if (t != null) {
                                t.message?.let { Log.d("onFailure", it) }
                                Toast.makeText(
                                    context,
                                    "Error on API call.Please provide a different food name",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }

        }

        return view
    }
}



