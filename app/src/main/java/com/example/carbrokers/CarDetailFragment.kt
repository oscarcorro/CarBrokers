package com.example.carbrokers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarDetailFragment : Fragment() {

    // Variable donde guardaremos el coche recibido
    private var selectedCar: Car? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recuperamos el coche enviado desde el fragmento anterior
        selectedCar = arguments?.getSerializable("car") as? Car
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtenemos las vistas del layout
        val imageView = view.findViewById<ImageView>(R.id.imageCarDetail)
        val textTitle = view.findViewById<TextView>(R.id.textTitleDetail)
        val textPrice = view.findViewById<TextView>(R.id.textPriceDetail)
        val textYear = view.findViewById<TextView>(R.id.textYear)
        val textColor = view.findViewById<TextView>(R.id.textColor)
        //botón para añadir a favoritos
        val btnFav = view.findViewById<Button>(R.id.botonAddToFavorites)

        //Si hay coche recibido, lo mostramos
        selectedCar?.let { car ->
            textTitle.text = "${car.car} ${car.car_model}"
            textPrice.text = "Precio: ${car.price}"
            textYear.text = "Año: ${car.car_model_year}"
            textColor.text = "Color: ${car.car_color}"

            // Cargar la misma imagen que usábamos en el RecyclerView
            val marca = car.car.lowercase()
            val imageRes = when {
                "porsche" in marca -> R.drawable.porsche
                "bmw" in marca -> R.drawable.bmw
                "audi" in marca -> R.drawable.audi
                "mercedes" in marca -> R.drawable.mercedes
                "volkswagen" in marca -> R.drawable.volkswagen
                "toyota" in marca -> R.drawable.toyota
                "chevrolet" in marca -> R.drawable.chevrolet
                "mitsubishi" in marca -> R.drawable.mitshubishi
                "saturn" in marca -> R.drawable.saturn
                "jeep" in marca -> R.drawable.jeep
                "dodge" in marca -> R.drawable.dodge
                "isuzu" in marca -> R.drawable.isuzu
                "mazda" in marca -> R.drawable.mazda
                "volvo" in marca -> R.drawable.volvo
                "gmc" in marca -> R.drawable.gmc
                "cadillac" in marca -> R.drawable.cadillac
                "ford" in marca -> R.drawable.ford
                else -> R.drawable.placeholder_image
            }

            Glide.with(requireContext())
                .load(imageRes)
                .into(imageView)

            //al pulsar botn favoritos, se añade
            btnFav.setOnClickListener {
                val favorite = FavoriteCar(
                    id = car.id,
                    car = car.car,
                    car_model = car.car_model,
                    car_color = car.car_color,
                    car_model_year = car.car_model_year,
                    price = car.price,
                    img = car.img
                )

                //meter en facvoritos con Room
                CoroutineScope(Dispatchers.IO).launch {
                    val db = AppDatabase.getDatabase(requireContext())
                    db.favoriteCarDao().insertFavorite(favorite)
                }

                //mensaje de confirmación
                Toast.makeText(requireContext(), "Añadido a favoritos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
