package com.dms.imagesearch.ui.adapter

/**
 * Describes all the events originated from
 * [ImagesListAdapterr].
 */
sealed class ImagesAdapterEvent {

    /* Describes item click event  */
    object ClickEvent : ImagesAdapterEvent()
}