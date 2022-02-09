package hr.algebra.android

import android.app.AlertDialog
import android.content.ContentUris
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



class ItemAdapter(private val context: Context, private val items: MutableList<Item>)
    : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        private val tvTextItem = itemView.findViewById<TextView>(R.id.tvTextItem)
        fun bind(item: Item) {
            Picasso.get()
                .load(File(item.image_url))
                .error(R.drawable.a)
                .transform(CropCircleTransformation())
                .into(ivItem)
            tvTextItem.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    = ViewHolder(itemView = LayoutInflater
            .from(parent.context).inflate(R.layout.item, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener{
            context.startActivity<ItemPagerActivity>(ITEM_POSITION, position)

        }
        holder.itemView.setOnLongClickListener{
            AlertDialog.Builder(context).apply {
                setTitle("Delete")
                setMessage(context.getString(R.string.delete_beer) + " '${item.name}'?")
                setIcon(R.drawable.ic_baseline_delete_forever_24)
                setCancelable(true)
                setNegativeButton(R.string.Cancel, null)
                setPositiveButton("Ok") {_, _ -> deleteItem(position)}
                show()
            }
            true
        }
        holder.bind(items[position])
    }

    private fun deleteItem(position: Int) {
        val item = items[position]
        context.contentResolver.delete(
            ContentUris.withAppendedId(BEER_PROVIDER_URI, item._id!!),
            null,
            null
        )
        File(item.image_url).delete()
        items.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

}