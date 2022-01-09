package jp.co.cte.expandrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jp.co.cte.expandrecyclerview.databinding.ActivityMainBinding
import jp.co.cte.expandrecyclerview.model.AreaItemView
import jp.co.cte.expandrecyclerview.model.AreaJsonData
import jp.co.cte.expandrecyclerview.util.FileUtil

class MainActivity : AppCompatActivity() {
    private val bindGallery: ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWidget()
    }

    private fun initWidget(){
        val adapter = AreaExpandAdapter()
        bindGallery.rvArea.layoutManager = LinearLayoutManager(this)
        bindGallery.rvArea.adapter = adapter
        ((bindGallery.rvArea.itemAnimator) as SimpleItemAnimator).supportsChangeAnimations = false
        adapter.initAdapterData(getListAreaData())

        bindGallery.iconBack.setOnClickListener { onBackPressed() }
        bindGallery.btnAreaConfirm.setOnClickListener { onBackPressed() }
    }

    private fun getListAreaData(): List<AreaItemView> {
        val listData = mutableListOf<AreaItemView>()
        val listAreaData: List<AreaJsonData> = Gson().fromJson(FileUtil.readAsString("area.json", this), object : TypeToken<List<AreaJsonData>>(){}.type)
        for (areaData in listAreaData){
            val listAreaItemChild = mutableListOf<AreaItemView>()
            for (i in 0 until areaData.childAreaName.size){
                val areaItemChild = AreaItemView(name = areaData.childAreaName.get(i), checkedCount = 0,
                    foldedState =true, checkedState =false,
                    areaParent = false, areaAllChild = (i==0), child = null)
                listAreaItemChild.add(areaItemChild)
            }

            val areaItemView =  AreaItemView(name = areaData.areaName, checkedCount = 0,
                foldedState =true, checkedState =false,
                areaParent = true, areaAllChild = false, child = listAreaItemChild)
            listData.add(areaItemView)
        }
        return listData
    }
}