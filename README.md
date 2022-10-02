# Module Dependencies

## Assumptions
- If in the input file there are lines defined for the same module name, the latter overrides the previous.
- As in example 3, my understanding/assumption is that circular dependencies result in errors.
- It seems in example 2, getModuleDependencies(A) it misses module C which should be printed out.
- It's a bit confusing in example 2 in which the getModuleDependencies(A) returns/print out A's last dependency which is D. However, getModuleDependencies(C) doesn't print out its last dependency which is E.
- Because of the previous 2 points, I assume we don't print a dependency twice. And the getModuleDependencies(A) should print {B,E,F,H,D,I,G,C}
- In example 3, it seems getModuleDependencies(G) should also result in error, because G->I->C->G

## Java
I tested on Java 15, but should be able to run with at least Java 8.

## How to run the code
This is a maven project. This also includes maven wrapper inside the project

### Install project dependencies
```
./mvnw clean install
```

### run without IDE
```
./mvnw compile exec:java
```

### Following is an example of the input file
- In each line, before colon is the module name, and after the colon is the depdencies of the module
```
A: B,C,D
C: E,F,G
G: H,I
```
- There are more example files can be found in `src/test/resources` folder


### This is a command line project
- First it will wait until user input the full filename
- Then enter the module which the user would like to find the dependencies
- There 3 types of ouput:
  - Has some dependencies: `{B,C}`
  - Has no dependencies: `{}`
  - And circular dependencies: `error`