package com.platzi.pizza.service;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.persistence.repository.PizzaRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Indica que esta clase es un servicio de Spring que contiene la lógica de negocio relacionada con las pizzas.
@Service
@Getter
@Setter
public class PizzaService {
    // Repositorio para interactuar con la base de datos.
    private final PizzaRepository pizzaRepository;

    // Constructor que utiliza inyección de dependencias para inicializar el repositorio.
    @Autowired
    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    /**
     * Obtiene una lista de todas las pizzas almacenadas en la base de datos.
     * @return Lista de todas las pizzas.
     */
    public List<PizzaEntity> getAll(){
        return this.pizzaRepository.findAll();
    }

    /**
     * Obtiene una lista de todas las pizzas disponibles ordenadas por precio.
     * @return Lista de pizzas disponibles.
     */
    public List<PizzaEntity> getAvailable(){
        List<PizzaEntity> availablePizzas = pizzaRepository.findAllByAvailableTrueOrderByPrice();
        return availablePizzas;
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
    public PizzaEntity getByPizzaName(String name){
        return pizzaRepository.findAllByAvailableTrueAndNameIgnoreCase(name);
    }

    /**
     * Busca una pizza por su ID.
     * @param pizzaId ID de la pizza.
     * @return Entidad de la pizza si existe, de lo contrario, null.
     */
    public PizzaEntity get(int pizzaId){
        return this.pizzaRepository.findById(pizzaId).orElse(null);
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
}
