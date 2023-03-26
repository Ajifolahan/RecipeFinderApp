package edu.quinnipiac.edu.ser210.RecpieFinderApp

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
var recipeList : ArrayList<RecipeItem> = ArrayList()

class RecyclerAdapter(val context: Context,  var navController: NavController) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
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


    inner class MyViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView!!.findViewById(R.id.item_title)
        private var pos:Int = 0

        init {
            itemView.setOnClickListener {
                val action = RecipeResultDirections.actionRecipeResultToRecipeDetails(pos)
                navController.navigate(action)

            }
        }
        fun bind(position:Int){
            pos = position
            val currRecipe = recipeList.get(position)
            title.text = currRecipe.title

        }

    }
}