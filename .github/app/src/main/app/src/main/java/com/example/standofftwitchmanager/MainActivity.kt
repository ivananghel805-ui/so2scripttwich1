package com.example.standofftwitchmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class AccountPair(
    val title: String,
    val status: String = "Не проверено"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ManagerScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerScreen() {
    var pairs by remember { mutableStateOf(listOf<AccountPair>()) }
    var title by remember { mutableStateOf("") }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Standoff Twitch Manager") }
                )
            }
        ) { padding ->
            Column(
                Modifier.fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(
                    "Менеджер пар аккаунтов",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(Modifier.height(8.dp))

                Text("Не вводи пароли и коды подтверждения.")

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Название пары") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            pairs = pairs + AccountPair(title.trim())
                            title = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Добавить пару")
                }

                Spacer(Modifier.height(12.dp))

                Text("Всего пар: ${pairs.size}")

                LazyColumn {
                    itemsIndexed(pairs) { index, pair ->
                        Card(
                            Modifier.fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(pair.title)
                                Text("Статус: ${pair.status}")

                                Row {
                                    TextButton(onClick = {
                                        val updated = pairs.toMutableList()
                                        updated[index] =
                                            pair.copy(status = "Проверено")
                                        pairs = updated
                                    }) {
                                        Text("Проверено")
                                    }

                                    TextButton(onClick = {
                                        pairs = pairs.filterIndexed {
                                            i, _ -> i != index
                                        }
                                    }) {
                                        Text("Удалить")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
