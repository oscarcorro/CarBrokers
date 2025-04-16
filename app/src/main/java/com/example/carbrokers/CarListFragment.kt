package com.example.carbrokers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//fragment que muestra la lista de coches
class CarListFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView

    val brandImages = mapOf(
        "BMW" to "https://cdn.pixabay.com/photo/2016/11/29/10/07/audi-1868726_1280.jpg",
        "Volkswagen" to "https://cdn.pixabay.com/photo/2017/01/06/19/15/volkswagen-1959194_1280.jpg",
        "Mitsubishi" to "https://cdn.pixabay.com/photo/2012/05/29/00/43/car-49278_1280.jpg",
        "Jeep" to "https://cdn.pixabay.com/photo/2020/01/08/13/55/jeep-4749589_1280.jpg",
        "Saturn" to "https://cdn.pixabay.com/photo/2013/07/12/15/52/classic-car-150815_1280.png",
        "Mercedes-Benz" to "https://cdn.pixabay.com/photo/2015/01/19/13/51/car-604019_1280.jpg",
        "Chevrolet" to "https://cdn.pixabay.com/photo/2015/01/19/13/51/car-604020_1280.jpg",
        "Dodge" to "https://cdn.pixabay.com/photo/2017/05/14/23/50/dodge-2312214_1280.jpg",
        "Ford" to "https://cdn.pixabay.com/photo/2015/01/19/13/51/car-604023_1280.jpg",
        "Toyota" to "https://cdn.pixabay.com/photo/2017/02/26/00/26/toyota-2102855_1280.jpg",
        "Land Rover" to "https://cdn.pixabay.com/photo/2015/02/11/01/56/land-rover-631446_1280.jpg"
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflar el layout del fragmento
        return inflater.inflate(R.layout.fragment_car_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //obtenemos el recyclerview
        recyclerView = view.findViewById(R.id.recycleCars)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //cargar los coches desde la API
        fetchCarData()
    }
    //función que llama a la API con retrofit
    private fun fetchCarData() {
        val call = RetrofitInstance.api.getCars()

        call.enqueue(object: Callback<carResponse> {
            override fun onResponse(call: Call<carResponse>, response: Response<carResponse>) {
                if(response.isSuccessful) {
                    val carList = response.body()?.cars ?: emptyList()
                    carList.forEach { car ->
                        car.img = brandImages[car.car] ?: "https://cdn.pixabay.com/photo/2012/05/29/00/43/car-49278_1280.jpg"
                    }

                    //adapter con los datos
                    val adapter = CarAdapter(carList){ selectedCar ->
                        //acción al hacer click en un coche
                        Toast.makeText(requireContext(), "${selectedCar.car} seleccionado", Toast.LENGTH_SHORT).show()
                    }
                    recyclerView.adapter = adapter
                }else {
                    //si hay error en la respuesta
                    Toast.makeText(requireContext(), "Error al cargar coches", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<carResponse>, t: Throwable) {
                //error
                Log.e("CarListFragment", "Error: ${t.message}")
                Toast.makeText(requireContext(), "Fallo de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}