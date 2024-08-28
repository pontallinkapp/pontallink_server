create table friendships_requests(

    id bigint not null auto_increment,
    id_user_request bigint not null,
    id_user_received bigint not null,
    status enum('PENDING', 'ACCEPTED', 'DECLINED') NOT NULL DEFAULT 'PENDING',

    primary key(id)

);