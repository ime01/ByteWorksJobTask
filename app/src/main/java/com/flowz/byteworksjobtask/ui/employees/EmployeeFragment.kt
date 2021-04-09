package com.flowz.byteworksjobtask.ui.employees

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flowz.byteworksjobtask.Model.Employee
import com.flowz.byteworksjobtask.R
import com.flowz.byteworksjobtask.util.onQueryTextChanged
import com.flowz.introtooralanguage.adapters.EmployeeAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_employee.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EmployeeFragment : Fragment(), EmployeeAdapter.RowClickListener {

    private lateinit var employeeAdapter : EmployeeAdapter
    private var sList  = ArrayList<Employee>()


    private val employeeViewModel by viewModels<EmployeeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val navController : NavController = Navigation.findNavController(view)

        employee_recycler.layoutManager = LinearLayoutManager(this.context)

         employeeAdapter = EmployeeAdapter(this)

        employeeViewModel.employeesFromdb.observe(viewLifecycleOwner, Observer {

            employeeAdapter.submitList(it)
            employee_recycler.adapter = employeeAdapter
        })

        swipeToDeleteEmployee()

        fab.setOnClickListener {
            navController.navigate(R.id.action_employeeFragment_to_addNewEmployeeFragment)
        }
    }

    private fun swipeToDeleteEmployee() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val employee = employeeAdapter.currentList[viewHolder.adapterPosition]
                employeeViewModel.deleteEmployee(employee)
                Snackbar.make(employee_recycler, "Employee ${employee.firstName} Deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO"){
                        employeeViewModel.insertEmployee(employee)
                    }.show()
            }

        }).attachToRecyclerView(employee_recycler)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_employee, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            searchDatabase(it)
            Log.d(TAG, "Search Successful")
        }

    }


    private fun searchDatabase(query:String){
        val searchQuery = "%$query%"
        employeeViewModel.searchEmployee(searchQuery).observe(viewLifecycleOwner, Observer {list->
            list.let {
                employeeAdapter.submitList(it)
            }

        })
    }

    companion object {
        const val TAG = "Employee Fragment"
    }

    override fun onItemClickListener(employee: Employee) {

        val action = EmployeeFragmentDirections.actionEmployeeFragmentToHiltEmployeeDetailsFragment()
        action.employee = employee
        Navigation.findNavController(requireView()).navigate(action)

    }
}