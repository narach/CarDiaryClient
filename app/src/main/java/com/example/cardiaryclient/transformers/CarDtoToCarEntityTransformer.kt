package com.example.cardiaryclient.transformers

import com.example.cardiaryclient.entity.CarEntity
import com.example.cardiaryclient.models.Content

class CarDtoToCarEntityTransformer {
    fun transform(carDto: Content) : CarEntity {
        return CarEntity(carDto.model.name, carDto.brand.name, carDto.year)
    }
}