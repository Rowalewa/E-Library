E-Library

📚 E-Library

A full-featured Android library management application built with **Kotlin** and **Jetpack Compose**, powered by **Firebase**. E-Library digitalises the end-to-end library experience — from browsing and borrowing books to home delivery and return tracking — across six distinct user roles.

---

## 📸 Screenshots

![dashboard_2](https://github.com/Rowalewa/E-Library/assets/132871124/c7ad337b-d759-4eb0-a557-f632e56a421d)
![home_2](https://github.com/Rowalewa/E-Library/assets/132871124/a182a4e2-6598-41f1-a1ac-aac03433d03f)
![staff_home_2](https://github.com/Rowalewa/E-Library/assets/132871124/f94b94f7-fa4b-44a5-a554-0d4e587bec2b)
![client_home_2](https://github.com/Rowalewa/E-Library/assets/132871124/454859db-de80-4d72-9f76-78da8311ac9f)

---

 ✨ Features

👤 Multi-Role System
E-Library supports six independent user roles, each with its own authentication flow, home screen, and tailored feature set:

| Role | Description |
|---|---|
| **Guest** | Browse the catalogue and view book details without an account |
| **Client** | Borrow books, manage a cart, request home delivery, track deliveries, and view borrowing history |
| **Staff** | Process in-library borrowing and returns on behalf of clients |
| **Attendant** | Oversee client accounts, delivery assignments, and return confirmations |
| **Delivery Personnel** | Accept delivery requests, update delivery status, and confirm returns |
| **Admin** | Full system control — manage all users, books, and app-wide settings |

📖 Book Catalogue
- Rich book records: title, author, ISBN, publisher, publication date, genre, edition, language, page count, shelf number, synopsis, cover image, and stock quantity
- Browse as guest or logged-in user
- Add, edit, and delete books (Admin / Staff)

🔄 Borrowing & Returns
- In-library borrowing processed by Staff
- Client self-service borrowing with due-date tracking
- Fine system for overdue returns (tracked per client account)
- Library drop-off and delivery-based return flows

🛒 Cart & Home Delivery
- Clients add books to cart and request delivery
- Delivery location selection with distance and pricing
- Real-time delivery status updates (Ordered → Picked Up → Delivered → Returned)
- Google Maps integration for delivery location display
- GPS-based location services

🔔 Notifications
- Firebase Cloud Messaging (FCM) push notifications
- Firebase In-App Messaging for contextual alerts

📞 Contact & Feedback
- Role-aware contact directory (Staff contacts visible from Client, Attendant, Admin, etc.)
- Feedback submission available to all logged-in roles

🛡️ Legal & Support
- Per-role User Manuals, Privacy Policy, and End User Licence Agreement (EULA)
- About screen for each role
- Network connectivity monitoring with offline state handling

---

🏗️ Architecture & Tech Stack

| Layer | Technology |
|---|---|
| **Language** | Kotlin |
| **UI** | Jetpack Compose + Material 3 |
| **Navigation** | Jetpack Navigation Compose |
| **State Management** | ViewModel + LiveData + Compose State |
| **Backend** | Firebase Realtime Database |
| **Authentication** | Firebase Authentication |
| **File Storage** | Firebase Storage |
| **Push Notifications** | Firebase Cloud Messaging (FCM) |
| **In-App Messaging** | Firebase In-App Messaging |
| **Maps** | Google Maps SDK + Maps Compose |
| **Location** | Google Play Services Location |
| **Image Loading** | Coil (coil-compose) |
| **Min SDK** | API 24 (Android 7.0) |
| **Target SDK** | API 34 (Android 14) |

---

📁 Project Structure

```
app/src/main/java/com/example/e_library/
├── data/                   # ViewModels & business logic
│   ├── AuthViewModel.kt        # Authentication for all roles
│   ├── BooksViewModel.kt       # Book CRUD operations
│   ├── DeliveryViewModel.kt    # Delivery lifecycle management
│   ├── TransactionViewModel.kt # Borrowing & return transactions
│   ├── FeedbackViewModel.kt    # Feedback submission & retrieval
│   ├── ContactViewModel.kt     # Contact directory
│   ├── DialogViewModel.kt      # Dialog state management
│   └── NetworkViewModel.kt     # Connectivity monitoring
│
├── models/                 # Data classes
│   ├── Books.kt
│   ├── Clients.kt
│   ├── Admin.kt
│   ├── Staff.kt
│   ├── Attendant.kt
│   ├── DeliveryPersonnel.kt
│   ├── BorrowingBook.kt
│   ├── CartOrder.kt
│   ├── Delivery.kt
│   ├── DeliveryLocation.kt
│   ├── DeliveryReturn.kt
│   ├── Feedback.kt
│   ├── Contact.kt
│   ├── NetworkUtils.kt
│   └── MyFirebaseMessagingService.kt
│
├── navigation/             # Navigation graph
│   ├── AppNavHost.kt           # Composable nav host
│   └── Routes.kt               # All route constants
│
└── ui/theme/screens/       # UI screens (110+ composables)
    ├── splash/             # Splash screen
    ├── dashboard/          # Role-selection dashboard
    ├── home/               # Guest home & book browsing
    ├── admin/              # Admin login, registration, account management
    ├── clients/            # Client auth, home, account
    ├── staff/              # Staff auth, home, account
    ├── attendant/          # Attendant auth, home, account
    ├── deliveryPersonnel/  # Delivery personnel auth, home, account
    ├── books/              # Book management screens
    ├── borrowing/          # Borrowing workflows
    ├── returning/          # Return workflows
    ├── cart/               # Cart management
    ├── delivery/           # Delivery tracking
    ├── deliveryReturn/     # Return confirmation flows
    ├── map/                # Google Maps screen
    ├── contact/            # Role-aware contact screens
    ├── feedback/           # Per-role feedback screens
    ├── about/              # Per-role about screens
    ├── userManual/         # Per-role user manuals
    ├── privacyPolicy/      # Per-role privacy policies
    └── endUserLicenceAgreement/  # Per-role EULAs
```

---

🚀 Getting Started

Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 8
- A Firebase project
- A Google Maps API key

Firebase Setup

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project.
2. Add an Android app with package name `com.example.e_library`.
3. Download `google-services.json` and place it in `app/`.
4. Enable the following Firebase services:
   - **Authentication** (Email/Password)
   - **Realtime Database**
   - **Storage**
   - **Cloud Messaging**
   - **In-App Messaging**

Google Maps Setup

1. Enable the **Maps SDK for Android** in the [Google Cloud Console](https://console.cloud.google.com/).
2. Create an API key and add it to your `local.properties`:

```properties
MAPS_API_KEY=your_api_key_here
```

### Build & Run

```bash
# Clone the repository
git clone https://github.com/Rowalewa/E-Library.git
cd E-Library

# Open in Android Studio and sync Gradle, or build from CLI:
./gradlew assembleDebug
```

---

🔒 Permissions

| Permission | Purpose |
|---|---|
| `INTERNET` | Firebase & Maps API communication |
| `ACCESS_NETWORK_STATE` | Offline/online state detection |
| `ACCESS_FINE_LOCATION` | Precise delivery location |
| `ACCESS_COARSE_LOCATION` | Approximate location fallback |
| `WAKE_LOCK` | Background notification processing |
| `RECEIVE` (C2DM) | Push notification delivery |

---

🗺️ Delivery Flow

```
Client adds book to cart
        ↓
Client selects delivery location & places order
        ↓
Delivery Personnel views pending orders
        ↓
Personnel picks up book → status: "Picked Up"
        ↓
Personnel delivers to client → status: "Delivered"
        ↓
Client initiates return OR Personnel collects return
        ↓
Attendant confirms return → borrowing record closed
```

---

🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m 'Add your feature'`
4. Push to the branch: `git push origin feature/your-feature`
5. Open a Pull Request

---

📄 Licence

This project is licenced under the terms described in the in-app End User Licence Agreement (EULA).

---

📬 Contact

For questions or support, use the in-app **Contact Us** screen or open an issue on GitHub.

