ACADEMIC TASK ORGANIZER
CS342 – Visual Programming

1. Project Description

Academic Task Organizer is a Java Swing desktop application that allows students to manage academic tasks. Users can add, update, delete, and clear tasks, as well as save and load task records using a text file.

2. Requirements

- Java Development Kit (JDK)
- NetBeans IDE

3. How to Run

1. Open the AcademicTaskOrganizer project in NetBeans.
2. Locate AcademicTaskOrganizer.java.
3. Run the project.
4. The Academic Task Organizer window will appear.

4. Application Features

- Add Task: Adds a new task with a unique ID.
- Update Task: Updates the selected task.
- Delete Task: Deletes the selected task after confirmation.
- Clear Fields: Clears the input fields.
- Save to File: Saves all tasks to tasks.txt.
- Load from File: Loads saved tasks from tasks.txt.

5. File Handling

The application uses BufferedWriter and FileWriter to save task records, and BufferedReader and FileReader to load them.

Each task is stored in the following format:

TaskID|TaskTitle|CourseCode|Priority|Status

Saving replaces the previous file contents. Loading restores the saved tasks without adding duplicate rows.

6. Input Validation and Exception Handling

- Task Title and Course Code cannot be empty.
- The application rejects pipe characters and line breaks in the text fields.
- Update and Delete require a selected task.
- Delete requires user confirmation.
- File errors are handled using try-catch blocks.
- Success messages are displayed after saving and loading.