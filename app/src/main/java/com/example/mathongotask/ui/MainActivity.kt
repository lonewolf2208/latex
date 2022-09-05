package com.example.mathongotask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mathongotask.ContentViewModel
import com.example.mathongotask.WordViewModelFactory
import com.example.mathongotask.databinding.ActivityMainBinding
import com.example.mathongotask.model.Adapterhome
import com.example.mathongotask.model.database.Content
import com.example.mathongotask.model.datahome
import com.example.mathongotask.database.ContentApplication
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var adapter: Adapterhome
    companion object{
        var index:Int=0
    }
    private val wordViewModel:ContentViewModel by viewModels() {
        WordViewModelFactory((application as ContentApplication).repository)
    }
    private var layoutManager: RecyclerView.LayoutManager?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val text = this.assets.open("data.json").bufferedReader().use { it.readText() }
        val allData = Gson().fromJson(text, datahome::class.java)
        adapter= Adapterhome(allData)
        layoutManager = LinearLayoutManager(
            this
        )
        binding.textView2.text=allData.size.toString()+"Qs | Low Input High Output Chapter"
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.adapter=adapter
        binding.tag.setOnClickListener {
            var content= listOf<Content>()
           wordViewModel.allWords.observe(this@MainActivity){
                content=it
            }
            if(binding.tag.text=="Not Attempted")
            {
                binding.tag.text="Attempted"
                if(!content.isEmpty()) {
                    var newdata=datahome()
                   for(i in content)
                   {
                       newdata.add(allData[i.id.toInt()])
                   }
                    adapter= Adapterhome(newdata)
                    binding.recyclerview.adapter=adapter
                    adapter.onClickListener(object : Adapterhome.ClickListener {
                        override fun OnClick(position: Int) {
                            index=position
                            startActivity(Intent(this@MainActivity, QuestionActivity::class.java))
                        }
                    })
                    adapter.notifyDataSetChanged()
                }
                else
                {
                    binding.recyclerview.visibility=View.GONE
                   binding.hiddenimage.visibility=View.VISIBLE
                    binding.hiddentext.visibility=View.VISIBLE
                }

            }
            else
            {
                binding.recyclerview.visibility=View.VISIBLE
                binding.hiddenimage.visibility=View.GONE
                binding.hiddentext.visibility=View.GONE
                binding.tag.text="Not Attempted"
                adapter=Adapterhome(allData)
                binding.recyclerview.adapter=adapter
                adapter.onClickListener(object : Adapterhome.ClickListener {
                    override fun OnClick(position: Int) {
                        index=position
                        startActivity(Intent(this@MainActivity, QuestionActivity::class.java))
                    }
                })
                adapter.notifyDataSetChanged()
            }
        }
       adapter.onClickListener(object : Adapterhome.ClickListener {
           override fun OnClick(position: Int) {
               index=position
               startActivity(Intent(this@MainActivity, QuestionActivity::class.java))
           }
       })
        setContentView(binding.root)
    }
}