package com.ratekino.exception

import java.util.*

class ErrorMessage(
    val statusCode: Int,
    val timeStamp: Date,
    val message: String?,
    val description: String)