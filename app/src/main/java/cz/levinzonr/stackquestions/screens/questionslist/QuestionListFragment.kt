package cz.levinzonr.stackquestions.screens.questionslist


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import cz.levinzonr.stackquestions.R
import cz.levinzonr.stackquestions.model.QuestionResponce
import cz.levinzonr.stackquestions.presenter.ListPresenter
import cz.levinzonr.stackquestions.screens.ViewCallBacks
import cz.levinzonr.stackquestions.screens.viewutils.InfiniteScrollListener
import cz.levinzonr.trendee.screens.artistslist.VerticalSpaceDecoration
import kotlinx.android.synthetic.main.fragment_question_list.view.*


/**
 * A simple [Fragment] subclass.
 */
class QuestionListFragment : Fragment(), ViewCallBacks<QuestionResponce> {

    lateinit var presenter: ListPresenter
    lateinit var adapter: QuestionListAdapter
    lateinit var questionResponce: QuestionResponce

    lateinit var recyclerView: RecyclerView

    companion object {
        const val TAG = "QuestionsListFragment"
        const val START_PAGE = 1
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        presenter = ListPresenter()
        adapter = QuestionListAdapter(context)
        val view = inflater!!.inflate(R.layout.fragment_question_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        initRecyclerView(recyclerView)
        presenter.attachView(this)
        presenter.getQuestionsPage(START_PAGE)


        return view
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(VerticalSpaceDecoration())
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.addOnScrollListener(object : InfiniteScrollListener(layoutManager) {
            override fun loadNext(pageToLoad: Int) {
                Log.d(TAG, "load next $pageToLoad")
                if (questionResponce.hasMore) {
                   presenter.getQuestionsPage(pageToLoad)
                } else {
                    Log.d(TAG, "End of the list")
                }
            }
        })
    }

    override fun onLoadingStarted() {
        Log.d(TAG, "on Loading started")
        recyclerView.post({
            adapter.isLoading = true
        })
    }

    override fun onLoadingFinished(result: QuestionResponce) {
        Log.d(TAG, "on Loading finished")
        adapter.setItems(result.items)
        questionResponce = result
        recyclerView.post({
            adapter.isLoading = false
        })    }

    override fun onError() {
        Log.d(TAG, "on Error")
        adapter.isLoading = false
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}// Required empty public constructor