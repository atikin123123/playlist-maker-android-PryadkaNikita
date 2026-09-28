package com.practicum.myapplication.ui.settings

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.myapplication.R
import androidx.core.net.toUri

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var isDarkTheme by remember { mutableStateOf(false) }

    // Строки
    val shareText = stringResource(R.string.share_link)
    val supportEmail = stringResource(R.string.support_email)
    val supportSubject = stringResource(R.string.support_subject)
    val supportMessage = stringResource(R.string.support_message)
    val agreementLink = stringResource(R.string.offer_link)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.white)) // Ресурс
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = colorResource(R.color.black) // Ресурс
                )
            }
            Text(
                text = stringResource(R.string.settings_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.black), // Ресурс
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Переключатель
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.settings_theme),
                fontSize = 16.sp,
                color = colorResource(R.color.black), // Ресурс
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { isDarkTheme = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorResource(R.color.yp_blue), // Ресурс
                    checkedTrackColor = colorResource(R.color.yp_blue).copy(alpha = 0.5f)
                )
            )
        }

        // Кнопки действий
        SettingsItem(stringResource(R.string.settings_share), Icons.Default.Share) {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            context.startActivity(Intent.createChooser(shareIntent, null))
        }

        SettingsItem(stringResource(R.string.settings_support), Icons.Default.HeadsetMic) {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:".toUri()
                putExtra(Intent.EXTRA_EMAIL, arrayOf(supportEmail))
                putExtra(Intent.EXTRA_SUBJECT, supportSubject)
                putExtra(Intent.EXTRA_TEXT, supportMessage)
            }
            context.startActivity(emailIntent)
        }

        SettingsItem(stringResource(R.string.settings_agreement), Icons.AutoMirrored.Filled.KeyboardArrowRight) {
            context.startActivity(Intent(Intent.ACTION_VIEW, agreementLink.toUri()))
        }
    }
}

@Composable
fun SettingsItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = colorResource(R.color.black), // Ресурс
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colorResource(R.color.yp_gray) // Ресурс
        )
    }
}