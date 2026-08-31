# UI test session

## 1. Create, list, mark, and unmark Level-4 tasks — FAILED

**Aim:** Confirm that todo, deadline, and event commands create correctly typed tasks; that `list` displays them; and that `mark` and `unmark` change a task's status.

**Command:**
```text
javac -d out\production\ip src\main\java\Dobby.java src\main\java\DobbyLogic.java src\main\java\DobbyUtil.java src\main\java\Task.java src\main\java\ToDo.java src\main\java\Deadline.java src\main\java\Event.java && java -cp out\production\ip Dobby
```

**Console input:**
```text
todo read book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
mark 2
unmark 2
list
bye
```

**Expected output:**
```text
____________________________________________________________
       *       .       *       .       *       
   .      ____        _     _              .   
     *   |  _ \  ___ | |__ | |__  _   _     * 
   .     | | | |/ _ \| '_ \| '_ \| | | |   . 
     *   | |_| | (_) | |_) | |_| | |_| |     * 
   .     |____/ \___/|_.__/|_.__/ \__, |   . 
                                  |___/        
       *       .       *       .       *       

> Dobby says hi!
> Dobby is ready to take orders.
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby noted a new Todo: read book
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby noted a new Deadline: return book by Sunday
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby noted a new Event: project meeting from Mon 2pm to 4pm
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby show 3 tasks:
1. [T][ ] read book
2. [D][ ] return book (by: Sunday)
3. [E][ ] project meeting (from: Mon 2pm to: 4pm)

____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby will mark this as done!
   [D][X] return book (by: Sunday)
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby will mark this as not done!
   [D][ ] return book (by: Sunday)
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby show 3 tasks:
1. [T][ ] read book
2. [D][ ] return book (by: Sunday)
3. [E][ ] project meeting (from: Mon 2pm to: 4pm)

____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby says goodbye to master!
____________________________________________________________
```

**Actual output:**
```text
____________________________________________________________
       *       .       *       .       *       
   .      ____        _     _              .   
     *   |  _ \  ___ | |__ | |__  _   _     * 
   .     | | | |/ _ \| '_ \| '_ \| | | |   . 
     *   | |_| | (_) | |_) | |_) | |_| |     * 
   .     |____/ \___/|_.__/|_.__/ \__, |   . 
                                  |___/        
       *       .       *       .       *       

> Dobby says hi!
> Dobby is ready to take orders.
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby noted a new Todo: read book
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby noted a new Deadline: return book by Sunday
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby noted a new Todo: project meeting from Mon 2pm to 4pm
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby show 3 tasks:
1. [T][ ] read book
2. [D][ ] return book (by: Sunday)
3. [E][ ] project meeting (from: Mon 2pm to: 4pm)

____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby will mark this as done!
   [D][X] return book (by: Sunday)
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby will mark this as not done!
   [D][ ] return book (by: Sunday)
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby show 3 tasks:
1. [T][ ] read book
2. [D][ ] return book (by: Sunday)
3. [E][ ] project meeting (from: Mon 2pm to: 4pm)

____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby says goodbye to master!
____________________________________________________________

```

Testing stopped after this failure; later cases were not run.
