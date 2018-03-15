package cz.levinzonr.stackquestions.screens.questionslist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import cz.levinzonr.stackquestions.R
import cz.levinzonr.stackquestions.model.Question
import kotlinx.android.synthetic.main.item_question.view.*

/**
 * Created by nomers on 3/15/18.
 */
class QuestionListAdapter(private val context: Context) : RecyclerView.Adapter<QuestionListAdapter.ViewHolder>(){
    private  val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val items : ArrayList<Question> = ArrayList()

    inner class ViewHolder(val view : View) : RecyclerView.ViewHolder(view) {

        fun bindView(question: Question) {
            view.question_title.text = question.title
            view.question_times_viewed.text = question.viewCount.toString()
            if (question.owner != null) {
                view.question_author_name.text = question.owner.displayName
                Picasso.get().load(question.owner.profileImage).into(view.question_author_image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.item_question, parent, false))
    }

    fun setItems(list: List<Question>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(items[position])
    }
}