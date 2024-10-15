package edu.uvg.myrecipeapp


import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val categoryDao = AppDatabase.getDatabase(application).categoryDao()
    private val mealDao = AppDatabase.getDatabase(application).mealDao()

    private val _categorieState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categorieState

    private val _mealsState = mutableStateOf(MealsState())
    val mealsState: State<MealsState> = _mealsState

    private val _mealDetailState = mutableStateOf(MealDetailState())
    val mealDetailState: State<MealDetailState> = _mealDetailState

    init {
        fetchCategories()
    }

    // Fetch categories: try local DB first, then fallback to API if needed
    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                // Try to fetch categories from local Room database
                val cachedCategories = categoryDao.getAllCategories()
                if (cachedCategories.isNotEmpty()) {
                    _categorieState.value = RecipeState(
                        list = cachedCategories.map { it.toCategory() },
                        loading = false,
                        error = null
                    )
                } else {
                    // If no cached data, fetch from API and store in the database
                    val response = recipeService.getCategories()
                    categoryDao.insertAll(response.categories.map { it.toCategoryEntity() })
                    _categorieState.value = RecipeState(
                        list = response.categories,
                        loading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _categorieState.value = RecipeState(
                    loading = false,
                    error = "Error fetching Categories: ${e.message}"
                )
            }
        }
    }

    // Fetch meals by category: try local DB first, then fallback to API if needed
    fun fetchMealsByCategory(category: String) {
        viewModelScope.launch {
            try {
                // Fetch from local Room DB if available
                val cachedMeals = mealDao.getMealsByCategory(category)
                if (cachedMeals.isNotEmpty()) {
                    _mealsState.value = MealsState(
                        list = cachedMeals.map { it.toMeal() },
                        loading = false,
                        error = null
                    )
                } else {
                    // If not cached, fetch from API and store in the database
                    val response = recipeService.getMealsByCategory(category)
                    mealDao.insertAll(response.meals.map { it.toMealEntity(category) })
                    _mealsState.value = MealsState(
                        list = response.meals,
                        loading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _mealsState.value = MealsState(
                    loading = false,
                    error = "Error fetching Meals: ${e.message}"
                )
            }
        }
    }


    // Fetch meal details: try local DB first, then fallback to API if needed
    fun fetchMealDetails(mealId: String) {
        viewModelScope.launch {
            try {
                // Fetch from local Room DB if available
                val cachedMeal = mealDao.getMealById(mealId)
                if (cachedMeal != null) {
                    _mealDetailState.value = MealDetailState(
                        meal = cachedMeal.toMealDetail(),
                        loading = false,
                        error = null
                    )
                } else {
                    // If not cached, fetch from API and store in the database
                    val response = recipeService.getMealDetails(mealId)
                    mealDao.insertMeal(response.meals.first().toMealEntity())
                    _mealDetailState.value = MealDetailState(
                        meal = response.meals.first(),
                        loading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _mealDetailState.value = MealDetailState(
                    loading = false,
                    error = "Error fetching Meal Details: ${e.message}"
                )
            }
        }
    }

    // States to manage UI state for different screens
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
