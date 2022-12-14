package com.example.collegeuserapp.model

data class AddNoticeModel(
    val noticeId:String?="",
    val date:String?=null,
    val time:String?=null,
    val title:String?="",
    val noticeDesc:String?="",
    val image:ArrayList<String> = ArrayList(),
    val timestamp:Any?=null
)
