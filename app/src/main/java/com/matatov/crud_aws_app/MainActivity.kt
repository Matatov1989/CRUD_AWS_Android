package com.matatov.crud_aws_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.matatov.crud_aws_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapterList: RecyclerAdapter
    private lateinit var userList: ArrayList<UserData>

    val MY_LOG = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        //initialization Amplify AWS
        iniAmplifyAWS()

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        //click to FAB for open dialog to input a new user
        binding.fab.setOnClickListener {
            openDialogInput()
        }

        //get all users from data and set to recycler view
        DynamoHelper.read { list ->
            runOnUiThread(Runnable {

                userList = ArrayList<UserData>()
                userList.addAll(list)

                adapterList = RecyclerAdapter(list)
                recyclerview.adapter = adapterList

                //listener for edit the user
                adapterList.setOnItemClickListenerEdit(object :
                    RecyclerAdapter.onItemClickListenerEdit {
                    override fun onItemClick(position: Int) {
                        openDialogUpdate(list[position], position)
                    }
                })

                //listener for remove the user
                adapterList.setOnItemClickListenerRemove(object :
                    RecyclerAdapter.onItemClickListenerRemove {
                    override fun onItemClick(position: Int) {
                        removeUser(list[position], position)
                    }
                })
            })
        }
    }

    //initialization Amplify AWS
    private fun iniAmplifyAWS() {
        try {
            //Amplify.addPlugin(AWSApiPlugin()) // UNCOMMENT this line once backend is deployed
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.configure(applicationContext)
            Log.i(MY_LOG, "Initialized Amplify")
        } catch (e: AmplifyException) {
            Log.e(MY_LOG, "Could not initialize Amplify", e)
        }
    }

    //dialog to input data of user and insert its to DynamoDB and recycler view
    private fun openDialogInput() {
        val view = layoutInflater.inflate(R.layout.dialog_input, null)
        val editName = view.findViewById(R.id.editTextName) as EditText
        val editAge = view.findViewById(R.id.editTextAge) as EditText
        val editCountry = view.findViewById(R.id.editTextCountry) as EditText

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.titleDialogCreateUser)
        builder.setView(view)
        builder.setPositiveButton(R.string.btnYes) { dialog, which ->
            val newUser = UserData(
                editName.text.toString(),
                editAge.text.toString().toInt(),
                editCountry.text.toString()
            )

            //call method create from object DynamoHelper for insert a new user
            DynamoHelper.create(newUser) { isSuccess ->
                runOnUiThread(Runnable {
                    if (isSuccess) {
                        Toast.makeText(
                            applicationContext,
                            R.string.toastSuccess,
                            Toast.LENGTH_LONG
                        ).show()

                        //Log.i(MY_LOG, "added new user = ${newUser.name}")

                        //add a new user to array list
                        userList.add(newUser)
                        //update recyclerview
                        adapterList.insertItem(userList, userList.size - 1)
                    } else
                        if (isSuccess)
                            Toast.makeText(
                                applicationContext,
                                R.string.toastError,
                                Toast.LENGTH_LONG
                            ).show()
                })
            }
            dialog.dismiss()
        }
        builder.setNegativeButton(R.string.btnNo) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    //dialog to update data of user and update its to DynamoDB and recycler view
    private fun openDialogUpdate(user: UserData, position: Int) {
        val view = layoutInflater.inflate(R.layout.dialog_input, null)
        val editName = view.findViewById(R.id.editTextName) as EditText
        val editAge = view.findViewById(R.id.editTextAge) as EditText
        val editCountry = view.findViewById(R.id.editTextCountry) as EditText

        editName.setText(user.name)
        editName.isEnabled = false

        editAge.setText(user.age.toString())
        editCountry.setText(user.country)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.titleDialogUpdateUser)
        builder.setView(view)
        builder.setPositiveButton(R.string.btnYes) { dialog, which ->

            //call method update from object DynamoHelper for update the user
            DynamoHelper.update(
                UserData(
                    user.uId,
                    user.name,
                    editAge.text.toString().toInt(),
                    editCountry.text.toString()
                )
            ) { isSuccess ->
                runOnUiThread(Runnable {
                    if (isSuccess) {
                        runOnUiThread(Runnable {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.toastUpdate, user.name),
                                Toast.LENGTH_LONG
                            ).show()

                            //update the user in array list view position
                            userList[position].age = editAge.text.toString().toInt()
                            userList[position].country = editCountry.text.toString()
                            //update recycler
                            adapterList.updateItem(userList, position)
                        })
                    } else
                        Toast.makeText(
                            applicationContext,
                            R.string.toastError,
                            Toast.LENGTH_LONG
                        ).show()
                })
            }
            dialog.dismiss()
        }
        builder.setNegativeButton(R.string.btnNo) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    //method to remove the user from DynamoDB
    private fun removeUser(user: UserData, position: Int) {
        //call method delete from object DynamoHelper for removing the user
        DynamoHelper.delete(user) { isSuccess ->
            runOnUiThread(Runnable {
                if (isSuccess) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.toastRemove, user.name),
                        Toast.LENGTH_LONG
                    ).show()

                    //update array list
                    userList.removeAt(position)
                    //update recycler view
                    adapterList.removeItem(userList, position)
                } else
                    Toast.makeText(
                        applicationContext,
                        R.string.toastError,
                        Toast.LENGTH_LONG
                    ).show()
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
