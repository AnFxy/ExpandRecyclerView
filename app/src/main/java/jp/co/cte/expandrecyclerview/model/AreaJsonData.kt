package jp.co.cte.expandrecyclerview.model

/**
 * 课题号（GORASA-XXXX)
 * 类或接口的描述信息
 * @author 2569658002@qq.com
 * @date 2022/1/9
 */
/**
 * 用于从json文件中解析映射的数据类，具体数据结构可以根据json文件改动。
 * @param areaName 父地址名
 * @param childAreaName 子地址名列表
 */
data class AreaJsonData(
    val areaName: String,
    val childAreaName: List<String>
)
