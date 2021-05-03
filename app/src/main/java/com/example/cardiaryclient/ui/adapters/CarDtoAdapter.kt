package com.example.cardiaryclient.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.cardiaryclient.databinding.CarItemBinding
import com.example.cardiaryclient.dto.CarDto

class CarDtoAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<CarDtoAdapter.CarViewHolder>() {

    inner class CarViewHolder(val binding: CarItemBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object :androidx.recyclerview.widget. DiffUtil.ItemCallback<CarDto>() {
        override fun areItemsTheSame(oldItem: CarDto, newItem: CarDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CarDto, newItem: CarDto): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = androidx.recyclerview.widget.AsyncListDiffer(this, diffCallback)
    var cars: List<CarDto>
       get() = differ.currentList
       set(list) {differ.submitList(list)}

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): CarViewHolder {
        return CarViewHolder(CarItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.binding.apply {
            val car = cars[position]
            tvBrand.text = car.brand.name
            tvModel.text = car.model.name
            tvMielage.text = car.mileage.toString()
            tvYear.text = car.year.toString()
            Glide.with(holder.itemView)
                .load(car.photoUrl)
                .into(ivImage)
//            ivImage.setImageURI(Uri.parse(car.photoUrl))
        }
    }

    override fun getItemCount(): Int {
        return cars.size
    }
}