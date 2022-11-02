package com.example.collegeuserapp.model

data class  UploadImageModel(
    val category:String?="",
    val imageId:String?="",
    val image:ArrayList<String> = ArrayList(),
    val timestamp:Any?=null
)
