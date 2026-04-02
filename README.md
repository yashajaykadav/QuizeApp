Task – 4 – Quiz Application
Problem Statement:
Design and develop an Online Quiz Application for internal students of a college or institute. The system allows an Admin/Trainer to create and schedule quizzes across multiple subjects and topics. Students can attempt multiple quizzes over time and view an overall performance summary of all quizzes attempted.

System Users:
1. Admin / Trainer
2. Student
Admin / Trainer Module:
- Add Subjects
- Add Topics under each Subject
- Add Questions under Subject and Topic
- Questions may be objective or descriptive and may include code snippets
- Each question has 4 options and 1 correct answer
- Each question carries 1 mark
- Create quizzes by selecting subject, topic, and questions
- Set quiz duration (e.g., 60 minutes)
- Schedule quizzes for specific dates
- Manage student login details
- Allow students to change passwords
Student Module:
- Login using credentials
- View quizzes scheduled for the current day
- Attempt multiple quizzes from different subjects and topics
- Start quiz with timer
- Questions appear one at a time
- Navigate using Next and Previous buttons
- Final Submit button
- Auto-submit on time expiry
Question Navigation Panel:
- Appears on the right-hand side of the quiz screen
- Displays question numbers in groups (1–4, 5–8, etc.)
- Color coding:
  Red: Not attempted
  Orange: Currently viewing
  Green: Attempted
- Buttons are clickable for direct navigation
Overall Student Result & Performance Module:
- After login, student can access an Overall Result page
- The page displays a list of all quizzes attempted across subjects and topics
- For each quiz, show:
  - Subject name
  - Topic name
  - Quiz date
  - Total marks
  - Marks obtained
  - Percentage
  - Status (Completed / Missed)
- Provide overall performance summary:
  - Total quizzes attempted
  - Average score
  - Best score
- Results are read-only
UI Mockup Description:
- Student Dashboard:
  - List of scheduled quizzes
  - Button to view Overall Results
- Quiz Screen:
  - Question area on the left
  - Timer on the top-right
  - Question navigation panel on the right
- Result Screen:
  - Tabular view of all attempted quizzes
  - Summary section at the top
Constraints:
- Each quiz is time-bound
- Quiz accessible only on scheduled date and time
- Only authenticated internal students allowed
- Real-time updates for question status colors
Evaluation Criteria:
- Correct implementation of features
- Proper database design for subjects, topics, quizzes, and results
- Clean and readable code
- User-friendly UI
- Secure authentication and authorization

Database design->(Mysql)
name of Database  : quize_db 
+-------------------+
| Tables_in_quiz_db |
+-------------------+
| answers           |
| attempts          |
| questions         |
| quiz_questions    |
| quizzes           |
| subjects          |
| topics            |
| users             |
+-------------------+

Users:-> 
mysql> desc users;
+----------+-------------------------+------+-----+---------+----------------+
| Field    | Type                    | Null | Key | Default | Extra          |
+----------+-------------------------+------+-----+---------+----------------+
| id       | bigint                  | NO   | PRI | NULL    | auto_increment |
| name     | varchar(50)             | NO   |     | NULL    |                |
| email    | varchar(100)            | NO   | UNI | NULL    |                |
| password | varchar(255)            | NO   |     | NULL    |                |
| role     | enum('ADMIN','STUDENT') | NO   |     | NULL    |                |
+----------+-------------------------+------+-----+---------+----------------+

topics->
+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| id         | bigint       | NO   | PRI | NULL    | auto_increment |
| name       | varchar(100) | NO   | MUL | NULL    |                |
| subject_id | bigint       | NO   | MUL | NULL    |                |
+------------+--------------+------+-----+---------+----------------+

subjects->
+-------+-------------+------+-----+---------+----------------+
| Field | Type        | Null | Key | Default | Extra          |
+-------+-------------+------+-----+---------+----------------+
| id    | bigint      | NO   | PRI | NULL    | auto_increment |
| name  | varchar(50) | NO   | UNI | NULL    |                |
+-------+-------------+------+-----+---------+----------------+

mysql> desc quizzes;
+----------------+--------+------+-----+---------+----------------+
| Field          | Type   | Null | Key | Default | Extra          |
+----------------+--------+------+-----+---------+----------------+
| id             | bigint | NO   | PRI | NULL    | auto_increment |
| topic_id       | bigint | NO   | MUL | NULL    |                |
| created_by     | bigint | NO   | MUL | NULL    |                |
| duration       | int    | NO   |     | NULL    |                |
| scheduled_date | date   | NO   | MUL | NULL    |                |
| start_time     | time   | NO   |     | NULL    |                |
| end_time       | time   | NO   |     | NULL    |                |
+----------------+--------+------+-----+---------+----------------+

mysql> desc quiz_questions;
+-------------+--------+------+-----+---------+----------------+
| Field       | Type   | Null | Key | Default | Extra          |
+-------------+--------+------+-----+---------+----------------+
| id          | bigint | NO   | PRI | NULL    | auto_increment |
| quiz_id     | bigint | NO   | MUL | NULL    |                |
| question_id | bigint | NO   | MUL | NULL    |                |
+-------------+--------+------+-----+---------+----------------+

mysql> desc questions;
+----------------+-----------------------+------+-----+---------+----------------+
| Field          | Type                  | Null | Key | Default | Extra          |
+----------------+-----------------------+------+-----+---------+----------------+
| id             | bigint                | NO   | PRI | NULL    | auto_increment |
| topic_id       | bigint                | NO   | MUL | NULL    |                |
| question_text  | text                  | NO   |     | NULL    |                |
| option_a       | varchar(255)          | NO   |     | NULL    |                |
| option_b       | varchar(255)          | NO   |     | NULL    |                |
| option_c       | varchar(255)          | NO   |     | NULL    |                |
| option_d       | varchar(255)          | NO   |     | NULL    |                |
| correct_answer | enum('A','B','C','D') | NO   |     | NULL    |                |
| marks          | int                   | YES  |     | 1       |                |
+----------------+-----------------------+------+-----+---------+----------------+

mysql> desc attempts;
+------------+----------------------------+------+-----+-----------+----------------+
| Field      | Type                       | Null | Key | Default   | Extra          |
+------------+----------------------------+------+-----+-----------+----------------+
| id         | bigint                     | NO   | PRI | NULL      | auto_increment |
| student_id | bigint                     | NO   | MUL | NULL      |                |
| quiz_id    | bigint                     | NO   | MUL | NULL      |                |
| start_time | datetime                   | NO   |     | NULL      |                |
| end_time   | datetime                   | YES  |     | NULL      |                |
| score      | int                        | YES  |     | 0         |                |
| status     | enum('COMPLETED','MISSED') | YES  |     | COMPLETED |                |
+------------+----------------------------+------+-----+-----------+----------------+

mysql> desc answers;
+-----------------+-----------------------+------+-----+---------+----------------+
| Field           | Type                  | Null | Key | Default | Extra          |
+-----------------+-----------------------+------+-----+---------+----------------+
| id              | bigint                | NO   | PRI | NULL    | auto_increment |
| attempt_id      | bigint                | NO   | MUL | NULL    |                |
| question_id     | bigint                | NO   | MUL | NULL    |                |
| selected_option | enum('A','B','C','D') | YES  |     | NULL    |                |
+-----------------+-----------------------+------+-----+---------+----------------+
