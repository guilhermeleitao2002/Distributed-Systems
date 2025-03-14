package pt.tecnico.addressbook.server.domain;

import pt.tecnico.addressbook.grpc.AddressBookList;
import pt.tecnico.addressbook.grpc.PersonInfo;
import pt.tecnico.addressbook.grpc.PersonInfo.PhoneType;
import pt.tecnico.addressbook.server.domain.exception.DuplicatePersonInfoException;
import pt.tecnico.addressbook.server.domain.exception.PersonNotFoundException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AddressBook {

    private ConcurrentHashMap<String, Person> people = new ConcurrentHashMap<>();

    public AddressBook() {
    }

    public void addPerson(String name, String nickname, String email, int phoneNumber, PhoneType type) throws DuplicatePersonInfoException {
        if(people.putIfAbsent(email, new Person(name, nickname, email, phoneNumber, type)) != null) {
            throw new DuplicatePersonInfoException(email);
        }
    }

    public AddressBookList proto() {
        return AddressBookList.newBuilder()
                .addAllPeople(people.values().stream().map(Person::proto).collect(Collectors.toList()))
                .build();
    }

    public PersonInfo searchPerson(String email) {
        Person person = people.get(email);
        if(person == null) {
            throw new PersonNotFoundException(email);
        }
        return person.proto();
    }

    public void removeAll(String name, String nickname) {
        if(people.values().stream().noneMatch(person -> person.getName().equals(name) || person.getNickname().equals(nickname))) {
            throw new PersonNotFoundException(name);
        } else {
            people.values().removeIf(person -> person.getName().equals(name) || person.getNickname().equals(nickname));
        }
    }
}
