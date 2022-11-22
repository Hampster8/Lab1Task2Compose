package com.schoolwork.lab1task2compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.schoolwork.lab1task2compose.ui.theme.Lab1Task2ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab1Task2ComposeTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    App()
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
    Lab1Task2ComposeTheme {
        Greeting("Android")
    }
}

@Preview
@Composable
fun App() {

    ComposableLifecycle { source, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                Log.d("TAG", "ComposableLifecycle: $source - $event")
            }
            Lifecycle.Event.ON_START -> {
                Log.d("TAG", "ComposableLifecycle: $source - $event")
            }
            Lifecycle.Event.ON_RESUME -> {
                Log.d("TAG", "ComposableLifecycle: $source - $event")
            }
            Lifecycle.Event.ON_PAUSE -> {
                Log.d("TAG", "ComposableLifecycle: $source - $event")
            }
            Lifecycle.Event.ON_STOP -> {
                Log.d("TAG", "ComposableLifecycle: $source - $event")
            }
            Lifecycle.Event.ON_DESTROY -> {
                Log.d("TAG", "ComposableLifecycle: $source - $event")
            }
            else -> {}
        }
    }
    Column(modifier = Modifier
        .background(Brush.verticalGradient(colors = listOf(Color.Transparent, colorResource(
            R.color.purple_200))))
        .fillMaxSize()
        .padding(15.dp), verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Martha Stewart's Pancakes", Modifier.padding(10.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold, color = Color.DarkGray, textAlign = TextAlign.Center)
        Image(painter = painterResource(R.drawable.pancakelab1part2),
            contentDescription = "pancakes", modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth())
        val toggleRecipe = remember { mutableStateOf(false) }

        Button(onClick = {
            toggleRecipe.value = !toggleRecipe.value
        }) {
            if (toggleRecipe.value) {
                Text("Hide Recipe")
            } else {
                Text("Show Recipe")
            }
        }
        if (toggleRecipe.value) {
            Text(stringResource(R.string.recipe),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.LightGray,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(colorResource(R.color.purple_700), RoundedCornerShape(20.dp))
                    .padding(15.dp))
        }
        Image(painter = painterResource(R.drawable.pancakedrawnlab1part2),
            contentDescription = "pancakes",
            modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ComposableLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit,
) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            onEvent(source, event)
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
}