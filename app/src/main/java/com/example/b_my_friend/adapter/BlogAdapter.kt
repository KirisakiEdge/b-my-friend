package com.example.b_my_friend.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.b_my_friend.R
import com.example.b_my_friend.databinding.ItemBlogBinding
import com.example.b_my_friend.model.Chat

class BlogAdapter () : RecyclerView.Adapter<BlogAdapter.ViewHolder>() {

    private val myDataset: MutableList<String> = mutableListOf("Material is the metaphor.\n" +
            "Material Design is inspired by the physical world and its textures, including how they reflect" +
            " light and cast shadows.",

    "The Material\n" +
            "One thing at a time. To connect Kant’s arguments to Material Design," +
            " we need to establish what makes the Material environment applicable to the physical one." +
            " For Kant, there are two properties that we must perceive about the world before any experience is discernible." +
            " These properties are space and time. In short, an experience happens in space and in time." +
            " Try to think of your hand without the space in between your fingers." +
            " Or yourself 20 years ago without time separating the person you were then from" +
            " the person you are now. Our environment is only plausible to us in the context of space and time.",

        "Elevation\n" +
                "Material surfaces exist on an x-axis, y-axis, and z-axis." +
                " The z-axis simulates elevation and elevation helps simulate depth. " +
                "All three axes give surfaces specific locations in their environment " +
                "and it is the relative location between different surfaces that" +
                " create the dynamic sense of depth and community that Material Design is known for.")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemBlogBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context)
            , R.layout.item_blog, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myDataset[position])

    }


    override fun getItemCount() = myDataset.size

    class ViewHolder(v: ItemBlogBinding) : RecyclerView.ViewHolder(v.root) {
        private val text: TextView = itemView.findViewById(R.id.text)
        fun bind(s: String){
            text.text = s
        }

    }
}