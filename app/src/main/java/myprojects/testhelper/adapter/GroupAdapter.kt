package myprojects.testhelper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myprojects.testhelper.R
import myprojects.testhelper.model.Groups
import myprojects.testhelper.model.ParentItem

class GroupAdapter() : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    val differ = AsyncListDiffer(this, MyDiffCallback)

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewGroupName: TextView = itemView.findViewById(R.id.textViewGroupName)
        val recyclerViewStudents: RecyclerView = itemView.findViewById(R.id.recyclerViewStudents)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = differ.currentList[position]
        holder.textViewGroupName.text = group.title

        val studentAdapter = StudentAdapter(group.students)
        holder.recyclerViewStudents.adapter = studentAdapter
        holder.recyclerViewStudents.layoutManager = LinearLayoutManager(holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    object MyDiffCallback : DiffUtil.ItemCallback<ParentItem>() {
        override fun areItemsTheSame(oldItem: ParentItem, newItem: ParentItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ParentItem, newItem: ParentItem): Boolean {
            return oldItem == newItem
        }
    }
}