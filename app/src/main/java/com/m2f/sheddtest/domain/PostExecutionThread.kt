package com.m2f.sheddtest.domain

import io.reactivex.Scheduler

/**
 * @author Marc Moreno
 * @since 5/4/18.
 */
interface PostExecutionThread {
    val scheduler: Scheduler
}