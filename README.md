### Genius Lyrics API (not-official)
This API is made to search and parse lyrics from the web. The Genius library should provide all the songs your users will ever search for. It uses genius's embed page to load lyrics without getting rate-limited, and caches every parsed lyrics and search results (runtime).

## Easy to use!
Just create a new *GLA* object and start with the *search(String)* method ;)

## Download

**Gradle**
```gradle
dependencies {
   implementation 'com.github.LowLevelSubmarine:GeniusLyricsAPI:master'
}

repositories {
    maven { url 'https://jitpack.io' }
}
```

**Maven**
```xml
<dependency>
    <groupId>com.github.LowLevelSubmarine</groupId>
    <artifactId>GeniusLyricsAPI</artifactId>
    <version>master</version>
</dependency>
```
```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```
