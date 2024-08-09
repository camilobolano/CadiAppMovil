package com.dixonguerrero.dev.auth.ui.screens.register.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
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
import com.dixonguerrero.dev.auth.ui.screens.register.viewModel.RegisterVM
import com.dixonguerrero.dev.auth.ui.theme.Primary
import com.dixonguerrero.dev.auth.ui.theme.Secondary

@Composable
fun RegisterPage(modifier: Modifier = Modifier,navController: NavController) {
    val viewModel: RegisterVM = remember { RegisterVM() }
    val isLoading by viewModel.isLoading.observeAsState()
    val message by viewModel.message.observeAsState()

    message?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
        viewModel.resetMessage()
    }


    var showDialog  by remember { mutableStateOf(true) }

    if (isLoading == true) {
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
                navController,
                onShowDialog = { showDialog = true }

            )
            LoginContent(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .padding(top = 130.dp),
                navController,viewModel, showDialog = showDialog, onShowDialog = { showDialog = false }
            )
        }
    }
}

@Composable
fun LoginContent(modifier: Modifier,navController: NavController,viewModel: RegisterVM, showDialog: Boolean, onShowDialog: () -> Unit = {}) {

    val name: String by viewModel.name.observeAsState("")
    val lastName: String by viewModel.lastName.observeAsState("")
    val document: String by viewModel.document.observeAsState("")
    val username: String by viewModel.username.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val isButtonEnabled: Boolean by viewModel.register.observeAsState(false)

    //Validations
    val validName: Boolean by viewModel.validName.observeAsState(true)
    val validLastName: Boolean by viewModel.validLastName.observeAsState(true)
    val validDocument: Boolean by viewModel.validDocument.observeAsState(true)
    val validUsername: Boolean by viewModel.validUsername.observeAsState(true)
    val validPassword: Boolean by viewModel.validPassword.observeAsState(true)




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

            Spacer(modifier = Modifier.height(20.dp))
            InputField(
                label = "Nombre",
                placeholder = "Ingrese su nombre de usuario",
                campo = name,
                onValueChange = {
                    viewModel.onChangeValues(it, lastName, document, username, password)
                }, isValid = validName
            )
            Spacer(modifier = Modifier.height(10.dp))
            InputField(
                label = "Apellido",
                placeholder = "Ingrese su apellido de usuario",
                campo = lastName,
                onValueChange = {
                    viewModel.onChangeValues(name, it, document, username, password)
                }, isValid = validLastName
            )
            Spacer(modifier = Modifier.height(10.dp))
            InputField(
                label = "Documento",
                placeholder = "Ingrese su documento de usuario",
                campo = document,
                onValueChange = {
                    viewModel.onChangeValues(name, lastName, it, username, password)
                }, isValid = validDocument
            )
            Spacer(modifier = Modifier.height(10.dp))
            InputField(
                label = "Usuario",
                placeholder = "Ingrese su nombre de usuario",
                campo = username,
                onValueChange = {
                    viewModel.onChangeValues(name, lastName, document, it, password)
                }, isValid = validUsername
            )
            Spacer(modifier = Modifier.height(10.dp))
            InputField(
                label = "Contraseña",
                placeholder = "Ingrese su contraseña",
                campo = password,
                onValueChange = {
                    viewModel.onChangeValues(name, lastName, document, username, it)
                }, isValid = validPassword
            )
            Spacer(modifier = Modifier.height(10.dp))
            ButtonRegister(isButtonEnabled,{ viewModel.onRegisterSelect(navController) })
            Spacer(modifier = Modifier.weight(1f))
            MessageFooter(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                navController
            )


            DialogInfoFields(showDialog = showDialog, { onShowDialog() })
        }
    }
}

@Composable
fun InputField(
    label: String,
    placeholder: String,
    campo: String = "",
    onValueChange: (String) -> Unit,
    isValid: Boolean
) {

    val borderColor = if (!isValid) Color.Red else Secondary
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
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .border(BorderStroke(2.dp, borderColor), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp)
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
fun DialogInfoFields(showDialog: Boolean = true, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                Button(onClick = { onDismiss() } , colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = Secondary
                )
                ) {
                    Text(text = "Entendido", fontWeight = FontWeight.Bold, )
                }
            },
            title = {
                Text(text = "Validaciones de Campos")
            },
            text = {
                Column {
                    Text(text = "A continuación, se detallan los requisitos para cada campo:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Nombre:")
                    Text(text = "- Debe tener más de 5 caracteres.")
                    Text(text = "- No debe contener espacios.")
                    Text(text = "- Debe contener solo letras.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Apellido:")
                    Text(text = "- Debe tener más de 5 caracteres.")
                    Text(text = "- No debe contener espacios.")
                    Text(text = "- Debe contener solo letras.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Documento:")
                    Text(text = "- Debe tener exactamente 8 dígitos.")
                    Text(text = "- No debe contener espacios.")
                    Text(text = "- Debe contener solo números.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Nombre de Usuario:")
                    Text(text = "- Debe tener más de 5 caracteres.")
                    Text(text = "- No debe contener espacios.")
                    Text(text = "- Debe contener solo letras o números.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Contraseña:")
                    Text(text = "- Debe tener más de 8 caracteres.")
                    Text(text = "- No debe contener espacios.")
                    Text(text = "- Debe contener solo letras o números.")
                }
            }
        )
    }
}


@Composable
fun ButtonRegister(validForRegister: Boolean, onClick: () -> Unit = {}) {

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
        enabled = validForRegister
    ) {
        Text(
            text = "Registrarme",
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun MessageFooter(modifier: Modifier, navController: NavController) {
    Text(
        text = "¿Ya tienes una cuenta? Inicia Sesion",
        modifier = modifier.clickable { navController.navigate(AppScreens.LoginPage.route) },
        fontSize = 17.sp,
        color = Primary,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Header(modifier: Modifier,navController: NavController,onShowDialog: () -> Unit) {
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { navController.popBackStack() }
                )

                Text(
                    text = "Registro",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Primary,
                    textAlign = TextAlign.Center,

                )

                Image(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onShowDialog() }
                )


            }




        }




    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPagePreview() {
    RegisterPage(Modifier, rememberNavController())
}
