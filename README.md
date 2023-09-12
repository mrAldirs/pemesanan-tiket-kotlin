# Pemesanan Tiket use kotlin language

![logo kotlin](https://kotlinlang.org/docs/images/kotlin-logo.png)

[![official project](https://jb.gg/badges/official.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
[![TeamCity (simple build status)](https://img.shields.io/teamcity/http/teamcity.jetbrains.com/s/Kotlin_KotlinPublic_Compiler.svg)](https://teamcity.jetbrains.com/buildConfiguration/Kotlin_KotlinPublic_Compiler?branch=%3Cdefault%3E&buildTypeTab=overview&mode=builds)
[![Maven Central](https://img.shields.io/maven-central/v/org.jetbrains.kotlin/kotlin-maven-plugin.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.jetbrains.kotlin%22)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Revved up by Gradle Enterprise](https://img.shields.io/badge/Revved%20up%20by-Gradle%20Enterprise-06A0CE?logo=Gradle&labelColor=02303A)](https://ge.jetbrains.com/scans?search.rootProjectNames=Kotlin)

## Kotlin Programming Language

Welcome to [Kotlin](https://kotlinlang.org/)!   
It is an open-source, statically typed programming language supported and developed by [JetBrains](https://www.jetbrains.com/) and open-source contributors.

Some handy links:

 * [Kotlin Site](https://kotlinlang.org/)
 * [Getting Started Guide](https://kotlinlang.org/docs/tutorials/getting-started.html)
 * [Try Kotlin](https://play.kotlinlang.org/)
 * [Kotlin Standard Library](https://kotlinlang.org/api/latest/jvm/stdlib/index.html)
 * [Issue Tracker](https://youtrack.jetbrains.com/issues/KT)
 * [Kotlin YouTube Channel](https://www.youtube.com/channel/UCP7uiEZIqci43m22KDl0sNw)
 * [Forum](https://discuss.kotlinlang.org/)
 * [Kotlin Blog](https://blog.jetbrains.com/kotlin/)
 * [Subscribe to Kotlin YouTube channel](https://www.youtube.com/channel/UCP7uiEZIqci43m22KDl0sNw)
 * [Follow Kotlin on Twitter](https://twitter.com/kotlin)
 * [Public Slack channel](https://slack.kotlinlang.org/)
 * [TeamCity CI build](https://teamcity.jetbrains.com/project.html?tab=projectOverview&projectId=Kotlin)

## Project Description

This mobile-based travel application makes it easy to search and make the best bookings. This application includes ordering train and plane tickets and helps customers create their accounts and manage a list of trips they want to take. This makes it very easy for travelers to save more time by ordering tickets. Plus, it has been further developed with a new database and features, namely hotel reservations. Just by using a smartphone and the internet, all types of orders can be made anytime and anywhere.

## Content

After you create a new project based on the current template repository using the **Use this template** button, a bare minimal scaffold will appear in your GitHub account with the following structure:

```
├── README.md                                             README file
├── api
│   └── web_service_tiket
├── app
│   ├── src
│   │   ├── androidTest
│   │   ├── main
│   │   │   ├── java
│   │   │   │   ├── AdapterHotel.kt
│   │   │   │   ├── AdapterTiket.kt
│   │   │   │   ├── DashboardActivity.kt
│   │   │   │   ├── EditTiketActivity.kt
│   │   │   │   ├── HistoryActivity.kt
│   │   │   │   ├── IklanActivity.kt
│   │   │   │   ├── InputDataActivity.kt
│   │   │   │   ├── InputHotelActivity.kt
│   │   │   │   ├── Ktp.kt
│   │   │   │   ├── LoginActivity.kt
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── MapsActivity.kt
│   │   │   │   ├── MediaHelper.kt
│   │   │   │   ├── PembayaranFragment.kt
│   │   │   │   ├── PhotoHelper.kt
│   │   │   │   ├── PopUp.kt
│   │   │   │   ├── QrActivity.kt
│   │   │   │   ├── RiwayatFragment.kt
│   │   │   │   └── UrlClass.kt
│   │   │   └── res
│   │   │       └── AndroidManifest.xml
│   │   └── test
│   ├── .gitignore                                        Git repository
│   ├── build.gradle                                      Gradle configuration
│   └── proguard-rules.pro                                Tool for shrinking, optimizing, and obfuscating Java
├── gradle
│   └── wrapper                                           Gradle Wrapper
├── build.gradle.kts                                      Gradle configuration created with Kotlin DSL
├── gradle.properties                                     Gradle configuration properties
├── gradlew                                               *nix Gradle Wrapper script
├── gradlew.bat                                           Windows Gradle Wrapper script
├── settings.gradle.kts                                   Gradle project settings
└── tiket.sql                                             Database script using sql
```

> Note: All task input files are excluded from the repository with `.gitignore` – we should not post them publicly, as Aldi Rosid Saputra asks for: [Post](https://instagram.com/mr.aldirs?igshid=OGQ5ZDc2ODk2ZA==).

## Application Features

If you want to install this application, you can use the following features.

- Interface (Seekbar, Recycler View, Image View, Video View, Image Button)
- QR Code & Shared Preferences
- Multimedia & Google Maps
- Web Service/Api using PHP language
- Search & Sort Data
- Create, Read, Update, and Delete with dialog confirmation

## Installation Steps

If you want to install this application, there are several steps you must pay attention to.

- Gradle version 8.0
- DistributionUrl=https\://services.gradle.org/distributions/gradle-8.0-bin.zip
- Download api (web service) and paste to htdocs if using apache
- Make sure the smartphone and server connections are the same
- Connect the Android application with an API (web service) by changing the device connection IP
- [how to change ip in api (web service)](http://www.aldi.com)
- [how to change ip in project android](http://www.aldi.com)

## Contribution

We welcome contributions! To contribute to this project, please follow these steps:

1. Fork this project
2. Create a new branch (`git checkout -b your-feature`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to your branch (`git push origin your-feature`)
5. Create a pull request

# License
Kotlin is distributed under the terms of the Apache License (Version 2.0). See [license folder](license/README.md) for details.

![https://www.apache.org/img/asf-estd-1999-logo.jpg](https://www.apache.org/img/asf-estd-1999-logo.jpg)
