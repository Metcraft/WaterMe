/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.waterme.data

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.waterme.model.Plant
import com.example.waterme.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit

class WorkManagerWaterRepository(context: Context) : WaterRepository {
    private val workManager = WorkManager.getInstance(context)

    override val plants: List<Plant>
        get() = DataSource.plants


    /*
    override fun scheduleReminder(duration: Long, unit: TimeUnit, plantName: String) {
        // add a data variable using data builder
        val data = Data.Builder()
        // data variable needs to consist of a single string with name is the key the plant name is passed as the value.
        data.putString(WaterReminderWorker.nameKey, plantName)

        // one-time work request using the duration and units that were passed to the function
        val workRequestBuilder = OneTimeWorkRequestBuilder<WaterReminderWorker>()
            .setInitialDelay(duration, unit)
            .setInputData(data.build())
            .build()


    // call the workManager EnqueueUniqueWork and pass the plant name and duration
        workManager.enqueueUniqueWork(
            plantName + duration,
            ExistingWorkPolicy.REPLACE,
            workRequestBuilder
        )

    }*/

    override fun scheduleReminder(duration: Long, unit: TimeUnit, plantName: String) {
        val data = Data.Builder()
        data.putString(WaterReminderWorker.nameKey, plantName)

        val workRequestBuilder = OneTimeWorkRequestBuilder<WaterReminderWorker>()
            .setInitialDelay(duration, unit)
            .setInputData(data.build())
            .build()

        workManager.enqueueUniqueWork(
            plantName + duration,
            ExistingWorkPolicy.REPLACE,
            workRequestBuilder
        )
    }
}
