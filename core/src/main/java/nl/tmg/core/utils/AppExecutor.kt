package nl.tmg.core.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class AppExecutor(val ioDisk: Executor,val mainThread: Executor) {

    constructor() : this(Executors.newSingleThreadExecutor(), MainThreadExecutor())

    class MainThreadExecutor : Executor {

        var handler = Handler(Looper.getMainLooper())

        /**
         * Executes the given command at some time in the future.  The command
         * may execute in a new thread, in a pooled thread, or in the calling
         * thread, at the discretion of the `Executor` implementation.
         *
         * @param command the runnable task
         * @throws RejectedExecutionException if this task cannot be
         * accepted for execution
         * @throws NullPointerException if command is null
         */
        override fun execute(command: Runnable?) {
            handler.post(command)
        }

    }
}