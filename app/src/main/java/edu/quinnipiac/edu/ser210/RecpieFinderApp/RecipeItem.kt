//@Authors: Camryn Keller and Momoreoluwa Ayinde

package edu.quinnipiac.edu.ser210.RecpieFinderApp

//model class of the API
data class RecipeItem(
    val ingredients: String,
    val instructions: String,
    val servings: String,
    val title: String
)