package com.androidvoyage.vocabhelper.adapters

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.androidvoyage.vocabhelper.R
import com.androidvoyage.vocabhelper.database.AppDatabase
import com.androidvoyage.vocabhelper.model.WordData
import android.content.DialogInterface


class WordRVAdapter(mContext: Context) : RecyclerView.Adapter<WordRVAdapter.WordRVViewHolder>() {

    var wordList: List<WordData>? = null
    var context = mContext
    var listner: ItemClickListner? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordRVViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.word_view, null)
        return WordRVViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wordList!!.size
    }

    override fun onBindViewHolder(holder: WordRVViewHolder, position: Int) {
        val wordData = wordList!![position]
        holder.tvWord.setText(wordData.word)
        holder.tvMeaning.setText(wordData.wordMeaning)

        holder.containerLayout.setOnClickListener(View.OnClickListener {
            listner!!.onItemClick(position, wordData)
        })

        holder.ivDelete.setOnClickListener(View.OnClickListener {

            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        AppDatabase.getAppDatabase(context).wordsDao().delete(wordData)
                        wordList = AppDatabase.getAppDatabase(context).wordsDao().getAllWordsDec()
                        notifyDataSetChanged()
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show()


        })
    }

    class WordRVViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val tvWord: TextView = itemView!!.findViewById(R.id.tvWord)
        val tvMeaning: TextView = itemView!!.findViewById(R.id.tvMeaning)
        val containerLayout: RelativeLayout = itemView!!.findViewById(R.id.containerLayout)
        val ivDelete: ImageView = itemView!!.findViewById(R.id.ivDelete)
    }


    interface ItemClickListner {
        fun onItemClick(position: Int, wordData: WordData)
    }
}
