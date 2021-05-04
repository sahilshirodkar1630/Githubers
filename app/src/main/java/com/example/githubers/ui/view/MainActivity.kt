package com.example.githubers.ui.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubers.R
import com.example.githubers.ui.adapter.UserAdapter
import com.example.githubers.data.api.Client
import com.example.githubers.ui.viewmodel.GithubViewModel
import com.example.mvvm.data.models.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

     private val vm by lazy {
       ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(GithubViewModel::class.java)
    }

    val list = arrayListOf<User>()
    val adapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        btn.setOnClickListener {
            val s = edtSearch.text.toString();
            searchUsers(s);
        }

        vm.fetchUsers()
        vm.users.observe(this, Observer {
            if(!it.isNullOrEmpty()){
                list.addAll(it)
                adapter.swapData(it)
                adapter.notifyDataSetChanged()
            }
        })

        adapter.onItemClick = {
            val builder = CustomTabsIntent.Builder();
            val customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse(it))
        }

    }

    private fun searchUsers(query: String){
        vm.searchUsers(query).observe(this, Observer {
            if(!it.isNullOrEmpty()){
                list.clear();
                list.addAll(it)
                Log.d("TAG", "length ${it.size}");
                adapter.swapData(list);
                adapter.notifyDataSetChanged()
            }else{
                list.clear()
                adapter.swapData(list);
                adapter.notifyDataSetChanged()
            }
        })
    }

}

