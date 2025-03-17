package myprojects.testhelper.adapter
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import myprojects.testhelper.R
import myprojects.testhelper.model.Questions

class QuestionsAdapter(private var questions: MutableList<Questions>) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionEditText: EditText = itemView.findViewById(R.id.questionEditText)
        val option1EditText: EditText = itemView.findViewById(R.id.option1EditText)
        val option2EditText: EditText = itemView.findViewById(R.id.option2EditText)
        val option3EditText: EditText = itemView.findViewById(R.id.option3EditText)
        val option4EditText: EditText = itemView.findViewById(R.id.option4EditText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.questionEditText.setText(question.question)
        holder.option1EditText.setText(question.option1)
        holder.option2EditText.setText(question.option2)
        holder.option3EditText.setText(question.option3)
        holder.option4EditText.setText(question.option4)

        holder.questionEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                question.question = s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        holder.option1EditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                question.option1 = s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        holder.option2EditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                question.option2 = s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        holder.option3EditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                question.option3 = s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        holder.option4EditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                question.option4 = s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }

    override fun getItemCount(): Int = questions.size

    fun getListOfQuestions(): MutableList<Questions>{
        return questions
    }

    fun setUpdatedQuestions(newQuestions: MutableList<Questions>){
        questions = newQuestions
        notifyDataSetChanged()
    }


    fun addQuestion(question: Questions) {
        questions.add(question)
        notifyItemInserted(questions.size - 1)
    }
}
