Overview of test files for WolfTasks

project0.txt - valid file with only a project name
project1.txt - valid file with 7 categories and 14 tasks
project2.txt - valid file with only categories. Categories out of order.
project3.txt - missing leading ! in file - IAE thrown with message Unable to load file.
project4.txt - task without a name - creates the project and category - invalid task isn't added
project5.txt - task without a duration - creates the project and category - invalid task isn't added
project6.txt - task without a category - creates the project and category - invalid task isn't added
project7.txt - task with a category not in the category list - creates the project and category - invalid task isn't added