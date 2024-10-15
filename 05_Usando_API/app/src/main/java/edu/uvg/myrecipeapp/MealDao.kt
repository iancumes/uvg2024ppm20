package edu.uvg.myrecipeapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealDao {

    // Insert multiple meals
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(meals: List<MealEntity>)

    // Insert a single meal
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity)

    // Get meals by category
    @Query("SELECT * FROM meals WHERE category = :category")
    suspend fun getMealsByCategory(category: String): List<MealEntity>

    // Get a meal by its ID
    @Query("SELECT * FROM meals WHERE idMeal = :mealId")
    suspend fun getMealById(mealId: String): MealEntity?
}
