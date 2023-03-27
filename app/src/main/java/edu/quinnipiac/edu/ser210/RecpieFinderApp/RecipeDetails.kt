package edu.quinnipiac.edu.ser210.RecpieFinderApp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import edu.quinnipiac.edu.ser210.RecpieFinderApp.databinding.FragmentRecipeDetailsBinding

class RecipeDetails : Fragment() {
    lateinit var requestQueue: RequestQueue

    var recipe_id: Int = 0
    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding !!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   recipient = arguments!!.getString("recipient")

        val appnetwork = BasicNetwork(HurlStack())
        val appcache = DiskBasedCache(requireContext().cacheDir, 5* 1024 * 1024) // 1MB cap
        requestQueue = RequestQueue(appcache, appnetwork).apply {
            start()
        }

        val bundle = arguments
        if (bundle == null) {
            Log.e("DetailFragment", "DetailFragment did not receive hero id")

            return
        }
        recipe_id = RecipeDetailsArgs.fromBundle(bundle).recipeId

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = recipeList.get(recipe_id).title
        binding.ingredients.text = recipeList.get(recipe_id).ingredients
        binding.servings.text = recipeList.get(recipe_id).servings
        binding.instructions.text = recipeList.get(recipe_id).instructions
        fetchData(recipeList.get(recipe_id).title)

    }

    fun fetchData(input: String) {
        val url = "https://www.googleapis.com/customsearch/v1?q=$input+recipe&cx=222f6e80dbc7642dc&imgSize=medium&searchType=image&key=${BuildConfig.api_key2}"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                if (response.getJSONArray("items").length() > 0) {
                    val imageURL = response.getJSONArray("items").getJSONObject(0).getString("link")
                    Glide.with(requireContext()).load(imageURL)
                        .apply(RequestOptions().centerCrop())
                        .into(binding.itemImage)
                }
            },
            { error ->
                Log.d("vol", error.toString())
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

}

