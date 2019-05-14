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

## Usage

### Methods

#### Getting information about a player

Returns: **Player**.

```kotlin
HypixelRankedAPI.getPlayerByName("name")
```

#### Getting leaderboard

Returns: **Leaderboard**.

```kotlin
HypixelRankedAPI.getLeaderboard()
```

### Entities

#### Player

Represents a ranked player with rating and position.

|   Property   	| Type 	|
|:------------:	|:----:	|
|  **rating**  	|  Int 	|
| **position** 	|  Int 	|

#### Leaderboard

Represents ranked leaderboard, extends `ArrayList<Leaderboard.Player>`.

##### Player

Represents a player from ranked leaderboard.

|   Property   	|  Type  	|
|:------------:	|:------:	|
|   **name**   	| String 	|
|  **rating**  	|   Int  	|
| **position** 	|   Int  	|
|   **wins**   	|   Int  	|
|   **kills**  	|   Int  	|

## License

The project is licensed under the **[MIT license](https://choosealicense.com/licenses/mit/)**.
