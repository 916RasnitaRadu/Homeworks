package com.example.expensewizard.ui.pages.addPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensewizard.data.TransactionViewModel
import com.example.expensewizard.model.Transaction
import java.util.Date
import com.example.expensewizard.model.Type
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPage(navController :NavController,viewModel: TransactionViewModel) {
    val BG_COLOR = Color(0xFFB57EDC);
    val BG_COLOR_CARDS = Color(0xFFFFF8E1);
    val fontFamily: FontFamily = FontFamily.Default

    var title by remember { mutableStateOf(TextFieldValue()) }
    var amount by remember { mutableStateOf(TextFieldValue()) }
    var type by remember {
        mutableStateOf(Type.EXPENSE)
    }
    var isChecked1 by remember { mutableStateOf(false) }
    var isChecked2 by remember { mutableStateOf(false) }
    var selectedDropdownItem by remember { mutableStateOf("Select a category") }
    val dropdownItems = listOf("Transport", "Food", "Entertaining", "House", "Health")
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var category by remember {
        mutableStateOf("")
    }

    var errors by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(BG_COLOR))
    {
        Column(modifier = Modifier
            .padding(30.dp)
            .fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(BG_COLOR_CARDS)
                ) {
                Row (horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BG_COLOR_CARDS)
                        .padding(4.dp, 20.dp)){
                    Text(text = "New Transaction",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = fontFamily,
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))

            TextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = { Text("Title..") },
                supportingText = {
                     if (errors) {
                         Text(modifier = Modifier.fillMaxWidth(),
                             text = "Title can not be empty!",
                             color = MaterialTheme.colorScheme.error)
                     }
                },
                trailingIcon = {
                    if (errors) {
                        Icon(imageVector = Icons.Default.Warning, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                    }   
                },
                modifier = Modifier
                    .background(BG_COLOR_CARDS)
                    .fillMaxWidth(),
                
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Second TextField
            TextField(
                value = amount,
                onValueChange = {
                    amount = it
                },
                label = { Text("Amount..")},
                supportingText = {
                    if (errors) {
                        Text(modifier = Modifier.fillMaxWidth(),
                            text = "Amount can not be empty and must be a number!",
                            color = MaterialTheme.colorScheme.error)
                    }
                },
                trailingIcon = {
                    if (errors) {
                        Icon(imageVector = Icons.Default.Warning, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier
                    .background(BG_COLOR_CARDS)
                    .fillMaxWidth()

            )

            Spacer(modifier = Modifier.height(16.dp))
            
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(BG_COLOR_CARDS)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(8.dp)
                            .background(BG_COLOR_CARDS)) {
                        Row (horizontalArrangement = Arrangement.Start, modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)){
                            Text(text = "Type",fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = fontFamily)
                        }

                        if (errors) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Error",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(4.dp)
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Please choose only one type!",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        Row (horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)) {
                            Text(text = "Expense",fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(20.dp, 1.dp))
                            Checkbox(checked = isChecked1, onCheckedChange = { isChecked1 = it }, modifier = Modifier.padding(start = 0.dp, end = 0.dp))
                        }
                        Row (horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)) {
                            Text(text = "Income",fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = fontFamily,
                                modifier = Modifier.padding(20.dp, 1.dp))
                            Checkbox(checked = isChecked2, onCheckedChange = { isChecked2 = it }, modifier = Modifier.padding(start = 0.dp, end = 0.dp))
                        }




                    }
                }

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(expanded = isExpanded,
                onExpandedChange = {isExpanded = it}) {
                TextField(value = selectedDropdownItem, onValueChange ={}, readOnly = true,
                    trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)},
                    supportingText = {
                        if (errors) {
                            Text(modifier = Modifier.fillMaxWidth(),
                                text = "Please select a category!",
                                color = MaterialTheme.colorScheme.error)
                        }
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .background(BG_COLOR_CARDS)
                        .fillMaxWidth())
                
                ExposedDropdownMenu(expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                    modifier = Modifier.fillMaxWidth()) {
                    dropdownItems.forEach { item ->
                        DropdownMenuItem(text = {Text(item)},
                            onClick = {
                                selectedDropdownItem = item
                                category = item
                                isExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                Button(colors = ButtonDefaults.buttonColors(BG_COLOR_CARDS),
                    onClick = {
                        if (isChecked1 && !isChecked2) type = Type.EXPENSE;
                        if (!isChecked1 && isChecked2) { type = Type.INCOME; category = "Income"; }

                        if (category.isEmpty() || title.text.isEmpty() || amount.text.isEmpty()
                            || (isChecked1 && isChecked2) || (!isChecked1 && !isChecked2)) { errors = true }
                        else errors = false



                        if (!errors) {
                            val transaction = Transaction(title.text,type,category,amount.text.toDouble(), Date())
                            viewModel.addTransaction(transaction)

                           // navController.navigate("home")
                            navController.popBackStack();
                        } },
                    modifier = Modifier
                        .padding(20.dp)
                        .size(width = 120.dp, height = 60.dp)

                )
                {
                    Text(text = "Save",
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                }
                Button(colors = ButtonDefaults.buttonColors(BG_COLOR_CARDS),
                    onClick = { navController.navigate("home") },
                    modifier = Modifier
                        .padding(20.dp)
                        .size(width = 120.dp, height = 60.dp)

                )
                {
                    Text(text = "Cancel",
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}



