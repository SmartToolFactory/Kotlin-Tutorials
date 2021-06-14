package com.smarttoolfactory.tutorial.chapter6Advanced

/*
Here is the complete list of various character classes constructs:
[abc]: It would match with text if the text is having either one of them(a,b or c) and only once.
[^abc]:  Any single character except a, b, or c (^ denote negation)
[a-zA-Z]:  a through z, or A through Z, inclusive (range)
[a-d[m-p]]:  a through d, or m through p: [a-dm-p] (union)
[a-z&&[def]]:  Any one of them (d, e, or f)
[a-z&&[^bc]]: a through z, except for b and c: [ad-z] (subtraction)
[a-z&&[^m-p]]:  a through z, and not m through p: [a-lq-z] (subtraction)

Predefined Character Classes – Metacharacters
These are like short codes which you can use while writing regex.

Construct	Description
.   ->	Any character (may or may not match line terminators)
\d  ->	A digit: [0-9]
\D  ->	A non-digit: [^0-9]
\s  ->	A whitespace character: [ \t\n\x0B\f\r]
\S  ->	A non-whitespace character: [^\s]
\w  ->	A word character: [a-zA-Z_0-9]
\W  ->	A non-word character: [^\w]

Boundary Matchers
^	Matches the beginning of a line.
$	Matches then end of a line.
\b	Matches a word boundary.
\B	Matches a non-word boundary.
\A	Matches the beginning of the input text.
\G	Matches the end of the previous match
\Z	Matches the end of the input text except the final terminator if any.
\z	Matches the end of the input text.
*/

fun main() {
    
}
