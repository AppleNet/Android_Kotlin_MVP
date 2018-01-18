package com.example.llcgs.android_kotlin.material.douya.detail.presenter.impl

import com.example.llcgs.android_kotlin.material.douya.detail.bean.Comment
import com.example.llcgs.android_kotlin.material.douya.detail.model.DetailModel
import com.example.llcgs.android_kotlin.material.douya.detail.presenter.IDetailPresenter
import com.example.llcgs.android_kotlin.material.douya.detail.view.DetailView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.material.detail.presenter.impl.DetailPresenter
 * @author liulongchao
 * @since 2017/12/14
 */
class DetailPresenter(private val view: com.example.llcgs.android_kotlin.material.douya.detail.view.DetailView) : com.example.llcgs.android_kotlin.material.douya.detail.presenter.IDetailPresenter {

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

    private val model = com.example.llcgs.android_kotlin.material.douya.detail.model.DetailModel()

    override fun loadComments() {
        model.loadComments()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    view.addDisposable(it)
                }
                .flatMap{ t ->
                    val list = ArrayList<com.example.llcgs.android_kotlin.material.douya.detail.bean.Comment>()
                    var comment: com.example.llcgs.android_kotlin.material.douya.detail.bean.Comment
                    t.forEach {
                        comment = com.example.llcgs.android_kotlin.material.douya.detail.bean.Comment().apply {
                            name = it
                            imageUrl = imageUriMap[it]
                            content = "NBA All Star"
                            timeDate = "2017-12-22"
                        }
                        list.add(comment)
                    }
                    Observable.just(list)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate {
                    //
                    view.dismissLoadingDialog()
                }
                .subscribe {
                    view.onGetComments(it)
                }
    }
}