package hr.algebra.android

import android.app.AlertDialog
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.android.framework.startActivity
import hr.algebra.android.model.Item
import java.io.File
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.nio.file.Files.delete


class ItemPagerAdapter(private val context: Context, private val items: MutableList<Item>)
    : RecyclerView.Adapter<ItemPagerAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        private val tvFirstBrewed = itemView.findViewById<TextView>(R.id.tvFirstBrewed)
        val ivRead = itemView.findViewById<ImageView>(R.id.ivRead)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        private val tvBrewedTips = itemView.findViewById<TextView>(R.id.tvBrewedTips)
        private val tvContributedBy = itemView.findViewById<TextView>(R.id.tvContributedBy)
        fun bind(item: Item) {
            Picasso.get()
                .load(File(item.image_url))
                .error(R.drawable.a)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
            tvFirstBrewed.text = item.first_brewed
            ivRead.setImageResource(if (item.read) R.drawable.ic_baseline_flag_circle_24_green else R.drawable.ic_baseline_flag_circle_24_red)
            tvTitle.text = item.name
            tvDescription.text = item.description
            tvBrewedTips.text = item.brewers_tips
            tvContributedBy.text = item.contributed_by
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    = ViewHolder(itemView = LayoutInflater
            .from(parent.context).inflate(R.layout.item_pager, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.ivRead.setOnClickListener {
            item.read = !item.read
            val uri = ContentUris.withAppendedId(BEER_PROVIDER_URI, item._id!!)
            val values = ContentValues().apply {
                put(Item::read.name, item.read)
            }
            context.contentResolver.update(uri, values, null, null)
            notifyItemChanged(position)
        }
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

}