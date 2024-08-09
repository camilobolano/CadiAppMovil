package com.dixonguerrero.dev.auth.ui.screens.login.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dixonguerrero.dev.auth.R
import com.dixonguerrero.dev.auth.navigation.AppScreens
import com.dixonguerrero.dev.auth.ui.screens.login.viewModel.LoginVM
import com.dixonguerrero.dev.auth.ui.screens.profile.viewModel.ProfileVM
import com.dixonguerrero.dev.auth.ui.theme.Primary
import com.dixonguerrero.dev.auth.ui.theme.Secondary
import kotlinx.coroutines.launch

@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController) {
    val  viewModel: LoginVM = remember { LoginVM() }

    val isLoading: Boolean by viewModel.isLoading.observeAsState(false)
    val message by viewModel.message.observeAsState()

    message?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
        viewModel.resetMessage()
    }


    if (isLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {

        Box(modifier = modifier.fillMaxSize()) {
            Header(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .align(Alignment.TopCenter),
                navController
            )
            LoginContent(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .padding(top = 240.dp),
                navController,
                viewModel
            )
        }
    }
}

@Composable
fun LoginContent(modifier: Modifier,navController: NavController,viewModel: LoginVM) {

    val user: String by viewModel.user.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val isLoginEnable: Boolean by viewModel.isLoginEnable.observeAsState(false)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .background(Secondary)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text ="Login",
                fontSize = 32.sp,
                color = Primary,
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 36.dp)
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            InputField(label = "Usuario", placeholder = "Ingrese su nombre de usuario",user,{viewModel.onLoginChanged(it, password)})
            Spacer(modifier = Modifier.height(10.dp))
            InputField(label = "Contraseña", placeholder = "Ingrese su contraseña",password,{viewModel.onLoginChanged(user, it)})
            Spacer(modifier = Modifier.height(10.dp))
            ButtonLogin(isLoginEnable, {
                    viewModel.onLoginSelected(navController)
                })
            Spacer(modifier = Modifier.weight(1f))
            MessageFooter(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp),
                navController
            )
        }
    }
}

@Composable
fun InputField(
    label: String,
    placeholder: String,
    campo: String = "",
    onValueChange: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .height(90.dp)
            .background(Color.White),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Secondary, shape = RoundedCornerShape(8.dp))
            ) {

                if (campo.isEmpty()) {
                    Text(
                        modifier = Modifier.background(Secondary),
                        text = placeholder,
                        color = Color.LightGray,
                        style = LocalTextStyle.current.copy(textAlign = TextAlign.Start)
                    )
                }
                BasicTextField(
                    value = campo,
                    onValueChange = { newValue ->
                        onValueChange(newValue)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions.Default
                )
            }
        }
    }
}
@Composable
fun ButtonLogin(isLoginEnable: Boolean = true, onClick: () -> Unit ) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = Secondary
        ),
        enabled = isLoginEnable
    ) {
        Text(
            text = "Iniciar Sesión",
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun MessageFooter(modifier: Modifier,navController: NavController) {
    Text(
        text = "¿No tienes una cuenta? Regístrate",
        modifier = modifier.clickable { navController.navigate(AppScreens.RegisterPage.route) },
        fontSize = 17.sp,
        color = Primary,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Header(modifier: Modifier, navController: NavController) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.screen2),
            contentDescription = "Fondo de pantalla Montañas Minimalistas",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )


            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back Button",
                modifier = Modifier.
                    padding(10.dp)
                    .size(32.dp)
                    .clickable { navController.navigate(AppScreens.HomePage.route) }
            )


        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    LoginPage(navController = rememberNavController())
}
