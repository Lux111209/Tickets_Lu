package luz.gaspario.tickets_lu

import Modelo.ClaseConexion
import android.annotation.SuppressLint
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

class Login : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnIniciar = findViewById<Button>(R.id.btnIniciar)

        btnIniciar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val contrasena = txtContrasena.text.toString()

            if (nombre.isEmpty()||contrasena.isEmpty()){
                Toast.makeText(this, "Ingresa datos que sean válidos", Toast.LENGTH_SHORT).show()
            } else {
                Log.i("Test de Credenciales", "Nombre: $nombre y Contraseña: $contrasena")
            }
        }

        btnIniciar.setOnClickListener {
            val pantallaPrincipal = Intent(this, Tickets::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().cadenaConexion()
                val ComprobarUsuario = objConexion?.prepareStatement("select * from Usuario where nombreUsuario = ? AND contrasenaUsuario = ?")!!

                ComprobarUsuario.setString(1, txtNombre.text.toString())
                ComprobarUsuario.setString(2, txtContrasena.text.toString())

                val resultado = ComprobarUsuario.executeQuery()
                if (resultado.next()){
                    startActivity(pantallaPrincipal)
                }
                withContext(Dispatchers.Main){
                    Toast.makeText(this@Login, "Se ha creado un usuario", Toast.LENGTH_SHORT).show()

                    txtNombre.setText("")
                    txtContrasena.setText("")
                }
                btnIniciar.setOnClickListener {
                    val pantallaLogin = Intent(this@Login, Login::class.java)
                    startActivity(pantallaLogin)
                }
            }

        }
    }
}