package com.castelaofp.books.ui.theme.screens.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.castelaofp.books.R




/**
 *
 * Cuando el usuario ha introducido un email correcto y un password de 7 caracteres
 * se habilita el boton de login
 *
 * Cuando el usuario pulsa sobre el boton de login se comprueba que las credenciales
 * introducidas son correctas (a@b.com / 12345678)
 *
 * Inmediatamente se muestra un toast indicando si fueron correctas o no las credenciales
 *
 * Created by Your name on 11/01/2024.
 */

const val TAG_LOG: String = "LoginScreen"

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    //TODO
    //1. Obtener el loginUiState con toda la información de la pantalla
    //2. Crear un box que ocupe toda la pantalla con un padding de 16.dp
    //3. Dentro del box, se llamara al Login con toda la informacion requerida
    val loginUiSate by viewModel.uiState.collectAsState()
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(
            Modifier.align(Alignment.Center),
            viewModel,
            loginUiSate.loginData,
            loginUiSate.loginEnable,
            loginUiSate.loginMessage,
            loginUiSate.loginChecked
        )
    }
}

/**
 * Representa toda la pantalla de login
 *
 * @viewModel viewModel con la información del controlador
 * @loginData contiene los datos del formulario: email y contraseña
 * @loginEnable sirve para indicar si hay que habilitar el boton de inicio de sesion
 * @loginMessage mensaje a mostrar al usuario una vez que ha iniciado sesion
 * @loginChecked util para mostrar y ocultar el toast que muestra el mensaje ok/ko de inicio
 * de sesion
 */
@Composable
fun Login(
    modifier: Modifier,
    viewModel: LoginViewModel,
    loginData: LoginData,
    loginEnable: Boolean,
    loginMessage: String,
    loginChecked: Boolean
) {
    //TODO
    //1.- Definir un mContext para el Toast: val mContext = LocalContext.current
    //2.- Definir una columna con los siguientes elementos: HeaderImage, EmailField, PasswordField, ForgotPassword, LoginButton
    //3.- Si se hizo login, habra que mostrar el toast y llamar al metodo onToasteShowed del viewModel
    // para hacer que desaparezca de nuestra vista una vez se ha mostrado
    val mContext = LocalContext.current

    Column(modifier = modifier) {
        HeaderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(16.dp))
        EmailField(loginData) { viewModel.onLoginChanged(it, loginData.password) }
        Spacer(modifier = Modifier.padding(16.dp))
        PasswordField(loginData) { viewModel.onLoginChanged(loginData.email, it) }
        ForgotPassword(Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.padding(16.dp))
        LoginButton(loginEnable, { viewModel.onLoginSelected() })
        //Si el usuario hizo login, mostramos el mensaje en un toast. El mensaje mostrar si fue correcto o no el login
        if (loginChecked) {
            mToast(mContext, loginMessage)
            viewModel.onToastShowed()
        }
    }
}

/**
 * Representa el logo de la pantalla de login
 */
@Composable
fun HeaderImage(modifier: Modifier) {
    //TODO
    //1.- Mostrar la imagen que guste en tu app
    Image(
        painter = painterResource(id = R.drawable.atom),
        contentDescription = "Header",
        modifier = modifier
    )
}

/**
 * Funcion generica para mostrar informacion de un toast
 */
private fun mToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

/**
 * Representa el boton de login
 *
 * @loginEnable sirve para habilitar el boton cuando los campos cumpla con los
 * requisitos exigidos
 * @onLoginSelected evento del VM que se lanzara al hacer click en login
 */
@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    //TODO
    //1. Crear un boton. Cuando se hace click se llamara a onLoginSelected. Solo estara habilitado cuando lo diga LoginEnable
    //2. Proporciona los estilos que gustes a los botones
    Button(onClick = {
        Log.d(TAG_LOG, "Onclick")
        onLoginSelected()
    }, modifier = Modifier
        .fillMaxWidth()
        .height(48.dp), colors = ButtonDefaults.buttonColors(
        contentColor = Color.White,
        disabledContentColor = Color.White,
    ), enabled = loginEnable) {
        Text(stringResource(R.string.login))
    }
}

/**
 * Representa el texto de "Olvide mi contraseña"
 */
@Composable
fun ForgotPassword(modifier: Modifier) {
    //TODO
    //1.- Caja de texto con olvidaste contraseña pero sin accion asociada
    Text(
        text =  stringResource(R.string.forgot_password),
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFB9600)
    )
}

/**
 * Caja de texto de email
 *
 * @loginData: contiene los valores email y password para mostrarlos en su campo correspondiente
 * @onLoginChanged: evento del VM que se invocará cada vez que haya un cambio en las cajas
 *          de texto de login
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(loginData: LoginData, onLoginChanged: (String) -> Unit) {
    //TODO
    //1.- Crear un TextField para el email, cada vez que cambie el valor del TextField, se llamara a onLoginChanged
    //2.- Aplicar la estetica visual que gustes
    //https://stackoverflow.com/questions/67320990/android-jetpack-compose-cant-set-backgroundcolor-for-outlinedtextfield
    TextField(
        value = loginData.email, onValueChange = onLoginChanged,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            //backgroundColor = Color(0xFFDEDDDD),//TODO ¿como es esto en material 3?
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )


}

/**
 * Caja de texto de password
 *
 * @loginData: contiene los valores email y password para mostrarlos en su campo correspondiente
 * @onLoginChanged: evento del VM que se invocará cada vez que haya un cambio en las cajas
 *          de texto de login
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(loginData: LoginData, onLoginChanged: (String) -> Unit) {
    //1.- Crear un TextField para el password, cada vez que cambie el valor del TextField, se llamara a onLoginChanged
    //2.- Aplicar la estetica visual que gustes
    var password by remember { mutableStateOf("") }
    //https://stackoverflow.com/questions/67320990/android-jetpack-compose-cant-set-backgroundcolor-for-outlinedtextfield
    TextField(
        value = loginData.password, onValueChange = onLoginChanged,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF636262),
            //backgroundColor = Color(0xFFDEDDDD),//TODO ¿como es esto en material 3?
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginScreenPreview() {
    val viewModel = LoginViewModel()
    LoginScreen(viewModel)
}
