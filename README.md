# QuoteGenerator
 
This is my Capstone Project for Udacity "Android Kotlin Developer" Course.

*** OVERVIEW ***

As the name suggests, Quote Generator gives the user the chanche to get a random quote by some famous people from the world and save it to a personal "favorite quotes" list. 

The app consists of three screen: a Home Fragment, a Quote Fragment and a FavoriteQuotesList Fragment. 
In order to achieve the desired functionality the quotes are retrieved from three separate public API that serve as providers. After generating a random quote from one of the providers, the user has the chance to save the generated quote in a "Favorite Quotes" list or generate a new random quote.

- Home Fragment

![HomeFragment](https://user-images.githubusercontent.com/98179119/158987538-bf877903-b1dd-4c3e-b8d0-fe2861393bb9.png)

In the HomeFragment the user is prompted to choose a provider for the quote. The providers are represented by three separate public API that serve the purpose. Clicking on each provider's name will trigger the navigation to the QuoteFragment, passing the providerId as an argument.

Inside this fragment is also present a "Picture of the day" as a little treat. The image is retrieved from a fourth public API, and by clicking on it the image is saved to the device's storage. 
The Picture feature might seem a bit "forced in place" and...well, actually, it is. Such feature wasn't present in my initial inception of the project but the reason behind its implementation is to meet the required specs in the Project Rubric. (In a real world situation this functionality would not be included)
When clicking on the picture the user is first of all asked to give the app permission to access the device storage. If such permission is denied, a Snackbar reminds the user that downloading the picture would not be possible until permission is granted. 
Once the permission is granted, the image is downloaded to the device gallery, then a notification is pushed in order to notify the successful download.

The fragment is initialized with an animation, giving a less "static" UX. 

- Quote Fragment

![QuoteFragmentInit](https://user-images.githubusercontent.com/98179119/158991046-7b102d3c-9910-4004-8b0a-61b3c7f7a8af.png)
![QuoteFragment](https://user-images.githubusercontent.com/98179119/158991149-3921943b-c39f-4b74-ac73-713f5324cacb.png)
![Dialog](https://user-images.githubusercontent.com/98179119/158991209-ab73cd10-6327-40e9-89b6-c82d42a5eca4.png)


The quote fragment represent the core of the app. Here the user is invited to click the "GENERATE" button, populating the Card View with a random quote from the API choosen in the Home Fragment, with its corresponding author. Inside said Card View a "heart" button is set visible (with an animation) after the user generate a quote, giving the chance to save the quote to a personal favorite list database. From this fragment the user is also able to navigate to the list of saved quotes (=FavFragment).
A "INFO" button shows some information regarding the app (at the moment it only says I'm the creator of this App).

- Favorite Quotes Fragment

// TODO inserire immagine 

Lastly, this fragment shows the quotes that the user has previously found interesting, and decided to save. 
It's basically just a RecyclerView loading data from a ROOM db. The fragment is initiated with a MotionLayout animation, always for the sake of a more engaging UX.


________________________________


*** HOW I MET PROJECT SPECS ***

°°° Android UI/UX

- "Build a navigable interface consisting of multiple screens of functionality and data."

Three screens have been implemented, and navigation between them is managed by a Navigation Controller. An argument bundle (= the providerID) is also passed from the Home Fragment and the Quote Fragment.

- "Construct interfaces that adhere to Android standards and display appropriately on screens of different size and resolution."

ConstraintLayout is the layout of choice, and its children are properly identified and constrained.
Nested layouts have been avoided (as much as possible).
Res directories include string, drawables, colors and dimensions.
A RecyclerView is used to display data from the database (=the saved quotes)

- "Animate UI components to better utilize screen real estate and create engaging content."

A MotionLayout manage the transition between the Quote Fragment and the Fav Fragment. Specifically, it impacts the alpha attribute of the destination fragment. A couple more animation are set for the "heart" and providers buttons.


°°° Local and Network data

- "Connect to and consume data from a remote data source such as a RESTful API."

Data is retrieved from 4 different API using Retrofit library and gson helper. Network request are operated inside the ViewModel using suspend functions and Coroutines. Read/write operations from/to the DB are handled coverting data asDomainModel or asDBModel.

- "Load network resources, such as Bitmap Images, dynamically and on-demand."

The app retrieves an image from a API, convert it to Bitmap and show it to the user, while providing a placeholder in case of missing Internet connection.
Picasso library was used for this task.

- "Store data locally on the device for use between application sessions and/or offline use."

The app stores data with appropriate data types inside an internal ROOM database, granting data persistence across sessions. 
Operations over the DB are managed asynchronously.


°°° Android system and hardware integration

- "Architect application functionality using MVVM."

A ViewModel is deployed in order to achieve the separation of concerns. 
System resources are utilized efficiently, preventing leaks where possible.

- "Implement logic to handle and respond to hardware and system events that impact the Android Lifecycle."

Events are handled the best way possible, preventing data loss when changing configuration. Also permission are properly managed (specifically the permission to save files in the device storage)

- "Utilize system hardware to provide the user with advanced functionality and features."

This requirement is satisfied by a notification when downlading a picture. No permission is needed for this specific hardware feature.


_______________________________

*** FUTURE FEATURES ROADMAP & KNOWN BUG/ISSUES ***

- Custom app icon
- Splash screen
- Manage Dark Theme
- Implement a (fake?) loading bar animation when generating a quote
- Web Intent for the "Powered by ${provider}" label to API website
- MailTo Intent in the Alert Dialog box 
- Manage possible duplicates in favorite quotes list
- Write function and UI for deleting a quote from the list
- Better favorite list layout (a header would be nice) 







