package com.dixonguerrero.dev.auth.ui.screens.profile.view


import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dixonguerrero.dev.auth.R
import com.dixonguerrero.dev.auth.ui.screens.profile.viewModel.ProfileVM
import com.dixonguerrero.dev.auth.ui.theme.BackgroundLoading
import com.dixonguerrero.dev.auth.ui.theme.Primary
import com.dixonguerrero.dev.auth.ui.theme.Secondary

@Composable
fun ProfilePage(modifier: Modifier = Modifier, navController: NavController, document: String?) {
    val  viewModel: ProfileVM = remember { ProfileVM() }
    val isLoading by viewModel.isLoading.observeAsState()

    LaunchedEffect(document) {
        document?.let {
            viewModel.getUser(it)
        }
    }




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
                    .height(400.dp)
                    .align(Alignment.TopCenter),
                viewModel,
                navController
            )

            LoginContent(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .padding(top = 210.dp),
                viewModel
            )
        }
    }
}


@Composable
fun LoginContent(modifier: Modifier, viewModel: ProfileVM) {

    val name: String? by viewModel.name.observeAsState()
    val lastName: String? by viewModel.lastName.observeAsState()
    val document: String? by viewModel.document.observeAsState()
    val username: String? by viewModel.username.observeAsState()
    val password: String? by viewModel.password.observeAsState();
    val update  by viewModel.update.observeAsState()
    val message by viewModel.message.observeAsState()

    //Validations
    val validName: Boolean by viewModel.validName.observeAsState(true)
    val validLastName: Boolean by viewModel.validLastName.observeAsState(true)
    val validDocument: Boolean by viewModel.validDocument.observeAsState(true)
    val validUsername: Boolean by viewModel.validUsername.observeAsState(true)
    val validPassword: Boolean by viewModel.validPassword.observeAsState(true)



    message?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
        viewModel.resetMessage()
    }



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

                InputField(
                    label = "Nombre",
                    placeholder = name,
                    {
                        viewModel.onChangeValues(
                            it,
                            lastName ?: "",
                            document ?: "",
                            username ?: "",
                            password ?: ""
                        )

                    }, validName
                )
                Spacer(modifier = Modifier.height(10.dp))
                InputField(
                    label = "Apellido",
                    placeholder = lastName,
                    {
                        viewModel.onChangeValues(
                            name ?: "",
                            it,
                            document ?: "",
                            username ?: "",
                            password ?: ""
                        )
                    }, validLastName
                )
                Spacer(modifier = Modifier.height(10.dp))
                InputField(
                    label = "Documento",
                    placeholder = document,
                    {
                        viewModel.onChangeValues(
                            name ?: "",
                            lastName ?: "",
                            it,
                            username ?: "",
                            password ?: ""
                        )

                    }, validDocument
                )
                Spacer(modifier = Modifier.height(10.dp))
                InputField(
                    label = "usuario",
                    placeholder = username,
                    {
                        viewModel.onChangeValues(
                            name ?: "",
                            lastName ?: "",
                            document ?: "",
                            it,
                            password ?: ""
                        )
                    }, validUsername
                )
                Spacer(modifier = Modifier.height(10.dp))
                InputField(
                    label = "Contraseña",
                    placeholder = password,
                    {
                        viewModel.onChangeValues(
                            name ?: "",
                            lastName ?: "",
                            document ?: "",
                            username ?: "",
                            it
                        )
                    }, validPassword

                )
                Spacer(modifier = Modifier.height(10.dp))
                ButtonRegister(isShow = update, {viewModel.updateUser()})
                Spacer(modifier = Modifier.weight(1f))

            }
        }
    }


@Composable
fun InputField(
    label: String,
    placeholder: String?,
    onValueChange: (String) -> Unit,
    isValid: Boolean
) {
    val borderColor = if (isValid) Secondary else Color.Red

    Card(
        modifier = Modifier
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .height(80.dp)
            .background(Secondary)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(15.dp)
            ),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Secondary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                color = Primary,
                fontWeight = FontWeight.Bold,
            )
            BasicTextField(
                value = placeholder ?: "",
                onValueChange = { onValueChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
        }
    }
}


@Composable
fun ButtonRegister(isShow: Boolean?, onClick: () -> Unit) {
    if (isShow == true) {
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Secondary
            )
        ) {
            Text(
                text = "Editar Datos",
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun Header(modifier: Modifier, viewModel: ProfileVM,navController: NavController) {

    var showDialog by rememberSaveable { mutableStateOf(false) }

    BackHandler {
        showDialog = true
    }
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.screen_profile),
            contentDescription = "Fondo de pantalla Montañas Minimalistas",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { }
                )
            }

            Image(
                painter = painterResource(id = R.drawable.profile), contentDescription = "Profile",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(150.dp)
                    .clip(shape = RoundedCornerShape(100.dp)),
            )

            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { showDialog = true }
                )
            }
        }

        MyDialog(
            show = showDialog,
            onDismiss = { showDialog = false },
            onConfirm = { showDialog = false
                viewModel.Logout(navController = navController) }
        )

        Text(
            text = "Datos Personales",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 0.dp),
            color = Secondary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )


    }
}

@Composable
fun MyDialog(
    show: Boolean = true,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (!show) return
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onConfirm() } , colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Secondary
            )
            ) {
                Text(text = "Si", fontWeight = FontWeight.Bold, )
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() } , colors = ButtonDefaults.buttonColors(
                containerColor = Secondary,
                contentColor = Primary
            )
            ) {
                Text(text = "No", fontWeight = FontWeight.Bold, )
            }
        },
        title = { Text(text = "Confirmación") },
        text = { Text(text = "¿Quieres cerrar sesión?") },

    )
}

