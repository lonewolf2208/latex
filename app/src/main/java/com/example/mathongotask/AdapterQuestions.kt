package com.example.mathongotask

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mathongotask.databinding.ViewquestionsBinding
import com.example.mathongotask.model.Option

class AdapterQuestions(var data:ArrayList<Option>): RecyclerView.Adapter<AdapterQuestions.ViewHolder>() {
    companion object
    {   var views: ArrayList<ViewHolder> = ArrayList()
    }
    var clickListener: ClickListener?=null
    interface ClickListener{
        fun OnClick(position:Int)
    }


    fun onClickListener( clickListener: ClickListener)
    {
        this.clickListener=clickListener
    }
    inner class ViewHolder(var binding: ViewquestionsBinding):RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder =ViewHolder(  (ViewquestionsBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)))


    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

        views.add(holder)
        holder.binding.optioncontent2.text=data[holder.adapterPosition].text.toString()
        holder.binding.textinputlayout2.setOnClickListener {
            clickListener?.OnClick(position)
            for(i in views)
            {
                    i.binding.carView.strokeColor=Color.parseColor("#DCDDE2")
            }
            holder.binding.carView.strokeColor=Color.parseColor("#1589EE")
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }
}