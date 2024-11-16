package com.platzi.pizza.persistence.repository;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {

    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();

    //Traer una pizza a partir de su nombre
    PizzaEntity findAllByAvailableTrueAndNameIgnoreCase(String name);

    //Ahora vamos a hacer un query method con containing en la descripción
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);

    //Ahora que no incluyan un ingrediente determinado
    List<PizzaEntity> findByAvailableTrueAndDescriptionNotContainingIgnoreCase(String ingredient);
}
