# Dependency injection for java & android

[![](https://jitpack.io/v/bishoybasily-fidelyo/yolo.svg)](https://jitpack.io/#bishoybasily-fidelyo/yolo)

## Overview

This library simplifies the process of di through a very simple api

## Setup (kotlin with gradle)
    repositories {
        maven { url 'https://jitpack.io' }        
    }

    dependencies {
        implementation 'com.github.bishoybasily-fidelyo.yolo:annotations:$version'
        kapt 'com.github.bishoybasily-fidelyo.yolo:processor:$version'
    }

## Example android kotlin

**Full example**
``` kotlin

val requester = PermissionsRequester().with(this)
val needed = arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)


// to ensure that the permission is granted without requesting
requester.ensure(needed).subscribe { fab.isEnabled = !it }

// to request the permission
requester
        .explain(R.string.title, R.string.message, R.string.allow, R.string.deny) // explanation dialog
        .request(needed) // needed permissions
        .subscribe {

            if (it) {
                // permission granted
            } else {
                // permission not granted
            }
        } // callback

// please note neither the explanation dialog nor the permission dialog will be prompted if the permission already granted,
// it means you can safely call request even if the permission already granted

// you can also skip the explanation dialog if you want
        
