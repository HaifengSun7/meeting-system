CSC207H1F - Phase 2

WELCOME TO THE CONFERENCE SYSTEM PROGRAM OF GROUP_0229!

- Group Members: Shaohong Chen, Dechen Han, Ruimin Mao, You Peng, Haifeng Sun, Wei Tao, Haoyang Wang, Jinyang Zhao
- We have demonstrated the working program on Dec.10,2020, to our TA Andrew Chow, during the last weekly meeting.

-Here is the guide to set up all required additional jdks.
    1, Go to https://github.com/xerial/sqlite-jdbc , Download the jar file of sqlite-jdbc.
        (We also have a copy of that file in <phase2\Additional files> folder)
    2, Put the jdk file into the folder where all other jdks are located.
        - This location can be found in File -> Project Settings -> SDKs.
        - Make sure the jre file is in the same folder as all other jres.
    3, Open intellij, in File > Project Structure > SDKs > add the jar file.
    4, Right click anywhere on the project panel, chose New > Datasource > Sqlite
    5, Fill in the location of database.db in phase2\src\resources.
    6, Install any other driver in Intellij if needed. Click test connection.
    7, If the connection is successfully built, you are good to go.

- General Instructions:
    - We are using JDK 8(From the link in quercus) and a JDBC driver of SQLite for the database.
    - To run the program, please run \phase 2\src\Main.java.
    - Meetings have to START after 9 a.m. and END before 5 p.m.
    - The UMLs in \phase2 folder are in the form of design.pdf.

- Some other info:
    - All inputs are done through command line. They are all case specific.
    - Please do not enter "Enter" to fast after each input.
    - Usually, commands are surrounded by square brackets.
        For example, “Input [e] to quit the system” requires the user to input “e” and press enter to send the line.
        “[3] Send a message” requires the user to input “3” and press enter to send the line. Most inputs in the menus are done this way.
    - Another type of input is “press enter to do something”.
        User is required to press down enter, while other inputs are almost always ignored.
    - Choose from a specific event/room/anything:
        Enter the number representing the things listed. They should be in the form of "[number] choice"
    - Finally, the last type of input is “Enter something to do something”.
        Here, user is required to enter a simple command, which is usually “username”, “password” or “serial number”.

- Some preparations:
    - For your ease, we have created some users, events, rooms and messages for testing.
    - Please keep in mind that all inputs are case sensitive.

- Here is a list of testing database that already exists, to help you test the project.
You can always access the database.db file through src\resources folder.
We strongly suggest not to change any of the files externally. The program always saves the file when an user logs off.

[Conferences]:
-Conference1
-NewConference
-DemoConf

[Users]:
Username: Organizer,     Password: password, Type: Organizer, Can send messages to: All
Username: OrganizerDemo, Password: password, Type: Organizer, Can send messages to: All
Username: User1,         Password: password, Type: Attendee,  Can send messages to: User2, Organizer
Username: User2,         Password: password, Type: Attendee,  Can send messages to: User1, Organizer
Username: User3,         Password: password, Type: Attendee,  Can send messages to: Organizer
Username: VIPUser,       Password: password, Type: VIP,       Can send messages to: Organizer
Username: Speaker1,      Password: password, Type: Speaker,   Can send messages to: User1, User2
Username: Speaker2,      Password: password, Type: Speaker,   Haven't received any message. Blank contact list.
Username: Speaker3,      Password: password, Type: Speaker,   Haven't received any message. Blank contact list.

[Sign-Up status]:
Speaker1 is the speaker of event 0
Speaker2 is the speaker of event 1
Speaker3 is the speaker of event 3

[Rooms]:
Room Number: 1,      Capacity: 3
Room Number: 2,      Capacity: 3
Room Number: 3,      Capacity: 2
Room Number: 202,    Capacity: 10
Room Number: 114514, Capacity: 1919810

[Events]:
ID Room MaxSpeakers MaxAttendees         Time           Length         Description                   Conference     VIP(Y/N)
0  202      1            9       2020-05-05 15:00:00.0    1            GreatThings                   Conference1       N
1   1       1            1       2020-05-05 15:00:00.0    1                Yes.                      Conference1       Y
2  202      0            5       2020-12-05 15:00:00.0    1   This is an event of a new conference   NewConference     N
3  202      3            5       2020-05-05 15:00:00.0    1                 Hi                       NewConference     N
4   1       0            2       1980-12-11 10:00:00.0    1             PartyDemo                     DemoConf         N

[Messages]:
Sender: User2,     Receiver: User1,     Message: Text.
Sender: Organizer, Receiver: User1      Message: Demo Message
Sender: Organizer, Receiver: User2      Message: Demo Message
Sender: Organizer, Receiver: User3      Message: Demo Message
Sender: Organizer, Receiver: VIPUser    Message: Demo Message

[Request]:
Sender: User1,     Status: Addressed,   Title:Demo,      Description: This is just a demo