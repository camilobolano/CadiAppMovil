package com.dixonguerrero.dev.auth.ui.screens.successRegister.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dixonguerrero.dev.auth.R
import com.dixonguerrero.dev.auth.navigation.AppScreens
import com.dixonguerrero.dev.auth.ui.theme.Primary
import com.dixonguerrero.dev.auth.ui.theme.Secondary
import com.dixonguerrero.dev.auth.ui.theme.Tertiary

@Composable
fun SuccessRegisterPage(modifier: Modifier,navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()
        SuccessPageContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.Center),
            navController
        )

    }

}

@Composable
fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.home),
        contentDescription = "Fondo de pantalla",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun SuccessPageContent(modifier: Modifier,navController: NavController) {

    Box(modifier = modifier){
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Header()
            Spacer(modifier = Modifier.padding(32.dp))
            Text(
                text = "Te has registrado con exito,  " +
                        "A continuacion inicia sesion",
                color = Secondary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(32.dp))
            ButtonLogin(navController)
            Spacer(modifier = Modifier.weight(1f))

        }

    }


}

@Composable
fun Header() {
    Image(
        painter = painterResource(id = R.drawable.ic_success),
        contentDescription = "Logo",
        modifier = Modifier.padding(vertical = 100.dp)
    )
}

@Composable
fun ButtonLogin(navController: NavController) {
    Button(
        onClick = { navController.navigate(AppScreens.LoginPage.route) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp,vertical = 10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Secondary,
            contentColor = Primary
        )
    ) {
        Text(
            text = "Iniciar Sesión",
            fontWeight = FontWeight.Bold,

            )
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessRegisterPagePreview() {
    SuccessRegisterPage(Modifier, rememberNavController())
}




