package com.example.person;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public final class PersonServiceDefault implements PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceDefault.class);

    private final PersonRepository personRepository;

    public PersonServiceDefault(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public Person create(String name, Integer age) {

        final var person = new Person();
        person.setName(name);
        person.setAge(age);

        final var personSaved = personRepository.save(person);
        LOGGER.info("Person saved (person={})", personSaved);

        return personSaved;
    }

    @Override
    public Person findOne(UUID personId) throws PersonNotFoundException {
        final var personNotCached = personRepository.findById(personId);
        if (personNotCached.isPresent()) {
            LOGGER.info("Person retrieved from database (personId={})", personId);

            return personNotCached.get();
        }

        LOGGER.info("Person not found (personId={})", personId);
        throw new PersonNotFoundException(personId);
    }
}
