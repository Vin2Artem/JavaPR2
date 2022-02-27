<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content='true' name='HandheldFriendly' />
    <meta content='width' name='MobileOptimized' />
    <meta content='yes' name='apple-mobile-web-app-capable' />
    <title>Регистрация</title>
    <link rel="stylesheet" href="styles/style.css"/>
    <script src="scripts/registration.js"></script>
</head>

<body>
<div class="content">
    <form action="#" method="post" onsubmit="return validateForm();">
        <div class="container signup">
            <div>
                <img class="logo" src="<c:url value="/pictures/logo.png"/>" alt="Logo">
            </div>

            <h1>Регистрация</h1>
            <p>Заполните форму ниже, чтобы создать аккаунт.</p>

            <hr>

            <h2>Личные данные</h2>

            <label for="surname" class="bold">Фамилия *</label>
            <input type="text" placeholder="Петров" id="surname" name="surname" required>

            <label for="name" class="bold">Имя *</label>
            <input type="text" placeholder="Иван" id="name" name="name" required>

            <label for="patronymic" class="bold">Отчество</label>
            <input type="text" placeholder="Сергеевич" id="patronymic" name="patronymic">

            <label for="sexb" class="bold">Пол</label>
            <div class="select" id="sexb" tabindex="1">
                <input type="text" id="sex" name="sex" value="мужской" class="hidden"/>
                <span class="select_value">мужской</span>
                <ul class="select_dropdown">
                    <li class="select_option">мужской</li>
                    <li class="select_option">женский</li>
                </ul>
                <img class="arrow" src="pictures/arrow.png">
            </div>

            <label for="birth" class="bold">Дата рождения</label>
            <input type="date" min="1900-01-01" max="<c:out value="${today}"/>" id="birth" name="birth" required>

            <hr>

            <h2>Аккаунт</h2>

            <label for="email" class="bold">Электронная почта *</label>
            <input type="email" placeholder="something@mail.ru" id="email" name="email" class="pop_field" required>
            <span class="pop_msg">Формат адреса: something@mail.ru</span>

            <label for="pwd" class="bold">Пароль *</label>
            <input type="password" class="pwd pop_field" minlength="6" placeholder="qW1!eR2@tY3#" id="pwd" name="pwd" required>
            <span class="pop_msg">Длина пароля должна быть > 6 символов</span>

            <label for="pwd-repeat" class="bold">Повторите пароль *</label>
            <input type="password" class="pwd_repeat pop_field" placeholder="qW1!eR2@tY3#" id="pwd-repeat" name="pwd-repeat" required>
            <span class="pop_msg">Пароли не совпадают</span>

            <hr>

            <h2>Контакты и местоположение</h2>

            <label for="tel" class="bold">Телефон *</label>
            <input type="tel" class="mask pop_field" placeholder="+7 (___) ___-__-__" value="+7 (___) ___-__-__"
                   id="tel" name="tel" required>
            <span class="pop_msg">Формат номера: +7 (917) 100-20-30</span>

            <label for="city" class="bold">Город *</label>
            <input type="text" placeholder="Москва" id="city" name="city" required>

            <label for="street" class="bold">Улица, дом</label>
            <input type="text" placeholder="Ленина, 31" id="street" name="street">

            <hr>

            <button type="submit" class="registerbtn">Зарегистрироваться</button>
        </div>

        <div class="container signin">
            <p>Уже есть аккаунт? <a href="/">Войти</a></p>
        </div>
    </form>
</div>
</body>

</html>
