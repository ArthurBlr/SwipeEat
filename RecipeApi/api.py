
"""
TODO :
    - get all tags;
    - get all tags without already selected tags;
    - get all ingredients;
    - get recipes by filters (name, minutes, tags, nutrition, n_steps, ingredients, n_ingredients)
"""

from typing import Union, List

from fastapi import FastAPI, Query
from pydantic import BaseModel

from database import *

class Ingredient(BaseModel):
    ingredient: str

raw_recipes = Raw_Recipes()
customer_recipes = Customer_Recipes(raw_recipes)

app = FastAPI()

@app.get("/recipes")
def recipes(name: Union[str, None] = None, minutes: Union[int, None] = None, tags: Union[str, None] = None, nutrition: Union[str, None] = None, n_steps: Union[int, None] = None, ingredients: Union[List[str], None] = Query(None), n_ingredients: Union[int, None] = None, count: Union[int, None] = 50):
    return customer_recipes.get_recipes_dataframe_filtered_copy(name=name, minutes=minutes, tags=tags, nutrition=nutrition, n_steps=n_steps, ingredients=ingredients, n_ingredients=n_ingredients).sample(frac=1).head(count).reset_index().to_dict('records')

@app.get("/ingredients/all")
def ingredients_all():
    return {"ingredients" : [ingredient[0] for ingredient in raw_recipes.uniques_string_list_by_column_name('ingredients')]}

@app.get("/ingredients/not_evaluated")
def ingredients_not_evaluated():
    return {"ingredients" : customer_recipes.get_not_evaluated_ingredients()} 

@app.get("/ingredients/evaluated")
def ingredients_evaluated():
    return {"liked_ingredients" : customer_recipes.get_liked_ingredients(), "disliked_ingredients" : customer_recipes.get_disliked_ingredients()}

@app.post("/reset")
def reset():
    customer_recipes.reset()

@app.post("/ingredients/like")
def ingredients_like(ingredient_data: Ingredient):
    ingredient = ingredient_data.ingredient
    if ingredient not in customer_recipes.get_liked_ingredients():
        customer_recipes.like_ingredient(ingredient)
    if ingredient in customer_recipes.get_disliked_ingredients():
        customer_recipes.disliked_ingredients.remove(ingredient)
    print("liked_ingredients : ", customer_recipes.get_liked_ingredients())
    print("disliked_ingredients : ", customer_recipes.get_disliked_ingredients())

@app.post("/ingredients/dislike")
def ingredients_dislike(ingredient_data: Ingredient):
    ingredient = ingredient_data.ingredient
    if ingredient not in customer_recipes.get_disliked_ingredients():
        customer_recipes.dislike_ingredient(ingredient)
    if ingredient in customer_recipes.get_liked_ingredients():
        customer_recipes.liked_ingredients.remove(ingredient)
    
    print("liked_ingredients : ", customer_recipes.get_liked_ingredients())
    print("disliked_ingredients : ", customer_recipes.get_disliked_ingredients())

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="localhost", port=80)