# Merge Conflict Resolution

## Situation

A merge conflict occurred when merging feature/add-division into develop.

## Cause

Both branches modified the file:

- src/main/java/.../Calculator.java

## Resolution Steps

1. Ran `git merge feature/add-division`
2. Conflict detected in Calculator.java
3. Manually edited the conflict markers:
   <<<<<<< HEAD
   ...
   =======
   ...
   > > > > > > > feature/add-division
4. Kept the correct logic from both branches
5. Saved file
6. Ran:
   git add Calculator.java
   git commit

## Result

The conflict was resolved successfully and the merge was completed.
