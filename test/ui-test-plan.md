# Level-4 UI test plan

These tests cover creating the three supported task types, listing tasks, and marking and unmarking a task.

## Create, list, mark, and unmark Level-4 tasks

**Aim:** Confirm that todo, deadline, and event commands create correctly typed tasks; that `list` displays them; and that `mark` and `unmark` change a task's status.

**Command:**
```text
javac -d out\production\ip src\main\java\Dobby.java src\main\java\DobbyLogic.java src\main\java\DobbyUtil.java src\main\java\Task.java src\main\java\ToDo.java src\main\java\Deadline.java src\main\java\Event.java && java -cp out\production\ip Dobby
```

**Input:**
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

## Reject malformed deadlines and events without adding tasks

**Aim:** Confirm that malformed deadline and event commands are rejected while a valid todo remains the only task in the list.

**Command:**
```text
javac -d out\production\ip src\main\java\Dobby.java src\main\java\DobbyLogic.java src\main\java\DobbyUtil.java src\main\java\Task.java src\main\java\ToDo.java src\main\java\Deadline.java src\main\java\Event.java && java -cp out\production\ip Dobby
```

**Input:**
```text
todo keep me
deadline missing date
list
event workshop /from Monday /to
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
     *   | |_| | (_) | |_) | |_) | |_| |     * 
   .     |____/ \___/|_.__/|_.__/ \__, |   . 
                                  |___/        
       *       .       *       .       *       

> Dobby says hi!
> Dobby is ready to take orders.
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby noted a new Todo: keep me
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby is confused. Dobby think you meant 'deadline <description> /by <date/time>'
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby show 1 tasks:
1. [T][ ] keep me

____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby is confused. Dobby think you meant 'event <description> /from <date/time> /to <date/time>'
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby show 1 tasks:
1. [T][ ] keep me

____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby says goodbye to master!
____________________________________________________________
```
