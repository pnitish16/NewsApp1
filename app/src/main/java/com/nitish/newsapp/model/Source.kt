package com.nitish.newsapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Source(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString()
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(name)
		parcel.writeString(id)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Source> {
		override fun createFromParcel(parcel: Parcel): Source {
			return Source(parcel)
		}

		override fun newArray(size: Int): Array<Source?> {
			return arrayOfNulls(size)
		}
	}
}