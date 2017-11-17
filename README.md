##Regular Expression Engine

**Introduction**
It is basically a “Regular Expression Engine.” The engine will be used for
searching patterns (which match a given regular expression) in a given text file. The program will take
two inputs: a regular expression and a text file. As output, program will print those lines of the
text file which contain strings that match the given regular expression.

- The alphabet, Σ, will consists of all printable ASCII characters except the metacharacters.
Metacharacters are: (, ), |, *, and &.
- We will omit the symbol ‘o’ in writing regular expressions. That is, instead of _R 1_ o _R 2_ , we will
    simply write _R 1 R 2_.
- The textbook uses the symbol “ ” to denote the union operator. Since this symbol is a non-⋃
    keyboard character, we will use the symbol “|” to denote the union operator.
- For simplicity we may assume that regular expression do not contain the symbols ε and. ∅

**Infix to Postfix**
It is easier to process a regular expression if it is in postfix form. For example the postfix equivalent of
the regular expression “ **(a|b)*xz*** ” is given by the expression “ **ab|*x&z*&** ”. Note that the
concatenation symbol is explicitly shown as the symbol “&” in this postfix expression. This makes it
easier to process the postfix expression. So, before processing the regular expression, we need to
convert it into postfix form. A C++ and Java code which makes this conversion will be provided.

**Project Parts**
The project will consist of two parts:
Part I: Construction of an NFA from a given regular expression.
Part II: Processing the given text file with the NFA.
