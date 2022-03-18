# QuoteGenerator
 
This is my Capstone Project for Udacity "Android Kotlin Developer" Course.

OVERVIEW

As the name suggests, Quote Generator gives the user the chanche to get a random quote by some famous people from the world and save it to a personal "favorite quotes" list. 

The app consists of three screen: a Home Fragment, a Quote Fragment and a FavoriteQuotesList Fragment. 
In order to achieve the desired functionality the quotes are retrieved from three separate public API that serve as providers. After generating a random quote from one of the providers, the user has the chance to save the generated quote in a "Favorite Quotes" list or generate a new random quote.

- Home Fragment

![HomeFragment](https://user-images.githubusercontent.com/98179119/158987538-bf877903-b1dd-4c3e-b8d0-fe2861393bb9.png)

In the HomeFragment the user is prompted to choose a provider for the quote. The providers are represented by three separate public API that serve the purpose. Clicking on each provider's name will trigger the navigation to the QuoteFragment, passing the providerId as an argument.

Inside this fragment is also present a "Picture of the day" as a little treat. The image is retrieved from a fourth public API, and by clicking on it the image is saved to the device's storage. 
The Picture feature might seem a bit "forced in place" and...well, actually, it is. Such feature wasn't present in my initial inception of the project but the reason behind its implementation is to meet the required specs in the Project Rubric. (In a real world situation this functionality would not be included)
When clicking on the picture the user is first of all asked to give the app permission to access the device storage. If such permission is denied, a snackbar reminds the user that downloading the picture would not be possible until permission is granted. 
Once the permission is granted, the image is downloaded to the device gallery, then a notification is pushed in order to notify the successful download.

The fragment is initialized with an animation, giving a less "static" UX. 

- Quote Fragment
![QuoteFragmentInit](https://user-images.githubusercontent.com/98179119/158991046-7b102d3c-9910-4004-8b0a-61b3c7f7a8af.png)
![QuoteFragment](https://user-images.githubusercontent.com/98179119/158991149-3921943b-c39f-4b74-ac73-713f5324cacb.png)
![Dialog](https://user-images.githubusercontent.com/98179119/158991209-ab73cd10-6327-40e9-89b6-c82d42a5eca4.png)


WIP






