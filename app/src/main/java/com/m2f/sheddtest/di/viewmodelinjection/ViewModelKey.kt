package com.flyshionista.flyshionistaandroid.main.di.viewmodelinjection

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass


/**
 * This is like [dagger.multibindings.ClassKey] but this ensure that the class must be a subtype of [ViewModel]
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)