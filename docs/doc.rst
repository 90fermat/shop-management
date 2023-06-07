SHOP-MANAGEMENT
Introuction
Shop-Management is an android application build with android Studio using kotlin language for his development. 
The Jetpack compose framework was using for the UI development. 
Shop-Management is also a hybrid Application in terms of working well both online and offline, this was achieve using Firebase.

Purposes of the app

The Shop-Management App has been developed to make it easier for users to manage small shops. 
It allows you to control sales in a shop, control product stocks, to always have an eye on daily receipts, to always know your profit in the shop.

Architecture

The App was build using Jetpack compose with clean Architecture.
•	separation of concerns was strictly applied. 
•	Unidirectional data flow (state in Compose UI)
state flows in only one direction. The events that modify the data flow in the opposite direction.

The app is divided into two Layers.  UI Layer and Data Layer

UI Layer

  Jetpack Compose

Jetpack Compose is Android’s recommended modern toolkit for building native UI.
In Compose all UI elements are Composable functions. An App Screen UI is the assembling of small composable components.
Some pictures screens of the app.

Data Layer 

  Firebase

We wanted for the app to works well both online and offline. The cloud function was a priority. 
What we mean with cloud function, is the possibility to change the phone, install the app and recover all your old data app in the old phone.
To achieve this we opted for firebase, which offers sweet cloud functions. 
It also manages local data using caching. 
Thus, if you are offline, all request you apply is stored and re try after the internet connection is reestablish.
Thus, Firebase ensure our data modeling online and also local.

  


