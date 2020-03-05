package com.stations.data

import android.os.Parcel
import android.os.Parcelable

data class ResponseAccessToken(val token_type: String, val access_token: String)

data class RequestAccessToken(
    val grant_type: String,
    val client_id: String,
    val client_secret: String,
    val code: String,
    val redirect_uri: String
)

data class RequestStations(
    val Authorization: String?,
    val city: String?,
    val lat: String?,
    val lon: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Authorization)
        parcel.writeString(city)
        parcel.writeString(lat)
        parcel.writeString(lon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RequestStations> {
        override fun createFromParcel(parcel: Parcel): RequestStations {
            return RequestStations(parcel)
        }

        override fun newArray(size: Int): Array<RequestStations?> {
            return arrayOfNulls(size)
        }
    }
}


data class StationResponse(val items: List<RadioStation>)

data class RadioStation(val attributes: Attributes, val links: Links)

data class Attributes(val brand: Brand)

data class Links(val brand: List<BrandImages>)

data class BrandImages(val href: String)

data class Brand(
    val name: String,
    val frequency: String,
    val marketCity: String,
    val tagline: String
)



