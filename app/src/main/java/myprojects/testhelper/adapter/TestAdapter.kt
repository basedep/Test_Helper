import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import myprojects.testhelper.R
import myprojects.testhelper.model.Tests

class TestAdapter(
    private var tests: List<Tests>,
    private val onEditClick: (Tests) -> Unit,
    private val onDeleteClick: (Tests) -> Unit
) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val testName: TextView = itemView.findViewById(R.id.textTestName)
        private val editButton: Button = itemView.findViewById(R.id.item_button_edit)
        private val deleteButton: Button = itemView.findViewById(R.id.item_button_delete)

        fun bind(test: Tests) {
            testName.text = test.nameOfTest

            editButton.setOnClickListener {
                onEditClick(test)
            }

            deleteButton.setOnClickListener {
                onDeleteClick(test)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false)
        return TestViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(tests[position])
    }

    override fun getItemCount(): Int {
        return tests.size
    }


    fun updateTests(newTests: List<Tests>) {
        tests = newTests
        notifyDataSetChanged()
    }
}
