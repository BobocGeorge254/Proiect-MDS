# MS Teams

MS Teams enables effortless registration and intercation between users, 
throught groups, where users can connect with their colleagues and engage in multiple channels
adapted for different purposes. The application also has a chat feature facilitating real
time communication.

## Project made by:

* Dana Mihai Razvan
* Boboc George
* Dirva Nicolae

## Requirements:

+ Download the application.

## User stories

* As a user, I want to be able to register and log in to my account easily.
* As a user, I want to be able to edit my profile.
* As a user, I want to be able to join a channel using an invitation code.
* As a user, I want to be able to create my own channels.
* As a user, I want to be able to create subchannels within the channels I create.
* As a user, I want to be able to edit, delete, and leave the channels I create.
* As a user, I want to be able to create posts in channels.
* As a user, I want to be able to delete these posts.
* As a user, I want to be able to leave comments on posts.
* As a user, I want to be able to delete these comments.
* As a user, I want to be able to view all comments on a post.
* As a user, I want to be able to upload documents to channels.
* As a user, I want to see the uploaded documents in the channels I am in.
* As a user, I want to be able to send messages to other users.
* As a user, I want to be able to search for users by email or username.
* As a user, I want to see the conversations with the users I have talked to.
* As a user, I want to be able to log out of my account.

## Backlog creation
![Nu s-a putut incarca imaginea!](https://github.com/BobocGeorge254/Proiect-MDS/blob/main/trello.png)

## Diagram

![Nu s-a putut incarca imaginea!](https://github.com/BobocGeorge254/Proiect-MDS/blob/main/diagrama.jpg)

## Source control (branch creation, merge/rebase, pull requests)

![Nu s-a putut incarca imaginea!](https://github.com/BobocGeorge254/Proiect-MDS/blob/main/branches.png) 

## Automated tests
* Test Description: 
The purpose of this test is to verify the correctness of the getters in the PreferencesManager class, which is responsible for managing user preferences.


* Test Steps: 
- Call the getter methods in the PreferencesManager class to retrieve the preferences.
- Compare the retrieved values with the expected values.


* Test Cases
- Get User ID
- Expected Result: The getUserId() method should return the correct user ID.
- Actual Result: The getUserId() method returned the expected user ID.
- Get User Name
- Expected Result: The getUserName() method should return the correct user name.
- Actual Result: The getUserName() method returned the expected user name.
- Get Email Address
- Expected Result: The getUserEmail() method should return the correct email address.
- Actual Result: The getUserEmail() method returned the expected email address.


* Similarly for profile activity


![Nu s-a putut incarca imaginea!](https://github.com/BobocGeorge254/Proiect-MDS/blob/main/tests.png)

## Bug report

During development we faced several bugs that were solved such a bug that was adding the same post/post reply multiple times to the database.
There are still a few known bugs left such as a bug where the profile image doesn't change in the databse every time the user tries to update it,
or a visual bug in the chat cattegory.

## Refactoring, code standards

We used refactoring to rename variables and function names as suggestive as possible.

## Comments

We used many comments in this project to clearly explain the implementation.This makes it easy for you to understand the code. 

## Design patterns

We used Singleton design pattern in utility classes such as Manager, PreferencesManager, Utils.

## ChatGPT

We used chatGPT to correctly position the messages on the page.Furthermore, We used it to generate the message sorting function and many other functions.

![Nu s-a putut incarca imaginea!](https://github.com/BobocGeorge254/Proiect-MDS/blob/main/chat1.png)
![Nu s-a putut incarca imaginea!](https://github.com/BobocGeorge254/Proiect-MDS/blob/main/chat2.png)

## Video demo
The demo of the app can be found [here](https://unibucro0-my.sharepoint.com/:v:/g/personal/andrei_nicolaescu_s_unibuc_ro/ESnEH8GgG8tPu_lm5bZWlskBrWcXJwT9Egu8K2R5tGbHVw).
