<div align="center">

# 📱 KYAU Student App

**A feature-rich Android application for Khwaja Yunus Ali University students**

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
[![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
[![Firebase](https://img.shields.io/badge/Backend-Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)](https://firebase.google.com)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active-success?style=for-the-badge)](https://github.com)

<br/>

*Empowering KYAU students with instant access to academics, attendance, results, and more — all in one place.*

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [System Architecture](#-system-architecture)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [Project Structure](#-project-structure)
- [Firebase Setup](#-firebase-setup)
- [Screenshots](#-screenshots)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🌟 Overview

The **KYAU Student App** is a native Android application built with **Java** that serves as a digital portal for students of Khwaja Yunus Ali University. It provides real-time access to academic records, attendance tracking, course schedules, financial information, and more — all backed by Firebase's powerful cloud infrastructure.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔐 **Authentication** | Secure login, registration, auto-login, and password management |
| 🏠 **Dashboard** | Personalized home screen with CGPA summary and university info |
| 📊 **Attendance** | Real-time attendance tracking with percentage and progress |
| 📚 **Courses** | Full course schedule and enrolled subject details |
| 💰 **Finance** | Fee status, due amounts, and payment records |
| 🏆 **Results** | Semester-wise GPA and cumulative CGPA display |
| 🪪 **Admit Card** | View and download admit card as a PNG file |
| 👤 **Profile** | Edit personal info and change password |
| 🔔 **Notifications** | Real-time push notifications for university updates |
| 📴 **Offline Support** | Local cache for uninterrupted access without internet |

---

## 🏗 System Architecture

The app follows a clean **3-tier layered architecture**:

```
┌─────────────────────────────────────────────────────────┐
│                   PRESENTATION LAYER                     │
│              Android UI  (Java + XML)                    │
│                                                          │
│  Splash · Login · Dashboard · Admit Card · Profile       │
│  Home · Attendance · Courses · Finance · Results         │
└──────────────────────┬──────────────────────────────────┘
                       │ calls
┌──────────────────────▼──────────────────────────────────┐
│                 BUSINESS LOGIC LAYER                     │
│               Java Models + Adapters                     │
│                                                          │
│  Models          Adapters         Auth Logic             │
│  ├─ Student      ├─ Attendance    ├─ Register            │
│  ├─ Attendance   ├─ Course        ├─ Login               │
│  ├─ Course       ├─ Finance       ├─ Logout              │
│  └─ Finance      └─ Result        └─ Change Password     │
│                                                          │
│  Data Logic                  File I/O                    │
│  ├─ Firestore read/write     ├─ Bitmap / PNG save        │
│  ├─ Real-time sync           ├─ FileProvider             │
│  └─ Offline cache            └─ Share                    │
└──────────────────────┬──────────────────────────────────┘
                       │ requests
┌──────────────────────▼──────────────────────────────────┐
│                    BACKEND LAYER                         │
│               Firebase  (Google Cloud)                   │
│                                                          │
│  Firebase Auth       Cloud Firestore    Local Storage    │
│  ├─ Email/Password   ├─ students/       ├─ SharedPrefs   │
│  ├─ Session token    ├─ attendance/     ├─ FileProvider  │
│  ├─ Auto-login       ├─ courses/        ├─ Admit Card    │
│  └─ Sign out         ├─ examInfo/       └─ External files│
│                      ├─ results/                         │
│                      ├─ finance/                         │
│                      ├─ Real-time sync                   │
│                      └─ Security rules                   │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────┐
│                    DEVICE LAYER                          │
│           Android device / emulator                      │
│                                                          │
│      Camera · Storage · Network · Share API · Notifications│
└─────────────────────────────────────────────────────────┘
```

---

## 🛠 Tech Stack

**Frontend / Mobile**
- Language: **Java**
- UI: **Android XML Layouts**
- Navigation: **Bottom Navigation View**
- Image handling: **Bitmap API**

**Backend & Cloud**
- Authentication: **Firebase Auth** (Email/Password)
- Database: **Cloud Firestore** (real-time, offline-capable)
- Storage: **Android SharedPreferences**, **FileProvider**, External Storage

**Device APIs**
- Camera API
- Android Storage
- Network connectivity
- Share API (Intent-based)
- Push Notifications (FCM)

---

## 🚀 Getting Started

### Prerequisites

- Android Studio **Flamingo** or later
- JDK **11** or higher
- Android SDK **API 24+** (Android 7.0)
- A Firebase project (see [Firebase Setup](#-firebase-setup))
- A Google account

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/salam0340/kyau-student-app.git
   cd kyau-student-app
   ```

2. **Open in Android Studio**
   ```
   File → Open → Select the cloned project folder
   ```

3. **Add your `google-services.json`**

   Download it from your Firebase console and place it in:
   ```
   app/google-services.json
   ```

4. **Sync Gradle and build**
   ```
   Build → Rebuild Project
   ```

5. **Run the app**
   ```
   Run → Run 'app'  (or press Shift+F10)
   ```

---

## 📁 Project Structure

```
kyau-student-app/
│
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/kyau/studentapp/
│   │       │   ├── activities/           # UI screens (Login, Dashboard, etc.)
│   │       │   ├── fragments/            # Home, Attendance, Courses, Finance, Results
│   │       │   ├── models/               # Student, Attendance, Course, Finance
│   │       │   ├── adapters/             # RecyclerView adapters
│   │       │   ├── auth/                 # Register, Login, Logout, ChangePassword
│   │       │   ├── data/                 # Firestore read/write, offline cache
│   │       │   └── utils/                # File I/O, Bitmap, FileProvider helpers
│   │       │
│   │       └── res/
│   │           ├── layout/               # XML layout files
│   │           ├── drawable/             # Icons and graphics
│   │           ├── values/               # Colors, strings, themes
│   │           └── navigation/           # Nav graph (if using NavComponent)
│   │
│   ├── google-services.json              # ⚠️ Not committed – add your own
│   └── build.gradle
│
├── gradle/
├── build.gradle
└── README.md
```

---

## 🔥 Firebase Setup

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project.

2. Register your Android app with the package name (e.g., `com.kyau.studentapp`).

3. Download the `google-services.json` file and place it in the `app/` directory.

4. Enable the following Firebase services:
   - **Authentication** → Email/Password sign-in method
   - **Cloud Firestore** → Create database (start in test mode, then add security rules)

5. Set up Firestore collections:
   ```
   /students/{uid}
   /attendance/{uid}
   /courses/{courseId}
   /examInfo/{examId}
   /results/{uid}
   /finance/{uid}
   ```

6. Apply Firestore security rules to restrict access by authenticated user UID.

---

## 🤝 Contributing

Contributions are welcome! Here's how to get started:

1. Fork this repository
2. Create a new branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a Pull Request

Please make sure your code follows the existing coding style and includes relevant comments.

---

## 👥 Contributors

Thanks to these amazing people who built this project:

<table>
  <tr>
    <td align="center">
      <b>Md. Abdus Salam (Team Leader)</b>
    </td>
    <td align="center">
      <b>Mst. Farhana Akter</b>
    </td>
    <td align="center">
      <b>Junan Nahiyan Niloy</b>
    </td>
    <td align="center">
      <b>Mst. Anika Sarker Imo</b>
    </td>
  </tr>
</table>

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<div align="center">

Made with ❤️ for KYAU Students

⭐ Star this repo if you found it helpful!

</div>
