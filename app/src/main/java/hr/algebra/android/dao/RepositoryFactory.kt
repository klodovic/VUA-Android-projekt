package hr.algebra.android.dao

import android.content.Context

fun getBeerRepository(context: Context?) = BeerSqlHelper(context)