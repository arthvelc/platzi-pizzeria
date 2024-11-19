package com.platzi.pizza.persistence.repository;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {

    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();

    //Traer una pizza a partir de su nombre
    Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);

    //Ahora vamos a hacer un query method con containing en la descripción
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);

    //Ahora que no incluyan un ingrediente determinado
    List<PizzaEntity> findByAvailableTrueAndDescriptionNotContainingIgnoreCase(String ingredient);

    //Contar cuantas pizzas veganas ofrecemos
    int countByVeganTrue();

    //ahora vamos a buscar el top 3 de las pizzas más baratas
    List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(Double price);
}
