package com.nitish.newsapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ArticlesItem (

	@field:SerializedName("publishedAt")
	val publishedAt: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("urlToImage")
	val urlToImage: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("source")
	val source: Source? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("content")
	val content: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readParcelable(Source::class.java.classLoader),
		parcel.readString(),
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(publishedAt)
		parcel.writeString(author)
		parcel.writeString(urlToImage)
		parcel.writeString(description)
		parcel.writeParcelable(source, flags)
		parcel.writeString(title)
		parcel.writeString(url)
		parcel.writeString(content)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ArticlesItem> {
		override fun createFromParcel(parcel: Parcel): ArticlesItem {
			return ArticlesItem(parcel)
		}

		override fun newArray(size: Int): Array<ArticlesItem?> {
			return arrayOfNulls(size)
		}
	}

}