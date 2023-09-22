package ru.petrolplus.pos.mainscreen.ui.settings.debug

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.petrolplus.pos.persitence.dto.ServicesDTO
import ru.petrolplus.pos.mainscreen.ui.settings.SettingsViewModel
import ru.petrolplus.pos.resources.ResourceHelper
import java.lang.StringBuilder

@Composable
fun SettingsDebugScreen(viewModel: SettingsViewModel) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp, 8.dp)) {
            var servicesString by remember { mutableStateOf("") }

            Text(
                text ="Настройки приложения",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                val file = ResourceHelper.getAssetFile("services.json")?.readText() ?: ""
                val importedServices = ResourceHelper.parseJson(file, ServicesDTO::class.java)
                val servicesStringBuilder = StringBuilder()
                importedServices.services.forEach {
                    servicesStringBuilder.append(it.toString())
                    servicesStringBuilder.append("\n\n")
                    servicesString = servicesStringBuilder.toString()
                }
                viewModel.addOrReplaceServices(importedServices)
            }) {
                Text(text = "загрузить сервисы из ассетов в б.д")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = servicesString)
        }

    }
    
    
}