package com.example.atmpatronesdediseo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
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
                ATMScrenn()
            }
        }
    }
}


@Composable
fun ATMScrenn(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var  saldo by rememberSaveable { mutableStateOf(465465) }
    var pantallaActual by rememberSaveable { mutableStateOf(1) }
    var texto by rememberSaveable { mutableStateOf("") }
    var monto by rememberSaveable { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        when(pantallaActual){
            2 -> {   // Pantalla Seleccion
                PantallaSeleccion(
                saldo = saldo,
                onDepositarClick = { pantallaActual = 4 },
                onRetirarClick = { pantallaActual = 3},
                onVolverClick = { pantallaActual = 1 } // Volver a la pantalla anterior
            )
            }
            1 -> {    // Pantalla inicial
            // Mostrar la pantalla anterior
            Pantallas(modifier = Modifier.padding(innerPadding)) {
                contenidoPantallaInicial(
                    modifier = Modifier,
                    text = texto,
                    onTextChanged = {it: String -> texto = it},
                    onClick = { if(texto == "1234") pantallaActual = 2 else  Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            }
            3 -> {
                Pantallas(modifier = Modifier.padding(innerPadding)) {
                    contenidoPantallaRetiro(
                        saldo = saldo,
                        onTextChanged = { newText : String ->
                            if (newText.all { it.isDigit() }) { // Permitir solo dígitos
                                monto += newText
                            }
                        },
                        onClick = {if(monto.toInt() <= saldo) saldo -= monto.toInt() else Toast.makeText(context, "No hay suficiente saldo", Toast.LENGTH_SHORT).show()}
                    )
                }
            }
            4 ->{
                Pantallas(modifier = Modifier.padding(innerPadding)) {
                    contenidoPantallaDepositar(
                        saldo = saldo,
                        onTextChanged = { newText : String ->
                            if (newText.all { it.isDigit() }) { // Permitir solo dígitos
                                monto += newText
                            }
                        },
                        onClick = {saldo += monto.toInt()}
                    )
                }

            }
        }
    }

}


@Composable
fun Pantallas(modifier: Modifier = Modifier, contenido : @Composable  (m: Modifier) -> Unit) {
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
fun PantallaSeleccion(saldo : Int,
                      onDepositarClick: () -> Unit,
                      onRetirarClick: () -> Unit,
                      onVolverClick: () -> Unit, // Callback para volver a la pantalla anterior
                      modifier: Modifier = Modifier
){
    Pantallas(modifier = modifier) {
        Column(
            modifier = modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Saldo actual: $saldo",
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Botón para depositar
            Button(
                onClick = onDepositarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Depositar", fontSize = 20.sp)
            }

            // Botón para retirar
            Button(
                onClick = onRetirarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Retirar", fontSize = 20.sp)
            }


            // Botón para volver a la pantalla anterior
            Button(
                onClick = onVolverClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Volver", fontSize = 20.sp)
            }
        }
    }


}


@Composable
fun contenidoPantallaInicial(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit,
    text: String,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Column(modifier.padding(top = 50.dp, bottom = 40.dp)){
            TextField(value = text, onValueChange = { onTextChanged(it) }, label = { Text("CONTRASEÑA") })
        }
        BotonesNumeros()
        Button(onClick = onClick ,content = { Text("Cargar") })
    }

}



@Composable
fun BotonesNumeros(modifier: Modifier = Modifier) {
    LazyVerticalGrid(modifier = Modifier.background(androidx.compose.ui.graphics.Color.Gray), columns = GridCells.Fixed(3)) {
        items(9) {numero ->
            BotonItem(numero = numero+1)

        }
    }

}

@Composable
fun BotonItem(numero: Int) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(10.dp)
            .size(100.dp)
        ) {
        Text(text = numero.toString(),fontSize = 30.sp)
    }


}


@Composable
fun contenidoPantallaRetiro(saldo: Int, onTextChanged: (String) -> Unit, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Saldo actual: $saldo",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        TextField("", {onTextChanged(it)}, label = { Text("Monto a retirar")},keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        Button(
            onClick = onClick ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Retirar", fontSize = 20.sp)
        }


    }
}




@Composable
fun contenidoPantallaDepositar(saldo: Int, onTextChanged: (String) -> Unit, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Saldo actual: 0",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        TextField("", {}, label = { Text("Monto a Depositar")})
        // Botón para depositar
        Button(
            onClick =
            {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Retirar", fontSize = 20.sp)
        }


    }
}




@Preview()
@Composable
fun PantallaSeleccionPreview() {
    ATMPatronesDeDiseñoTheme {
        PantallaSeleccion(
            saldo = 0,
            onDepositarClick = {},
            onRetirarClick = {},
            onVolverClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun PantallaInicial(name: String, modifier: Modifier = Modifier) {
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .background(androidx.compose.ui.graphics.Color.DarkGray)) {
//        Pantallas(contenido = {contenidoPantallaInicial(
//            onTextChanged = { it: String -> texto = it},
//            text = texto,
//            onClick = { if(texto == "1234") pantallaActual = 2 else println("Contraseña incorrecta") }
//        ) })
//    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInicialPreview() {
    ATMPatronesDeDiseñoTheme {
        PantallaInicial("Android")
    }
}

