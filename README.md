[ ![Download](https://api.bintray.com/packages/mdashlw/maven/hypixel-ranked-api/images/download.svg) ](https://bintray.com/mdashlw/maven/hypixel-ranked-api/_latestVersion)
[![CircleCI](https://circleci.com/gh/mdashlw/hypixel-ranked-api.svg?style=svg)](https://circleci.com/gh/mdashlw/hypixel-ranked-api)

# Hypixel Ranked API

Java Hypixel Ranked API wrapper.

## Importing

Replace `VERSION` with the latest version above.

### Gradle Groovy DSL

<details><summary>build.gradle</summary>
<p>

```gradle
repositories {
    jcenter()
}

dependencies {
    implementation 'ru.mdashlw.hypixel:hypixel-ranked-api:VERSION'
}
```

</p>
</details>

### Gradle Kotlin DSL

<details><summary>build.gradle.kts</summary>
<p>

```kotlin
repositories {
    jcenter()
}

dependencies {
    implementation("ru.mdashlw.hypixel:hypixel-ranked-api:VERSION")
}
```

</p>
</details>

### Maven

<details><summary>pom.xml</summary>
<p>

```xml
<depedencies>
    <dependency>
        <groupId>ru.mdashlw.hypixel</groupId>
        <artifactId>hypixel-ranked-api</artifactId>
        <version>VERSION</version>
  </dependency>
</depedencies>

<repositories>
    <repository>
      <id>jcenter</id>
      <name>JCenter</name>
      <url>https://jcenter.bintray.com/</url>
    </repository>
</repositories>
```

</p>
</details>

## Understanding

Hypixel does not have an API for Ranked, so they only way to get player's rating/position or leaderboard is to scrap their website.

Since it's not meant to be scraped, it doesn't actually care about everyone and it may take awhile to load sometimes.
Also Cloudflare can be enabled, so scraper will have to bypass it, therefore the API can take up to 30 seconds to respond,
If the request takes more than 30 seconds, you will get 503.

## Usage

Base URL: https://hypixel-ranked-api.herokuapp.com

### Getting started

```java
HypixelRankedAPI api = new HypixelRankedAPI();
HypixelRankedAPI api = new HypixelRankedAPI(new OkHttpClient());
HypixelRankedAPI api = new HypixelRankedAPI(new OkHttpClient(), new ObjectMapper());
```

### Methods

* All methods return **CompletableFuture**.
* All UUIDs can be both dashed and undashed.

#### Getting information about a player

Endpoint: `/player/:name`.

Returns: **RankedPlayer** (nullable).

```java
HypixelRankedAPI#retrievePlayer("uuid");
HypixelRankedAPI#retrievePlayer("nickname");
```

#### Getting the leaderboard

Endpoint: `/leaderboard`.

Returns: **List\<LeaderboardPlayer>** (never-null).

```java
HypixelRankedAPI#retrieveLeaderboard();
```

#### Getting seasons

Endpoint: `/seasons`.

Returns: **List\<RankedSeason>** (never-null).

```java
HypixelRankedAPI#retrieveSeasons();
```

#### Getting information about a game

Endpoint: `/game/:id`.

Returns: **Game** (nullable).

```java
HypixelRankedAPI#retrieveGame("id");
```

### Utils

#### Get rating/position in the season

Returns: **int**.

```java
HypixelUtil.getRating(SkyWars, RankedSeason);
HypixelUtil.getPosition(SkyWars, RankedSeason);
```

#### Get Ranked seasons of a player

Returns: **Map\<RankedSeason, RatingPositionEntry>** (never-null).

```java
HypixelUtil.getSeasons(HypixelRankedAPI, HypixelPlayer);
```

## License

The project is licensed under the **[MIT license](https://choosealicense.com/licenses/mit/)**.
