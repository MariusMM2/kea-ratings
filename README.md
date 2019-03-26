# KEA Ratings - A course and teacher feedback app

Provided you're a student at KEA, this app allows you to submit your experience with your favorite teachers and courses during your education.

## Structure and Logic

A teacher and a course are modelled into a single entity containing a name, a picture and multiple scores. 

Each of these scores (ratings) must have a student that submitted them. If a student tries to submit a second score for a teacher or course, he will instead have his original score presented for editing.

The available courses, teachers and students that can log in are static, while the scores for each item are stored in a SQLite database on the device.

## Usage

1. Start the app.
2. Login with your student credentials*.
3. Choose a teacher or course to rate by tapping the arrow on their card.
4. Start the rating by tapping the FAB in the corner.
5a. If you rated that teacher or course before, you will see your previous rating, which you can modify.
5b. If this is the first time you rate that item, you will see empty Rating Bars which you must fill out.
6. Submit your rating by tapping the new FAB in the corner. You will see the changes in the overall rating of the item after submitting.

##### Notes:
\* The currently available credentials are:  
Usernames:  
stud1@stud.kea.dk  
stud2@stud.kea.dk  
stud3@stud.kea.dk  
stud4@stud.kea.dk  
stud5@stud.kea.dk  
  
Password:  
12345 (same for all)
