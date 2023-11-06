grammar temp;


E
    : E '+' T
    | E '-' T
    | T
    ;

T
    : T '*' F
    | F
    ;

F
    : 'a'
    | '(' + E + ')'
    ;