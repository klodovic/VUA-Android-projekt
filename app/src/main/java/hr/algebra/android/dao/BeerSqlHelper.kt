package hr.algebra.android.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.algebra.android.model.Item

private const val DB_NAME = "beers.db"
private const val DB_VERSION = 1
private const val TABLE = "beers"

private val CREATE = "create table $TABLE(" +
        "${Item::_id.name} integer primary key autoincrement, " +
        "${Item::name.name} text not null, " +
        "${Item::tagline.name} text not null, " +
        "${Item::first_brewed.name} text not null, " +
        "${Item::description.name} text not null, " +
        "${Item::image_url.name} text not null, " +
        "${Item::brewers_tips.name} text not null, " +
        "${Item::contributed_by.name} text not null, " +
        "${Item::read.name} integer not null" +
        ")"

private const val DROP = "drop table $TABLE"


class BeerSqlHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    BeerRepository {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP)
        onCreate(db)
    }

    override fun delete(selection: String?, selectionArgs: Array<String>?)
            = writableDatabase.delete(TABLE, selection, selectionArgs)

    override fun update(values: ContentValues?, selection: String?, selectionArgs: Array<String>?)
            = writableDatabase.update(TABLE, values, selection, selectionArgs)

    override fun insert(values: ContentValues?)
            = writableDatabase.insert(TABLE, null, values)

    override fun query(projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor
            = readableDatabase.query(
                TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
            )
}