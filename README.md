# Бэкенд приложение с микросервисной архитектурой - прототип небольшого банка.

## Описание проекта:
Пользователь отправляте заявку на кредит. Осущесвляется прескопринг заявки, после
чего она сохраняется в БД и в ответ пользователь получает 4 кредитных предложения в зависимости
с разными кредитными условиями (например без страховки, со страховкой, с зарплатным клиентом, 
со страховкой и зарплатным клиентом) или отказ. Пользователь выбирает одно предложение и отправляет запрос на кредит.
После чего заявка на кредит и данные кредита сохраняются в базу.  
После получени полных данных от пользователя Сделка сохранеяется в обновленную заявку в БД.

Данный проект в настоящее время активно разрабатывается.   
Здесь не доконца описан полный функционал приложения.

## Особенности проекта на данном этапе:
Весь работоспособный код находется в ветке develop. А так же в ветках отведенных от develop.

## Технологии  используемые в проекте:

- Java 21
- SpringBoot 2.7
- PostgreSQL
- Spring Data JPA
- JUnit
- Lombok
- MapStruct
- Feign Client
- Liquibase

## Ссылка на документации:

##### К микросервису Calculator:
```http://localhost:8080/swagger-ui/index.html#/```

##### К микросервису Deal:
```http://localhost:8081/swagger-ui/index.html#/```