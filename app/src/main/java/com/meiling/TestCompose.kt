package com.meiling

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Composable
fun DefaultPreview() {
        Greeting("Androi")
}

@Composable
fun Test(){
    val list = (1..20).map { "Item $it" }

    Box(modifier = Modifier
        .fillMaxWidth()
        .requiredHeightIn(0.dp, if (list.size <= 3) 48.dp else 96.dp)
        .background(Color.White)) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(list) { index, item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .height(48.dp)
                        .padding(12.dp)
                        .background(Color.LightGray)
                        .fillMaxWidth(),
                )
            }
        }
    }

}


@Composable
fun ScrollableItemList() {
    val list = (1..3).map { "Item $it" }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeightIn(0.dp, if (list.size <= 3) 48.dp else 96.dp)
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(list) { index, item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .height(48.dp)
                        .padding(12.dp)
                        .background(Color.LightGray)
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScrollableItemListPreview() {
    ScrollableItemList()
}
