package com.example.carbrokers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text

//adaptador del recyclerview, recibe una lista de coches y una función para manejar los clics
class CarAdapter (
    private val carList: List<Car>, //lista de coches a mostrar
    private val onItemClick: (Car) -> Unit //acción al hacer click en un coche de la lista
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carImage: ImageView = itemView.findViewById(R.id.imageCar) //imagen del coche
        val carTitle: TextView = itemView.findViewById(R.id.textTitle) //marca y modelo del coche
        val carPrice: TextView = itemView.findViewById(R.id.textPrice) //precio del coche
    }

    ///inflamos la ivsta de cada coche
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_car, parent, false) //usara el layout de item_car
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CarViewHolder,
        position: Int,
    ) {
        val car = carList[position] //obtener el coche actual de la lista
        //texto con la marca y el modelo
        holder.carTitle.text = "${car.car} ${car.car_model}"
        //precio del coche
        holder.carPrice.text = car.price
        //imagen del coche, cargandola con glide desde la URL
        Log.d("Glide", "Cargando imagen: ${car.img}")
        //Convertimos la marca a minúsculas para comparar
        val marca = car.car.lowercase()
        //Seleccionamos la imagen correspondiente según la marca
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
            else -> R.drawable.placeholder_image //imagen por defecto
        }
        // Cargamos la imagen local desde drawable
        Glide.with(holder.itemView.context)
            .load(imageRes)
            .into(holder.carImage)
        //Manejar clic en el coche
        holder.itemView.setOnClickListener {
            val fragment = CarDetailFragment()

            // Preparamos el coche para pasarlo al nuevo fragmento
            val bundle = Bundle()
            bundle.putSerializable("car", car)
            fragment.arguments = bundle

            // Iniciamos el fragmento desde el contexto del holder
            val activity = holder.itemView.context as? AppCompatActivity
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, fragment)
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    //función que devuelve el total de elementos de la lista
    override fun getItemCount(): Int = carList.size

}