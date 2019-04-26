### Genius Lyrics API (not-official)
This API is made to search and parse lyrics from the web. The Genius library should provide all the songs your users will ever search for. It uses genius's embed page to load lyrics without getting rate-limited, and caches every parsed lyrics and search results (runtime).

## Easy to use!
Just create a new *GLA* object and start with the *search(String)* method ;)

## Download
# Gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
dependencies {
        implementation 'com.github.LowLevelSubmarine:GeniusLyricsAPI-not-official-:master'
}
# Maven
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
<dependency>
    <groupId>com.github.LowLevelSubmarine</groupId>
    <artifactId>GeniusLyricsAPI-not-official-</artifactId>
    <version>master</version>
</dependency>
