package edu.quinnipiac.edu.ser210.RecpieFinderApp

import android.content.Context
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

var recipeList : ArrayList<RecipeItem> = ArrayList()

class RecyclerAdapter(val context: Context,  var navController: NavController) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    lateinit var requestQueue: RequestQueue


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val appnetwork = BasicNetwork(HurlStack())
        val appcache = DiskBasedCache(context.cacheDir, 5* 1024 * 1024) // 1MB cap
        requestQueue = RequestQueue(appcache, appnetwork).apply {
            start()
        }

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.bind(position)
    }

    fun setRecipeListItems(recipe: ArrayList<RecipeItem>) {
        recipeList = recipe
        notifyDataSetChanged()
    }


    inner class MyViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView!!.findViewById(R.id.item_title)
        val image: ImageView = itemView!!.findViewById(R.id.item_image)
        private var pos: Int = 0

        init {
            itemView.setOnClickListener {
                val action = RecipeResultDirections.actionRecipeResultToRecipeDetails(pos)
                navController.navigate(action)

            }
        }
        fun bind(position: Int) {
            pos = position
            val currRecipe = recipeList.get(position)
            title.text = currRecipe.title
            val words = title.text.split("\\s+")
            val result = words.joinToString("+")
            fetchData(result)
        }

        fun fetchData(input: String) {
            val url = "https://www.googleapis.com/customsearch/v1?q=$input+recipe&cx=222f6e80dbc7642dc&imgSize=medium&searchType=image&key=${BuildConfig.api_key2}"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    if (response.getJSONArray("items").length() > 0) {
                        val imageURL = response.getJSONArray("items").getJSONObject(0).getString("link")
                        Glide.with(context).load(imageURL)
                            .apply(RequestOptions().centerCrop())
                            .into(image)
                    }
                },
                { error ->
                    Log.d("vol", error.toString())
                }
            )

            requestQueue.add(jsonObjectRequest)
        }
    }
}