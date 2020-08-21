package com.dms.imagesearch.core.storage.entity

import androidx.room.migration.Migration

/**
 * Describes migration related to [ImagesDatabase].
 */
internal object ImageDatabaseMigrations {

    // Bump this on changing the schema
    const val latestVersion = 1

    val allMigrations: Array<Migration>
        get() = arrayOf(

        )
}