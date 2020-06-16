package com.timhuang.coroutinerequestdemo.helper

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.toast(message:CharSequence?): Toast = Toast.makeText(this,message, Toast.LENGTH_SHORT).apply { show() }

fun Context.toastLong(message:CharSequence?): Toast = Toast.makeText(this,message, Toast.LENGTH_LONG).apply { show() }

fun Fragment.toast(message:CharSequence?) = activity?.toast(message)

fun Fragment.toastLong(message:CharSequence?) = activity?.toastLong(message)