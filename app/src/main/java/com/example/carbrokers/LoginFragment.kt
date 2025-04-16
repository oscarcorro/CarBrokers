package com.example.carbrokers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

//Fragmento de Login (pantalla de inicio de sesión)
class LoginFragment: Fragment() {
    //crear interfaz de login
    override fun onCreateView(
        inflater: LayoutInflater, //convierte XML en vista
        container: ViewGroup?, //padre donde estara el fragment
        savedInstanceState: Bundle? //datos guardados si el fragment ya existia
    ): View? {
        //devuelve el layout que usa este fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    //una vez creada la vista
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //buscamos los elementos del layout por su ID
        val textUser = view.findViewById<EditText>(R.id.textUser) //campo de texto del usuario
        val textPassword = view.findViewById<EditText>(R.id.textPassword) //campo de texto de la contraseña
        val botonLogin = view.findViewById<Button>(R.id.botonLogin) //boton de iniciar sesión

        //cuando se hace click en el botón:
        botonLogin.setOnClickListener{
            //scar lo que se ha escrito
            val username = textUser.text.toString()
            val password = textPassword.text.toString()

            if(username.isNotBlank() && password.isNotBlank()){
                //mensaje de que se ha hecho bien el "login"
                Toast.makeText(requireContext(), "Login hecho con exito", Toast.LENGTH_SHORT).show()

                //cambiamos al siguiente fragment, reemplazándolo con otro de prueba
                parentFragmentManager.commit{
                    replace(R.id.fragmentContainerView, CarListFragment())
                    addToBackStack(null) //opción de volver atrás con el botón
                }
            }else{
                //mostramos un error si algunos de los campos esta vacíos
                Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}