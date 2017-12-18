package com.example.llcgs.android_kotlin.material.main.fragment.home.model

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialModel
import com.example.llcgs.android_kotlin.material.main.fragment.home.bean.BroadListContent
import com.example.llcgs.android_kotlin.utils.BaseUtil
import com.example.llcgs.android_kotlin.utils.TransitionUtils
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.material.main.fragment.home.model.HomeBroadcastListModel
 * @author liulongchao
 * @since 2017/12/13
 */
class HomeBroadcastListModel : BaseMaterialModel {

    private var imageUriMap = mapOf(
            "Kobe" to "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1833372117,3098616043&fm=27&gp=0.jpg",
            "James" to "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511431498137&di=a424959097ddd8b873830dc2cabfe835&imgtype=0&src=http%3A%2F%2Fp0.ifengimg.com%2Fpmop%2F2017%2F1016%2F45BA17371B729FDA9CEDE03DD5FC97E54D0198F6_size21_w450_h335.jpeg",
            "Wade" to "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511496848608&di=aa9de4330e49cc97079567b2d882b3cf&imgtype=0&src=http%3A%2F%2Fy3.ifengimg.com%2Fa%2F2015_43%2F32a810780d248a2.jpg",
            "Bosh" to "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3339659324,2490265514&fm=27&gp=0.jpg",
            "Anthony" to "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=716717509,101173529&fm=27&gp=0.jpg",
            "Paul" to "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511496791721&di=1a3c9c8386774f6eb1f8f436c721b2b2&imgtype=0&src=http%3A%2F%2Fwww.114nba.com%2Fdata%2Fupload%2F20170817%2F5994eda436990.jpg",
            "Answer" to "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511496588314&di=e3eee129bf6faa08b871c670a75a6bc3&imgtype=0&src=http%3A%2F%2Fimgstore.cdn.sogou.com%2Fapp%2Fa%2F100540002%2F721562.jpg",
            "Durant" to "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3485282968,3316615547&fm=27&gp=0.jpg",
            "Curry" to "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3399755636,2484826123&fm=27&gp=0.jpg",
            "Harden" to "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3953725604,1944972813&fm=27&gp=0.jpg",
            "Jordon" to "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1740189470,641310618&fm=11&gp=0.jpg",
            "Nash" to "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=361419781,2013243477&fm=27&gp=0.jpg",
            "Allen" to "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2739361932,3703585115&fm=27&gp=0.jpg"

    )

    private var desMap = mapOf(
            "Kobe" to "科比·布莱恩特（Kobe Bryant），1978年8月23日出生于美国宾夕法尼亚州费城，前美国职业篮球运动员，司职得分后卫/小前锋（锋卫摇摆人），绰号“黑曼巴”/“小飞侠”，[1]  整个NBA生涯（1996年-2016年）全部效力于NBA洛杉矶湖人队",
            "James" to "勒布朗·詹姆斯（LeBron James），1984年12月30日出生在美国俄亥俄州阿克伦，美国职业篮球运动员，司职小前锋，绰号“小皇帝”，效力于NBA克利夫兰骑士队",
            "Wade" to "德怀恩·韦德（Dwyane Wade），1982年1月17日出生于美国伊利诺伊州芝加哥，美国职业篮球运动员，司职得分后卫，效力于NBA克里夫兰骑士队，绰号“闪电侠”（The Flash）",
            "Bosh" to "克里斯·波什（Chris Bosh），1984年3月24日出生于美国得克萨斯州达拉斯，前美国职业篮球运动员，司职大前锋，绰号“龙王”",
            "Anthony" to "卡梅隆·安东尼（Carmelo Anthony），1984年5月29日出生于美国纽约市布鲁克林区（Brooklyn， New York），美国职业篮球运动员，司职小前锋，现效力于NBA俄克拉荷马城雷霆队",
            "Paul" to "克里斯·保罗（Chris Paul），1985年5月6日出生于美国北卡罗来纳州温斯顿-塞勒姆（Winston-Salem，North Carolina），美国职业篮球运动员，司职控球后卫，效力于NBA休斯顿火箭队",
            "Answer" to "阿伦·艾弗森（Allen Iverson），1975年6月7日出生于美国弗吉尼亚州汉普顿，前知名美国职业篮球运动员，司职后卫（双能卫），[1]  绰号“答案”（The Answer）/AI[1]  ，曾任美国男篮梦之队队长[1]  ",
            "Durant" to "凯文·杜兰特（Kevin Durant），1988年9月29日出生于美国华盛顿哥伦比亚特区（Washington,District of Columbia），美国职业篮球运动员，司职小前锋，效力于NBA金州勇士队",
            "Curry" to "斯蒂芬·库里（Stephen Curry），1988年3月14日出生于美国俄亥俄州阿克伦（Akron,Ohio），美国职业篮球运动员，司职控球后卫，效力于NBA金州勇士队",
            "Harden" to "詹姆斯·哈登（James Harden），1989年8月26日出生于美国加利福尼亚州洛杉矶（ Los Angeles, California），美国职业篮球运动员，司职后卫，效力于NBA休斯顿火箭队",
            "Jordon" to "迈克尔·乔丹（Michael Jordan），1963年2月17日生于美国纽约布鲁克林，前美国职业篮球运动员，司职得分后卫，绰号“飞人”（Air Jordan）",
            "Nash" to "史蒂夫·纳什（Steve Nash），1974年2月7日出生于南非约翰内斯堡，前加拿大职业篮球运动员，司职控球后卫，绰号“风之子”",
            "Allen" to "雷·阿伦（Ray Allen），1975年7月20日出生于美国加利福尼亚州美熹德，前美国职业篮球运动员[1]  ，司职得分后卫，绰号“君子雷”/“君子剑”[2]  ，有着出色的三分球投射能力"
    )

    private var contentMap = mapOf(
            "Kobe" to "https://baike.baidu.com/item/%E7%A7%91%E6%AF%94%C2%B7%E5%B8%83%E8%8E%B1%E6%81%A9%E7%89%B9/318773?fr=aladdin&fromid=133066&fromtitle=%E7%A7%91%E6%AF%94",
            "James" to "https://baike.baidu.com/item/%E5%8B%92%E5%B8%83%E6%9C%97%C2%B7%E8%A9%B9%E5%A7%86%E6%96%AF/1989503?fr=aladdin&fromid=30516&fromtitle=James",
            "Wade" to "https://baike.baidu.com/item/%E5%BE%B7%E6%80%80%E6%81%A9%C2%B7%E9%9F%A6%E5%BE%B7/5457042?fr=aladdin&fromid=510718&fromtitle=WADE",
            "Bosh" to "https://baike.baidu.com/item/%E5%85%8B%E9%87%8C%E6%96%AF%C2%B7%E6%B3%A2%E4%BB%80/2274415?fr=aladdin",
            "Anthony" to "https://baike.baidu.com/item/%E5%8D%A1%E6%A2%85%E9%9A%86%C2%B7%E5%AE%89%E4%B8%9C%E5%B0%BC/1903398",
            "Paul" to "https://baike.baidu.com/item/%E5%85%8B%E9%87%8C%E6%96%AF%C2%B7%E4%BF%9D%E7%BD%97/967371?fr=aladdin",
            "Answer" to "https://baike.baidu.com/item/%E9%98%BF%E4%BC%A6%C2%B7%E8%89%BE%E5%BC%97%E6%A3%AE/386640?fr=aladdin",
            "Durant" to "https://baike.baidu.com/item/%E5%87%AF%E6%96%87%C2%B7%E6%9D%9C%E5%85%B0%E7%89%B9/3726277?fr=aladdin",
            "Curry" to "https://baike.baidu.com/item/%E6%96%AF%E8%92%82%E8%8A%AC%C2%B7%E5%BA%93%E9%87%8C/902812?fr=aladdin",
            "Harden" to "https://baike.baidu.com/item/%E8%A9%B9%E5%A7%86%E6%96%AF%C2%B7%E5%93%88%E7%99%BB/745489?fr=aladdin",
            "Jordon" to "https://baike.baidu.com/item/%E8%BF%88%E5%85%8B%E5%B0%94%C2%B7%E4%B9%94%E4%B8%B9/21768?fr=aladdin",
            "Nash" to "https://baike.baidu.com/item/%E5%8F%B2%E8%92%82%E5%A4%AB%C2%B7%E7%BA%B3%E4%BB%80/812358?fr=aladdin",
            "Allen" to "https://baike.baidu.com/item/%E9%9B%B7%C2%B7%E9%98%BF%E4%BC%A6/394332?fr=aladdin"
    )

    fun getData(): Observable<List<BroadListContent>> {
        val array = BaseUtil.context().resources.getStringArray(R.array.databinding_nba)
        val list = ArrayList<BroadListContent>()
        var broadListContent: BroadListContent
        array.forEach {
            broadListContent = BroadListContent()
            broadListContent.avatar = imageUriMap[it]
            broadListContent.name = it
            broadListContent.time = "12月11日 17:44 推荐日记"
            broadListContent.content = "NBA（National Basketball Association）是美国男子职业篮球联赛的简称，于1946年6月6日在纽约成立，由北美三十支队伍组成的男子职业篮球联盟，汇集了世界上最顶级的球员，是美国四大职业体育联盟之一"
            broadListContent.attachmentImage = imageUriMap[it]
            broadListContent.attachmentTitle = "About $it Star"
            broadListContent.attachmentDes = desMap[it]
            broadListContent.desUrl = contentMap[it]
            list.add(broadListContent)
        }
        return Observable.just(list)
    }

    fun addTransitionAnimation(activity: Activity, view: View): Observable<Bundle> {
        val bundle = TransitionUtils.makeActivityOptionsBundle(activity, view)
        return Observable.just(bundle)
    }
}