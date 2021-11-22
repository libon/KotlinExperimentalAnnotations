Sample reproduction project for issues with warnings using kotlin experimental apis

# Step 1 - No annotations or `languageSettings` gradle configuration
* Use an experimental api in a test class (`MyTestApplicaton`) (ok) and in an app class (`MyApplication`) (not ok)
  - Important: this api should be on an **overridden function**. In this example, it's on a class extetnding `Application` and on the overridden `onCreate()`
    ```kotlin
    override fun onCreate() {
        super.onCreate()
        TimeSource.Monotonic.markNow()
    }
    ```
* Build the app and test: `./gradlew clean assembleDebug testDebugUnitTest`.
* Gradle outputs these **errors** for `MyApplication` and aborts the build:
```
e: /Users/calvarez/dev/projects/KotlinExperimentalAnnotations/app/src/main/java/ca/rmen/kotlinexperimentalannotations/MyApplication.kt: (11, 9): This declaration is experimental and its usage must be marked with '@kotlin.time.ExperimentalTime' or '@OptIn(kotlin.time.ExperimentalTime::class)'
e: /Users/calvarez/dev/projects/KotlinExperimentalAnnotations/app/src/main/java/ca/rmen/kotlinexperimentalannotations/MyApplication.kt: (11, 30): This declaration is experimental and its usage must be marked with '@kotlin.time.ExperimentalTime' or '@OptIn(kotlin.time.ExperimentalTime::class)'
```
* Android Studio Arctic Fox displays red compilation **errors** with this message, in both classes:
```
This declaration is experimental and its usage must be marked with '@kotlin.time.ExperimentalTime' or '@OptIn(kotlin.time.ExperimentalTime::class)'
```

# Step 2 - Add `OptIn` annotation
* Add this annotation to the overriden `onCreate` function in both `MyApplication` and `MyTestApplication`:
    ```kotlin
    @OptIn(ExperimentalTime::class)
    override fun onCreate() {
        super.onCreate()
        TimeSource.Monotonic.markNow()
    }
    ```
* Build the app and test: `./gradlew clean assembleDebug testDebugUnitTest`.
* Gradle outputs these **warnings**:
```
> Task :app:compileDebugKotlin
w: /Users/calvarez/dev/projects/KotlinExperimentalAnnotations/app/src/main/java/ca/rmen/kotlinexperimentalannotations/MyApplication.kt: (8, 6): This class can only be used with the compiler argument '-opt-in=kotlin.RequiresOptIn'

> Task :app:compileDebugUnitTestKotlin
w: /Users/calvarez/dev/projects/KotlinExperimentalAnnotations/app/src/test/java/ca/rmen/kotlinexperimentalannotations/MyTestApplication.kt: (7, 6): This class can only be used with the compiler argument '-opt-in=kotlin.RequiresOptIn'
```
* Android Studio Arctic Fox displays **warnings** with this message, in both classes:
```
This class can only be used with the compiler argument '-Xopt-in=kotlin.RequiresOptIn'
```

# Step 3 - Add `languageSettings` configuration in `build.gradle`
Add the following gradle configuration to allow `OptIn` **only for tests**:
```gradle
kotlin {
    sourceSets {
        test {
            languageSettings {
                optIn("kotlin.RequiresOptIn")
            }
        }
        debugUnitTest {
            languageSettings {
                optIn("kotlin.RequiresOptIn")
            }
        }
        releaseUnitTest {
            languageSettings {
                optIn("kotlin.RequiresOptIn")
            }
        }
    }
}
```
* Build the app and test: `./gradlew clean assembleDebug testDebugUnitTest`.
* ✅Gradle outputs a **warning**, only for `MyApplication`. This is the desired behavior:
```
w: /Users/calvarez/dev/projects/KotlinExperimentalAnnotations/app/src/main/java/ca/rmen/kotlinexperimentalannotations/MyApplication.kt: (8, 6): This class can only be used with the compiler argument '-opt-in=kotlin.RequiresOptIn'
```
* Android Studio Arctic Fox displays **warnings**:
  - ✅For the app class: this is expected
  - ❌For the test class: this is unexpected. It's not taking into account our gradle conf
  - The kotlin plugin is: 203-1.5.31-release-550-AS7717.8
  - An update for the kotlin plugin is available: 203-1.6.0-release-798-AS7717.8
* Android Studio Bumblebee 2020.1.1 Beta 3 has the same behavior. It displays **warnings**:
  - ✅For the app class: this is expected
  - ❌For the test class: this is unexpected. It's not taking into account our gradle conf
  - The kotlin plugin is: 211-1.5.30-release-408-AS7442.40
  - An update for the kotlin plugin is available: 211-1.6.0-release-798-AS7442.40
* Android Studio Chipmunk 2021.2.1 Canary 5 has the same behavior. It displays **warnings**:
  - ✅For the app class: this is expected
  - ❌For the test class: this is unexpected. It's not taking into account our gradle conf
  - The kotlin plugin is: 212-1.5.31-release-556-AS4638.7
  - No kotlin plugin update is proposed

The warnings displayed in Android Studio are on the `OptIn` annotation:
```
This class can only be used with the compiler argument '-Xopt-in=kotlin.RequiresOptIn'
```

# Step 4 - Update the Android Studio kotlin plugin
Android Studio Arctic Fox:
* In Android Studio, update the kotlin plugin to 203-1.6.0-release-798-AS7717.8
* Restart the IDE
* kotlin plugin 203-1.6.0-release-798-AS7717.8 is installed
* We have the same behavior: We see **warnings**:
  - ✅For the app class: this is expected
  - ❌For the test class: this is unexpected. It's not taking into account our gradle conf

Android Studio Bumblebee:
* In Android Studio, update the kotlin plugin to 211-1.6.0-release-798-AS7442.40
* Restart the IDE
* Kotlin plugin 211-1.6.0-release-798-AS7442.40 is installed
* We have the same behavior: We see **warnings**:
  - ✅For the app class: this is expected
  - ❌For the test class: this is unexpected. It's not taking into account our gradle conf

