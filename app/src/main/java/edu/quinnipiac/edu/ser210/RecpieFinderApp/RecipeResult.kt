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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   recipient = arguments!!.getString("recipient")
        val bundle = arguments
        if (bundle == null) {
            Log.e("RecipeResult", "RecipeResult did not receive recipe id")

            return
        }

        message = RecipeResultArgs.fromBundle(bundle).message

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_result, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerAdapter = RecyclerAdapter(requireContext(), Navigation.findNavController(view))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerAdapter


        val apiInterface = APiInterface.create().getRecipes(message)

        if (apiInterface != null) {
            apiInterface.enqueue(object : Callback<ArrayList<RecipeItem?>?>{
                override fun onResponse(
                    call: Call<ArrayList<RecipeItem?>?>,
                    response: Response<ArrayList<RecipeItem?>?>
                ) {
                    if (response?.body() != null) {
                        recyclerAdapter.setRecipeListItems(response?.body() !! as ArrayList<RecipeItem>)

                    }
                }

                override fun onFailure(call: Call<ArrayList<RecipeItem?>?>, t: Throwable) {
                    if (t != null)
                        t.message?.let {Log.d("onFailure", it) }
                }

            })
        }

    }

}