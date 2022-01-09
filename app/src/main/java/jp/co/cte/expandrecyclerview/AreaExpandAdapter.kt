package jp.co.cte.expandrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jp.co.cte.expandrecyclerview.databinding.ItemAreaChildBinding
import jp.co.cte.expandrecyclerview.databinding.ItemAreaParentBinding
import jp.co.cte.expandrecyclerview.model.AreaItemView

/**
 * 课题号（GORASA-XXXX)
 * 类或接口的描述信息
 * @author 2569658002@qq.com
 * @date 2022/1/9
 */
class AreaExpandAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), FoldState{
    private val mListData: MutableList<AreaItemView> = mutableListOf()
    private val AREA_PARENT: Int = 1;
    private val AREA_CHILD: Int = 2;

    class AreaParentViewHolder(itemParentBind: ItemAreaParentBinding) : RecyclerView.ViewHolder(itemParentBind.root){
        val mItemParentBind: ItemAreaParentBinding = itemParentBind
    }

    class AreaChildViewHolder(itemChildBind: ItemAreaChildBinding) : RecyclerView.ViewHolder(itemChildBind.root){
        val mItemChildBind: ItemAreaChildBinding = itemChildBind;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == AREA_PARENT) {
            val holder = AreaParentViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_area_parent, parent, false))
            holder.mItemParentBind.ivBtnOpenAndClose.setOnClickListener {
                val areaItemView = holder.mItemParentBind.areaItemView
                if(areaItemView != null){
                    if (areaItemView.foldedState){
                        openState(areaItemView, holder.layoutPosition)
                    } else {
                        closeState(areaItemView, holder.layoutPosition)
                    }
                }
            }
            return holder
        } else {
            val holder = AreaChildViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_area_child, parent, false))
            holder.mItemChildBind.cbCheck.setOnClickListener {
                val areaItemView = holder.mItemChildBind.areaItemView
                if (areaItemView != null){
                    areaItemView.checkedState = holder.mItemChildBind.cbCheck.isChecked
                    updateAreaParentItemCount(holder.mItemChildBind.cbCheck.isChecked, holder.layoutPosition, areaItemView.areaAllChild)
                }
            }
            return holder
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val areaItemView = mListData.get(position)
        if (areaItemView.areaParent){
            val parentHolder = holder as AreaParentViewHolder
            parentHolder.mItemParentBind.areaItemView = areaItemView
        } else {
            val childHolder = holder as AreaChildViewHolder
            childHolder.mItemChildBind.areaItemView = areaItemView
        }
    }

    private fun updateAreaParentItemCount(isChecked: Boolean, position: Int, isAllChildCheck: Boolean) {
        for (i in  (position-1) downTo 0) {
            val areaItemView = mListData.get(i)
            if (areaItemView.areaParent && areaItemView.child != null && !areaItemView.child.isEmpty()) {

                if (isAllChildCheck) {
                    areaItemView.checkedCount = if (isChecked) (areaItemView.child.size - 1) else 0
                    for (areaChild in areaItemView.child) {
                        areaChild.checkedState = isChecked
                    }
                } else {
                    areaItemView.checkedCount = areaItemView.checkedCount + (if (isChecked) 1 else -1)
                    areaItemView.child.get(0).checkedState = (areaItemView.checkedCount == (areaItemView.child.size - 1))
                }
                notifyItemRangeChanged(i, areaItemView.child.size + 1)
                return
            }
        }
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(mListData.get(position).areaParent) AREA_PARENT else AREA_CHILD
    }

    fun initAdapterData(listData: List<AreaItemView>?){
        mListData.clear()
        if(listData != null){
            mListData.addAll(listData)
        }
        notifyDataSetChanged()
    }

    override fun openState(areaParent: AreaItemView, position: Int) {
        if (areaParent.child != null && areaParent.child.size > 0) {
            mListData.addAll(position + 1, areaParent.child)
            areaParent.foldedState = false
            notifyItemRangeInserted(position + 1, areaParent.child.size)
            notifyItemChanged(position)
        }
    }

    override fun closeState(areaParent: AreaItemView, position: Int) {
        if (areaParent.child != null && areaParent.child.size > 0){
            mListData.subList(position + 1, position + 1 + areaParent.child.size).clear()
            areaParent.foldedState = true
            notifyItemRangeRemoved(position + 1, areaParent.child.size)
            notifyItemChanged(position)
        }
    }
}