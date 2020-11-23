<h1 name="contents">LabPreproduction homework</h1>
<ol>
<li><a href="#hf1">homework_1</a></li>
<li><a href="#hf2">homework_2</a></li>
<li><a href="#hf3">homework_3</a></li>
<li><a href="#hf4">homework_4</a></li>
<li><a href="#hf5">homework_5</a></li>
</ol>


<h3 name="hw1">homework #1: Spring framework</h3>
Rework your pet project with Spring framework, using java configuration, DAO layer leave as is, without usage of ORM frameworks.
<hr>

<h3 name="hw2">homework #2: Spring BPP</h3>
Логгировать время выполнения интерфейсных методов классa помеченного аннотацией <strong>@Timed</strong> 
<hr>

<h3 name="hw3">homework #3: Spring MVC Exceptions handling</h3>
Implement exception handling in your pet project using <strong>@ContollerAdvice<strong>
<hr>

<h3 name="hw4">homework #4: Spring Integration & Spring Batch</h3>
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
<hr>

<p><a href="#contents">Назад к оглавлению</a></p>


