package com.example.carbrokers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView //lista de coches favcoritos
    private lateinit var emptyText: TextView //texto si no hay favoritos
    private lateinit var carAdapter: CarAdapter
    private var favoriteCars: List<FavoriteCar> = listOf() //lista vacía de coches favoritos

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerFavorites)
        emptyText = view.findViewById(R.id.textNoFavorites)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadFavorites()
    }

    private fun loadFavorites() {
        //corutina asincrona para no bloquear
        lifecycleScope.launch {
            val dao = AppDatabase.getDatabase(requireContext()).favoriteCarDao() //accedemos al dao
            favoriteCars = withContext(Dispatchers.IO) {
                dao.getAllFavorites() //cargamos todos los favoritos desde la BBDD
            }

            if(favoriteCars.isNotEmpty()) {
                emptyText.visibility = View.GONE //ocultar en caso de haber favoritos

                val carList = favoriteCars.map{
                    Car(
                        id = it.id,
                        car= it.car,
                        car_model = it.car_model,
                        car_color = it.car_color,
                        car_model_year = it.car_model_year,
                        price = it.price,
                        img = it.img
                    )
                }

                //adaptador de car que vamos areutilizar
                //con ayuda de gpt he implementado que se vuelvan a mostrar los detalles del coche al pulsar
                carAdapter = CarAdapter(carList) { car ->
                    val fragment = CarDetailFragment().apply {
                        arguments = Bundle().apply {
                            putSerializable("car", car)
                        }
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                recyclerView.adapter = carAdapter
            } else {
                //mostramos mensaje de lista vacía
                emptyText.visibility = View.VISIBLE
            }
        }
    }
}
