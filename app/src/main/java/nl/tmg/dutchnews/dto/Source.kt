package nl.tmg.dutchnews.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Source(val id : String?, val name : String): Parcelable