package com.mpierucci.android.dropdownmnu.leak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.mpierucci.android.dropdownmnu.leak.ui.theme.DropDownMenuLeakTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DropDownMenuLeakTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var showMenu by remember {
                        mutableStateOf(false)
                    }
                    Box {
                        DropDownSelectionItem(
                            currentSelection = 2,
                            selections = mapOf(
                                1 to "1",
                                2 to "2",
                                3 to "3"
                            ),
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            onExpandClick = { showMenu = true },
                            onSelected = {}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DropDownMenuLeakTheme {
        Greeting("Android")
    }
}

@Composable
fun <K> DropDownSelectionItem(
    modifier: Modifier = Modifier,
    currentSelection: K?,
    selections: Map<K, String>,
    expanded: Boolean,
    label: String? = null,
    isError: Boolean = false,
    dropDownOffset: DpOffset = DpOffset(0.dp, 0.dp),
    onDismissRequest: () -> Unit,
    onExpandClick: () -> Unit,
    onSelected: (key: K) -> Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        val dropDownIcon =
            if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
        TextField(value = "", onValueChange = {}, trailingIcon = {
            Icon(
                dropDownIcon,
                contentDescription = "",
                modifier = Modifier.clickable(onClick = onExpandClick)
            )
        })
        Box {
            DropdownMenu(
                offset = dropDownOffset,
                expanded = expanded,
                onDismissRequest = onDismissRequest
            ) {
                selections.forEach { selectionEntry ->
                    DropdownMenuItem(
                        onClick = { onSelected(selectionEntry.key) }
                    ) {
                        Text(text = selectionEntry.value)
                    }
                }
            }
        }
    }
}
