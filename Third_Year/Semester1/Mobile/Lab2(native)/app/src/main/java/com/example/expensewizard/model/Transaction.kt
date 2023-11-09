package com.example.expensewizard.model

import android.util.Log
import java.util.Date
data class Transaction (
    var id : Int,
    var title: String,
    var type:Type,
    var category: String,
    var amount : Double,
    var timestamp : Date
) {
    companion object {
        var currentId = 0
    }

    constructor(title: String, type: Type, category: String, amount: Double, timestamp: Date) :
            this(currentId++,title,type,category,amount,timestamp)
    {
        Log.i("Model Transaction Class: ", "Current id is $currentId") }


}