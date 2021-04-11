# Title

# Not code ``` start
# ``` also not code

```antlrv4
## Primary Expression
primaryExpression
    :   Identifier
    |   Constant
    |   StringLiteral+
    |   '(' expression ')'
    |   genericSelection
    |   '__extension__'? '(' compoundStatement ')' // Blocks (GCC extension)
    |   '__builtin_va_arg' '(' unaryExpression ',' typeName ')'
    |   '__builtin_offsetof' '(' typeName ',' unaryExpression ')'
    ;

## Generic Section
genericSelection
    :   '_Generic' '(' assignmentExpression ',' genericAssocList ')'
    ;

## Generic Association List
genericAssocList
    :   genericAssociation
    |   genericAssocList ',' genericAssociation
    ;

## Generic Assosiation
genericAssociation
    :   typeName ':' assignmentExpression
    |   'default' ':' assignmentExpression
    ;

## Postfix Expression
postfixExpression
    :   primaryExpression
    |   postfixExpression '[' expression ']'
    |   postfixExpression '(' argumentExpressionList? ')'
    |   postfixExpression '.' Identifier
    |   postfixExpression '->' Identifier
    |   postfixExpression '++'
    |   postfixExpression '--'
    |   '(' typeName ')' '{' initializerList '}'
    |   '(' typeName ')' '{' initializerList ',' '}'
    |   '__extension__' '(' typeName ')' '{' initializerList '}'
    |   '__extension__' '(' typeName ')' '{' initializerList ',' '}'
    ;
```

## Argument Expression List
```antlrv4
argumentExpressionList
    :   assignmentExpression
    |   argumentExpressionList ',' assignmentExpression
    ;

## Unary Expression
unaryExpression
    :   postfixExpression
    |   '++' unaryExpression
    |   '--' unaryExpression
    |   unaryOperator castExpression
    |   'sizeof' unaryExpression
    |   'sizeof' '(' typeName ')'
    |   '_Alignof' '(' typeName ')'
    |   '&&' Identifier // GCC extension address of label
    ;

## Unary Operator
unaryOperator
    :   '&' | '*' | '+' | '-' | '~' | '!'
    ;
```

## `Code` explained