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
Also Cloudflare can be enabled, so scraper will have to bypass it, therefore the API can take up to 30 seconds to respond,
If the request takes more than 30 seconds, you will get 503.

## Usage

Base URL:
https://hypixel-ranked-api.herokuapp.com

### Methods

All return types are nullable.

#### Getting information about a player

You can use an IGN or undashed UUID as the name.

Endpoint: `/player/:name`.

Returns: **Player**.

```kotlin
HypixelRankedApi.retrievePlayer("name")
HypixelRankedApi.retrievePlayer("uuid")
```

#### Getting leaderboard

Endpoint: `/leaderboard`.

Returns: **Leaderboard**.

```kotlin
HypixelRankedApi.retrieveLeaderboard()
```

#### Getting seasons

Endpoint: `/seasons`.

Returns: **List<Season>**.

```kotlin
HypixelRankedApi.retrieveSeasons()
```

#### Getting game stats

Endpoint: `/game/:id`.

Returns: **Game**.

```kotlin
HypixelRankedApi.retrieveGame("id")
```

### Extensions

#### HypixelPlayer

##### rankedSeasons

Returns player's seasons. To use this, you must call `HypixelRankedApi.retrieveSeasons()` at least once.

Type: **Map<Season, Pair<Int, Int>>**.

```kotlin
HypixelPlayer#rankedSeasons
```

#### SkyWars

##### getRating

Returns rating the player has gotten in this season or 0.

Returns: **Int**.

```kotlin
SkyWars#getRating(Season)
```

##### getPosition

Returns position the player has gotten in this season or 0.

Returns: **Int**.

```kotlin
SkyWars#getPosition(Season)
```

### Entities

#### Player

Represents a ranked player.

|   Property   |   Type  |  Description  |
|:------------:|:-------:|:-------------:|
|   **uuid**   |  String | Undashed UUID |
|   **name**   |  String |  Displayname  |
|   **rank**   | String? |      Rank     |
|   **guild**  | String? |     Guild     |
|  **rating**  |   Int   |     Rating    |
| **position** |   Int   |    Position   |

#### Leaderboard

Represents ranked leaderboard, extends `ArrayList<Leaderboard.Player>`.

##### Player

Represents a player from ranked leaderboard.

|   Property   |  Type  |  Description  |
|:------------:|:------:|:-------------:|
|   **uuid**   | String | Undashed UUID |
|   **name**   | String |      Name     |
|  **rating**  |   Int  |     Rating    |
| **position** |   Int  |    Position   |
|   **wins**   |   Int  |      Wins     |
|   **kills**  |   Int  |     Kills     |

#### Season

Represents a ranked season.

|     Property    |      Type     |                                  Description                                 |
|:---------------:|:-------------:|:----------------------------------------------------------------------------:|
|    **number**   |      Int      |                           Season number, 1-indexed                           |
| **hiddenInAPI** |    Boolean    | Indicates whether Hypixel returns rating/position for a player in API or not |
| **leaderboard** | List\<Player> |                              Stored leaderboard                              |

##### Player

|   Property   |  Type  |  Description  |
|:------------:|:------:|:-------------:|
|   **uuid**   | String | Undashed UUID |
|   **name**   | String |      Name     |
|  **rating**  |   Int  |     Rating    |
| **position** |   Int  |    Position   |

##### Game

...

## License

The project is licensed under the **[MIT license](https://choosealicense.com/licenses/mit/)**.
