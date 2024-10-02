package edu.uvg.myrecipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import edu.uvg.myrecipeapp.ui.theme.MyRecipeAppTheme

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyRecipeAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "categories") {
                    composable("categories") {
                        RecipeScreen(onCategoryClick = { category ->
                            navController.navigate("meals/$category")
                        })
                    }
                    composable("meals/{category}") { backStackEntry ->
                        val category = backStackEntry.arguments?.getString("category")
                        MealsScreen(modifier = Modifier, category = category ?: "", onMealClick = { mealId ->
                            navController.navigate("mealDetails/$mealId")
                        })
                    }
                    composable("mealDetails/{mealId}") { backStackEntry ->
                        val mealId = backStackEntry.arguments?.getString("mealId")
                        MealDetailScreen(modifier = Modifier, mealId = mealId ?: "")
                    }
                }
            }
        }
    }
}