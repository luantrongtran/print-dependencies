# Module Dependencies

## Assumptions
- If in the input file there are lines defined for the same module name, the latter overrides the previous.
- As in example 3, my understanding/assumption is that circular dependencies result in errors.
- It seems in example 2, getModuleDependencies(A) it misses module C which should be printed out.
- It's a bit confusing in example 2 in which the getModuleDependencies(A) returns/print out A's last dependency which is D. However, getModuleDependencies(C) doesn't print out its last dependency which is E.
- Because of the previous 2 points, I assume we don't print a dependency twice. And the getModuleDependencies(A) should print {B,E,F,H,D,I,G,C}
- In example 3, it seems getModuleDependencies(G) should also result in error, because G->I->C->G