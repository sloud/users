# Sloud Users Plugin

Spigot powered plugin for basic user management.

## Features

* Default user class for basic database interaction and storage purposes that is highly extendable.
* Basic user controller handling name changes, play time, player status (online/offline) and uuid storage.

## Compiling

You need to install [Gradle](https://gradle.org/), mainly for dependency management, on your machine to be able to compile the sources.

When installed run `gradle build` in your console. Compiled JAR files can be found at `/build/libs/`.

## Plugin Installation

To be able to use this plugin you need at least Spigot 1.12 installed on your Minecraft Server.

Also this plugin requires you to have the Sloud Core plugin installed, which can also be found in the Sloud Components repository on GitHub.

When all requirements are met, move the compiled JAR file into your servers `/plugins` folder and restart the server.

## Changelog

**1.0.1**

- Updated dependencies

**1.0.0**

- Initial release
