# 📸 SnapCam Video

![GitHub Repo Stars](https://img.shields.io/github/stars/suryamkumar/SnapCam_Video?style=social)
![GitHub Forks](https://img.shields.io/github/forks/suryamkumar/SnapCam_Video?style=social)

## 📜 Description
SnapCam Video is an Android application built with **Kotlin and CameraX**, enabling seamless video recording. The app efficiently manages resources and ensures smooth recording using coroutines. It includes a background service that handles timed video segmentation, storing videos in an organized manner.

## 🚀 Features
- 📹 **High-Quality Video Recording** using CameraX
- ⏳ **Automated Video Segmentation** with a timer that increments filenames every 10 minutes
- ⚡ **Optimized Performance** using Kotlin coroutines for background processing
- 📁 **Efficient File Management** to store recordings in an organized manner
- 🎯 **Smooth UI & Performance** ensuring a user-friendly experience

## 🏗 Project Structure
### 📂 MainActivity
- Handles CameraX video capture functionality
- Ensures seamless video recording with high-quality output
- Manages user interactions for starting and stopping recordings

### 📂 Service
- Implements coroutine functions to manage the recording process in the background
- Uses a timer to increment filenames every **10 minutes** for structured storage
- Allows continuous recording without interruptions by handling tasks efficiently on different threads

## 📷 Screenshots
| Home Screen | Recording Screen | File Management |
|------------|----------------|-----------------|
| ![Home](https://via.placeholder.com/150) | ![Recording](https://via.placeholder.com/150) | ![Files](https://via.placeholder.com/150) |

## 📂 Installation
```sh
# Clone the repository
git clone https://github.com/suryamkumar/SnapCam_Video.git

# Open in Android Studio and build the project
```

## 🔧 Tech Stack
- **Kotlin**
- **CameraX** for video recording
- **Coroutines** for efficient background tasks

## 🤝 Contribution
Contributions are welcome! If you'd like to improve the project, follow these steps:
1. **Fork** the repository
2. **Create a new branch** (`feature-branch`)
3. **Commit your changes**
4. **Push to your branch** and submit a **Pull Request**


---

**Thank you for visiting this project! 😊**
