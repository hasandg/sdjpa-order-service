drop table if exists parent cascade;

create table parent
(
    id        bigint not null auto_increment primary key,
    created_date timestamp,
    last_modified_date timestamp
) engine = InnoDB;

create table child
(
    id bigint not null auto_increment primary key,
    amount int,
    parent_id bigint,
    created_date timestamp,
    last_modified_date timestamp,
    constraint child_parent_fk FOREIGN KEY (parent_id) references parent(id)
);