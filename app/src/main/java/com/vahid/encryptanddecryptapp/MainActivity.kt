package com.vahid.encryptanddecryptapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vahid.encryptanddecryptapp.androidcrypto.CryptoManager
import com.vahid.encryptanddecryptapp.ui.theme.EncryptAndDecryptAppTheme
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cryptoManager = CryptoManager()
        setContent {
            EncryptAndDecryptAppTheme {
                // A surface container using the 'background' color from the theme
                var messageToEncrypt by remember {
                    mutableStateOf("")
                }
                var messageToDecrypt by remember {
                    mutableStateOf("")
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    TextField(
                        value = messageToEncrypt,
                        onValueChange = { messageToEncrypt = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(text = "encrypt string")
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = {
                            val bytes = messageToEncrypt.encodeToByteArray()
                            val file = File(filesDir, "secret.txt")
                            if (!file.exists()) {
                                file.createNewFile()
                            }
                            val fos = FileOutputStream(file)
                            messageToDecrypt = cryptoManager.encrypt(
                                bytes = bytes,
                                outputStream = fos
                            ).decodeToString()
                        }) {
                            Text(text = "Encrypt")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            val file = File(filesDir, "secret.txt")
                            messageToEncrypt = cryptoManager.decrypt(
                                inputStream = FileInputStream(file)
                            ).decodeToString()

                        }) {
                            Text(text = "Decrypt")
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EncryptAndDecryptAppTheme {
        Greeting("Android")
    }
}