package hr.algebra.android.model

import com.google.gson.annotations.SerializedName

data class Item(
    var _id: Long?,
    val name : String,
    val tagline : String,
    val first_brewed : String,
    val description : String,
    val image_url : String,
    //val food_pairing : String,
    val brewers_tips : String,
    val contributed_by : String,
    var read: Boolean
)
