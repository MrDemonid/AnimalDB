# Задание.

## Диаграмма классов.
Создать диаграмму классов с родительским классом `"Животные"`, 
и двумя подклассами: `"Pets"` и `"Pack animals"`.
В составы классов которых в случае `Pets` войдут классы:

**собаки, кошки, хомяки**, 

а в класс `Pack animals` войдут: 

**Лошади, верблюды и ослы**.

Каждый тип животных будет характеризоваться 
(например, имена, даты рождения, выполняемые команды и т.д)

## ООП и Java.
Создать иерархию классов в Java, который будет повторять диаграмму классов 
созданную в задаче *6 (Диаграмма классов)*. Схема в папке **asset**.

### Программа-реестр домашних животных
Написать программу на Java, которая будет имитировать реестр домашних животных. 
Должен быть реализован следующий функционал:

- **Добавление нового животного**. Реализовать функциональность для добавления новых животных в реестр. Животное должно определяться в правильный класс (например, "собака", "кошка", "хомяк" и т.д.)

- **Список команд животного**. Вывести список команд, которые может выполнять добавленное животное (например, "сидеть", "лежать").

- **Обучение новым командам**. Добавить возможность обучать животных новым командам. Вывести список животных по дате рождения.

- **Навигация по меню**. Реализовать консольный пользовательский интерфейс с меню для навигации между вышеуказанными функциями.

- **Счетчик животных**. Создать механизм, который позволяет вывести на экран общее количество созданных животных любого типа (Как домашних, так и вьючных), то есть при создании каждого нового животного счетчик увеличивается на “1”.


---
# Решение.

В задании не сказано, как именно реализовать БД. Учитывая, что на Java мы еще не проходили работу с базами данных, плюс фраза в задании:`"имитировать реестр домашних животных"`, говорят о том, что предполагается имитация БД, с сохранением данных в простой формат и использованием простых списков для манипуляций с "БД".

Но, такая имитация у нас уже была, где я уже реализовал и работу с форматом файлов, похожим на упрощенный JSON, а так же реализовал меню в консоли:
https://github.com/MrDemonid/JavaControlToyShop.git


Чтобы не повторяться, решил самостоятельно изучить работу с MySQL, а консольное меню заменить на GUI - Swing. 

Поэтому:

- **Список команд животного** - утратило смысл, поскольку все данные о животных представлены в таблице.

- **Обучение новым командам** - утратило смысл, вместо этого реализован функционал редактирования учетной записи животного, где можно поменять не только команды, но и его другие данные (кроме вида, поскольку кошка при всё желании не станет собакой).

- **Навигация по меню** - интерфейс реализован таким образом, чтобы все было под рукой и надобность в меню просто отпала. 

- **Счетчик животных** - у нас данные о животных теперь хранятся в базе данных и для того, чтобы узнать сколько их у нас, уже не нужно ничего считать - мы это знаем из выборки данных. И место счетчика просто показываю сколько животных отображено в данный момент в таблице (при отсутствии фильтра и будет общее кол-во животных в БД).


### Из особенностей реализации.

- Программа реализована по модели `MVC`, где `View` и `Model` ничего не знают друг о друге, а связаны между собой посредством `Controller`.

- `GUI` запущена в отдельном потоке, чтобы избавиться от возможных подвисаний `Model`.

- Обратная связь от пользователя к контроллеру происходит через модель сообщений (events), благодаря чему `View` не нужно ничего знать даже о контроллере. Созданы отдельные классы `Event` и `Listener` для каждого события от пользователя (папка view.events).

- Для удобства, создание новых экземпляров классов животных реализовано через класс-фабрику (`AnimalFactory`). 

- Вывод табличных данных реализован через классы-фильтры. Поскольку каждый из фильтров нужен только в единственном экземпляре, то их получение так же реализовано посредством класса-фабрики (`FilterFactory`), которая гарантирует, что каждый фильтр будет в единственном экземпляре.

В подпапке asset находятся скрипты, с помощью которых создаётся БД и наполняется животными. Так же там скрипты с запросами, которые и используются в программе.

## Сборка и запуск.

Для запуска программы необходим запущенный сервер MySQL. 
Драйвер MySQL, необходимый программе, находится в подпапке
текущего проекта:

/mysql-connector-j-8.4.0/

и его нужно прописать в настройках: File->Project Structure->Libraries.

Так же используется сторонняя библиотека miglayout-3.7.4.jar,
расположенная в подпапке :

/lib/

Эту библиотеку так же нужно добавить в настройках: 
File->Project Structure->Global Libraries.



