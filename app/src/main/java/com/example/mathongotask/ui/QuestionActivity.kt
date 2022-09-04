package com.example.mathongotask.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.mathongotask.ContentViewModel
import com.example.mathongotask.WordViewModelFactory
import com.example.mathongotask.databinding.ActivityQuestionBinding
import com.example.mathongotask.model.database.Content
import com.example.mathongotask.model.datahome
import com.example.mathongotask.database.ContentApplication
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity() {
    private val contentViewModel: ContentViewModel by viewModels() {
        WordViewModelFactory((application as ContentApplication).repository)
    }
    lateinit var binding:ActivityQuestionBinding
    var correct=0
    var choosed=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQuestionBinding.inflate(layoutInflater)
        val text = this.assets.open("data.json").bufferedReader().use { it.readText() }
        val allData = Gson().fromJson(text, datahome::class.java)
        binding.content.text=allData[MainActivity.index].question.text.toString()
        binding.questionindex.text="Q"+ (MainActivity.index+1).toString() + " ("+allData[MainActivity.index].type.toString() + ")"
        binding.questiontag.text="JEE Main " + allData[MainActivity.index].previousYearPapers[0].toString()
        binding.optioncontent1.text=allData[MainActivity.index].options[0].text
        binding.optioncontent2.text=(allData[MainActivity.index].options[1].text)
        binding.optioncontent3.text=(allData[MainActivity.index].options[2].text)
        binding.optioncontent4.text=(allData[MainActivity.index].options[3].text)

        binding.optioncontent1.setOnClickListener {
            binding.button.isEnabled=true
            choosed=1
        }

        binding.optioncontent2.setOnClickListener {
            binding.button.isEnabled=true
            choosed=2
        }

        binding.optioncontent3.setOnClickListener {
            choosed=3
            binding.button.isEnabled=true
        }
        binding.optioncontent4.setOnClickListener {
            choosed=4
            binding.button.isEnabled=true
        }
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
                binding.solutiontext.text=allData[MainActivity.index].solution.text.toString()
                binding.solutiontext.visibility=View.VISIBLE
                if(allData[MainActivity.index].options[choosed-1].isCorrect)
                {
                    when{
                        choosed==1->{
//                            binding.textinputlayout1.hint = "You Marked | Correct Answer"
                            binding.textinputlayout1.boxStrokeColor=Color.parseColor("#31D99C")}

                        choosed==2->{
//                            binding.textinputlayout2.hint = "You Marked | Correct Answer"
                            binding.textinputlayout2.strokeColor=Color.parseColor("#31D99C")}

                        (choosed==3)->{
//                            binding.textinputlayout3.hint = "You Marked | Correct Answer"
                            binding.textinputlayout3.strokeColor=Color.parseColor("#31D99C")}

                        choosed==4->{
//                            binding.textinputlayout4.hint = "You Marked | Correct Answer"
                            binding.textinputlayout4.strokeColor=Color.parseColor("#31D99C")}
                    }
                }
                else
                {
                    when{
                        choosed==1->{
//                            binding.textinputlayout1.hint = "You Marked | Inorrect Answer"
                            binding.textinputlayout1.boxStrokeColor=Color.parseColor("#FF5858")}

                        choosed==2->{
//                            binding.textinputlayout2.hint = "You Marked | Inorrect Answer"
                            binding.textinputlayout2.strokeColor=Color.parseColor("#FF5858")}

                        (choosed==3)->{
//                            binding.textinputlayout3.hint = "You Marked | Inorrect Answer"
                            binding.textinputlayout3.strokeColor=Color.parseColor("#FF5858")}

                        choosed==4->{
//                            binding.textinputlayout4.hint = "You Marked | Inorrect Answer"
                            binding.textinputlayout4.strokeColor=Color.parseColor("#FF5858")}
                    }
                    when{
                        allData[MainActivity.index].options[0].isCorrect->{
//                            binding.textinputlayout1.hint = "Correct Answer"
                            binding.textinputlayout1.boxStrokeColor=Color.parseColor("#31D99C")
                        }
                        allData[MainActivity.index].options[1].isCorrect->{
//                            binding.textinputlayout2.hint = "Correct Answer"
                            binding.textinputlayout2.strokeColor=Color.parseColor("#31D99C")
                        }
                        allData[MainActivity.index].options[2].isCorrect->{
//                            binding.textinputlayout3.hint = "Correct Answer"
                            binding.textinputlayout3.strokeColor=Color.parseColor("#31D99C")
                        }
                        allData[MainActivity.index].options[3].isCorrect->{
//                            binding.textinputlayout4.hint = "Correct Answer"
                            binding.textinputlayout4.strokeColor=Color.parseColor("#31D99C")
                        }

                    }
                }
            }
        }
        setContentView(binding.root)
    }
}