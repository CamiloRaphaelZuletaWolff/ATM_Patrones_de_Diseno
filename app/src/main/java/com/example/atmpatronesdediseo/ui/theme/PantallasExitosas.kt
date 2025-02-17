import android.os.Bundle
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.atmpatronesdediseo.ui.theme.ATMPatronesDeDiseñoTheme


@Composable
fun contenidoPantallaDepositoExitoso(
    onVolverClick: () -> Unit
) {
    Column(
        modifier = Modifier.background(androidx.compose.ui.graphics.Color.LightGray)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Depósito Exitoso",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp) )
                    Text(
                    text = "Tu depósito se ha realizado correctamente.",
            fontSize = 16.sp)

        Button(
            onClick = onVolverClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Volver", fontSize = 20.sp)
        }
    }
}

@Composable
fun contenidoPantallaRetiroExitoso(
    onVolverClick: () -> Unit
) {
    Column(
        modifier = Modifier.background(androidx.compose.ui.graphics.Color.LightGray)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Retiro Exitoso",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp))
                    Text(
                    text = "Tu retiro se ha realizado correctamente.",
            fontSize = 16.sp
        )
        Button(
            onClick = onVolverClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Volver", fontSize = 20.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewDepositoExitoso() {
    contenidoPantallaDepositoExitoso(onVolverClick = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewRetiroExitoso() {
    contenidoPantallaRetiroExitoso(onVolverClick = {})
}