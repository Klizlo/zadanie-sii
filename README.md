## How to run the application:
1. Download the project from repository.
2. Open it by IDE e.g. IntelliJ IDEA.
3. Run the application.

Notice:<br/>
Emails sent during the registration for lecture are saved in powiadomienia.txt file in /src/main/resources directory.

## URLs with sample requests.

Get the conference schedule:<br/>
GET: localhost:8080/lectures

Sign in the lecture:<br/>
PUT: localhost:8080/lectures/{lecture_id}/users<br/>
Request body:<br/>
{<br/>
&emsp;"email": "{email}",<br/>
&emsp;"login": "{login}"<br/>
}

For example:<br/>
localhost:8080/lectures/1/users<br/>
{<br/>
&emsp;"email": "j.nowak@poczta.pl",<br/>
&emsp;"login": "Janek"<br/>
}

Get registered users:<br/>
GET: localhost:8080/users

Get information about lectures on which user signed up for:<br/>
GET: localhost:8080/users/{login}/lectures

For example:<br/>
localhost:8080/users/Janek/lectures

Change user email:<br/>
PUT: localhost:8080/users/{login}<br/>
Request body:<br/>
{<br/>
&emsp;"email": "{email}"<br/>
}

For example:<br/>
localhost:8080/users/Janek<br/>
{<br/>
&emsp;"email": "j.nowak123@poczta.pl"<br/>
}

Cancel booking for lecture:<br/>
DELETE: localhost:8080/users/{login}/lectures/{lecture_id}

For example:
localhost:8080/users/Janek/lectures/1

Get statement for organizers:<br/>
GET: localhost:8080/lectures/statement