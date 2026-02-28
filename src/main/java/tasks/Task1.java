package tasks;

import common.Person;
import common.PersonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  /*
  В общем, здесь создается мапа id to Person и заполняется за O(m), m - кол-во персон, вставка в среднем О(1).
  Теперь создаем итоговый список с капасити n, n - кол-во id, чтобы можно было его заполнять с помощью add и при этом
  не будет тратиться время на расширение, то есть если бы превысилась капасити. И затем мы проходим в цикле за O(n),
  n - кол-во id, и добавляем в список соотв. персону с id который сейчас текущий в списке personIds. И так как у нас
  размер сета с персонами всегда <= размера списка со всеми айдишниками, то можно положить что в худшем случае у нас
  будет длина сета с персонами равна длине списка айдишников, то есть равна n. Тогда будет сложность O(2 * n) и это
  равно O(n), как то так
   */
  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);

    Map<Integer, Person> idPersonMap = persons.stream()
        .collect(Collectors.toMap(
            Person::id,
            Function.identity()
        ));

    return personIds.stream()
        .map(idPersonMap::get)
        .collect(Collectors.toList());
  }
}
