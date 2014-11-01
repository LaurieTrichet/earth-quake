EARTHQUAKES

Description
------------
This application retrieve earth quakes data from a REST web service and display it through a list and a map to localize the earthquake.

Platform: Android
compatibility : API 10 to API 21

Dependencies
-------------

- Volley
- Android support v4
- Android support v7

Pattern used
-------------
Singleton: The singleton allows the access of data through the application. The singleton object is keep in memory the application's lifetime.
Enum Singleton : The enum singleton thread safety is guaranteed by JVM.
Builder : Because the earthquake object has more than 4 members, it preferable to use a builder for readability.

Testing
--------
Unit testing of data parsing.

Reusability
--------------
All the backend structure is reusable, because the behavior is described by interfaces.



