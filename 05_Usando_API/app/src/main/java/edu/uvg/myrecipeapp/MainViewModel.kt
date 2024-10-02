package edu.uvg.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _categorieState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categorieState

    private val _mealsState = mutableStateOf(MealsState())
    val mealsState: State<MealsState> = _mealsState

    private val _mealDetailState = mutableStateOf(MealDetailState())
    val mealDetailState: State<MealDetailState> = _mealDetailState

    init {
        fetchCategories()
    }

    // Fetch categories
    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = recipeService.getCategories()
                _categorieState.value = RecipeState(
                    list = response.categories,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _categorieState.value = RecipeState(
                    loading = false,
                    error = "Error fetching Categories: ${e.message}"
                )
            }
        }
    }

    // Fetch meals by category
    fun fetchMealsByCategory(category: String) {
        viewModelScope.launch {
            try {
                val response = recipeService.getMealsByCategory(category)
                _mealsState.value = MealsState(
                    list = response.meals,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _mealsState.value = MealsState(
                    loading = false,
                    error = "Error fetching Meals: ${e.message}"
                )
            }
        }
    }

    // Fetch meal details
    fun fetchMealDetails(mealId: String) {
        viewModelScope.launch {
            try {
                val response = recipeService.getMealDetails(mealId)
                _mealDetailState.value = MealDetailState(
                    meal = response.meals.firstOrNull(),
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _mealDetailState.value = MealDetailState(
                    loading = false,
                    error = "Error fetching Meal Details: ${e.message}"
                )
            }
        }
    }

    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )

    data class MealsState(
        val loading: Boolean = true,
        val list: List<Meal> = emptyList(),
        val error: String? = null
    )

    data class MealDetailState(
        val loading: Boolean = true,
        val meal: MealDetail? = null,
        val error: String? = null
    )
}
