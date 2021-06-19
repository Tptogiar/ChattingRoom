create table if not exists user(
    userID int primary key auto_increment,
    userName varchar(15) not null ,
    userPassword varchar(20) not null,
    isOnline tinyint(1) not null default 0,
    isBlock tinyint(1) not null default 0
);


create table if not exists message(
    messageID int primary key auto_increment,
    sendUserID int not null,
    sendUserName varchar(15) not null,
    receiverUserID int not null,
    transferTime datetime not null
);


create table  if not exists contain(
    message_id int primary key auto_increment,
    text varchar(2048),
    image mediumblob,
    vedio longblob,
    files longblob,
    constraint message_contain
        foreign key (message_id)
        references message(messageID)
        on delete cascade
);


create table  if not exists friend(
    userID int not null ,
    friendID int not null ,
    group_name varchar(20)
);
set global max_allowed_packet = 20000
