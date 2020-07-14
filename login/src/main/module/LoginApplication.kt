import com.example.llc.base.RouterRuleCreator
import com.example.llc.kotlin.basic.BaseModuleApplication
import com.lzh.nonview.router.RouterConfiguration
import com.lzh.nonview.router.anno.RouteConfig

/**
 * LoginApplication
 * @author liulongchao
 * @since 2018/3/23
 */
@RouteConfig(baseUrl = "module://", pack = "com.example.llc.base")
class LoginApplication: BaseModuleApplication() {

    override fun onCreate() {
        super.onCreate()
        RouterConfiguration.get().addRouteCreator(RouterRuleCreator())
    }

}
