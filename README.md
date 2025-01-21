# Countdoku

Countdoku is a puzzle game application built using Kotlin and Java. The project uses Gradle as the build system and Android Studio as the development environment.

## Features

- Puzzle selection and solving
- Custom progress bar
- Rotating shapes
- Database integration for storing puzzle levels

## Project Structure

- `src/main/java/com/coda/countdoku/`: Contains the main source code for the application.
  - `data/local/dao/`: Contains the Data Access Object (DAO) classes for database operations.
  - `models/`: Contains the data models used in the application.
  - `presentation/puzzle/`: Contains the UI components and screens for the puzzle game.
  - `presentation/puzzle/component/`: Contains custom UI components used in the puzzle screen.

## Dependencies

- Kotlin
- Java
- AndroidX
- Jetpack Compose
- SQLite

## Getting Started

### Prerequisites

- Android Studio Meerkat | 2024.3.1 Nightly 2025-01-12
- Gradle

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/countdoku.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.

### Running the Application

1. Connect an Android device or start an emulator.
2. Click on the "Run" button in Android Studio.

## Usage

- Select a puzzle level to start playing.
- Use the provided numbers and operations to solve the puzzle.
- Track your progress using the custom progress bar.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.

## Acknowledgements

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [AndroidX](https://developer.android.com/jetpack/androidx)
- [SQLite](https://www.sqlite.org/index.html)

---

Feel free to customize this README file according to your project's specific details and requirements.
