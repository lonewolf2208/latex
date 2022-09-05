package com.example.mathongotask.ui

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mathongotask.AdapterQuestions
import com.example.mathongotask.ContentViewModel
import com.example.mathongotask.R
import com.example.mathongotask.WordViewModelFactory
import com.example.mathongotask.database.ContentApplication
import com.example.mathongotask.databinding.ActivityQuestionBinding
import com.example.mathongotask.model.Option
import com.example.mathongotask.model.database.Content
import com.example.mathongotask.model.datahome
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson


class QuestionActivity : AppCompatActivity() {
    private val contentViewModel: ContentViewModel by viewModels() {
        WordViewModelFactory((application as ContentApplication).repository)
    }
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var binding:ActivityQuestionBinding
    lateinit var adapter:AdapterQuestions
    var choosed=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQuestionBinding.inflate(layoutInflater)
        AdapterQuestions.views.clear()
        val text = this.assets.open("data.json").bufferedReader().use { it.readText() }
        val allData = Gson().fromJson(text, datahome::class.java)
        binding.content.text=allData[MainActivity.index].question.text.toString()
        binding.questionindex.text="Q"+ (MainActivity.index+1).toString() + " ("+allData[MainActivity.index].type.toString() + ")"
        binding.questiontag.text="JEE Main " + allData[MainActivity.index].previousYearPapers[0].toString()
        if(MainActivity.index==0)
        {
            binding.image.isEnabled=false
        }
        binding.materialCardView.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
            adapter= AdapterQuestions(allData[MainActivity.index].options as ArrayList<Option>)
        binding.recycle.adapter=adapter
        layoutManager = LinearLayoutManager(
            this
        )
        binding.recycle.layoutManager = layoutManager
        adapter.onClickListener(object : AdapterQuestions.ClickListener {
            override fun OnClick(position: Int) {
                choosed=position+1
                binding.button.isEnabled=true
//                Toast.makeText(this@QuestionActivity, "Clicked", Toast.LENGTH_SHORT).show()
            }
        })


        binding.button.setOnClickListener {

            if(binding.button.text=="Next") {
                contentViewModel.insert(arrayListOf(Content(MainActivity.index.toString())))
                MainActivity.index++
                finish()
                overridePendingTransition(0, 0)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
            else
            {
                binding.button.text="Next"
                binding.textView5.visibility=View.VISIBLE
                binding.solutiontext.text=allData[MainActivity.index].solution.text
                binding.solutiontext.visibility=View.VISIBLE
                if(allData[MainActivity.index].options[choosed-1].isCorrect)
                {
                    val dialodView =
                    LayoutInflater.from(this).inflate(R.layout.snackbar_custom, null)
                    val mBuilder = AlertDialog.Builder(this)
                        .setView(dialodView)

                    val alertDialog: AlertDialog =mBuilder.create()
                    alertDialog.getWindow()?.requestFeature(Window.FEATURE_NO_TITLE)
                    alertDialog.show()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.window?.setGravity(Gravity.TOP)
                    Handler().postDelayed({alertDialog.dismiss()},1000)
                        AdapterQuestions.views[choosed-1].binding.correctAnswer.visibility=View.VISIBLE
                    AdapterQuestions.views[choosed-1].binding.carView.strokeColor=Color.parseColor("#31D99C")
                }
                else
                {
//                    Toast.makeText(this, AdapterQuestions.views.size.toString(), Toast.LENGTH_SHORT).show()
                    AdapterQuestions.views[choosed-1].binding.IncorrectAnswer.visibility=View.VISIBLE
                    AdapterQuestions.views[choosed-1].binding.carView.strokeColor=Color.parseColor("#FF5858")
                    for(i in 0..3)
                    {
                        if(allData[MainActivity.index].options[i].isCorrect)
                        {
                            AdapterQuestions.views[i].binding.correctAnswer.visibility=View.VISIBLE
                                     AdapterQuestions.views[i].binding.carView.strokeColor=Color.parseColor("#31D99C")
                        }
                    }
                }
            }
        }
        binding.materialCardView11.setOnClickListener {
            MainActivity.index++
            finish()
            overridePendingTransition(0, 0)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        setContentView(binding.root)
    }
}