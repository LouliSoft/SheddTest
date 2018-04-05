package com.m2f.sheddtest.data.executor

import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobExecutor
@Inject constructor(private val threadPoolExecutor: ThreadPoolExecutor) : Executor {

    override fun execute(runnable: Runnable) {
        this.threadPoolExecutor.execute(runnable)
    }

}
