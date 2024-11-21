package com.platzi.pizza.service;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.persistence.repository.PizzaPageSortRepository;
import com.platzi.pizza.persistence.repository.PizzaRepository;
import com.platzi.pizza.service.dto.UpdatePizzaPriceDto;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Indica que esta clase es un servicio de Spring que contiene la lógica de negocio relacionada con las pizzas.
@Service
@Getter
@Setter
public class PizzaService {
    // Repositorio para interactuar con la base de datos.
    private final PizzaRepository pizzaRepository;

    private final PizzaPageSortRepository pizzaPageAndSortingRepository;

    // Constructor que utiliza inyección de dependencias para inicializar el repositorio.
    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPageSortRepository pizzaPageAndSortingRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPageAndSortingRepository = pizzaPageAndSortingRepository;
    }


    /**
     * Obtiene una lista de todas las pizzas almacenadas en la base de datos.
     * @return Lista de todas las pizzas.
     */
    public Page<PizzaEntity> getAll(int page, int elements){
        Pageable pageRequest = PageRequest.of(page, elements);
        return this.pizzaPageAndSortingRepository.findAll(pageRequest);
    }

    /**
     * Obtiene una lista de todas las pizzas disponibles ordenadas por precio.
     * @return Lista de pizzas disponibles.
     */
    public Page<PizzaEntity> getAvailable(int page, int elements, String sortBy, String sortDireccion){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDireccion), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);

        return pizzaPageAndSortingRepository.findByAvailableTrue(pageRequest);
    }


    public List<PizzaEntity> getByDescription(String description){
        return pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(description);
    }

    public List<PizzaEntity> getWithOutIngredient(String ingredient){
        return pizzaRepository.findByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }

    /**
     * Busca una pizza disponible por su nombre, ignorando mayúsculas y minúsculas.
     * @param name Nombre de la pizza.
     * @return Entidad de la pizza si existe, de lo contrario, null.
     */
    public Optional<PizzaEntity> getByPizzaName(String name){
        return pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name);
    }

    /**
     * Busca una pizza por su ID.
     * @param pizzaId ID de la pizza.
     * @return Entidad de la pizza si existe, de lo contrario, null.
     */
    public PizzaEntity get(int pizzaId){
        return this.pizzaRepository.findById(pizzaId).orElse(null);
    }

    public int countVegan(){
        return pizzaRepository.countByVeganTrue();
    }

    public List<PizzaEntity> getCheapest(Double price){
        return pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    /**
     * Guarda o actualiza una pizza en la base de datos.
     * @param pizzaEntity Entidad de la pizza a guardar.
     * @return Entidad de la pizza después de ser guardada.
     */
    public PizzaEntity save(PizzaEntity pizzaEntity){
        return this.pizzaRepository.save(pizzaEntity);
    }

    /**
     * Verifica si una pizza existe en la base de datos por su ID.
     * @param idPizza ID de la pizza.
     * @return true si existe, de lo contrario, false.
     */
    public boolean exist(int idPizza){
        return pizzaRepository.existsById(idPizza);
    }

    /**
     * Elimina una pizza de la base de datos si existe.
     * @param idPizza ID de la pizza a eliminar.
     */
    public void delete(int idPizza){
        if(this.exist(idPizza)){
            pizzaRepository.deleteById(idPizza);
        }
    }

    @Transactional
    public void updatePrice (UpdatePizzaPriceDto dto){
        pizzaRepository.updatePrice(dto);
    }
}
