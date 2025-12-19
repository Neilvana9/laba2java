Тема: Библиотека
Сущности: Book, Author, Reader, (Fine?)
Книга принадлежит одному или нескольким авторам; читатель оформляет выдачу книги и возвращает её к сроку.
Штраф начисляется за просрочку и закрепляется за читателем по конкретной книге.

(будем использовать raw ->.json)

1. Создание автора:
POST http://localhost:8080/authors
{
    "name": "Эрих Мария Ремарк"
}


2. Создание книги:
POST http://localhost:8080/books
{
    "authorIds": [1],
    "authorNames": ["Эрих Мария Ремарк"],
    "title": "На западном фронте без перемен"
}


3. Создание книги(несколько авторов):
POST http://localhost:8080/books
{
    "authorIds": [1, 2],
    "authorNames": ["Блок", "Пушкин"],
    "title": "Цирк в поднебесье"
}


4. Создание читателя:
POST http://localhost:8080/readers
{
    "name": "Владислав Барабанов"
}


5. Выдача книги:
POST http://localhost:8080/books/borrow
{
    "bookId": 1,
    "readerId": 1
}


6. Возврат книги:
POST http://localhost:8080/books/{id}/return


7. Получение всех авторов:
GET http://localhost:8080/authors


8. Получение всех книг:
GET http://localhost:8080/books


9. Получение всех читателей:
GET http://localhost:8080/readers


10. Создать штраф:
POST http://localhost:8080/fines
{
    "readerId": 1,
    "bookId": 1,
    "amount": 500.0
}


11. Оплатить штраф:
POST http://localhost:8080/fines/{id}/pay


12. Обновить автора:
PUT http://localhost:8080/authors/{id}
{
    "name": "Эрих Мария Ремарк"
}


13. Удалить автора:
DELETE http://localhost:8080/authors/{id}


14. Удаление читателя/пользователя:
DELETE http://localhost:8080/readers/{id}


15. Удаление штрафа:
DELETE http://localhost:8080/fines/{id}



16. Удаление книги:
DELETE http://localhost:8080/books/{id}
