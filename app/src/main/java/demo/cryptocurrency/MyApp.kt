package demo.cryptocurrency

import android.app.Application
import com.idescout.sql.SqlScoutServer

/**
 * @author  Qing Guo
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SqlScoutServer.create(this, "demo.cryptocurrency")
    }

}