# WebApp
MVC

v.0.0.1 [Обслуживание веб-контента с помощью Spring MVC](https://spring.io/guides/gs/serving-web-content/),
<br/>
[Spring Boot: делаем простое веб приложение на Java (простой сайт)](https://www.youtube.com/watch?v=jH17YkBTpI4&list=PLU2ftbIeotGoGSEUf54LQH-DgiQPF2XRO&index=1)

* change thymeleaf to mustache

v.0.0.2 [Доступ к данным с помощью MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

* Spring Boot JPA (Hibernate): добавляем базу данных в веб приложение на Java
* добавляем сообщения в бд
* фильтр сообщений по тегу
* постгрес в докере

v.0.0.3 Spring Boot Security: добавлена регистрация и авторизация пользователей в приложение

* Добавлен RegistrationController
* GreetingsController -> MainController
* add UserRepository
* add WebSecurityConfig
* registration.html

v.0.0.4 Hibernate связи между таблицами базы данных (one to many)

* Бин из спринг секъюрити UserDetails перенесен в UserService
* Добавлен автор в сооющения
* Отображение сообщений переведено в таблицу

v0.0.5 Фикс фильтра для сообщений
* Удален постмаппинг "/filter" перенесен в GetMapping "/main"

v0.0.6 Spring Boot Security: добавлена панель администратора и роли пользователей, ограничен доступ
* Добавлена страница пользователей и страница редактирования пользователей
* Настроена админка
* UserController.java отображение изменение и сохранение пользователей
* userEdit.html редактирование
* userList.html просмотр всех пользователей

v0.0.7 Загрузка файлов на сервер и раздача статики
* MainController в метод add добавлена загрузка изображений
* Message хранит путь к файлу
* В форму main добавлено созраниние и отображение изобращения в сообщении
* Добавлены css
* В WebSecurityConfig добавлены настройки для доступа к стилям