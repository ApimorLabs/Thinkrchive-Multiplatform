---
sidebar_position: 4
---

# Module Structure

This project has mutliple modules with their own functionality, features, dependencies and uses.

Structuring your Mutliplatform project is necessary so that you can organized your code and reduce
module dependency which may slow down your build time. It is also important to separate your target
platform into their own modules for easy dependency managing.

## Modules

### :androidApp

This module contains all the Android App code that is not shared anywhere else. It is the root point
of the Android app and contains all the Android UI for now. It does not carry any business logic
other than Google Play Billing code that will soon be migrated to common code.

### :desktopApp

This module contains all the desktop App code that is not shared anywhere else. It will be home for
all Compose Desktop UI code. It will not carry any business logic as all of it will be shared across
all platforms.

:::danger UI not completed There is currently no UI for the Desktop App as I am still migrating most
of the business logic outside of the Android app.
:::

### :buildSrc

This module is for dependency configuration so we have a centralized place to access all our
dependency definitions and versions. This makes managing dependencies in multiple modules very easy.
Updating dependecies also becomes a very easy task.

### :common

The common module will house all the multiplaform modules that have shared code in them.

### :common:model

This module contains all model classes used inside the app. It contains the data model for database
& networking, domain models, states and their mappers. It is used in every module in this project.

### :common:integration

This module contains all the shared code that will be integrated to platform. It combines shared
code from all other common modules and produces `states` and `side-effects` that will be consumed by
the platforms. It is here to make sure that none of business logic from other modules like network,
database and settings is directly shared to the platforms.

It is the home of our MVI integration and business logic. It receives intents from the outiside,
processes them and returns the required `states` and `side-effects`. It also consumes all the data
provided by the other common modules into meaningful responses. Only the domain models get out of
here.

### :common:persistence

This is the local persistence module that contains modules for database and settings. This is where
local data storage happens

#### :common:persistence:database

This module contains all the [SQLDelight](https://github.com/cashapp/sqldelight) code for SQLite
databases on Android and Desktop-JVM. This is were all the SQL queries are written. It provides
platfrom specific SQL drivers through dependency injection and all the data is then processed in
the `:common:integration` module repository.

#### :common:persistence:settings

This module contains [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings)
code that gets and saves settings through it's repository. It is a multiplatform implementation that
doesn't need any platform specific implementation unless you wan't the platform specific features
like DataStore, Observable settings, Windows Registry, etc.
