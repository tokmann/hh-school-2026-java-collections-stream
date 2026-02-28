package tasks;

import common.Person;

import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {

  /*
  В этом задании ничего необычного, просто несколько сравнений с защитой от null значений, пусть null будет в конце
   */
  public static List<Person> sort(Collection<Person> persons) {
    return persons.stream().sorted(Comparator
        .comparing(Person::secondName, Comparator.nullsLast(String::compareTo))
        .thenComparing(Person::firstName, Comparator.nullsLast(String::compareTo))
        .thenComparing(Person::createdAt, Comparator.nullsLast(Instant::compareTo)))
        .toList();
  }
}
