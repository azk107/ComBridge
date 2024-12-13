import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.combridge.R

data class SliderItem(
    val imageUrl: String,
)
class SliderAdapter(private val items: List<SliderItem>) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    class SliderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageViewSlider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val item = items[position]

        Glide.with(holder.imageView.context)
            .load(item.imageUrl)
            .into(holder.imageView)

    }
    override fun getItemCount(): Int = items.size
}