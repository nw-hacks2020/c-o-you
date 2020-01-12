![](https://github.com/nw-hacks2020/c-o-you/blob/master/COyou_logo.png?raw=true)

by Napat Karnsakultorn, Serena Huang, Ben Henaghan, Chad Malla

University of British Columbia 

NWHacks 2020 

## The Idea Behind the App

When you're grabbing dinner at a restaurant, how do you choose which meal to order? 

Perhaps you look at the price, calorie count, or, if you're trying to be environmentally-conscious, the carbon footprint of your meal.

COyou is an Android app which reveals the carbon footprint of the meals on a restaurant menu, so that you can make the most informed choice about the food you eat.

### Why is this important? 

Food production is responsible for a quarter of all greenhouse gas emissions which contribute to global warming. In particular, meat and other animal products are responsible for more than half of food-related greenhouse gas emissions. However, even among fruits and vegetables, the environmental impact of different foods varies considerably. Therefore, being able to determine the carbon footprint of what you eat can make a difference to the planet.

## How COyou Works

All the user has to do is to take a picture of the restaurant menu. 

The app will determine the ingredients of each dish, then display the carbon footprints of the dishes, based on the CO2 emissions produced from farm to plate. 

### More Technical Details

Using pandas, we cleaned our [carbon emissions per ingredients data](https://link.springer.com/article/10.1007/s11367-019-01597-8#Sec24) and mapped it by ingredient-emission.

We used Firebase to enable OCR, so that the app recognizes the text of the menu items. Using Google Cloud's Natural Language API, we broke each menu item name down into entities. For example:
         (1) scrambled eggs -> "eggs", 
         (2) yogurt & granola -> "yogurt", "granola"

If an entry is found (for simpler menu items such as "apple juice"), the CO2 emission is immediately returned. Otherwise, we make an API call to USDA Food Central's database, which returns a list of ingredients for the menu item. Then, we map the ingredients to its CO2 emissions and sum the individual CO2 emissions of each ingredient in the dish. Finally, we display a list of the ingredients and the total CO2 emissions of each dish. 

## The Creation of COyou

We used Android Studio, Google Firebase, Google NLP API, and an enthusiasm for food and restaurants in the creation of COyou. 

## Sources and Further Reading

1. [Determining the climate impact of food for use in a climate tax—design of a consistent and transparent model](https://link.springer.com/article/10.1007/s11367-019-01597-8#Sec24)
2. [Carbon Footprint Factsheet](http://css.umich.edu/factsheets/carbon-footprint-factsheet)
3. [Climate change food calculator: What's your diet's carbon footprint?](https://www.bbc.com/news/science-environment-4645971)
4. [USDA Food Data Central API](https://fdc.nal.usda.gov/index.htm)
