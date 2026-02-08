# 🎬 FawShows App

This is a **multi-module Android application** built with **Clean Architecture** and **MVVM** principles.

The app allows users to explore:
- ⭐ Best movies and TV shows
- 🔥 Daily & weekly trending content
- 🎥 Now playing (in theaters)
- 🚀 Upcoming movies
- 📺 Popular TV series

---

## 🧱 Architecture

The project follows **Clean Architecture** with a **multi-module** setup to improve scalability, testability, and maintainability.


### Key patterns & tools:
- MVVM
- Kotlin Coroutines & Flow
- Dependency Injection (Hilt)
- Retrofit
- Jetpack Compose
- Firebase Crashlytics

---

## 🔥 Firebase Crashlytics Setup (IMPORTANT)

This project uses **Firebase Crashlytics** for crash reporting.

❗ **To avoid build/runtime errors**, you must create your **own Firebase project**.

### Steps:
1. Go to **Firebase Console**
2. Create a new Firebase project
3. Add an **Android app**
4. Download your `google-services.json`
5. Place it inside:

## 🛠 Backend Service (Spring Boot)

This application also has a **custom backend service** written in **Spring Boot**.

### Backend features:
- Provides movie & TV related data
- Acts as a custom API layer
- Designed to work with this Android application

### 📥 Backend Repository
You must also download and run the backend project from the following repository:

👉 **Backend Repository Link:**  
`https://github.com/Hassma21/movienews-Spring-Boot`

---

## 🌐 Backend Configuration (IMPORTANT)

After cloning the backend project:

- You **must update the IP address** in the Android project
- Replace the base URL with **your own local IP address**

Example:
```text
http://YOUR_LOCAL_IP:8080/
