# OpenSubtitle

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.saifullah-nurani/opensubtitle.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.saifullah-nurani%22%20AND%20a:%22opensubtitle%22)

OpenSubtitle is a lightweight, easy-to-use open-source library to search and download subtitles from OpenSubtitles.org without needing complicated authentication or API keys. It supports both Kotlin Coroutines and pure Java implementations.

## Installation

Add the dependency to your app-level `build.gradle.kts` (or `build.gradle`):

```kotlin
dependencies {
    implementation("io.github.saifullah-nurani:opensubtitle:<latest-version>")
    
    // Optional: Use OkHttp for actual network calls instead of the default HttpURLConnection
    implementation("io.github.saifullah-nurani:opensubtitle-okhttp:<latest-version>")
}
```

## Setup & Configuration

The standard `OpenSubtitle` client uses Java's built-in `HttpURLConnection`. If you prefer to use the `OkHttp` module you added, you can override the standard HTTP client:

### Kotlin
```kotlin
import io.github.saifullah.nurani.opensubtitle.OpenSubtitle
import io.github.saifullah.nurani.opensubtitle.okhttp.openSubtitleOkHttpClient

// Must be set once before making any requests.
OpenSubtitle.setHttpClient(openSubtitleOkHttpClient()) 
```

### Java
```java
import io.github.saifullah.nurani.opensubtitle.OpenSubtitle;
import io.github.saifullah.nurani.opensubtitle.okhttp.OpenSubtitleOkHttpClientKt;

// Must be set once before making any requests.
OpenSubtitle.setHttpClient(OpenSubtitleOkHttpClientKt.openSubtitleOkHttpClient());
```

---

## 🚀 Usage in Kotlin

In Kotlin, all network operations are **synchronous**. You must wrap the calls in a background thread to prevent blocking the Main Thread (e.g., using `Dispatchers.IO`).

### 1. Construct the URL and Search

```kotlin
import io.github.saifullah.nurani.opensubtitle.OpenSubtitle
import io.github.saifullah.nurani.opensubtitle.SearchOnly
import io.github.saifullah.nurani.opensubtitle.buildOpenSubtitleUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

val openSubtitle = OpenSubtitle()

suspend fun findSubtitles(movieTitle: String) {
    withContext(Dispatchers.IO) {
        try {
            // 1. Build the Search URL
            val searchUrl = buildOpenSubtitleUrl {
                setTitle(movieTitle)
                setSearchOnly(SearchOnly.Movies)
            }
            
            // 2. Perform the Search (Page 1)
            val subtitleData = openSubtitle.search(searchUrl, 1)
            
            // 3. Process the results
            subtitleData.subtitles.forEach { subtitle ->
                println("Found: ${subtitle.title} (${subtitle.subLanguage})")
            }
            
        } catch (e: Exception) {
            println("Error fetching subtitles: ${e.message}")
        }
    }
}
```

### 2. Get the Direct Download Link

```kotlin
suspend fun downloadSubtitle(subtitleId: Long) {
    withContext(Dispatchers.IO) {
        try {
            val downloadLink = openSubtitle.getDirectLink(subtitleId)
            println("Download URL: $downloadLink")
            // You can now use a DownloadManager or OkHttp to save the file
        } catch (e: Exception) {
            println("Failed to fetch download link.")
        }
    }
}
```

---

## ☕ Usage in Java

In Java, calls are synchronous. You must run them inside an `ExecutorService` (or any background thread utility like `AsyncTask`, `RxJava`, etc.) to prevent blocking the UI thread.

### 1. Construct the URL and Search

```java
import io.github.saifullah.nurani.opensubtitle.OpenSubtitle;
import io.github.saifullah.nurani.opensubtitle.SearchOnly;
import io.github.saifullah.nurani.opensubtitle.data.Subtitle;
import io.github.saifullah.nurani.opensubtitle.data.SubtitleData;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SubtitleManager {
    
    private final OpenSubtitle openSubtitle = new OpenSubtitle();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void searchSubtitles(String movieTitle) {
        // Build URL using the standard Builder pattern instead of DSL
        OpenSubtitle.URLBuilder builder = new OpenSubtitle.URLBuilder();
        builder.setTitle(movieTitle);
        builder.setSearchOnly(SearchOnly.Movies);
        final String searchUrl = builder.build();

        // Run synchronously in the background execution pool
        executorService.execute(() -> {
            try {
                SubtitleData subtitleData = openSubtitle.search(searchUrl, 1);
                
                for (Subtitle sub : subtitleData.getSubtitles()) {
                    System.out.println("Found: " + sub.getTitle() + " (" + sub.getSubLanguage() + ")");
                }
            } catch (Exception e) {
                System.err.println("Error fetching subtitles: " + e.getMessage());
            }
        });
    }
}
```

### 2. Get the Direct Download Link

```java
public void fetchDownloadLink(long subtitleId) {
    executorService.execute(() -> {
        try {
            String downloadLink = openSubtitle.getDirectLink(subtitleId);
            System.out.println("Download URL: " + downloadLink);
        } catch (Exception e) {
            System.err.println("Failed to fetch download link.");
        }
    });
}
```

---

## Clean Up

To avoid memory leaks, ensure the underlying `HttpClient` is closed when you no longer need `OpenSubtitle` (e.g., when the application is destroyed).

```kotlin
OpenSubtitle.close()
```

## License
MIT License. See [LICENSE](LICENSE).
