package com.joyy.colors

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ColorsActivity : AppCompatActivity() {

    data class ColorItem(val name: String, val c: String)

    val colors = arrayListOf<ColorItem>(
        ColorItem("鸨色", "#f7acbc"),
        ColorItem("赤白橡", "#deab8a"),
        ColorItem("油色", "#817936"),
        ColorItem("绀桔梗", "#444693"),
        ColorItem("踯躅色", "#ef5b9c"),
        ColorItem("肌色", "#fedcbd"),
        ColorItem("伽罗色", "#7f7522"),
        ColorItem("花色", "#2b4490"),
        ColorItem("桜色", "#feeeed"),
        ColorItem("橙色", "#f47920"),
        ColorItem("青丹", "#80752c"),
        ColorItem("瑠璃色", "#2a5caa"),
        ColorItem("蔷薇色", "#f05b72"),
        ColorItem("灰茶", "#905a3d"),
        ColorItem("莺色", "#87843b"),
        ColorItem("琉璃绀", "#224b8f"),
        ColorItem("韩红", "#f15b6c"),
        ColorItem("茶色", "#8f4b2e"),
        ColorItem("利久色", "#726930"),
        ColorItem("绀色", "#003a6c"),
        ColorItem("珊瑚色", "#f8aba6"),
        ColorItem("桦茶色", "#87481f"),
        ColorItem("媚茶", "#454926"),
        ColorItem("青蓝", "#102b6a"),
        ColorItem("红梅色", "#f69c9f"),
        ColorItem("枯茶", "#5f3c23"),
        ColorItem("蓝海松茶", "#2e3a1f"),
        ColorItem("杜若色", "#426ab3"),
        ColorItem("桃色", "#f58f98"),
        ColorItem("焦茶", "#6b473c"),
        ColorItem("青钝", "#4d4f36"),
        ColorItem("胜色", "#46485f"),
        ColorItem("薄柿", "#ca8687"),
        ColorItem("柑子色", "#faa755"),
        ColorItem("抹茶色", "#b7ba6b"),
        ColorItem("群青色", "#4e72b8"),
        ColorItem("薄红梅", "#f391a9"),
        ColorItem("杏色", "#fab27b"),
        ColorItem("黄緑", "#b2d235"),
        ColorItem("铁绀", "#181d4b"),
        ColorItem("曙色", "#bd6758"),
        ColorItem("蜜柑色", "#f58220"),
        ColorItem("苔色", "#5c7a29"),
        ColorItem("蓝铁", "#1a2933"),
        ColorItem("红色", "#d71345"),
        ColorItem("褐色", "#843900"),
        ColorItem("若草色", "#bed742"),
        ColorItem("青褐", "#121a2a"),
        ColorItem("赤丹", "#d64f44"),
        ColorItem("路考茶", "#905d1d"),
        ColorItem("若緑", "#7fb80e"),
        ColorItem("褐返", "#0c212b"),
        ColorItem("红赤", "#d93a49"),
        ColorItem("饴色", "#8a5d19"),
        ColorItem("萌黄", "#a3cf62"),
        ColorItem("藤纳戸", "#6a6da9"),
        ColorItem("臙脂色", "#b3424a"),
        ColorItem("丁子色", "#8c531b"),
        ColorItem("苗色", "#769149"),
        ColorItem("桔梗色", "#585eaa"),
        ColorItem("真赭", "#c76968"),
        ColorItem("丁子茶", "#826858"),
        ColorItem("草色", "#6d8346"),
        ColorItem("绀蓝", "#494e8f"),
        ColorItem("今様色", "#bb505d"),
        ColorItem("黄栌", "#64492b"),
        ColorItem("柳色", "#78a355"),
        ColorItem("藤色", "#afb4db"),
        ColorItem("梅染", "#987165"),
        ColorItem("土器色", "#ae6642"),
        ColorItem("若草色", "#abc88b"),
        ColorItem("藤紫", "#9b95c9"),
        ColorItem("退红色", "#ac6767"),
        ColorItem("黄枯茶", "#56452d"),
        ColorItem("松叶色", "#74905d"),
        ColorItem("青紫", "#6950a1"),
        ColorItem("苏芳", "#973c3f"),
        ColorItem("狐色", "#96582a"),
        ColorItem("白緑", "#cde6c7"),
        ColorItem("菫色", "#6f60aa"),
        ColorItem("茜色", "#b22c46"),
        ColorItem("黄橡", "#705628"),
        ColorItem("薄緑", "#1d953f"),
        ColorItem("鸠羽色", "#867892"),
        ColorItem("红", "#a7324a"),
        ColorItem("银煤竹", "#4a3113"),
        ColorItem("千草色", "#77ac98"),
        ColorItem("薄色", "#918597"),
        ColorItem("银朱", "#aa363d"),
        ColorItem("涅色", "#412f1f"),
        ColorItem("青緑", "#007d65"),
        ColorItem("薄鼠", "#6f6d85"),
        ColorItem("赤", "#ed1941"),
        ColorItem("胡桃色", "#845538"),
        ColorItem("浅緑", "#84bf96"),
        ColorItem("鸠羽鼠", "#594c6d"),
        ColorItem("朱色", "#f26522"),
        ColorItem("香色", "#8e7437"),
        ColorItem("緑", "#45b97c"),
        ColorItem("菖蒲色", "#694d9f"),
        ColorItem("洗朱", "#d2553d"),
        ColorItem("国防色", "#69541b"),
        ColorItem("草色", "#225a1f"),
        ColorItem("江戸紫", "#6f599c"),
        ColorItem("红桦色", "#b4534b"),
        ColorItem("练色", "#d5c59f"),
        ColorItem("木贼色", "#367459"),
        ColorItem("紫", "#8552a1"),
        ColorItem("红绯", "#ef4136"),
        ColorItem("肉色", "#cd9a5b"),
        ColorItem("常盘色", "#007947"),
        ColorItem("灭紫", "#543044"),
        ColorItem("桦色", "#c63c26"),
        ColorItem("人色", "#cd9a5b"),
        ColorItem("緑青色", "#40835e"),
        ColorItem("葡萄鼠", "#63434f"),
        ColorItem("铅丹色", "#f3715c"),
        ColorItem("土色", "#b36d41"),
        ColorItem("千歳緑", "#2b6447"),
        ColorItem("古代紫", "#7d5886"),
        ColorItem("赭", "#a7573b"),
        ColorItem("小麦色", "#df9464"),
        ColorItem("深緑", "#005831"),
        ColorItem("暗红", "#401c44"),
        ColorItem("绯色", "#aa2116"),
        ColorItem("琥珀色", "#b76f40"),
        ColorItem("萌葱色", "#006c54"),
        ColorItem("葡萄", "#472d56"),
        ColorItem("丹", "#b64533"),
        ColorItem("木兰色", "#ad8b3d"),
        ColorItem("青白橡", "#375830"),
        ColorItem("茄子绀", "#45224a"),
        ColorItem("土", "#b54334"),
        ColorItem("栀子色", "#dea32c"),
        ColorItem("革色", "#274d3d"),
        ColorItem("紫绀", "#411445"),
        ColorItem("焦香", "#853f04"),
        ColorItem("朽叶", "#d1923f"),
        ColorItem("麹尘", "#375830"),
        ColorItem("浓色", "#4b2f3d"),
        ColorItem("真红", "#840228"),
        ColorItem("萱草色", "#c88400"),
        ColorItem("仙斎茶", "#27342b"),
        ColorItem("二蓝", "#402e4c"),
        ColorItem("绯", "#7a1723"),
        ColorItem("黄金", "#c37e00"),
        ColorItem("若竹色", "#65c294"),
        ColorItem("菖蒲色", "#c77eb5"),
        ColorItem("红海老茶", "#a03939"),
        ColorItem("金色", "#c37e00"),
        ColorItem("青磁色", "#73b9a2"),
        ColorItem("牡丹色", "#ea66a6"),
        ColorItem("浅苏芳", "#8a2e3b"),
        ColorItem("金茶", "#e0861a"),
        ColorItem("青竹色", "#72baa7"),
        ColorItem("赤紫", "#f173ac"),
        ColorItem("鸢色", "#8e453f"),
        ColorItem("卵色", "#ffce7b"),
        ColorItem("铁色", "#005344"),
        ColorItem("白", "#fffffb"),
        ColorItem("小豆色", "#8f4b4a"),
        ColorItem("山吹色", "#fcaf17"),
        ColorItem("锖鼠", "#122e29"),
        ColorItem("胡粉色", "#fffef9"),
        ColorItem("弁柄色", "#892f1b"),
        ColorItem("黄土色", "#ba8448"),
        ColorItem("铁御纳戸", "#293047"),
        ColorItem("生成色", "#f6f5ec"),
        ColorItem("栗梅", "#6b2c25"),
        ColorItem("朽叶色", "#896a45"),
        ColorItem("青緑", "#00ae9d"),
        ColorItem("灰白", "#d9d6c3"),
        ColorItem("海老茶", "#733a31"),
        ColorItem("空五倍子色", "#76624c"),
        ColorItem("锖浅葱", "#508a88"),
        ColorItem("石竹色", "#d1c7b7"),
        ColorItem("深绯", "#54211d"),
        ColorItem("莺茶", "#6d5826"),
        ColorItem("水浅葱", "#70a19f"),
        ColorItem("象牙色", "#f2eada"),
        ColorItem("赤铜色", "#78331e"),
        ColorItem("向日葵色", "#ffc20e"),
        ColorItem("新桥色", "#50b7c1"),
        ColorItem("乳白色", "#d3d7d4"),
        ColorItem("赤褐色", "#53261f"),
        ColorItem("郁金色", "#fdb933"),
        ColorItem("浅葱色", "#00a6ac"),
        ColorItem("薄钝", "#999d9c"),
        ColorItem("金赤", "#f15a22"),
        ColorItem("砂色", "#d3c6a6"),
        ColorItem("白群", "#78cdd1"),
        ColorItem("银鼠", "#a1a3a6"),
        ColorItem("赤茶", "#b4533c"),
        ColorItem("芥子色", "#c7a252"),
        ColorItem("御纳戸色", "#008792"),
        ColorItem("茶鼠", "#9d9087"),
        ColorItem("赤锖色", "#84331f"),
        ColorItem("淡黄", "#dec674"),
        ColorItem("瓮覗", "#94d6da"),
        ColorItem("鼠色", "#8a8c8e"),
        ColorItem("黄丹", "#f47a55"),
        ColorItem("亜麻色", "#b69968"),
        ColorItem("水色", "#afdfe4"),
        ColorItem("薄墨色", "#74787c"),
        ColorItem("赤橙", "#f15a22"),
        ColorItem("枯色", "#c1a173"),
        ColorItem("蓝鼠", "#5e7c85"),
        ColorItem("利休鼠", "#7c8577"),
        ColorItem("柿色", "#f3704b"),
        ColorItem("鸟子色", "#dbce8f"),
        ColorItem("秘色", "#76becc"),
        ColorItem("铅色", "#72777b"),
        ColorItem("肉桂色", "#da765b"),
        ColorItem("黄色", "#ffd400"),
        ColorItem("空色", "#90d7ec"),
        ColorItem("灰色", "#77787b"),
        ColorItem("桦色", "#c85d44"),
        ColorItem("蒲公英色", "#ffd400"),
        ColorItem("青", "#009ad6"),
        ColorItem("钝色", "#4f5555"),
        ColorItem("炼瓦色", "#ae5039"),
        ColorItem("中黄", "#ffe600"),
        ColorItem("蓝色", "#145b7d"),
        ColorItem("煤竹色", "#6c4c49"),
        ColorItem("锖色", "#6a3427"),
        ColorItem("刈安色", "#f0dc70"),
        ColorItem("浓蓝", "#11264f"),
        ColorItem("黒茶", "#563624"),
        ColorItem("桧皮色", "#8f4b38"),
        ColorItem("黄檗色", "#fcf16e"),
        ColorItem("勿忘草色", "#7bbfea"),
        ColorItem("黒橡", "#3e4145"),
        ColorItem("栗色", "#8e3e1f"),
        ColorItem("緑黄色", "#decb00"),
        ColorItem("露草色", "#33a3dc"),
        ColorItem("浓鼠", "#3c3645"),
        ColorItem("黄赤", "#f36c21"),
        ColorItem("鶸色", "#cbc547"),
        ColorItem("缥色", "#228fbd"),
        ColorItem("墨", "#464547"),
        ColorItem("代赭", "#b4532a"),
        ColorItem("海松色", "#6e6b41"),
        ColorItem("浅缥", "#2468a2"),
        ColorItem("黒", "#130c0e"),
        ColorItem("骆驼色", "#b7704f"),
        ColorItem("鶸茶", "#596032"),
        ColorItem("薄缥", "#2570a1"),
        ColorItem("黒铁", "#281f1d"),
        ColorItem("黄茶", "#de773f"),
        ColorItem("山鸠色", "#525f42"),
        ColorItem("薄花色", "#2585a6"),
        ColorItem("蝋色", "#2f271d"),
        ColorItem("洗柿", "#c99979"),
        ColorItem("生壁色", "#5f5d46"),
        ColorItem("绀青", "#1b315e"),
        ColorItem("紫黒", "#1d1626")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colors)
        findViewById<RecyclerView>(R.id.mRecyclerView).adapter = Adapter()
    }


    inner class Adapter : RecyclerView.Adapter<VH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val inflate = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            return VH(inflate)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: VH, position: Int) {
            val tv = holder.itemView.findViewById<TextView>(android.R.id.text1)
            val colorItem: ColorItem = colors[position]
            tv.text = "${colorItem.name}\n${colorItem.c}"
            tv.setBackgroundColor(Color.parseColor(colorItem.c.trim()))
            holder.itemView.setOnClickListener {
                copyContentToClipboard(colorItem.c.trim(), holder.itemView.context)
                Toast.makeText(
                    holder.itemView.context,
                    "${colorItem.c.trim()} 复制到剪贴板",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        override fun getItemCount(): Int {
            return colors.size
        }

    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        fun copyContentToClipboard(content: String, context: Context) {
            // 获取剪贴板管理器
            val cm: ClipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 创建普通字符型ClipData
            val clipData = ClipData.newPlainText("Label", content)
            // 将ClipData内容放到系统剪贴板里
            cm.setPrimaryClip(clipData)
        }
    }

}