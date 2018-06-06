# StringsLoader
StringsLoader simplifies the way of loading Strings from a remote source in an Android application.

## How to use it
StringsLoader exposes a method to load Strings from a JSON remote and local file. Because the loading request is asynchronous,
you'll have to implement `LoaderCallback` interface.
An example is worth a thousand words, so
```kotlin
StringsLoader.loadFromRemote(URL, CACHE_DIRECTORY, ConverterType.JSON, object : LoaderCallback {
    override fun onComplete() {
        // The download has successfully completed
    }

    override fun onError(throwable: Throwable) {
        // An exception occurred
    }
})
```
```kotlin
StringsLoader.loadFromLocal(PATH, context, ConverterType.JSON, object : LoaderCallback {
    override fun onComplete() {
        // The download has successfully completed
    }

    override fun onError(throwable: Throwable) {
        // An exception occurred
    }
})
```
When the download has successfuly completed, you can get string data from any type of key. If the key is not found,
StringsLoader return an empty string.
```kotlin
val str = StringsLoader.get("key.1")
```

Note that StringsLoader is a singleton. A single instance of the cache is created. To clear the cache a
`clear` function is provided.

## Improvements
* Centralize error management
* Implement a retry strategy
* Allows a custom configuration to manage caching with OkHttp

## Note
The `loader` module is generic. By creating another type of loader you can load any kind of data. See the
`stringsloader` module.
