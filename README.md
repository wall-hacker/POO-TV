````
  ____           _____       
 |  _ \ ___   __|_   _|_   __
 | |_) / _ \ / _ \| | \ \ / /
 |  __/ (_) | (_) | |  \ V / 
 |_|   \___/ \___/|_|   \_/  
                             
  ````
- - -
### Description
- - -  
Poo-Tv is a backend implementation of a platform for watching
movies/series from the user's perspective. The basic functionalities
offered for this platform include logging in, registering, and moving
on a specific page available. From the movies page, the user can also
search for a specific movie, filter the movie list based on desired
actors, genres, and even sort movies based on duration and rating.
In order to watch a movie, the user has to purchase it for the
standard price (2 tokens), or for free if the user has premium
account. A user can upgrade his account to premium for 10 tokens and
get 15 free movies, then he pays 2 tokens for each purchase movie
just like standard users. After purchasing the movie, the user can
watch it, then like and rate the movie from 1 to 5. The tokens are
taken explicitly by the user from his balance. At the end, the user
can log out and the platform is restores to unauthenticated homepage.
- - -
### Structure && Implementation 
- - -
**1] actions package:**
 - The actions package contains the implementations of all
desired functionalities, be it a ChangePage type action or an 
OnPage type action

 - The ChangePageAction class contains all the methods or as I
call them actions related to changing the current page(login, 
register, logut, movies, seeDetalis, upgrades); these methods
are then called using a switch statement by a subclass named
ChangePage

- The OnPageAction class contains all the methods or as I
call them actions that can happen on the current page(login,
register, search, filter, buyTokens, buyPremiumAccount,
purchase, watch, like, rate); these methods are then called
using a switch statement by a subclass named OnPage

- These two abstract classes, ChangePage and OnPage, are the
most important because they are the one called by the main
method

**2] database package:**
 - The database package contains all the necessary classes
to gather all information about the users, movies and actions

 - They contain fields in such a way that the JSON structure
of the input files can be parsed accordingly and all information
accessed by the Main class and the classes in actions package

**3] the Main class:**
- The Main class consists of a simple structure:
  - the input file is parsed by our Input type object and the
  rest of the classes in our database package, using an
  objectmapper
  - then we set the currentPage and currentUser, objects used
  in basically all the other methods
  - then we iterate through the Actions array and call on the
  respective methods, be it a ChangePage or a OnPage type
  action
  - inside the actions' package methods the output ArrayNode
  gets filled with information, the last step in our Main
  being to write that information to the output file

- - -
### Feedback && Comments
- - -

This was a very interesting project that I enjoyed working on.

The concept behind this so-called "simplified backend" weren't
very clearly explained. But the process of implementing it was
quite straightforward.

I found it interesting that compared to the last project we had
to start from 0 and create all our 'database' classes as I called
them.

I still haven't mastered the concepts of Object Oriented
Programming yet but I think I am on the right track. Hopefully
in the next stage of this project I'll be able to refine
everything and make use of more design patterns.
