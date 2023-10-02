package ru.petrolplus.pos.core

import javax.inject.Scope

@Scope
annotation class AppScope

@Scope
annotation class MainScreenScope

@Scope
annotation class SettingsScreenScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class P7LibScope
