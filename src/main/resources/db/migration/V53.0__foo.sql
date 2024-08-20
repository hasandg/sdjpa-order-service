create table foo
(
    id bigint not null auto_increment primary key,
    amount int,
    order_header_id bigint,
    created_date timestamp,
    last_modified_date timestamp,
    constraint foo_order_header_fk FOREIGN KEY (order_header_id) references order_header(id)
);

# alter table order_header
#     add column foo_id bigint;
#
# alter table order_header
#     add constraint order_foo_fk
#         foreign key (foo_id) references foo (id);

# insert into foo (amount) values ('123');

# update order_header set order_header.foo_id = (select id from foo limit 1);