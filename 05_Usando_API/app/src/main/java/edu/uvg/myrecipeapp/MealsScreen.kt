package edu.uvg.myrecipeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun MealsScreen(modifier: Modifier = Modifier, category: String, onMealClick: (String) -> Unit) {
    val viewModel: MainViewModel = viewModel()
    val mealsState by viewModel.mealsState

    LaunchedEffect(category) {
        viewModel.fetchMealsByCategory(category)
    }

    Box(modifier = modifier.fillMaxSize()) {  // Usamos el Modifier
        when {
            mealsState.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }

            mealsState.error != null -> {
                Text("Error occurred: ${mealsState.error}")
            }

            else -> {
                LazyVerticalGrid(GridCells.Fixed(2)) {
                    items(mealsState.list) { meal ->
                        MealItem(meal = meal, onMealClick = onMealClick)
                    }
                }
            }
        }
    }
}


@Composable
fun MealItem(meal: Meal, onMealClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable { onMealClick(meal.idMeal) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(meal.strMealThumb),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().aspectRatio(1f)
        )

        Text(
            text = meal.strMeal,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
