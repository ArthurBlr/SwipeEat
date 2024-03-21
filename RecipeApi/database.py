"""
TODO:
    - Filtre nutrition faire avec dictionnaire;
"""


import pandas as pd
from ast import literal_eval

def uniques_string_list_by_column_name(df, column, desc=True):
    """ 
    Compte le nombre de fois que chaque ingrédient apparait dans la colonne et les trie par ordre décroissant ou croissant.

    args:
        column : str
        desc : bool
    """
    all_values = {}
    for values in df[column]:
        for value in values:
            if value in all_values:
                all_values[value] += 1
            else:
                all_values[value] = 1

    sorted_values = sorted(all_values.items(), key=lambda x: x[1], reverse=desc)
    return sorted_values

def filter_recipes_by_name(df, name):
    """
    Filtre les recettes par nom donné.

    args:
        df : dataframe
        name : str
    """
    return df[df['name'].apply(lambda x: x.lower() == name.lower())]

def filter_recipes_by_minutes(df, minutes):
    """
    Filtre les recettes par minutes données.

    args:
        df : dataframe
        minutes : int
    """
    return df[df['minutes'] <= minutes]

def filter_recipes_by_n_steps(df, n_steps):
    """
    Filtre les recettes par nombre d'étapes données.

    args:
        df : dataframe
        n_steps : int
    """
    return df[df['n_steps'] <= n_steps]

def filter_recipes_by_n_ingredients(df, n_ingredients):
    """
    Filtre les recettes par nombre d'ingrédients donnés.

    args:
        df : dataframe
        n_ingredients : int
    """
    return df[df['n_ingredients'] <= n_ingredients]

def filter_recipes_by_ingredients(df, ingredients):
    """
    Filtre les recettes par ingrédients donnés.

    args:
        df : dataframe
        ingredients : [ingredient1, ingredient2, ingredient3, ...]
    """
    return df[df['ingredients'].apply(lambda x: all(ingredient in ingredients for ingredient in x))]

def filter_recipes_by_tags(df, tags):
    """
    Filtre les recettes par tags donnés.

    args:
        df : dataframe
        tags : [tag1, tag2, tag3, ...]
    """
    return df[df['tags'].apply(lambda x: all(tag in x for tag in tags))]

def filter_recipes_by_nutrition_max(df, nutrition):
    """
    Filtre les recettes par nutritions maximales données.

    args:
        df : dataframe
        nutrition : [calories (#), total fat (PDV), sugar (PDV), sodium (PDV), protein (PDV), saturated fat (PDV), carbohydrates (PDV)]
    """
    return df[df['nutrition'].apply(lambda x: all(x[i] <= nutrition[i] for i in range(7)))]
             

class Raw_Recipes:
    def __init__(self, df=None, empty=True):
        if empty:
            self.df = pd.read_csv('data\Food.com Recipes and Interactions\RAW_recipes.csv')
            self.df.set_index('id', inplace=True)
            self.df.index.name = 'recipe_id'
            self.df.drop(columns=['contributor_id', 'submitted'], inplace=True)
            self.df.dropna(inplace=True)

            list_column_to_format = ['tags', 'steps', 'ingredients', 'nutrition']
            for list_column in list_column_to_format:
                #df[column] = df[column].apply(lambda x: x[1: -1].replace("'", "").split(","))
                self.df[list_column] = self.df[list_column].apply(literal_eval)
        else:
            self.df = df

    def get_copy_dataframe(self):
        """
        Retourne une copie du dataframe.
        """
        return self.df.copy(deep=True)

    def uniques_string_list_by_column_name(self, column, desc=True):
        """ 
        Compte le nombre de fois que chaque ingrédient apparait dans la colonne et les trie par ordre décroissant ou croissant.

        args:
            column : str
            desc : bool
        """
        return uniques_string_list_by_column_name(self.df, column, desc)
    
    def filter_recipes_by_name(self, name):
        """
        Filtre les recettes par nom donné.

        args:
            df : dataframe
            name : str
        """
        return Raw_Recipes(filter_recipes_by_name(self.df, name), empty=False)
    
    def filter_recipes_by_minutes(self, minutes):
        """
        Filtre les recettes par minutes données.

        args:
            df : dataframe
            minutes : int
        """
        return Raw_Recipes(filter_recipes_by_minutes(self.df, minutes), empty=False)
    
    def filter_recipes_by_n_ingredients(self, n_ingredients):
        """
        Filtre les recettes par nombre d'ingrédients donnés.

        args:
            df : dataframe
            n_ingredients : int
        """
        return Raw_Recipes(filter_recipes_by_n_ingredients(self.df, n_ingredients), empty=False)

    def filter_recipes_by_ingredients(self, ingredients):
        """
        Filtre les recettes par ingrédients donnés.

        args:
            df : dataframe
            ingredients : [ingredient1, ingredient2, ingredient3, ...]
        """
        return Raw_Recipes(filter_recipes_by_ingredients(self.df, ingredients), empty=False)

    def filter_recipes_by_tags(self, tags):
        """
        Filtre les recettes par tags donnés.

        args:
            df : dataframe
            tags : [tag1, tag2, tag3, ...]
        """
        return Raw_Recipes(filter_recipes_by_tags(self.df, tags), empty=False)

    def filter_recipes_by_nutrition_max(self, nutrition):
        """
        Filtre les recettes par nutritions maximales données.

        args:
            df : dataframe
            nutrition : [calories (#), total fat (PDV), sugar (PDV), sodium (PDV), protein (PDV), saturated fat (PDV), carbohydrates (PDV)]
        """
        return Raw_Recipes(filter_recipes_by_nutrition_max(self.df, nutrition), empty=False)
    
    def __str__(self) -> str:
        return self.df.head().to_string()
    

class Customer_Recipes:
    def __init__(self, raw_recipes):
        self.liked_ingredients = []
        self.disliked_ingredients = []
        self.raw_recipes = raw_recipes

    def reset(self):
        self.liked_ingredients = []
        self.disliked_ingredients = []
        
    def like_ingredient(self, ingredient):
        self.liked_ingredients.append(ingredient)

    def dislike_ingredient(self, ingredient):
        self.disliked_ingredients.append(ingredient)

    def get_liked_ingredients(self):
        return self.liked_ingredients
    
    def get_disliked_ingredients(self):
        return self.disliked_ingredients
    
    def get_not_evaluated_ingredients(self):
        list = [ingredient for ingredient, count in self.raw_recipes.uniques_string_list_by_column_name('ingredients')]
        return [ingredient for ingredient in list if ingredient not in self.liked_ingredients and ingredient not in self.disliked_ingredients]
    
    # get recipes by filters (name, minutes, ingredients, tags, nutrition, n_steps, n_ingredients)
    def get_recipes_dataframe_filtered_copy(self, name=None, minutes=None, ingredients=None, tags=None, nutrition=None, n_steps=None, n_ingredients=None):
        recipes = self.raw_recipes
        if name is not None:
            recipes = recipes.filter_recipes_by_name(name)
        if minutes is not None:
            recipes = recipes.filter_recipes_by_minutes(minutes)
        if n_steps is not None:
            recipes = recipes.filter_recipes_by_n_steps(n_steps)
        if n_ingredients is not None:
            recipes = recipes.filter_recipes_by_n_ingredients(n_ingredients)
        if ingredients is not None:
            recipes = recipes.filter_recipes_by_ingredients(ingredients)
        if tags is not None:
            recipes = recipes.filter_recipes_by_tags(tags)
        if nutrition is not None:
            recipes = recipes.filter_recipes_by_nutrition_max(nutrition)
        return recipes.get_copy_dataframe()
    
    def get_raw_recipes(self):
        return self.raw_recipes
    
    def __str__(self) -> str:
        return f"liked_ingredients : {self.liked_ingredients}\ndisliked_ingredients : {self.disliked_ingredients}"
    


if __name__ == "__main__":
    raw_recipes = Raw_Recipes()

    print(raw_recipes.filter_recipes_by_ingredients(['butter', 'sugar']).filter_recipes_by_nutrition_max([1, 100, 100, 100, 100, 100, 100]))

    customer_recipes = Customer_Recipes(raw_recipes)