# Gamehub - Android version

This app is made for those who like to play (board)games but need others to play with them. As a user you will be able to create a game party to play with others or join an already existing party. In doing so, you will meet new people and have lots of fun!

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone git@github.com:MoutPessemier/GameHub-Android.git
```

### Test credentials

Email: test@hotmail.com

Password: P@ssword1111

### Slow load on launch
This is normal, since I'm using the free hosting platform of [Heroku](https://www.heroku.com/). This means that my backend is running online, but it's not running permanently. When you send your first request, the backend will start up but by that time, a `SocketTimeoutException` will be thrown. This is caught and resent but this means that the inital startup will be rather slow. After that inital request, my backend will have started up and will work smoothly.


## Technologies Used:

 - Frontend: Android (Kotlin 1.3.50, Android Studio 3.5)
 - Backend: Node.JS with an Express layer
 - Database: MongoDB (mongoose)
 
## Usage:

1. Have a **virtual machine** installed / **physical device** on which you can install the apk
2. Clone this project using a cloning tool like git
3. Run

## Links:

- [IOS Frontend](https://github.com/MoutPessemier/GameHub-IOS)
- [Backend](https://github.com/MoutPessemier/GameHub-Backend)
- [Twitter](https://twitter.com/MoutPessemier)
- [LinkedIn](https://www.linkedin.com/in/moutpessemier/)

Copyright 2019 © by Mout Pessemier
