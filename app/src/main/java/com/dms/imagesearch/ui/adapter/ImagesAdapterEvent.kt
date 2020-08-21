package com.dms.imagesearch.ui.adapter

/**
 * Describes all the events originated from
 * [ImagesAdapter].
 */
sealed class ImagesAdapterEvent {

    /* Describes item click event  */
    object ClickEvent : ImagesAdapterEvent()
}