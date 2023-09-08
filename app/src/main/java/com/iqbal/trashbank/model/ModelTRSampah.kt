package com.iqbal.trashbank.model

import com.google.gson.annotations.SerializedName

data class ModelTRSampah(

	@field:SerializedName("id_sampah")
	val idSampah: Int? = null,

	@field:SerializedName("id_pengajuan")
	val idPengajuan: Int? = null,

	@field:SerializedName("jenis_sampah")
	val jenisSampah: String? = null,

	@field:SerializedName("harga_sampah")
	val hargaSampah: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("StatusCode")
	val StatusCode: Int? = null,

	@field:SerializedName("Message")
	val Message: String? = null
)
