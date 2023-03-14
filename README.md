# ТЗ: «Трекер задач».

## Типы задач

Простейшим кирпичиком такой системы является задача (англ. task). У задачи есть следующие свойства:

* Название, кратко описывающее суть задачи (например, «Переезд»).
* Описание, в котором раскрываются детали.
* Уникальный идентификационный номер задачи, по которому её можно будет найти.
* Статус, отображающий её прогресс. Мы будем выделять следующие этапы жизни задачи:
1. NEW — задача только создана, но к её выполнению ещё не приступили.
2. IN_PROGRESS — над задачей ведётся работа.
3. DONE — задача выполнена.

Иногда для выполнения какой-нибудь масштабной задачи её лучше разбить на подзадачи (англ. subtask). Большую задачу, которая делится на подзадачи, мы будем называть эпиком (англ. epic).
Таким образом, в нашей системе задачи могут быть трёх типов: обычные задачи, эпики и подзадачи. Для них должны выполняться следующие условия:

* Для каждой подзадачи известно, в рамках какого эпика она выполняется.
* Каждый эпик знает, какие подзадачи в него входят.
* Завершение всех подзадач эпика считается завершением эпика.

## Менеджер

Кроме классов для описания задач, вам нужно реализовать класс для объекта-менеджера. Он будет запускаться на старте программы и управлять всеми задачами. В нём должны быть реализованы следующие функции:

1. Возможность хранить задачи всех типов. Для этого вам нужно выбрать подходящую коллекцию.
2. Методы:
    1. Получение списка всех задач.
    2. Получение списка всех эпиков.
    3. Получение списка всех подзадач определённого эпика.
    4. Получение задачи любого типа по идентификатору.
    5. Добавление новой задачи, эпика и подзадачи. Сам объект должен передаваться в качестве параметра.
    6. Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде параметра.
    7. Удаление ранее добавленных задач — всех и по идентификатору.
3. Управление статусами осуществляется по следующему правилу:
    1. Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией о самой задаче.
    2. Для эпиков:
        * если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
        * если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
        * во всех остальных случаях статус должен быть IN_PROGRESS.

### Менеджер теперь интерфейс

Из темы об абстракции и полиморфизме вы узнали, что при проектировании кода полезно разделять требования к желаемой функциональности объектов и то, как эта функциональность реализована. То есть набор методов, который должен быть у объекта, лучше вынести в интерфейс, а реализацию этих методов — в класс, который его реализует. Теперь нужно применить этот принцип к менеджеру задач.

1. Класс `TaskManager` должен стать интерфейсом. В нём нужно собрать список методов, которые должны быть у любого объекта-менеджера.  Вспомогательные методы, если вы их создавали, переносить в интерфейс не нужно.
2. Созданный ранее класс менеджера нужно переименовать в `InMemoryTaskManager`. Именно то, что менеджер хранит всю информацию в оперативной памяти, и есть его главное свойство, позволяющее эффективно управлять задачами. Внутри класса должна остаться реализация методов. При этом важно не забыть имплементировать `TaskManager`, ведь в Java класс должен явно заявить, что он подходит под требования интерфейса.

### История просмотров задач

Добавьте в программу новую функциональность — нужно, чтобы трекер отображал последние просмотренные пользователем задачи. Для этого добавьте метод `getHistory()` в  `TaskManager` и реализуйте его — он должен возвращать последние 10 просмотренных задач. Просмотром будем считаться вызов у менеджера методов получения задачи по идентификатору  — `getTask(),` `getSubtask()` и `getEpic()`. От повторных просмотров избавляться не нужно.

У метода getHistory() не будет параметров. Это значит, он формирует свой ответ, анализируя исключительно внутреннее состояние полей объекта менеджера. Подумайте, каким образом и какие данные вы запишете в поля менеджера для возможности извлекать из них историю посещений. Так как в истории отображается, к каким задачам было обращение в методах getTask(), getSubtask() и getEpic(), эти данные в полях менеджера будут обновляться при вызове этих трех методов.

### Утилитарный класс

Со временем в приложении трекера появится несколько реализаций интерфейса `TaskManager`.   Чтобы не зависеть от реализации, создайте утилитарный класс `Managers`.  На нём будет лежать вся ответственность за создание менеджера задач. То есть `Managers` должен сам подбирать нужную реализацию `TaskManager`и возвращать объект правильного типа.

У `Managers`будет метод  `getDefault()`.  При этом вызывающему неизвестен конкретный класс, только то, что объект, который возвращает `getDefault()`, реализует интерфейс `TaskManager`.

### Статусы задач как перечисление

Так как варианты возможных статусов у задачи ограничены, для их хранения в программе лучше завести перечисляемый тип `enum`.

- Как перенести статусы задач в `enum`

  Ранее мы использовали для хранения статусов задач тип `String` — теперь три соответствующих поля в классе нужно объединить в `enum` с тремя значениями. Не забудьте, что все элементы перечисления принято писать как константы: в верхнем регистре.


### Тестирование вашего решения

Убедитесь, что ваше решение работает! В главном классе воспроизведите несложный пользовательский сценарий:

- создайте несколько задач разного типа.
- вызовите разные методы интерфейса `TaskManager` и напечатайте историю просмотров после каждого вызова. Если код рабочий, то история просмотров задач будет отображаться корректно.

### Сделайте историю задач интерфейсом

В этом спринте возможности трекера ограничены — в истории просмотров допускается дублирование и она может содержать только десять задач. В следующем спринте вам нужно будет убрать дубли и расширить её размер. Чтобы подготовиться к этому, проведите рефакторинг кода.

Создайте отдельный интерфейс для управления историей просмотров — `HistoryManager`. У него будет два метода. Первый `add(Task task)` должен помечать задачи как просмотренные, а второй `getHistory()` — возвращать их список.

Объявите класс `InMemoryHistoryManager` и перенесите в него часть кода для работы с историей из  класса `InMemoryTaskManager`.  Новый класс `InMemoryHistoryManager` должен реализовывать интерфейс `HistoryManager`.

Добавьте в служебный класс `Managers` статический метод `HistoryManager getDefaultHistory()`.  Он должен возвращать объект `InMemoryHistoryManager` — историю просмотров.

Проверьте, что теперь `InMemoryTaskManager` обращается к менеджеру истории через интерфейс `HistoryManager` и использует реализацию, которую возвращает метод `getDefaultHistory()`.