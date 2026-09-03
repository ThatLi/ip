# UI test session

## 1. Create, list, mark, and unmark Level-5 tasks — PASSED

**Aim:** Confirm that todo, deadline, and event commands create correctly typed tasks; that `list` displays them; and that `mark` and `unmark` change a task's status. Each successful change also saves the task list to `data/duke.txt` without changing the console output.

**Command:**
```text
if exist data\duke.txt del /q data\duke.txt & javac -d out\production\ip src\main\java\Dobby.java src\main\java\DobbyLogic.java src\main\java\DobbyUtil.java src\main\java\Storage.java src\main\java\Task.java src\main\java\ToDo.java src\main\java\Deadline.java src\main\java\Event.java && java -cp out\production\ip Dobby
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

## 2. Skip invalid saved tasks without losing valid tasks — PASSED

**Aim:** Confirm that an invalid saved record is reported and ignored while valid saved tasks still load.

**Command:**
```text
(echo T ^| 0 ^| recovered task&echo invalid saved task)>data\duke.txt & javac -d out\production\ip src\main\java\Dobby.java src\main\java\DobbyLogic.java src\main\java\DobbyUtil.java src\main\java\Storage.java src\main\java\Task.java src\main\java\ToDo.java src\main\java\Deadline.java src\main\java\Event.java && java -cp out\production\ip Dobby
```

**Console input:**
```text
list
bye
```

**Expected output:**
```text
> Dobby skipped 1 invalid saved task(s).
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
> Dobby show 1 tasks:
1. [T][ ] recovered task

____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby says goodbye to master!
____________________________________________________________
```

**Actual output:**
```text
> Dobby skipped 1 invalid saved task(s).
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
> Dobby show 1 tasks:
1. [T][ ] recovered task

____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby says goodbye to master!
____________________________________________________________

```

## 3. Reject malformed deadlines and events without adding tasks — PASSED

**Aim:** Confirm that malformed deadline and event commands are rejected while a valid todo remains the only task in the list.

**Command:**
```text
if exist data\duke.txt del /q data\duke.txt & javac -d out\production\ip src\main\java\Dobby.java src\main\java\DobbyLogic.java src\main\java\DobbyUtil.java src\main\java\Storage.java src\main\java\Task.java src\main\java\ToDo.java src\main\java\Deadline.java src\main\java\Event.java && java -cp out\production\ip Dobby
```

**Console input:**
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

## 4. Load saved tasks when Dobby starts — PASSED

**Aim:** Confirm that a saved todo, completed deadline, and event are restored and listed when Dobby starts.

**Command:**
```text
(echo T ^| 0 ^| read book&echo D ^| 1 ^| return book ^| Sunday&echo E ^| 0 ^| project meeting ^| Mon 2pm ^| 4pm)>data\duke.txt & javac -d out\production\ip src\main\java\Dobby.java src\main\java\DobbyLogic.java src\main\java\DobbyUtil.java src\main\java\Storage.java src\main\java\Task.java src\main\java\ToDo.java src\main\java\Deadline.java src\main\java\Event.java && java -cp out\production\ip Dobby
```

**Console input:**
```text
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
> Dobby show 3 tasks:
1. [T][ ] read book
2. [D][X] return book (by: Sunday)
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
> Dobby show 3 tasks:
1. [T][ ] read book
2. [D][X] return book (by: Sunday)
3. [E][ ] project meeting (from: Mon 2pm to: 4pm)

____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby says goodbye to master!
____________________________________________________________

```
