package edu.uvg.myrecipeapp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun MealDetailScreen(modifier: Modifier = Modifier, mealId: String) {
    val viewModel: MainViewModel = viewModel()
    val mealDetailState by viewModel.mealDetailState

    // Fetch the meal details when the screen is displayed
    LaunchedEffect(mealId) {
        viewModel.fetchMealDetails(mealId)
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            mealDetailState.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }

            mealDetailState.error != null -> {
                Text("Error occurred: ${mealDetailState.error}")
            }

            else -> {
                mealDetailState.meal?.let { meal ->
                    // Display the meal details
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(meal.strMealThumb),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = meal.strMeal,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Display ingredients
                        Text(
                            text = "Ingredients:",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        IngredientList(meal)

                        // Display instructions
                        Text(
                            text = "Instructions:",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = meal.strInstructions,
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientList(meal: MealDetail) {
    // List of ingredients and measures
    val ingredients = listOf(
        meal.strIngredient1 to meal.strMeasure1,
        meal.strIngredient2 to meal.strMeasure2,
        meal.strIngredient3 to meal.strMeasure3,
        meal.strIngredient4 to meal.strMeasure4,
        meal.strIngredient5 to meal.strMeasure5,
        meal.strIngredient6 to meal.strMeasure6,
        meal.strIngredient7 to meal.strMeasure7,
        meal.strIngredient8 to meal.strMeasure8,
        meal.strIngredient9 to meal.strMeasure9,
        meal.strIngredient10 to meal.strMeasure10,
        meal.strIngredient11 to meal.strMeasure11,
        meal.strIngredient12 to meal.strMeasure12,
        meal.strIngredient13 to meal.strMeasure13,
        meal.strIngredient14 to meal.strMeasure14,
        meal.strIngredient15 to meal.strMeasure15,
        meal.strIngredient16 to meal.strMeasure16,
        meal.strIngredient17 to meal.strMeasure17,
        meal.strIngredient18 to meal.strMeasure18,
        meal.strIngredient19 to meal.strMeasure19,
        meal.strIngredient20 to meal.strMeasure20
    ).filter { it.first.isNotBlank() } // Filter out empty ingredients

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        ingredients.forEach { (ingredient, measure) ->
            Text(
                text = "$measure $ingredient",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}
