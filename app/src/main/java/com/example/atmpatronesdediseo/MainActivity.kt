package com.example.atmpatronesdediseo

import android.graphics.Color
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmpatronesdediseo.ui.theme.ATMPatronesDeDiseñoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ATMPatronesDeDiseñoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaInicial(modifier: Modifier = Modifier, contenido : @Composable  (m: Modifier) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(androidx.compose.ui.graphics.Color.LightGray)
        ,contentAlignment = Alignment.Center

    ) {
        Box(
            modifier = modifier
                .padding(50.dp)
                .background(androidx.compose.ui.graphics.Color.Gray),
            contentAlignment = Alignment.Center,
        ) {
            contenido(m = Modifier.fillMaxSize())
        }
    }

}


@Composable
fun contenidoPantalla(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        TextField(value ="", onValueChange = {}, label = { Text("USUARIO") })
        TextField(value ="", onValueChange = {}, label = { Text("CONTRASEÑA") })
        Button(onClick = { /*TODO*/ },content = { Text("Cargar") })             }

}

@Composable
fun Numeros(modifier: Modifier = Modifier) {
    LazyVerticalGrid(modifier = Modifier.background(androidx.compose.ui.graphics.Color.Gray), columns = GridCells.Fixed(3)) {
        items(9) {numero ->
            botonItem(numero = numero+1)

        }
    }
    
}

@Composable
fun botonItem(numero: Int) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(10.dp)
            .size(100.dp)
        ) {
        Text(text = numero.toString(),fontSize = 30.sp)
    }


}

@Preview
@Composable
private fun NumerosPreview() {
    Numeros()


}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(androidx.compose.ui.graphics.Color.DarkGray)) {
        PantallaInicial(contenido = {contenidoPantalla()})
        Numeros()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ATMPatronesDeDiseñoTheme {
        Greeting("Android")
    }
}