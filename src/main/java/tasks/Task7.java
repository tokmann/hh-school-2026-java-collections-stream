package tasks;

import common.Company;
import common.Vacancy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/*
Из коллекции компаний необходимо получить всевозможные различные названия вакансий
 */
public class Task7 {

  /*
  Здесь сделал полностью с помощью стримов, использовал flatMap так как у нас вакансии это тоже Set, так мы
  получим стримы вакансий, а затем уже для каждой вакансии в этих стримах с помощью map получаем ее title и собираем
  все в set. И есть небольшая проверка чтобы не было NullPointerException
   */
  public static Set<String> vacancyNames(Collection<Company> companies) {
    return companies.stream()
        .filter(company -> company.getVacancies() != null)
        .flatMap(company -> company.getVacancies().stream())
        .map(Vacancy::getTitle)
        .collect(Collectors.toSet());
  }

}
