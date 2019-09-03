package com.dicoding.surya.mademovieapp.utils

import java.io.IOException

/**
 * Created by suryamudti on 03/08/2019.
 */
class ApiException(message:  String) : IOException(message)

class NoInternetException(message: String) : IOException(message)