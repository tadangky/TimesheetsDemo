package com.tadev.android.timesheets.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tadev.android.timesheets.R
import com.tadev.android.timesheets.data.model.TodosResponse
import com.tadev.android.timesheets.databinding.ActivityMainBinding
import com.tadev.android.timesheets.databinding.ActivityMainBinding.inflate
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.getTodos()

        mainViewModel.todoList.observe(this) {
            Timber.e("MainAc observe todo: ${it.size}")
            val adapter = TodosAdapter(it)
            binding.recyclerView.adapter = adapter
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private class TodosAdapter(
        var list: List<TodosResponse>,
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemViewType(position: Int): Int {
            return if (position % 3 == 1) TYPE_2 else TYPE_1
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                TYPE_1 -> {
                    val itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.snippet_item1, parent, false)
                    FirstViewHolder(itemView)
                }
                TYPE_2 -> {
                    val itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.snippet_item2, parent, false)
                    SecondViewHolder(itemView)
                }
                else -> {
                    val itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.snippet_item1, parent, false)
                    FirstViewHolder(itemView)
                }
            }
        }

        override fun getItemCount() = list.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder.itemViewType) {
                TYPE_1 -> {
                    val view = holder as FirstViewHolder
                    view.text.text = list[position].title
                }
                TYPE_2 -> {
                    val view = holder as SecondViewHolder
                    view.text.text = list[position].title
                }
            }
        }

    }


    private class FirstViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView

        init {
            text = itemView.findViewById<View>(R.id.text) as TextView

        }
    }

    private class SecondViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView

        init {
            text = itemView.findViewById<View>(R.id.textSeparator) as TextView

        }
    }

    companion object {
        private val TYPE_1 = 1
        private val TYPE_2 = 2
        private val TYPE_3 = 3
    }

}