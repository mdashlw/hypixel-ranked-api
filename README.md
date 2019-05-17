[ ![Download](https://api.bintray.com/packages/mdashlw/maven/hypixel-ranked-api/images/download.svg) ](https://bintray.com/mdashlw/maven/hypixel-ranked-api/_latestVersion)
[![pipeline status](https://gitlab.com/mdashlw/hypixel-ranked-api/badges/master/pipeline.svg)](https://gitlab.com/mdashlw/hypixel-ranked-api/commits/master)

# Hypixel Ranked API

Custom Hypixel API for Ranked SkyWars.

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
Also they can enable Cloudflare, so scraper will have to bypass it, therefore an API can take up to 15s to respond.

## Usage

Base URL:
https://hypixel-ranked-api.herokuapp.com

### Methods

#### Getting information about a player

Endpoint: `/player/:nickname`.

Returns: **Player**.

```kotlin
HypixelRankedAPI.getPlayerByName("nickname")
```

#### Getting leaderboard

Endpoint: `/leaderboard`.

Returns: **Leaderboard**.

```kotlin
HypixelRankedAPI.getLeaderboard()
```

### Entities

#### Player

Represents a ranked player with rating and position.

|   Property   	| Type 	| Description 	|
|:------------:	|:----:	|:-----------:	|
|  **rating**  	|  Int 	|    Rating   	|
| **position** 	|  Int 	|   Position  	|

#### Leaderboard

Represents ranked leaderboard, extends `ArrayList<Leaderboard.Player>`.

##### Player

Represents a player from ranked leaderboard.

|   Property   	|  Type  	| Description 	|
|:------------:	|:------:	|:-----------:	|
|   **name**   	| String 	|     Name    	|
|  **rating**  	|   Int  	|    Rating   	|
| **position** 	|   Int  	|   Position  	|
|   **wins**   	|   Int  	|     Wins    	|
|   **kills**  	|   Int  	|    Kills    	|

## License

The project is licensed under the **[MIT license](https://choosealicense.com/licenses/mit/)**.
