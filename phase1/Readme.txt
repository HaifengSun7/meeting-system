CSC207H1F - Phase 1

WELCOME TO THE CONFERENCE SYSTEM PROGRAM OF GROUP_0229!

- Group Members: Shaohong Chen, Dechen Han, Ruimin Mao, You Peng, Haifeng Sun, Wei Tao, Haoyang Wang, Jinyang Zhao

- General Instructions:
    - To run the program, please run >src >Main.java.
    - Assumptions: All events (or “talks”) are 1 hour long, when adding events, please enter 1 when being asked for durations.
    - Meetings have to start after 9 a.m. and ends before 5 p.m., which means we need to start the latest meeting in a day before 4 p.m.
    - No comma is allowed in event descriptions.
    - No comma is allowed in usernames.
    - The UMLs in \phase1 folder are in the series of design.pdf. However, There are .uml and .png files with detailed filenames in \UMLs folder.

- Some other info:
    - All inputs are done through command line.
    - Usually, commands are surrounded by square brackets.
        For example, “Input [e] to quit the system” requires the user to input “e” and press enter to send the line.
        “[3] Send a message” requires the user to input “3” and press enter to send the line. Most inputs in the menus are done this way.
    - Another type of input is “press enter to do something”.
        User is required to press down enter, while other inputs are almost always ignored.
    - Choose from a specific event/room/anything:
        Enter the number representing the things listed. They should be in the form of "[number] choice"
    - Finally, the last type of input is “Enter something to do something”.
        Here, user is required to enter a simple command, which is usually “username”, “password” or “serial number”.

-Some preparations:
    - For your ease, we have created some users, events, rooms and messages for testing.
    - Please keep in mind that logging in is case sensitive.

Here is a list of users, rooms, events and messages that are stored in our .csv files.
You can always access those through src > resources folder.
We strongly suggest not to change any of the files externally. The program always saves the file when an user logs off.

Users:
Username: Organizer, Password: password, Type: Organizer, Can send messages to: Speaker1, User1, User2
Username: User1,     Password: password, Type: Attendee,  Can send messages to: Speaker1, User2
Username: User2,     Password: password, Type: Attendee,  Can send messages to: User1, Organizer
Username: Speaker1,  Password: password, Type: Speaker,   Can send messages to: Organizer, User1, User2

Rooms:
Room Number: 1, Capacity: 3
Room Number: 2, Capacity: 3
Room Number: 3, Capacity: 2

Events:
Room Number: 1, Time: 2000-10-25 12:00:00.0, Duration: 1 hour, Description: This is an event, Attendees: User1,User2, Speaker: Speaker1
Room Number: 2, Time: 2010-01-19 12:00:00.0, Duration: 1 hour, Description: This is an event, Attendees: User1,User2, Speaker: None
Room Number: 2, Time: 2010-01-19 13:00:00.0, Duration: 1 hour, Description: This is an event, Attendees: User1,User2, Speaker: None
Room Number: 3, Time: 2015-12-13 15:00:00.0, Duration: 1 hour, Description: This is an event, Attendees: User1

Messages:
Sender: User1,     Receiver: User2,     Message: Hello, User2.
Sender: User2,     Receiver: User1,     Message: Hey!
Sender: User1,     Receiver: User2,     Message: Nice to see you!
Sender: Organizer, Receiver: User1,     Message: Hello everyone!
Sender: Organizer, Receiver: User2,     Message: Hello everyone!
Sender: Organizer, Receiver: Speaker1,  Message: Hello everyone!