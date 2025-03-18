package myprojects.testhelper.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import myprojects.testhelper.R
import myprojects.testhelper.model.Tests

class AvailableTestAdapter(
    private val context: Context,
    private val testsList: List<Tests>,
    private val onItemClick: (Tests) -> Unit
) : RecyclerView.Adapter<AvailableTestAdapter.TestViewHolder>() {

    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val testNameTextView: TextView = itemView.findViewById(R.id.available_test_item)

        fun bind(test: Tests) {
            testNameTextView.text = test.nameOfTest
            itemView.setOnClickListener { onItemClick(test) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_available_test, parent, false)
        return TestViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val test = testsList[position]
        holder.bind(test)
    }

    override fun getItemCount(): Int {
        return testsList.size
    }
}
