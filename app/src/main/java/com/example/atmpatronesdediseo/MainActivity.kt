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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
                ATMScreen()
            }
        }
    }
}


@Composable
fun ATMScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var  saldo by rememberSaveable { mutableLongStateOf(465465) }
    var pantallaActual by rememberSaveable { mutableLongStateOf(1) }
    var texto by rememberSaveable { mutableStateOf("") }
    var monto by rememberSaveable { mutableStateOf("0") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        when(pantallaActual){
            2L -> {   // Pantalla Seleccion
                PantallaSeleccion(
                saldo = saldo,
                onDepositarClick = { pantallaActual = 4 },
                onRetirarClick = { pantallaActual = 3},
                onVolverClick = { pantallaActual = 1 } // Volver a la pantalla anterior
            )
            }
            1L -> {    // Pantalla inicial
            // Mostrar la pantalla anterior
            Pantallas(modifier = Modifier.padding(innerPadding)) {
                contenidoPantallaInicial(
                    modifier = Modifier,
                    text = texto,
                    onButtonPressed = { texto += it },
                    eliminar = {texto = ""},
                    onClick = {
                        if(texto == "1234") pantallaActual = 2 else  Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()


                    }
                )
            }
            }
            3L -> {    // Pantalla Retirar Dinero
                monto = "0"
                Pantallas(modifier = Modifier.padding(innerPadding)) {
                    ContenidoPantallaRetiro(
                        saldo = saldo,
                        textoTextField = monto,
                        onTextChanged = { newText : String ->
                            if (newText.all { it.isDigit() }) { // Permitir solo dígitos
                                monto = newText
                            }else{
                                Toast.makeText(context, "Solo puedes introducir numeros", Toast.LENGTH_SHORT).show()

                            }
                        },
                        onClick = {if(monto.toLong() <= saldo){
                            saldo -= monto.toLong()
                            Toast.makeText(context, "Retiro exitoso", Toast.LENGTH_SHORT).show()
                        }
                        else Toast.makeText(context, "No hay suficiente saldo", Toast.LENGTH_SHORT).show()},
                        volver = {pantallaActual = 2}
                    )
                }
            }
            4L ->{   // Pantalla Depositar Dinero
                monto = "0"
                Pantallas(modifier = Modifier.padding(innerPadding)) {
                    contenidoPantallaDepositar(
                        saldo = saldo,
                        onTextChanged = { newText : String ->
                            if (newText.all { it.isDigit() }) { // Permitir solo dígitos
                                monto = newText
                            }else{
                                Toast.makeText(context, "Solo puedes introducir numeros", Toast.LENGTH_SHORT).show()

                            }
                        },
                        onClick = {
                            saldo += monto.toLong()
                            Toast.makeText(context, "Deposito exitoso", Toast.LENGTH_SHORT).show()
                                  },
                        textoTextField = monto,
                        volver = {pantallaActual = 2}

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
fun PantallaSeleccion(
    saldo: Long,
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
                modifier = Modifier.padding(vertical = 16.dp).background(MaterialTheme.colorScheme.background)
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
    text: String,
    onClick: () -> Unit,
    onButtonPressed: (String) -> Unit,
    eliminar: () -> Unit
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Row(modifier.padding(top = 50.dp, bottom = 40.dp, end = 10.dp,start = 10.dp)){
            Text(text = text, modifier= Modifier.weight(5F).background(MaterialTheme.colorScheme.background))
            IconButton(onClick = eliminar,modifier = modifier.weight(1F)) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Borrar")
            }

        }
        BotonesNumeros(onItemPressed = onButtonPressed)
        Button(onClick = onClick ,content = { Text("Cargar") })
    }

}



@Composable
fun BotonesNumeros(modifier: Modifier = Modifier, onItemPressed: (String) -> Unit) {
    LazyVerticalGrid(modifier = Modifier.background(androidx.compose.ui.graphics.Color.Gray), columns = GridCells.Fixed(3)) {
        items(9) {numero ->
            BotonItem(numero = numero+1) { onItemPressed((numero+1).toString()) }

        }
    }

}

@Composable
fun BotonItem(numero: Int, function: () -> Unit) {
    Button(
        onClick = function,
        modifier = Modifier
            .padding(10.dp)
            .size(100.dp)
        ) {
        Text(text = numero.toString(),fontSize = 30.sp)
    }


}


@Composable
fun ContenidoPantallaRetiro(
    saldo: Long,
    onTextChanged: (String) -> Unit,
    onClick: () -> Unit,
    textoTextField: String,
    volver: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Saldo actual: $saldo",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp).background(MaterialTheme.colorScheme.background)
        )

        TextField(value = textoTextField, {onTextChanged(it)}, label = { Text("Monto a retirar")},keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        Button(
            onClick = onClick ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Retirar", fontSize = 20.sp)
        }
        IconButton(onClick = volver) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
        }



    }
}




@Composable
fun contenidoPantallaDepositar(
    saldo: Long,
    onTextChanged: (String) -> Unit,
    onClick: () -> Unit,
    textoTextField: String,
    volver: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Saldo actual: $saldo",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp).background(MaterialTheme.colorScheme.background)
        )

        TextField(textoTextField, {onTextChanged(it)}, label = { Text("Monto a Depositar")})
        // Botón para depositar
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Depositar", fontSize = 20.sp)
        }
        IconButton(onClick = volver) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
        }


    }
}





