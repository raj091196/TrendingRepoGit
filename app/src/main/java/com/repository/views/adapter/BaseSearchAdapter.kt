package com.repository.views.adapter

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView

/**
 *  @param T type of the recycler view data
 *  @param VH View holder for the recycler View
 */
abstract class BaseSearchAdapter<T, VH : RecyclerView.ViewHolder?>(data: ArrayList<T>) :
    RecyclerView.Adapter<VH>(), Filterable {

    private var wholeList: List<T> = ArrayList()

    private var searchList: List<T> = ArrayList()

    /**
     *  @return viewHolder instance
     *  create the ViewHolder
     */
    abstract fun initViewHolder(parent: ViewGroup, viewType: Int): VH

    /**
     *  @param holder view Holder
     *  @param position viewHolder position
     *  @param data viewHolder position data
     *  bind the viewHolder to the Recycler View
     */
    abstract fun bindViewHolderData(holder: VH, position: Int, data: T)

    /**
     *  method to check for the filter Constrain
     */
    abstract fun hasSearchKey(value: T, filterKey: String): Boolean

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return initViewHolder(parent, viewType)
    }

    init {
        wholeList = data
        searchList = data
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        bindViewHolderData(holder, position, searchList[position])
    }

    open fun setData(data: ArrayList<T>) {
        this.wholeList = data
        this.searchList = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    /**
     *  Performs the filter operation on the recycler view Item
     *  and Update the View Which Matches the Filter criteria
     */
    override fun getFilter(): Filter {

        return object : Filter() {

            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResult = FilterResults()
                val resultList = ArrayList<T>()
                if (p0 != null && p0.isNotEmpty() && wholeList.isNotEmpty()) {
                    for (i in wholeList)
                        if (hasSearchKey(i, p0.toString())) resultList.add(i)
                    filterResult.values = resultList
                    filterResult.count = resultList.size
                } else {
                    filterResult.values = wholeList
                    filterResult.count = wholeList.size
                }
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                p1?.let {
                    @Suppress("UNCHECKED_CAST")
                    searchList = ArrayList(p1.values as ArrayList<T>)
                    notifyDataSetChanged()
                }
            }
        }
    }
}