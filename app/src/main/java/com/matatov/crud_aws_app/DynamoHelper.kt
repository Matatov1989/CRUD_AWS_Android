package com.matatov.crud_aws_app

import android.util.Log
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.generated.model.MyFirstTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object DynamoHelper {

    const val MY_LOG = "myLogs"

    //create a new user and added to MyFirstTest table of DynamoDB AWS
    //and return boolean result to MainActivity class
    fun create(newUser: UserData, onComplete: (isSuccess: Boolean) -> Unit) {
        val item: MyFirstTest = MyFirstTest.builder()
            .name(newUser.name)
            .age(newUser.age)
            .country(newUser.country)
            .build()

        //insert to DynamoDB
        Amplify.DataStore.save(
            item,
            { success ->
                onComplete(true)
            },
            { error ->
                onComplete(false)
            }
        )
    }

    //get all users from MyFirstTest table AWS DynamoDB
    //return array list with users to MainActivity class for output on recycler view
    fun read(onComplete: (userList: ArrayList<UserData>) -> Unit) {

        val userList = arrayListOf<UserData>()

        //query from DynamoDB
        Amplify.DataStore.query(
            MyFirstTest::class.java,
            { items ->
                while (items.hasNext()) {
                    val item = items.next()
                    //fill array list
                    userList.add(UserData(item.id, item.name, item.age, item.country))
                }
                onComplete(userList)
            },
            { failure -> Log.e(MY_LOG, "Could not query DataStore", failure) }
        )
    }

    //update user's age and country in MyFirstTest table DynamoDB AWS
    //1. get object from table via user id
    //2. change data in object
    //3. save the new object
    //return boolean result to MainActivity class
    fun update(user: UserData, onComplete: (isSuccess: Boolean) -> Unit) {
        //query get object from table via user id
        Amplify.DataStore.query(
            MyFirstTest::class.java,
            Where.id(user.uId),
            { items ->
                if (items.hasNext()) {
                    val item = items.next()

                    //change data in object
                    val updateItem = item.copyOfBuilder()
                        .age(user.age)
                        .country(user.country)
                        .build()

                    //save (update) the new object
                    Amplify.DataStore.save(
                        updateItem,
                        {
                            onComplete(true)
                        },
                        {
                            onComplete(false)
                        }
                    )
                }
            },
            { failure ->
                Log.e("myLogs", "Could not query DataStore", failure)
                onComplete(false)
            }
        )
    }

    //remove user from MyFirstTest table DynamoDB via user id
    //return boolean result to MainActivity class
    fun delete(user: UserData, onComplete: (isSuccess: Boolean) -> Unit) {
        //get item via id object
        val deleteItem = MyFirstTest.justId(user.uId)

        //remove from DynamoDB
        Amplify.DataStore.delete(deleteItem,
            { deleted ->
                onComplete(true)
            },
            { failure ->
                onComplete(false)
            }
        )
    }
}
