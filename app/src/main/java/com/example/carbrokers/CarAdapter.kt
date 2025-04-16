package com.example.carbrokers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        Glide.with(holder.itemView.context)
            .load(car.img)
            .into(holder.carImage)
        //Manejar clic en el coche
        holder.itemView.setOnClickListener{
            onItemClick(car)
        }
    }

    //función que devuelve el total de elementos de la lista
    override fun getItemCount(): Int = carList.size

}