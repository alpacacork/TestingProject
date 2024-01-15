package com.brentcodes.testingproject

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(
    viewModel: MyVM,
    modifier: Modifier = Modifier
) {

    val filtersList = viewModel.filteredList.collectAsState()

    LazyColumn(modifier = modifier) {

        item {
            SearchBar(onSearch = { Log.d("Searching", "Searching now") }, viewModel = viewModel)
        }

        item {
            FiltersBar(viewModel = viewModel, filtersList = filtersList)
        }
        item {
            Button(onClick = { Log.d("Status", "${filtersList.value}") }) {
                Text("Show status")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    viewModel: MyVM
) {

    var text by remember {
        viewModel.query
    }

    Column(modifier = modifier.fillMaxWidth()) {

        //Row to contain the Search Text Bar and the Search Button
        Row(modifier = Modifier.padding(20.dp, 20.dp, 20.dp, 2.dp)) {

            //Text Field for Entering Search Text
            TextField(
                value = text,
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(autoCorrect = false),
                onValueChange = {
                    if (!it.contains("\n")) {
                        text = it
                        viewModel.query.value = it
                    }
                },
                label = { androidx.compose.material3.Text("Search") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                modifier = modifier
                    .fillMaxWidth(0.8F)
                    .padding(0.dp, 0.dp, 20.dp, 0.dp)
            )

            //Search Icon Button
            IconButton(
                onClick = {
                    onSearch
                },
                modifier = Modifier
                    .background(Color(0xFF00abe3), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun FiltersBar(
    viewModel: MyVM,
    filtersList: State<List<Person>>
) {
    Log.d("FiltersBar", "Recomposing FiltersBar with FiltersList: ${filtersList.value}")

    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        filtersList.value.forEach { filter ->
            Button(
                onClick = { filter.clicked = true },
                modifier = Modifier.background(if (filter.clicked) Color.Green else Color.Red)
            ) {
                Text("Click me for ${filter.name}", color = Color.White)
            }
        }
    }
}