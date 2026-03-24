package com.example.habittracker.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.R
import com.example.habittracker.ui.theme.BackgroundLight
import com.example.habittracker.ui.theme.BorderColor
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.Primary
import com.example.habittracker.ui.theme.TextHint
import com.example.habittracker.ui.theme.TextOnDark
import com.example.habittracker.ui.theme.TextPrimary

@Composable
fun SignUpScreen(
    onBackClick      : () -> Unit = {},
    onNextClick      : () -> Unit = {},
    onCreateAccount  : () -> Unit = {},
    onLogin          : () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var termsAccepted by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    // ── Shared OutlinedTextField colors ──────────────────────────

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedBorderColor = Primary,
        unfocusedBorderColor = BorderColor,
        cursorColor = Primary,
        focusedTrailingIconColor = TextPrimary,
        unfocusedTrailingIconColor = TextHint,
        focusedLabelColor = Primary,
        unfocusedLabelColor = TextHint,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        // ── Back Button ───────────────────────────────────────────
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White)
                .clickable { onBackClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back to Onboarding Screen"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Title ─────────────────────────────────────────────────
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo_colored),
                contentDescription = "Logo",
                Modifier.padding(end = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome 👋",
                fontSize = 28.sp,
                fontFamily = NunitoFontFamily,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // ── USERNAME ────────────────────────────────────────────────
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "USERNAME",
                    fontFamily = NunitoFontFamily,
                    fontSize = 12.sp
                )
            },
            placeholder = {
                Text(
                    text = "Enter you username",
                    fontFamily = NunitoFontFamily,
                    color = TextHint
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = textFieldColors,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── E-MAIL ────────────────────────────────────────────────
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "E-MAIL",
                    fontFamily = NunitoFontFamily,
                    fontSize = 12.sp
                )
            },
            placeholder = {
                Text(
                    text = "example@gmail.com",
                    fontFamily = NunitoFontFamily,
                    color = TextHint
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = textFieldColors,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── BIRTHDAY ────────────────────────────────────────────────
        OutlinedTextField(
            value         = birthdate,
            onValueChange = { birthdate = it },
            modifier      = Modifier.fillMaxWidth(),
            label         = {
                Text(
                    text       = "BIRTHDATE",
                    fontFamily = NunitoFontFamily,
                    fontSize   = 12.sp
                )
            },
            placeholder = {
                Text(
                    text       = "mm/dd/yyyy",
                    fontFamily = NunitoFontFamily,
                    color      = TextHint
                )
            },
            trailingIcon = {
                Icon(
                    imageVector        = Icons.Default.DateRange,
                    contentDescription = "Pick date",
                    modifier           = Modifier.size(20.dp)
                )
            },
            singleLine      = true,
            shape           = RoundedCornerShape(14.dp),
            colors          = textFieldColors,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── PASSWORD ──────────────────────────────────────────────
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "PASSWORD",
                    fontFamily = NunitoFontFamily,
                    fontSize = 12.sp
                )
            },
            placeholder = {
                Text(
                    text = "Enter your password",
                    fontFamily = NunitoFontFamily,
                    color = TextHint
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible)
                            "Hide password"
                        else
                            "Show password",
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = textFieldColors,
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ── Privacy Policy ────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = {
                    termsAccepted = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Primary,
                    uncheckedColor = BorderColor,
                    checkmarkColor = Color.White
                )
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "I agree to the ",
                fontSize = 12.sp,
                fontFamily = NunitoFontFamily
            )
            Text(
                text = "Privacy Policy",
                fontSize = 12.sp,
                fontFamily = NunitoFontFamily,
                color = Primary,
                modifier = Modifier.clickable { }
            )
            Text(
                text = " and ",
                fontSize = 12.sp,
                fontFamily = NunitoFontFamily
            )
            Text(
                text = "Terms of Service",
                fontSize = 12.sp,
                fontFamily = NunitoFontFamily,
                color = Primary,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // ── Already have account ────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have account? Let's get back! ",
                fontSize = 14.sp,
                fontFamily = NunitoFontFamily
            )
            Text(
                text = "Sign In",
                fontSize = 14.sp,
                fontFamily = NunitoFontFamily,
                color = Primary,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable { onLogin() }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Next Button ───────────────────────────────────────────
        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Next",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoFontFamily,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignUpScreenPreview(){
    SignUpScreen()
}