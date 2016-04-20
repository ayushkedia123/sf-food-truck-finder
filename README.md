# Food Trucks Finder

An Android application to find Food Trucks near you in San Fransisco. You can find all the Food Trucks near you in the map and in the list view. You can get the truck details like name of vendor, permit, distance from you, menu, location and open hours.

Data is provided by [data.sfgov.org](data.sfgov.org) . You can access this dataset via the [Socrata Open Data API](https://dev.socrata.com/).

# Screenshots
<p align="center">
  <img src="https://raw.github.com/ayushkedia123/sf-food-truck-finder/master/screenshots/splash.png" width="350"/>
  <img src="https://raw.github.com/ayushkedia123/sf-food-truck-finder/master/screenshots/map_foodtrucks.png" width="350"/>
</p>
<p align="center">
  <img src="https://raw.github.com/ayushkedia123/sf-food-truck-finder/master/screenshots/list_foodtrucks.png" width="350"/>
  <img src="https://raw.github.com/ayushkedia123/sf-food-truck-finder/master/screenshots/foodtruckdetails.png" width="350"/>
</p>

## Sailent Features:
- View the food trucks near you in the map.
- View the list of food trucks sorted by distance nearest to you.
- On clicking the marker you can view the details of each food truck like permit, distance, open hours, location, menu items and vendor name.

## Technologies used:
#### Build Tools:
|Name|Version|Description|
|---|---|---|
| [Gradle](http://gradle.org/docs/current/release-notes) | 2.8 | Gradle build system |
| [Android Gradle Build Tools](http://tools.android.com/tech-docs/new-build-system) | 1.5.0 | Official Gradle Plugin |
| [Android SDK Build Tools](http://developer.android.com/tools/revisions/build-tools.html) | 23.0.3 | Official Build Tools |
| [Android Studio](http://tools.android.com/recent) | 1.5.1 | Official IDE |

####Android Libraries:
|Name|Version|Description|
|---|---|---|
| [Android AppCompat-v7](http://developer.android.com/tools/support-library/features.html#v7-appcompat) | 23.2.1 | Support Library API 7+|
| [Android Google Play Services](https://developer.android.com/google/play-services/index.html) | 8.4.0 | Google Maps, Google Location |
| [Retrofit](http://square.github.io/retrofit/) | 1.9.0 | Network library |
| [OkHttp](http://square.github.io/okhttp//) | 2.7.2 | HTTP Client |

## Future wishlist
- Adding an option in the settings to select the Kilometre range upto which you want to see the food trucks.
- Giving option to search the food truck based on the location name or vendor name.
- Allowing an option to rate and reviews the food truck so that it helps other customers.
- Add food trucks to the favorite list.
- Sharing food truck details with your family and friends.