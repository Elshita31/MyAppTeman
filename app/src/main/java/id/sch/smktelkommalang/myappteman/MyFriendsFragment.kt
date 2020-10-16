package id.sch.smktelkommalang.myappteman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_friends_fragment.*

class MyFriendsFragment : Fragment() {

    companion object{
        fun newInstance(): MyFriendsFragment{
            return MyFriendsFragment()
        }
    }

    private var listTeman : List<MyFriend>? = null
    private var db : MyFriendDao.AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?{
        return inflater.inflate(R.layout.my_friends_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState:
    Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initLocalDB(){
        db = MyFriendDao.AppDatabase.getAppDataBase(activity!!)
        myFriendDao = db?.myfriendDao()
    }

    private fun initView(){
        fabAddFriend.setOnClickListener{(activity as
                MainActivity).tampilMyFriendsAddFragment()}
        ambilDataTeman()
    }

    private fun ambilDataTeman(){
        listTeman = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(this, Observer { r->
            listTeman = r

            when{
                listTeman?.size == 0 -> tampilToast("Belum ada data teman")

                else -> {
                    tampilTeman()
                }
            }
        })
    }

    private fun tampilToast(message: String){
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }
    private fun tampilTeman(){
        listMyFriends.layoutManager = LinearLayoutManager(activity)
        listMyFriends.adapter = MyFriendAdapter(activity!!, (listTeman as ArrayList<MyFriend>?)!!)
    }
    override fun onDestroy(){
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

//    lateinit var listTeman : MutableList<MyFriend>

//    private fun simulasiDataTeman(){
//        listTeman = ArrayList()
//
//        listTeman.add(MyFriend("Park Chanyeol", "Laki-laki", "parkchanyeol@gmail.com", "085765456564", "Bandung"))
//        listTeman.add(MyFriend("Byun Baekhyun", "Laki-laki", "bbkhyun@gmail.com", "08234456564", "Bandung"))
//    }
//
//    private fun tampilTeman(){
//        listMyFriends.layoutManager = LinearLayoutManager(activity)
//        listMyFriends.adapter = MyFriendAdapter(activity!!, listTeman as ArrayList<MyFriend>)
//    }
}
