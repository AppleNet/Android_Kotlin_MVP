package com.example.llc.binder;

import com.example.llc.binder.Person;

interface IPersonInterface {

    void addPerson(in Person person);
    List<Person> getPersons();
}