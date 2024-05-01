package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.graphics.Color

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Task(val text: String, val priority: Priority)

enum class Priority { HIGH, MEDIUM, LOW }

@Composable
fun ToDoTheme(content: @Composable () -> Unit) {
    val colorScheme = lightColorScheme(
        primary = Color(0xFF007bff),
        secondary = Color(0xFFFFC107),
        background = Color.White
    )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoTheme {
                val tasks = remember { mutableStateListOf<Task>() }
                var newTask by remember { mutableStateOf("") }
                var selectedPriority by remember { mutableStateOf(Priority.MEDIUM) }

                Column(modifier = Modifier.fillMaxSize()) {
                    OutlinedTextField(
                        value = newTask,
                        onValueChange = { newTask = it },
                        label = { Text("Add a new task") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        RadioButton(
                            selected = selectedPriority == Priority.HIGH,
                            onClick = { selectedPriority = Priority.HIGH },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.primary)
                            )
                        )
                        Text(
                            text = "High",
                            modifier = Modifier.padding(start = 8.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        RadioButton(
                            selected = selectedPriority == Priority.MEDIUM,
                            onClick = { selectedPriority = Priority.MEDIUM },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.primary)
                            )
                        )
                        Text(
                            text = "Medium",
                            modifier = Modifier.padding(start = 8.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        RadioButton(
                            selected = selectedPriority == Priority.LOW,
                            onClick = { selectedPriority = Priority.LOW },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.primary)
                            )
                        )
                        Text(
                            text = "Low",
                            modifier = Modifier.padding(start = 8.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = {
                            if (newTask.isNotBlank()) {
                                tasks.add(Task(newTask, selectedPriority))
                                newTask = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add")
                    }
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(tasks.sortedBy { it.priority }) { task ->
                            TaskRow(task = task)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskRow(task: Task) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = task.text)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = task.priority.toString(),
                color = when (task.priority) {
                    Priority.HIGH -> MaterialTheme.colorScheme.error
                    Priority.MEDIUM -> MaterialTheme.colorScheme.primary
                    Priority.LOW -> MaterialTheme.colorScheme.onBackground
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoTheme {
        val tasks = listOf(
            Task("Buy groceries", Priority.MEDIUM),
            Task("Finish project report", Priority.HIGH),
            Task("Clean the house", Priority.LOW)
        )
        Column {
        }
    }
}

