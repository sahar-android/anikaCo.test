package com.application.aniktest.presentation

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.application.aniktest.R
import com.application.aniktest.ResturantViewModel
import com.application.aniktest.data.entities.Customer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val TAG = "AddCustomers3253454"
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddCustomers(navHostController: NavHostController, resturantViewModel: ResturantViewModel) {

    Column(Modifier.fillMaxSize()) {
        
        Spacer(modifier = Modifier.height(30.dp))
        
        GetCustomerNameField(resturantViewModel)  //get and add customer to DB
        
        
        Spacer(modifier = Modifier.height(30.dp))
        
        Text(text = "Customers List", fontSize = 18.sp, textDecoration = TextDecoration.Underline)
        
        Spacer(modifier = Modifier.height(5.dp))
        
        ShowCustomerList(resturantViewModel,navHostController)

    }
}


@Composable
fun GetAllCustomers(resturantViewModel: ResturantViewModel):List<Customer>{

    val result=resturantViewModel.customers.collectAsState().value
    resturantViewModel.getCustomers()

    return when(result){
        is RoomState.SUCCESSGETCus ->{
            result.customers

        }
        is RoomState.LOADING ->{
            //for show progressbar
            return emptyList()
        }
        is RoomState.ERROR ->{
            //for show error
            return emptyList()
        }

        else -> {
            Log.i(TAG, "ShowCustomerList: error")
            emptyList()
        }
    }
     
}

@Composable
fun ShowCustomerList(resturantViewModel: ResturantViewModel, navHostController: NavHostController) {
    val scrollState = rememberLazyListState()
    val list= GetAllCustomers(resturantViewModel = resturantViewModel)
    LazyColumn(
        Modifier
            .padding(10.dp)
            .background(Color.LightGray).alpha(0.5f),
        state = scrollState){
        items(list){
            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 5.dp)) {
                Text(text = "${it.customerId}.${it.customerName}", fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterStart))
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd).size(46.dp)
                        .clickable {
                           navHostController.navigate("customer_info/${it.customerName}/${it.customerId}")
                        }
                )
                Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Black).align(
                    Alignment.BottomCenter))

            }
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GetCustomerNameField(resturantViewModel: ResturantViewModel) {
    var customerName by rememberSaveable {
        mutableStateOf("default name")
    }


    var addClick by remember{ mutableStateOf(false) } //click flag of + icon press
    if(addClick){
        AddCustomerToDB(customerName,resturantViewModel){
            if(it) customerName=""   //for clear input text when previous is done
        }

    }

    Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
        TextField(
            modifier = Modifier
                .onFocusChanged {
                    customerName = ""
                    //should set toast or another way  determine based on the opinion of the employer
                    /*if (!it.hasFocus && customerName.isNotEmpty())
                        customerName = "should not be null"*/

                }
                .wrapContentHeight()
                .weight(2f),
            value = customerName,
            onValueChange = {
                    customerName = it

            },
            label = { Text(stringResource(id = R.string.customer_name)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(id = R.string.customer)
                )
            }
        )
        Icon(
            modifier = Modifier
                .weight(1f)
                .wrapContentSize()
                .size(54.dp)
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            CoroutineScope(Dispatchers.IO).launch{
                                addClick = true
                            }

                        }

                        MotionEvent.ACTION_UP -> {
                            CoroutineScope(Dispatchers.IO).launch{
                                addClick = false
                            }

                        }

                        else -> false
                    }
                    true
                },
            imageVector = Icons.Filled.Add,
            contentDescription = "null"
        )
    }
}

fun AddCustomerToDB(customerName: String, resturantViewModel: ResturantViewModel,successAdding:(flg:Boolean)->Unit) {
   if(customerName.isNotEmpty())
    CoroutineScope(Dispatchers.IO).launch {
        resturantViewModel.insertCustomer(Customer(0,customerName))
        resturantViewModel.getCustomers()
        successAdding(true) //for hoisting of  clicking state of + icon
    }

}

