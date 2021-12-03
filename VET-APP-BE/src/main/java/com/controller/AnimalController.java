package com.controller;

import com.enums.AnimalStatus;
import com.model.ResponseTemplate;

import com.repository.RoleDao;
import com.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import com.model.Animal;
import com.repository.AnimalDao;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/animal")
public class AnimalController {

    @Autowired
    private UserDao userRepository;

    @Autowired
    private RoleDao roleRepository;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private AnimalDao animalRepository;

    @RequestMapping(value = "/getAvailableAnimal", method = RequestMethod.GET)
    public ResponseTemplate fetchByStatus(HttpServletRequest request) {
        ResponseTemplate ret = new ResponseTemplate();

        Iterable<Animal> animals = animalRepository.findAnimalByStatus();

        //Iterable<Animal> animals1 = animalRepository.findAllByStatus("available");
        ret.setData(animals);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all animal succ");

        return ret;
    }
    @RequestMapping(value = "/setUnavailableStatus", method = RequestMethod.GET)
    public ResponseTemplate updateAnimalStatus(@RequestParam("id") int id, HttpServletRequest request) {
        Optional<Animal> animal= animalRepository.findById(id);
        AnimalStatus status = AnimalStatus.Unavailable;
        Animal animalObj = animal.get();
        animalObj.setStatus(status);

        animalRepository.save(animalObj);
        ResponseTemplate ret = new ResponseTemplate();
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("update animal succ");
        return ret;
    }




    @RequestMapping(value = "/getAllAnimal", method = RequestMethod.GET)
    public ResponseTemplate fetchAllAnimal(HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        Iterable<Animal> animals = animalRepository.findAll();
        ret.setData(animals);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all animal succ");

        return ret;
    }

    @RequestMapping(value = "/getAnimalById", method = RequestMethod.GET)
    public ResponseTemplate fetchById(@RequestParam("id") int id, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        Optional<Animal> animal= animalRepository.findById(id);
        ret.setData(animal);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find animal by id succ");
        return ret;
    }


    @RequestMapping(value = "/deleteAnimalById", method = RequestMethod.DELETE)
    public ResponseTemplate deleteAnimalConfig(@RequestParam("id") int id, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        if(animalRepository.existsById(id)){
            // if id exists user can be deleted
            animalRepository.deleteById(id);
            ret.setCode(HttpStatus.OK.value());
            ret.setMessage("delete Animal succ");
        }else{
            ret.setCode(HttpStatus.BAD_REQUEST.value());
            ret.setMessage("Animal does not exist cannot be deleted");
        }
        return ret;
    }


    @RequestMapping(value = "/addAnimal", method = RequestMethod.POST)
    public ResponseTemplate addAnimalConfig(@RequestBody Animal animal, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        if (animalRepository.existsById(animal.getId())) {
            ret.setCode(HttpStatus.BAD_REQUEST.value());
            ret.setMessage("ID already taken");
            return ret;
        }

        animalRepository.save(animal);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("animal added succ");

        return ret;
    }


    @RequestMapping(value = "/updateAnimal", method = RequestMethod.POST)
    public ResponseTemplate updateAnimalConfig(@RequestBody Animal animal, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        animalRepository.save(animal);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("update animal succ");

        return ret;
    }

}
