package luz.gaspario.tickets_lu

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val contrasena = txtContrasena.text.toString()

            if (nombre.isEmpty()||contrasena.isEmpty()){
                Toast.makeText(this, "Ingresa datos que sean válidos", Toast.LENGTH_SHORT).show()
            } else {
                Log.i("Test de Credenciales", "Nombre: $nombre y Contraseña: $contrasena")
            }
        }

        btnRegister.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().cadenaConexion()
                val addUsuario = objConexion?.prepareStatement("insert into Usuario (UUID_Usuario, nombreUsuario, contrasenaUsuario) values (?, ?, ?)")!!

                addUsuario.setString(1, UUID.randomUUID().toString())
                addUsuario.setString(2, txtNombre.text.toString())
                addUsuario.setString(3, txtContrasena.text.toString())

                addUsuario.executeUpdate()

                withContext(Dispatchers.Main){
                    Toast.makeText(this@MainActivity, "Se ha creado un usuario", Toast.LENGTH_SHORT).show()

                    txtNombre.setText("")
                    txtContrasena.setText("")
                }

                btnRegister.setOnClickListener {
                    val pantallaLogin = Intent(this@MainActivity, Login::class.java)
                    startActivity(pantallaLogin)
                }
            }
        }

    }
}