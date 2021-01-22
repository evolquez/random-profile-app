# Random Profile APP

## Main Goal
- Create an Android app using the Random User API.

## Functional Requirements
 - Must have a grid list showing at least 50 profile image thumbnails per page.
 - You must implement some kind of pagination or “infinite scroll” on the grid when the user
reaches the end of the list.
- Saved users grid list: this list needs to have a horizontal scrolling and will be displayed on
top of the home screen with the saved users, followed by the users list loaded from the
API.
- This list will be displayed always when it exist at least one element on it, even if the user
closes the app.
- A search bar where there user can search a specific profile by their name.
- A “suggestions” feature on the search bar where a list of suggested results will pop under
the search field based on user input.
- When users tap on a thumbnail on the grid or a result in the suggestion list, they’re taken
into an item detail screen.
- Add feature to save user number as a phone contact with name field auto completed in
the “Add Contact” screen of the default phone app on the device.

## Item detail screen
- Large profile image is shown on the top of the screen, and some basic user data is listed below: username, first name, last name, email address, etc. Feel free to be creative on which data should be displayed, but the name, email and phone number must be always displayed.
- The app needs to have the possibility to mark as “favorite” a user. When this occurs, a new list should be displayed on the home screen with the saved users.

## Libraries
- RxJava
- Retrofit
- GSON
- Picasso
- Room

## Configurations
- View Binding
- Compiled SDK Version **29**
- Min SDK Version **26**
- Target SDK Version **26**
