insert into "users"(username,password,enabled)
values('MarkoMaric','$2a$10$.9rRB6hrHmd3Me6Rv4dDv.omhK8QQQxvCU2uVa3jh83AY/8tgx38i',true);

insert into "users"(username,password,enabled)
values('MarijaMarijanovic','$2a$10$.9rRB6hrHmd3Me6Rv4dDv.omhK8QQQxvCU2uVa3jh83AY/8tgx38i',true);

insert into "users"(username,password,enabled)
values('IvoIvic','$2a$10$.9rRB6hrHmd3Me6Rv4dDv.omhK8QQQxvCU2uVa3jh83AY/8tgx38i',true);

insert into "users"(username,password,enabled)
values('AnaAnic','$2a$10$.9rRB6hrHmd3Me6Rv4dDv.omhK8QQQxvCU2uVa3jh83AY/8tgx38i',true);

insert into "users"(username,password,enabled)
values('IvanIvanovic','$2a$10$.9rRB6hrHmd3Me6Rv4dDv.omhK8QQQxvCU2uVa3jh83AY/8tgx38i',true);


insert into "authorities"(username, authority)
values('MarkoMaric','LECTURER');

insert into "authorities"(username, authority)
values('IvanIvanovic','LECTURER');

insert into "authorities"(username, authority)
values('MarijaMarijanovic','STUDENT');

insert into "authorities"(username, authority)
values('IvoIvic','STUDENT');

insert into "authorities"(username, authority)
values('AnaAnic','STUDENT');


insert into "course"(course_id,name,username)
values(99,'C# and .NET Internet application','MarkoMaric');

insert into "course"(course_id,name,username)
values(98,'Android Development','MarkoMaric');

insert into "course"(course_id,name,username)
values(97,'iOS Development','MarkoMaric');


insert into "student_enrollment"(course,student)
values(98,'IvoIvic');

insert into "student_enrollment"(course,student)
values(99,'IvoIvic');

