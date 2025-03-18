package myprojects.testhelper.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import myprojects.testhelper.R
import myprojects.testhelper.model.TestResults

class TestResultsAdapter(private var testResultsList: List<TestResults>) :
    RecyclerView.Adapter<TestResultsAdapter.TestResultsViewHolder>() {

    class TestResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTestId: TextView = itemView.findViewById(R.id.passed_test_item)
        val textViewCorrect: TextView = itemView.findViewById(R.id.correct_count)
        val textViewWrong: TextView = itemView.findViewById(R.id.wrong_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestResultsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_passed_tests, parent, false)
        return TestResultsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TestResultsViewHolder, position: Int) {
        val currentResult = testResultsList[position]
        holder.textViewTestId.text = "Тест: ${currentResult.testName}"
        holder.textViewCorrect.text = currentResult.correct.toString()
        holder.textViewWrong.text = currentResult.wrong.toString()
    }

    override fun getItemCount() = testResultsList.size

    fun updateData(newResults: List<TestResults>) {
        testResultsList = newResults
        notifyDataSetChanged()
    }

    }

