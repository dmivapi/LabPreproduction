<h1 id="contents">LabPreproduction homework</h1>
<ol>
<li><a href="#hw1">homework_1</a></li>
<li><a href="#hw2">homework_2</a></li>
<li><a href="#hw3">homework_3</a></li>
<li><a href="#hw4">homework_4</a></li>
<li><a href="#hw5">homework_5</a></li>
</ol>


<h3 id="hw1">homework #1: Spring framework</h3>
Rework your pet project with Spring framework, using java configuration, DAO layer leave as is, without usage of ORM frameworks.
<p><a href="#contents">Назад к оглавлению</a></p>
<hr>

<h3 id="hw2">homework #2: Spring BPP</h3>
Логгировать время выполнения интерфейсных методов классa помеченного аннотацией <strong>@Timed</strong>
<p><a href="#contents">Назад к оглавлению</a></p> 
<hr>

<h3 id="hw3">homework #3: Spring MVC Exceptions handling</h3>
Implement exception handling in your pet project using <strong>@ContollerAdvice</strong>
<p><a href="#contents">Назад к оглавлению</a></p>
<hr>

<h3 id="hw4">homework #4: Spring Integration &amp; Spring Batch</h3>
<ol>
<li>Create an integration flow that process orders.
There are two states for an order which you are interested in: WAITING_FOR_PAYMENT and PAYMENT_COMPLETED. Also there is CANCELED state that should be skipped. 
You will receive orders from a csv file, process them and save in memory.

<code>class Order {
	...
	private OrderState orderState;
	…
}</code>

<code>enum OrderState {
	CANCELED, 
	WAITING_FOR_PAYMENT, 
	PAYMENT_COMPLETED 
}</code>

You need to create these two endpoints and integrate them using Spring Integration.
Please use the <strong>MessagingGateway</strong> annotation to create an endpoint.
</li>
<li>Create application using Spring Batch that can fetch user rows from db and check account balance and if balance is lower than 10$ send e-mail with notification.</li>
</ol>

<strong>Tests and logs are required! Use Spring, not Spring Boot!</strong>
<p><a href="#contents">Назад к оглавлению</a></p>
<hr>

<h3 id="hw5">homework #5: Spring Boot &amp; Microservices</h3>
<ul>
<li>Сгенерировать новый проект Spring Boot с использованием https://start.spring.io/ или Idea </li>
<li>Добавить при генерации проекта следующие dependency: Web, testing, Jpa, Actuator, lombok(опционально)</li>
<li>Добавить Spring Data репозиторий - интерфейс без имплементации для доступа к данным. В качестве данных использовать таблицу из предыдущих проектов или произвольную.</li>
<li>Репозиторий должен содержать не менее двух методов: все сущности из таблицы и одну сущность по id. CRUD операции не нужны. Пейжинация и сортировка будет плюсом но не обязательны</li>
<li>Аннотировать класс-модель данных для загрузки из базы данных. Класс должен содержать как минимум два поля id и name</li>
<li>Реализовать Rest контроллер который должен содержать два метода: вернуть все сущности и вернуть одну сущность по id. Использовать методы GET. Возможность пейжинации и сортивовки будут плюсом но не обязательны.</li>
<li>Сконфигурировать метрики и health. данные должны быть доступны по стандартным путям actuator/health actuator/metrics или переопределенным</li>
<li>Имплементировать кастомный healthCheck который помимо статуса приложения UP/DOWN будет возвращать название приложения</li>
<li>Опционально: добавить тесты</li>
<li>Опционально: использовать lombok</li>
</ul>

<p><a href="#contents">Назад к оглавлению</a></p>