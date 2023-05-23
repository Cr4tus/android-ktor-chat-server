# Ktor Chat Server

## What does the app do?
The server has been running on a VM from Google, which is quite expensive. Once it is running, it is able to connect to its clients through websockets and store the messages using a **MongoDB** database which has to be already configured on that specific machine (another option is to make the right changes and set up a **MongoDB Cloud**).

## Programming Lannguages:
- Kotlin

## Frameworks & Libraries:
- Ktor - https://ktor.io/docs/create-server.html
- Gradle - https://docs.gradle.org/current/userguide/kotlin_dsl.html
- MongoDB - https://www.mongodb.com
- KMongo (**MongoDB ToolKit** that makes available features of **Kotlin**) - https://litote.org/kmongo/
- Koin (for Dependency Injection) - https://insert-koin.io

## Implementation:
The project starts from the *Application.kt* file:
```kotlin
import io.ktor.application.*
import com.example.plugins.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    // each row configures a plugin
    // be careful if there are plugins that depend on other ones
    // and configure them in the right order
    this.configureDependencyInjection()
    this.configureSockets()
    this.configureRouting()
    this.configureSerialization()
    this.configureMonitoring()
    this.configureSecurity()
}
```
Using the **Ktor** framework, you have to *'add'* & *'configure'* your plugins (there are a lot of plugins already defined, but you can also create your own ones). This is a example of a file that configures a plugin (Monitoring - this plugin helps you debug and observe log data from your server):
```kotlin
package com.example.plugins

import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.application.*
import io.ktor.request.*

fun Application.configureMonitoring() {
    install(CallLogging) {
        this.level = Level.INFO // level of how much info you want to show within the logs
        filter { call -> call.request.path().startsWith("/") } // filter the routes you want to show logs
    }
}

```
Once you're done coding, the most interesting part is the configuration of the server and the database engine. I've succeeded to configure the **MongoDB** server **restart** if there is any problem with the VM. Also, you have to manually set up it to always restart at **the same port**, otherwise the server should also be modified every time.