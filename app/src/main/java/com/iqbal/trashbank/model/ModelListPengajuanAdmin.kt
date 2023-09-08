package com.iqbal.trashbank.model

import com.google.gson.annotations.SerializedName

data class ModelListPengajuanAdmin(

	@field:SerializedName("note")
	val note: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("id_masyarakat")
	val idMasyarakat: Int? = null,

	@field:SerializedName("tanggal_pengajuan")
	val tanggalPengajuan: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
