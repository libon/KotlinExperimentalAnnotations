Sample reproduction project for issues with warnings using kotlin experimental apis

* Use an experimental api in a test (ok) and in the app (not ok)
* Add gradle config with `languageSettings` and `optIn` to tell gradle we know what we're doing for tests
* Expected behavior:
  - Command line (`./gradlew clean assembleDebug testDebugUnitTest`):
    - Warning for the app class
    - No warning for the test class
  - Android Studio:
    - Warning for the app class
    - No warning for the test class
* Actual behavior:
  - Command line (`./gradlew clean assembleDebug testDebugUnitTest`):
    - ✅ Warning for the app class
    - ❌Warning for the test class
  - Android Studio:
    - ❌Warning for the app class
    - ✅No warning for the test class
