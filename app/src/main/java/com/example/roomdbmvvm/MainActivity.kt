package com.example.roomdbmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdbmvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var employerViewModel: EmployerViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = EmployerDatabase.getInstance(application).EmployerDAO
        val repository = EmployerRepository(dao)
        val factory = EmployerViewModelFactory(repository)
        employerViewModel = ViewModelProvider(this, factory).get(EmployerViewModel::class.java)
        binding.myViewModel = employerViewModel
        binding.lifecycleOwner = this

        employerViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.employerRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({ selectedItem: Employer -> listItemClicked(selectedItem) })
        binding.employerRecyclerView.adapter = adapter
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        employerViewModel.getSavedEmployers().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber: Employer) {
        employerViewModel.initUpdateAndDelete(subscriber)
    }
}