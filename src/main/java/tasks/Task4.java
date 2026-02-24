package tasks;

import common.ApiPersonDto;
import common.Person;
import common.PersonConverter;
import java.util.ArrayList;
import java.util.List;

/*
Задача 4
Список персон класса Person необходимо сконвертировать в список ApiPersonDto
(предположим, что это некоторый внешний формат)
Конвертер для одной персоны - personConverter.convert()
FYI - DTO = Data Transfer Object - распространенный паттерн, можно погуглить
 */
public class Task4 {

  private final PersonConverter personConverter;

  public Task4(PersonConverter personConverter) {
    this.personConverter = personConverter;
  }

  /*
  Здесь просто использовал map в стриме чтобы получать из одного объекта какой то другой. Написал ссылку на метод вместо
  лямбды. Защиту от null никакую тут уже не сделать, я не создавал convert(), но и не надо ее тут, если маппинг хорошо
  сделан.
   */
  public List<ApiPersonDto> convert(List<Person> persons) {
    return persons.stream()
        .map(personConverter::convert)
        .toList();
  }
}
