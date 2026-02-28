package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;
import org.w3c.dom.ls.LSOutput;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {

  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  /*
  В этой задачке я создаю мапу id to PersonWithResumes и заполняю ее изначально объектами с установленными персонами
  и пустыми сетами, это происходит за O(n), n - персоны. Потом использую эту мапу чтобы передать id персон в метод.
  И далее прохожу по сету резюме и добавляю резюме в сет к соответствующей персоне по ее id за O(m), m - кол-во резюме.
  Итоговая сложность будет O(n + m), линейная, я думаю самый быстрый вариант.
   */
  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {

    Set<Resume> resumes = personService.findResumes(persons.stream()
        .map(Person::id)
        .collect(Collectors.toSet())
    );

    Map<Integer, Set<Resume>> personIdResumesMap = resumes.stream()
        .collect(Collectors.groupingBy(
            Resume::personId,
            Collectors.toSet()
        ));

    return persons.stream()
        .map(person ->
            new PersonWithResumes(person, personIdResumesMap.getOrDefault(person.id(), Set.of()))
        )
        .collect(Collectors.toSet());
  }
}
