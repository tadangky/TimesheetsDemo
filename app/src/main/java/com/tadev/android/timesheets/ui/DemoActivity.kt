package com.tadev.android.timesheets.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.abdularis.civ.AvatarImageView
import com.google.gson.Gson
import com.tadev.android.timesheets.R
import com.tadev.android.timesheets.data.model.Job
import com.tadev.android.timesheets.data.model.Row
import com.tadev.android.timesheets.databinding.ActivityDemoBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding

    private lateinit var demoViewModel: DemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        demoViewModel = ViewModelProvider(this)[DemoViewModel::class.java]
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        demoViewModel.getJobs()

        demoViewModel.jobList.observe(this) {
            var sortedJobs = it.sortedBy { it.type }
            Timber.d("sortedJobs: ${sortedJobs.size}")

            val job1 = Job(type = TYPE_PRUNING_HEADER)
            val job2 = Job(type = TYPE_THINING_HEADER)

            val pruningSize = sortedJobs.filter { it.type == TYPE_PRUNING_SERVER }.size
            val thiningSize = sortedJobs.filter { it.type == TYPE_THINING_SERVER }.size
            sortedJobs = sortedJobs.toMutableList()
            if (pruningSize > 0) {
                sortedJobs.add(0, job1)
                if (thiningSize > 0) {
                    sortedJobs.add(pruningSize + 1, job2)
                }
            } else {
                if (thiningSize > 0) {
                    sortedJobs.add(0, job2)
                }
            }

            Timber.d("sortedJobs22: ${sortedJobs.size}")

            val adapter = JobAdapter(sortedJobs, { type, number ->
                applyAll(sortedJobs, type, number)
            }, { type ->
                addMaxTrees(sortedJobs, type)
            })
            binding.recyclerView.adapter = adapter
            demoViewModel.loading.postValue(false)
        }

        demoViewModel.loading.observe(this) {
            binding.progress.isVisible = it
        }
    }

    private fun applyAll(jobs: List<Job>, type: Int, number: Int): List<Job> {
        jobs.forEach {
            if (it.type == type) {
                it.rateType = 1
                it.rate = number
            }
        }
        return jobs
    }

    private fun addMaxTrees(jobs: List<Job>, type: Int) {
        val jobType = if (type == TYPE_PRUNING_HEADER) TYPE_PRUNING_SERVER else TYPE_THINING_SERVER
        jobs.forEach {
            if (it.type == jobType) {
                it.row?.forEach { row ->
                    if (row.otherNumber != null) {
                        val otherNumber = row.otherNumber ?: 0
                        row.current = row.max - otherNumber
                    } else {
                        row.current = row.max
                    }
                }
            }
        }
    }

    private class JobAdapter(
        var jobs: List<Job>,
        var applyAll: (Int, Int) -> Unit,
        var addMaxTree: (Int) -> Unit
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemViewType(position: Int): Int {
            return when (jobs[position].type) {
                TYPE_PRUNING_HEADER, TYPE_THINING_HEADER -> TYPE_HEADER
                else -> TYPE_ITEM
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                TYPE_HEADER -> {
                    val itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.header, parent, false)
                    HeaderView(itemView)
                }
                else -> {
                    val itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item, parent, false)
                    ItemView(itemView)
                }
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder.itemViewType) {
                TYPE_HEADER -> {
                    val view = holder as HeaderView
                    val type = jobs[position].type
                    if (type == TYPE_PRUNING_HEADER) {
                        view.type.text = "Pruning"
                    } else if (type == TYPE_THINING_HEADER) {
                        view.type.text = "Thining"
                    }

                    view.button.setOnClickListener {
                        addMaxTree(type)
                        notifyDataSetChanged()
                    }
                }
                else -> {
                    val view = holder as ItemView
                    val name = jobs[position].staffName
                    view.name.text = name
                    view.orchardInfo.text = jobs[position].orchard
                    view.block.text = jobs[position].block
                    view.avatar.text = name?.substring(0, 1)?.toUpperCase()

                    val usePieceRate = jobs[position].rateType == 1

                    view.pieceRateLayout.isVisible = usePieceRate
                    view.applyAll.isVisible = usePieceRate
                    view.wagesText.isVisible = !usePieceRate

                    val jobName =
                        if (jobs[position].type == TYPE_PRUNING_SERVER) "Pruning" else "Thining"
                    view.wagesText.text = "$jobName will be paid by wages in this timesheet."

                    if (usePieceRate) {
                        (view.radioGroup[0] as RadioButton).isChecked = true
                    } else {
                        (view.radioGroup[1] as RadioButton).isChecked = true
                    }

                    view.applyAll.setOnClickListener {
                        val rateInput = view.rateInput.text.toString()
                        if (rateInput.isNullOrEmpty()) {
                            Toast.makeText(
                                view.applyAll.context,
                                "Please insert number!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val number = rateInput.toInt()
                            val type = jobs[position].type
                            applyAll(type, number)
                            notifyDataSetChanged()
                        }
                    }

                    view.rateInput.setText(jobs[position].rate.toString())

                    view.radioGroup.setOnCheckedChangeListener { _, id ->
                        val selectedPieceRate = id == R.id.pieceRateBtn
                        view.pieceRateLayout.isVisible = selectedPieceRate
                        view.applyAll.isVisible = selectedPieceRate
                        view.wagesText.isVisible = !selectedPieceRate
                    }

                    view.layout.removeAllViews()
                    view.layout1.removeAllViews()

                    jobs[position].row?.forEach {
                        val rowSquare =
                            View.inflate(view.avatar.context, R.layout.row_square_item, null)
                        view.layout1.addView(rowSquare)

                        val squareLayout =
                            rowSquare.findViewById<ConstraintLayout>(R.id.squareLayout)
                        val nameTv = rowSquare.findViewById<TextView>(R.id.rowName)
                        val otherIv = rowSquare.findViewById<ImageView>(R.id.other)
                        nameTv.text = it.name
                        if (it.active) {
                            nameTv.setTextColor(Color.parseColor("#FFFFFFFF"))
                            squareLayout.setBackgroundResource(R.drawable.square_normal_background)
                        } else {
                            nameTv.setTextColor(Color.parseColor("#FF000000"))
                            squareLayout.setBackgroundResource(R.drawable.square_disable_background)
                        }
                        otherIv.isVisible = it.other != null && it.active

                        if (it.active) {
                            val row =
                                View.inflate(view.avatar.context, R.layout.input_row_item, null)
                            row.findViewById<TextView>(R.id.rowMax).text = it.max.toString()
                            val rowInput = row.findViewById<AppCompatEditText>(R.id.rowInput)
                            //Using tag for get value
                            rowInput.tag = "INPUT_ROW_${position}_${it.name}"
                            rowInput.setText(it.current.toString())
                            //TODO set max number can input
                            row.findViewById<TextView>(R.id.rowTitle).text =
                                "Trees for row ${it.name}"
                            row.findViewById<TextView>(R.id.other).text =
                                "${it.other} (${it.otherNumber})"
                            view.layout.addView(row)
                        }
                    }

                }
            }
        }

        override fun getItemCount(): Int {
            return jobs.size
        }

    }

    private class HeaderView(view: View) : RecyclerView.ViewHolder(view) {
        val type: TextView
        val button: Button

        init {
            type = view.findViewById(R.id.type)
            button = view.findViewById(R.id.headerButton)
        }
    }

    private class ItemView(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: AvatarImageView
        val name: TextView
        val orchardInfo: TextView
        val block: TextView
        val wagesText: TextView
        val layout: LinearLayout
        val layout1: LinearLayout
        val pieceRateLayout: ConstraintLayout
        val radioGroup: RadioGroup
        val applyAll: Button
        val rateInput: AppCompatEditText

        init {
            avatar = view.findViewById(R.id.avatar)
            name = view.findViewById(R.id.name)
            orchardInfo = view.findViewById(R.id.orchardInfo)
            block = view.findViewById(R.id.block)
            wagesText = view.findViewById(R.id.wagesText)
            layout = view.findViewById(R.id.layout)
            layout1 = view.findViewById(R.id.layout1)
            pieceRateLayout = view.findViewById(R.id.pieceRateLayout)
            radioGroup = view.findViewById(R.id.radioGroup)
            applyAll = view.findViewById(R.id.applyAll)
            rateInput = view.findViewById(R.id.rateInput)
        }
    }

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_ITEM = 2
        private const val TYPE_PRUNING_HEADER = 888
        private const val TYPE_THINING_HEADER = 999

        private const val TYPE_PRUNING_SERVER = 1
        private const val TYPE_THINING_SERVER = 2
    }
}