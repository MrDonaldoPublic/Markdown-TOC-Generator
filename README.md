1. [Jetbrains Summer Internship](#jetbrains-summer-internship)
    1. [*Тема*](#тема)
    2. [_Задача для отбора_](#задача-для-отбора)
        1. [Входные данные](#входные-данные)
        2. [Выходные данные:](#выходные-данные)
    3. [Решение](#решение)
        1. [Идея решения](#идея-решения)
            1. [Лексический анализатор](#лексический-анализатор)
            2. [Парсер](#парсер)
        2. [Что работает](#что-работает)
        3. [Не поддерживается](#не-поддерживается)
    4. [Материалы](#материалы)


# Jetbrains Summer Internship

## *Тема*
Кастомные агрегации на таблицах

## _Задача для отбора_
Генератор оглавления для markdown файлов

Это должна быть command-line тула на Java, которая принимает на вход путь к markdown файлу, добавляет к нему в начало оглавление и выводит результат в standard output.

Нельзя пользоваться библиотеками для генерации и парсинга markdown.

В качестве результата отправьте ссылку на репозиторий на github или bitbucket.

Вопросы пишите [сюда](mailto:liudmila.kornilova@jetbrains.com)

### Входные данные
sample.md:
```markdown
# My Project
## Idea
content
## Implementation
### Step 1
content
### Step 2
content
```

### Выходные данные:
Консольный вывод:
```markdown
1. [My Project](#my-project)
    1. [Idea](#idea)
    2. [Implementation](#implementation)
        1. [Step 1](#step-1)
        2. [Step 2](#step-2)

# My Project
## Idea
content
## Implementation
### Step 1
content
### Step 2
content
```

## Решение
Написано на Java.

Запускаемый класс [Generator](src/Generator.java) содержит метод **main**, который принимает
строку, содержащую путь к файлу markdown.

Если программе не передан файл, файл не найден или файл не является markdown, программа завершается
с _соответствующим_ сообщением.

Если в файле не было заголовков, программа вернёт всё содержание файла без оглавления.

В штатских ситуациях программа выводит оглавление, а потом содержание файла в консольный вывод.

### Идея решения
Идея разбита на [лексический анализатор](src/MdLexer.java), который возвращает текущий токен и соответствующую строку,
и [парсер](src/MdParser.java), который разбирает информацию от лексического анализатора.

#### Лексический анализатор
Поскольку заголовки в markdown заканчиваются одной или двумя строками, лексический анализатор, считывая очередную строку
(считывание до перевода строки) с файла, проверяет, не является ли заголовком эта строка.

А именно, проверяет на то, начинается ли строка с `#` или находятся ли под непустой строкой только символы `=` или `-`.

Если очередная строка подходит под это условие, лексический анализатор возвращает токен заголовка и строку
(если она содержит в себе ссылки, то анализатор заменит все ссылки на их названия).

Например:
```markdown
1. [Это ссылка. А это нет](#это-ссылка-а-это-нет)

# [Это](https://vk.com) [ссылка](https://jetbrains.com). А это нет
```

При этом, если строка, подходящая под условие заголовка, находится в блоке с кодом (```), она игнорируется.

#### Парсер
Парсер, в свою очередь, строит дерево из заголовков.

В процессе построения парсер проверяет, что вернул ему лексический анализатор. Если вернул заголовок, то вычисляет уровень
заголовка, после чего смотрит, куда вставить очередную _вершину_ заголовка.

Условие вставки вершины:
0. У дерева есть единственный корень, уровень которого равен 0.
1. Если уровень предыдущей вершины меньше, чем у текущей, текущая вершина является ребёнком предыдущей.
2. Если уровни равны, то предыдущая и текущая вершины являются братьями.
3. Если уровень предыдущей вершины больше, чем у текущей, текущей нужно стать ребёнком того, у кого уровень строго меньше, чем у него.

Пример:
```markdown
## Second
### Third
# First
```
1. Предыдущего нет (уровень предыдущего 0), текущая — ребёнок корня.
2. У предыдущего уровень (2) больше, чем у текущей (3). Текущая — ребёнок предыдущего заголовка.
3. У текущего заголовка уровень меньше, чем у предыдущей. После поиска оказывается, что единственная вершина, чей уровень меньше, - корень.
Итого:
```markdown
1. [Second](#second)
    1. [Third](#third)
2. [First](#first)

## Second
### Third
# First
```

### Что работает
Заголовки вида:
```markdown
# Heading level 1

## Heading level 2

### Heading level 3

#### Heading level 4

##### Heading level 5

###### Heading level 6

Heading level 1
===============

Heading level 2
---------------
```

Заголовки с выделением текста (жирный, курсив, перечёркивание), ссылки, вставка кода (начинается с ```)

### Не поддерживается
HTML-вставки

## Материалы
* [Markdown Guide](https://www.markdownguide.org/basic-syntax/)
* [Github Guides](https://guides.github.com/features/mastering-markdown/)
