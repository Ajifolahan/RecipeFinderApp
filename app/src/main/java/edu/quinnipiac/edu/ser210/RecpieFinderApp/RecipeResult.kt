//@Authors: Camryn Keller and Momoreoluwa Ayinde

package edu.quinnipiac.edu.ser210.RecpieFinderApp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeResult : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    var message = ""

    // Retrieve the message passed from the previous fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle == null) {
            Log.e("RecipeResult", "RecipeResult did not receive recipe id")
            return
        }
        message = RecipeResultArgs.fromBundle(bundle).message
    }

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the recycler view and adapter
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerAdapter = RecyclerAdapter(requireContext(), Navigation.findNavController(view))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter

        // Call the API to retrieve the list of recipes based on the user's search input
        val apiInterface = APiInterface.create().getRecipes(message)
        if (apiInterface != null) {
            apiInterface.enqueue(object : Callback<ArrayList<RecipeItem?>?>{
                override fun onResponse(
                    call: Call<ArrayList<RecipeItem?>?>,
                    response: Response<ArrayList<RecipeItem?>?>
                ) {
                    // Set the retrieved list of recipes to the adapter
                    if (response?.body() != null) {
                        recyclerAdapter.setRecipeListItems(response?.body() !! as ArrayList<RecipeItem>)
                    }
                }

                override fun onFailure(call: Call<ArrayList<RecipeItem?>?>, t: Throwable) {
                    // Handle the case where the API call fails
                    if (t != null)
                        t.message?.let {Log.d("onFailure", it) }
                }

            })
        }

    }

}
