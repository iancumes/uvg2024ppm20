package edu.uvg.myrecipeapp

/// Converting Category to Entity and vice versa
fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        idCategory = idCategory,
        strCategory = strCategory,
        strCategoryThumb = strCategoryThumb,
        strCategoryDescription = strCategoryDescription
    )
}

fun CategoryEntity.toCategory(): Category {
    return Category(
        idCategory = idCategory,
        strCategory = strCategory,
        strCategoryThumb = strCategoryThumb,
        strCategoryDescription = strCategoryDescription
    )
}

// Converting MealEntity to Meal
fun MealEntity.toMeal(): Meal {
    return Meal(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb
    )
}

// Converting Meal to MealEntity, including the category
fun Meal.toMealEntity(category: String): MealEntity {
    return MealEntity(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        strInstructions = "", // Aquí puedes agregar más campos si los tienes
        category = category
    )
}
// Converting MealDetail to MealEntity
fun MealDetail.toMealEntity(): MealEntity {
    return MealEntity(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        strInstructions = strInstructions,
        category = "" // Puedes eliminar o manejar mejor este campo si no es necesario en los detalles
    )
}



fun MealEntity.toMealDetail(): MealDetail {
    return MealDetail(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        strInstructions = strInstructions,
        strIngredient1 = "",
        strIngredient2 = "",
        strIngredient3 = "",
        strIngredient4 = "",
        strIngredient5 = "",
        strIngredient6 = "",
        strIngredient7 = "",
        strIngredient8 = "",
        strIngredient9 = "",
        strIngredient10 = "",
        strIngredient11 = "",
        strIngredient12 = "",
        strIngredient13 = "",
        strIngredient14 = "",
        strIngredient15 = "",
        strIngredient16 = "",
        strIngredient17 = "",
        strIngredient18 = "",
        strIngredient19 = "",
        strIngredient20 = "",
        strMeasure1 = "",
        strMeasure2 = "",
        strMeasure3 = "",
        strMeasure4 = "",
        strMeasure5 = "",
        strMeasure6 = "",
        strMeasure7 = "",
        strMeasure8 = "",
        strMeasure9 = "",
        strMeasure10 = "",
        strMeasure11 = "",
        strMeasure12 = "",
        strMeasure13 = "",
        strMeasure14 = "",
        strMeasure15 = "",
        strMeasure16 = "",
        strMeasure17 = "",
        strMeasure18 = "",
        strMeasure19 = "",
        strMeasure20 = ""

    )
}
