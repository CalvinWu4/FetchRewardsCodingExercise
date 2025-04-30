# Fetch Rewards Android App

An Android application that retrieves and displays data from the Fetch Rewards hiring API.

## Features

- Fetches data from the Fetch Rewards hiring API
- Filters out items with null or blank names
- Sorts items by listId and name
- Groups items by listId for organized display
- Handles network errors gracefully with user feedback
- Implements coroutines for asynchronous operations

## Technical Details

- **Language**: Kotlin
- **Min SDK Version**: 26 (Android 8.0)
- **Target SDK Version**: 32
- **Architecture**: Single Activity
- **Network Operations**: Kotlin Coroutines
- **JSON Parsing**: Gson
- **UI**: Native Android Views with ViewBinding

## Dependencies

- androidx.core:core-ktx:1.7.0
- androidx.appcompat:appcompat:1.4.1
- com.google.android.material:material:1.5.0
- androidx.constraintlayout:constraintlayout:2.1.3
- com.google.code.gson:gson:2.8.9
- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0
- org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0

## Setup

1. Clone the repository
2. Open the project in Android Studio
3. Sync project with Gradle files
4. Run the app on an emulator or physical device

## API

The app fetches data from `https://hiring.fetch.com/hiring.json`. The response is an array of items with the following structure:

```json
{
    "id": Integer,
    "listId": Integer,
    "name": String
}
```

## Data Processing

The app processes the API data by:
1. Filtering out null or empty names
2. Sorting by listId and name
3. Grouping items by listId for display

## Error Handling

- Network errors are caught and displayed to the user
- Error messages are shown in red text at the center of the screen
- The app uses coroutines for managing background tasks and UI updates 