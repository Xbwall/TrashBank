package com.iqbal.trashbank.model

import com.google.gson.annotations.SerializedName

class ModelListPengajuan {

	@field:SerializedName("StatusCode")
	val StatusCode: Int? = null

	@field:SerializedName("Message")
	val Message: String? = null

	@field:SerializedName("note")
	val note: String? = null

	@field:SerializedName("id")
	val id: Int? = null

	@field:SerializedName("id_masyarakat")
	val idMasyarakat: Int? = null

	@field:SerializedName("id_pengajuan")
	val id_pengajuan: Int? = null

	@field:SerializedName("tanggal_pengajuan")
	val tanggalPengajuan: String? = null

	@field:SerializedName("status")
	val status: String? = null
}
