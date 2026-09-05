# Level-6 UI test plan

These tests cover creating the three supported task types, listing tasks, marking and unmarking a task, and saving and loading task-list changes through `data/duke.txt`.

## Create, list, mark, and unmark dated tasks

**Aim:** Confirm that todo, deadline, and event commands create correctly typed tasks; dates are reformatted for display; and `mark` and `unmark` change a task's status. Each successful change also saves the task list to `data/duke.txt` without changing the console output.

**Command:**
```text
cmd /c "if exist data\duke.txt del /q data\duke.txt" & javac -d out\production\ip src\main\java\dobby\*.java src\main\java\dobby\ui\cli\Ui.java && java -cp out\production\ip Dobby
```

**Input:**
```text
todo read book
deadline return book /by 2/12/2019 1800
event project meeting /from 2019-12-03 0900 /to 2019-12-03 1100
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
> Dobby noted a new Deadline: return book by Dec 02 2019, 18:00
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby noted a new Event: project meeting from Dec 03 2019, 09:00 to Dec 03 2019, 11:00
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby show 3 tasks:
1. [T][ ] read book
2. [D][ ] return book (by: Dec 02 2019, 18:00)
3. [E][ ] project meeting (from: Dec 03 2019, 09:00 to: Dec 03 2019, 11:00)

____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby will mark this as done!
   [D][X] return book (by: Dec 02 2019, 18:00)
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby will mark this as not done!
   [D][ ] return book (by: Dec 02 2019, 18:00)
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby show 3 tasks:
1. [T][ ] read book
2. [D][ ] return book (by: Dec 02 2019, 18:00)
3. [E][ ] project meeting (from: Dec 03 2019, 09:00 to: Dec 03 2019, 11:00)

____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby says goodbye to master!
____________________________________________________________
```

## Skip invalid saved tasks without losing valid tasks

**Aim:** Confirm that an invalid saved record is reported and ignored while valid saved tasks still load.

**Command:**
```text
(echo T ^| 0 ^| recovered task&echo invalid saved task)>data\duke.txt & javac -d out\production\ip src\main\java\dobby\*.java src\main\java\dobby\ui\cli\Ui.java && java -cp out\production\ip Dobby
```

**Input:**
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

## Reject malformed task dates without adding tasks

**Aim:** Confirm that missing markers and invalid calendar dates are rejected while a valid todo remains the only task in the list.

**Command:**
```text
cmd /c "if exist data\duke.txt del /q data\duke.txt" & javac -d out\production\ip src\main\java\dobby\*.java src\main\java\dobby\ui\cli\Ui.java && java -cp out\production\ip Dobby
```

**Input:**
```text
todo keep me
deadline missing date
list
event workshop /from 2019-02-30 /to 2019-03-01
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
> Dobby needs valid dates: yyyy-MM-dd, optionally followed by HHmm.
____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby show 1 tasks:
1. [T][ ] keep me

____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby says goodbye to master!
____________________________________________________________
```

## Load saved tasks when Dobby starts

**Aim:** Confirm that a saved todo, completed deadline, and event with ISO dates are restored and reformatted when Dobby starts.

**Command:**
```text
(echo T ^| 0 ^| read book&echo D ^| 1 ^| return book ^| 2019-12-02 1800&echo E ^| 0 ^| project meeting ^| 2019-12-03 0900 ^| 2019-12-03 1100)>data\duke.txt & javac -d out\production\ip src\main\java\dobby\*.java src\main\java\dobby\ui\cli\Ui.java && java -cp out\production\ip Dobby
```

**Input:**
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
2. [D][X] return book (by: Dec 02 2019, 18:00)
3. [E][ ] project meeting (from: Dec 03 2019, 09:00 to: Dec 03 2019, 11:00)

____________________________________________________________
Tell Dobby: ____________________________________________________________
> Dobby says goodbye to master!
____________________________________________________________
```
