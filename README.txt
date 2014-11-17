EARTHQUAKES

Description
------------
This application retrieves earth quakes data from a REST web service and displays it on a list and a map to localize earthquakes.

Platform: Android
compatibility : API 10 to API 21

Instructions
------------
If you want to run the project on your computer, and test the debug apk, you have to replace to the Google map api key.
The app-release.apk is a signed app so it can be used to test.

Dependencies
-------------

- Volley (online doc : http://afzaln.com/volley/)
- Android support v4
- Android support v7
- https://github.com/JakeWharton/double-espresso gradle port of espresso

Software Patterns
-----------------
Singleton: The singleton allows the access of data through the application. The singleton object is kept in memory during the application's lifetime.
Enum Singleton : The enum singleton thread safety and serialization is guaranteed by the JVM.
Builder : Because the earthquake object has more than 4 members, it is preferable to use a builder for readability.
Factory : Provide an abstraction level between the backend and the frontend.

Software design
---------------
Encapsulation: to guaranty that the data cannot be modified and result in side effects.

Testing
--------
Unit testing of data parsing.
Functional backend's testing.

Reusability
--------------
The backend structure is reusable in other projects.
- IDataAccessor
- AbstractWebServiceClient
- VolleyHelper

Behavior
---------
The app's main screen is a list displaying earthquakes sorted by date in descendant order.
When user selects an item from the list, the data is displayed on a map.
A button is available in the action bar to switch to map or list view.
If a network error occurred a message is displayed to the user inviting him to check his network settings,
but a better way of doing this would be to launch the setting activity.
On the map view, markers are not customized, but a good addition would be to design a proper marker view.

Conception
-----------
The app receives data from a REST webservice, so a server cache policy must be defined.
In the web service example there is no cache so the data will be refreshed each time the activity is presented.
This behavior guarantees that the data will be the same on the app and on the server.
I used the Volley library, so the cache is handled by default, but since the no-cache cache directive might be set,
I modified the volley library cache parsing to keep a cache entry ready when the app is in offline mode.
If the app is just about displaying content, and there is no complex data model, or offline creation/modification,
there is no need for a data base. A good cache management is enough to access data in offline mode.
The app uses fragments to display the information, one for the list view, the other for the map view.
On smartphone each fragment is handled by an activity, so I didn't have to modify the back stack.
It would be the case if I exchanged the fragments programmatically. On tablet one activity manages the two fragments.




