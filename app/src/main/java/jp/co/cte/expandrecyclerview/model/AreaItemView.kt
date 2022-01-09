package jp.co.cte.expandrecyclerview.model

/**
 * 课题号（GORASA-XXXX)
 * 类或接口的描述信息
 * @author 2569658002@qq.com
 * @date 2022/1/9
 */
/**
 * @param name item的显示名字
 * @param checkedCount 子item被选中的个数（这个属性只有父item才有意义）
 * @param foldedState item是否是收起状态（这个属性只有父item才有意义）
 * @param checkedState item是否被被选中 （这个属性只有子item才有意义）
 * @param areaParent item 是否是父item
 * @param areaAllChild item 是否为子item中的"选中全部子item"
 * @param child item 的子item （这个属性只有父item才有意义）
 *
 */
data class AreaItemView(
    val name: String,
    var checkedCount: Int,
    var foldedState: Boolean,
    var checkedState: Boolean,
    val areaParent: Boolean,
    val areaAllChild: Boolean,
    val child: List<AreaItemView>?
)