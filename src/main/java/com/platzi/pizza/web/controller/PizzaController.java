package com.platzi.pizza.web.controller;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.service.PizzaService;
import com.platzi.pizza.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

// Indica que esta clase es un controlador REST que maneja las solicitudes relacionadas con las pizzas.
@RestController
@RequestMapping("/api/pizzas") // Define el endpoint base para las solicitudes relacionadas con pizzas.
public class PizzaController {

    // Servicio que contiene la lógica de negocio para manejar las pizzas.
    private final PizzaService pizzaService;

    // Constructor con inyección de dependencias para inicializar el servicio.
    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    /**
     * Endpoint para obtener todas las pizzas.
     * @return Lista de todas las pizzas almacenadas.
     */
    @GetMapping
    public ResponseEntity<Page<PizzaEntity>> getAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int elements){
        return ResponseEntity.ok(this.pizzaService.getAll(page, elements));
    }

    /**
     * Endpoint para obtener las pizzas disponibles (que están en venta).
     * @return Lista de pizzas disponibles.
     */
    @GetMapping("/available")
    public ResponseEntity<Page<PizzaEntity>> getAvailable(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "2") int elements,
                                                          @RequestParam(defaultValue = "price") String sortBy,
                                                          @RequestParam(defaultValue = "ASC") String sortDirection){
        return ResponseEntity.ok(pizzaService.getAvailable(page, elements, sortBy, sortDirection));
    }

        /**
     * Endpoint to get pizzas by their description.
     *
     * @param description The description of the pizzas to search for.
     * @return A ResponseEntity containing a list of PizzaEntity objects that match the given description.
     */
    @GetMapping("whit/{description}")
    public ResponseEntity<List<PizzaEntity>> getByDescription(@PathVariable String description){
        return ResponseEntity.ok(pizzaService.getByDescription(description));
    }

    @GetMapping("/without/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWithOut(@PathVariable String ingredient){
        return ResponseEntity.ok(pizzaService.getWithOutIngredient(ingredient));
    }

    /**
     * Endpoint para obtener una pizza específica por su ID.
     * @param idPizza ID de la pizza a buscar.
     * @return La entidad de la pizza encontrada.
     */
    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> get(@PathVariable int idPizza){
        return ResponseEntity.ok(pizzaService.get(idPizza));
    }

    @GetMapping("/vegan")
    public int countVegan(){
        return pizzaService.countVegan();
    }

    /**
     * Endpoint para obtener una pizza disponible por su nombre (ignora mayúsculas y minúsculas).
     * @param name Nombre de la pizza a buscar.
     * @return La entidad de la pizza encontrada.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> getByName(@PathVariable String name) {
        return pizzaService.getByPizzaName(name)
                .map(ResponseEntity::ok) // Si el valor existe, se devuelve con 200 OK
                .orElse(ResponseEntity.badRequest().build()); // Si no existe, devuelve 400 Bad Request
    }

    @GetMapping("/cheapest/{price}")
    public ResponseEntity<List<PizzaEntity>> getCheapest(@PathVariable Double price){
        return ResponseEntity.ok(pizzaService.getCheapest(price));
    }


    /**
     * Endpoint para agregar una nueva pizza.
     * @param pizza Entidad de la pizza a agregar.
     * @return La entidad de la pizza recién creada o un error si ya existe.
     */
    @PostMapping
    public ResponseEntity<PizzaEntity> add(@RequestBody PizzaEntity pizza){
        if(pizza.getIdPizza() == null || !this.pizzaService.exist(pizza.getIdPizza())){
            return ResponseEntity.ok(pizzaService.save(pizza));
        }

        return ResponseEntity.badRequest().build(); // Retorna un error si la pizza ya existe.
    }

    /**
     * Endpoint para actualizar una pizza existente.
     * @param pizza Entidad de la pizza con los datos actualizados.
     * @return La entidad de la pizza actualizada o un error si no existe.
     */
    @PutMapping
    public ResponseEntity<PizzaEntity> update(@RequestBody PizzaEntity pizza){
        if(pizza.getIdPizza() != null && pizzaService.exist(pizza.getIdPizza())){
            return ResponseEntity.ok(pizzaService.save(pizza));
        }

        return ResponseEntity.badRequest().build(); // Retorna un error si la pizza no existe.
    }

    @PutMapping("/price")
    public ResponseEntity<Void> updatePrice(@RequestBody UpdatePizzaPriceDto dto){
        if(pizzaService.exist(dto.getPizzaId())){
            pizzaService.updatePrice(dto);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build(); // Retorna un error si la pizza no existe.
    }

    /**
     * Endpoint para eliminar una pizza existente por su ID.
     * @param idPizza ID de la pizza a eliminar.
     * @return Código HTTP 200 si se elimina correctamente o un error si no existe.
     */
    @DeleteMapping("/{idPizza}")
    public ResponseEntity<Void> delete(@PathVariable int idPizza){
        if(pizzaService.exist(idPizza)){
            pizzaService.delete(idPizza);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build(); // Retorna un error si la pizza no existe.
    }
}
