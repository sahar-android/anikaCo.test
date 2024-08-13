package com.application.aniktest.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.application.aniktest.ResturantViewModel
import com.application.aniktest.data.entities.CustomerFoodCrossRef
import com.application.aniktest.data.entities.CustomerWithFoods
import com.application.aniktest.data.entities.Food


private const val TAG = "CustomerInfo4544"

@Composable
fun CustomerInfo(
    navController: NavHostController,
    resturantViewModel: ResturantViewModel,
    customerName: String?,
    customerId: Int?
) {

    //customerId is passed to this fun, by call of this id from middle table(CustomerwithFood)
    //get foodids already are there.
    //otherwise i show all food with a checkbox front of it, if evey one of them is already favourite
    //food,checkbox will be enable
    //by click on evey checkbox of foods , this food add to favourit food of certain customer that
    //we got it'id before.

    Column(Modifier.fillMaxSize().padding(horizontal = 10.dp)) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Favourite Foods: $customerName", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .background(Color.Black)
        )

        if (customerId != null){
            resturantViewModel.getFoodsOfCustomer()

            val foodList: MutableList<Int> = mutableListOf()

            when(val result=resturantViewModel.foodsOfCustomer.collectAsState().value){
                is RoomState.SUCCESSGETCUSFOOD -> {
                   
                    result.foods.forEach {
                        if(it.customerId==customerId){
                            foodList.add(it.foodId)
                        }


                    }
                }

                else -> {}
            }
            ShowListFoodForSelect(resturantViewModel, customerId,foodList)
        }

        else
            Toast.makeText(LocalContext.current, "id_customer is not available", Toast.LENGTH_SHORT)
                .show()


    }
}



@Composable
fun ShowListFoodForSelect(    //create for selecting as favourit foods by enable checkbox
    resturantViewModel: ResturantViewModel,
    customerId: Int,
    foodIdList:List<Int>
) {


    val listFood = GetAllFoods(resturantViewModel = resturantViewModel)
    val scrollState = rememberLazyListState()


    LazyColumn(
        Modifier
            .padding(10.dp),
        state = scrollState
    ) {
        items(listFood) {
            var checked by remember { mutableStateOf(false) }

            foodIdList.forEach {favFoodId->
                if(it.foodId==favFoodId)  checked=true
            }



            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = it.foodName,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Checkbox(checked = checked, onCheckedChange = {
                    checked = it
                },
                    modifier = Modifier.align(Alignment.CenterEnd).size(46.dp))

                if (checked)
                   AddFoodToFav(customerId, it.foodId,resturantViewModel)
            }
        }
    }

}

@Composable
fun AddFoodToFav(customerId: Int, foodId: Int, resturantViewModel: ResturantViewModel) {
      resturantViewModel.insertCustomerWithFood(CustomerFoodCrossRef(customerId,foodId))
      resturantViewModel.getFoodsOfCustomer()
}
