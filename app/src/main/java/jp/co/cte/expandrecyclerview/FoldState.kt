package jp.co.cte.expandrecyclerview

import jp.co.cte.expandrecyclerview.model.AreaItemView

/**
 * 课题号（GORASA-XXXX)
 * 类或接口的描述信息
 * @author 2569658002@qq.com
 * @date 2022/1/9
 */
interface FoldState {
    fun openState(areaParent: AreaItemView, position: Int)
    fun closeState(areaParent: AreaItemView, position: Int)
}