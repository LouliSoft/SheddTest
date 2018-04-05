package com.m2f.sheddtest.presentation.core

import com.m2f.sheddtest.domain.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

/**
 * @author Marc Moreno
 * @since 5/4/18.
 */
@Singleton
class MainThread(override val scheduler: Scheduler = AndroidSchedulers.mainThread()) : PostExecutionThread