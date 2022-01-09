package jp.co.cte.expandrecyclerview.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * 课题号（GORASA-XXXX)
 * 类或接口的描述信息
 * @author 2569658002@qq.com
 * @date 2022/1/9
 */
object FileUtil {
    @JvmStatic
    fun readAsString(fileName: String, context: Context): String? = readAsStream(fileName, context)?.use { inputStream ->
        BufferedReader(InputStreamReader(inputStream, "utf-8")).use {
            val stringBuffer = StringBuffer()
            it.forEachLine {
                stringBuffer.append(it)
            }
            stringBuffer.toString()
        }
    }

    fun readAsStream(fileName: String, context: Context): InputStream? = context.assets?.open(fileName)
}