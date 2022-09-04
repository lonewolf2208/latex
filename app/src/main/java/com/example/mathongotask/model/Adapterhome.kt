package com.example.mathongotask.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mathongotask.databinding.AdapterViewBinding

class Adapterhome(var data:ArrayList<datahomeItem>): RecyclerView.Adapter<Adapterhome.ViewHolder>() {
    var clickListener: ClickListener?=null
    interface ClickListener{
        fun OnClick(position:Int)
    }
    fun onClickListener( clickListener: ClickListener)
    {
        this.clickListener=clickListener
    }
    inner class ViewHolder(var binding:AdapterViewBinding):RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                clickListener?.OnClick(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =ViewHolder(  (AdapterViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.index.text=(holder.adapterPosition + 1).toString()
        holder.binding.content.text=data[holder.adapterPosition].question.text.toString()
        holder.binding.year.text="JEE Main" + data[holder.adapterPosition].previousYearPapers[0].toString()
    }

    override fun getItemCount(): Int {
     return data.size
    }
}