select orderheade0_.id                 as id1_4_0_,
       orderheade0_.created_date       as created_2_4_0_,
       orderheade0_.last_modified_date as last_mod3_4_0_,
       orderheade0_.bill_to_address    as bill_to_4_4_0_,
       orderheade0_.bill_to_city       as bill_to_5_4_0_,
       orderheade0_.bill_to_state      as bill_to_6_4_0_,
       orderheade0_.bill_to_zip_code   as bill_to_7_4_0_,
       orderheade0_.customer_id        as custome13_4_0_,
       orderheade0_.order_status       as order_st8_4_0_,
       orderheade0_.shipping_address   as shipping9_4_0_,
       orderheade0_.shipping_city      as shippin10_4_0_,
       orderheade0_.shipping_state     as shippin11_4_0_,
       orderheade0_.shipping_zip_code  as shippin12_4_0_,
       orderappro1_.id                 as id1_3_1_,
       orderappro1_.created_date       as created_2_3_1_,
       orderappro1_.last_modified_date as last_mod3_3_1_,
       orderappro1_.approved_by        as approved4_3_1_,
       orderappro1_.order_header_id    as order_he5_3_1_
from order_header orderheade0_
         left outer join order_approval orderappro1_ on orderheade0_.id = orderappro1_.order_header_id
where orderheade0_.id = ?;

/* lazy loading */

select orderheade0_.id                 as id1_4_0_,
       orderheade0_.created_date       as created_2_4_0_,
       orderheade0_.last_modified_date as last_mod3_4_0_,
       orderheade0_.bill_to_address    as bill_to_4_4_0_,
       orderheade0_.bill_to_city       as bill_to_5_4_0_,
       orderheade0_.bill_to_state      as bill_to_6_4_0_,
       orderheade0_.bill_to_zip_code   as bill_to_7_4_0_,
       orderheade0_.customer_id        as custome13_4_0_,
       orderheade0_.order_status       as order_st8_4_0_,
       orderheade0_.shipping_address   as shipping9_4_0_,
       orderheade0_.shipping_city      as shippin10_4_0_,
       orderheade0_.shipping_state     as shippin11_4_0_,
       orderheade0_.shipping_zip_code  as shippin12_4_0_,
       orderappro1_.id                 as id1_3_1_,
       orderappro1_.created_date       as created_2_3_1_,
       orderappro1_.last_modified_date as last_mod3_3_1_,
       orderappro1_.approved_by        as approved4_3_1_,
       orderappro1_.order_header_id    as order_he5_3_1_
from order_header orderheade0_
         left outer join order_approval orderappro1_ on orderheade0_.id = orderappro1_.order_header_id
where orderheade0_.id = ?

/* eager loading */

select orderheade0_.id                 as id1_4_0_,
       orderheade0_.created_date       as created_2_4_0_,
       orderheade0_.last_modified_date as last_mod3_4_0_,
       orderheade0_.bill_to_address    as bill_to_4_4_0_,
       orderheade0_.bill_to_city       as bill_to_5_4_0_,
       orderheade0_.bill_to_state      as bill_to_6_4_0_,
       orderheade0_.bill_to_zip_code   as bill_to_7_4_0_,
       orderheade0_.customer_id        as custome13_4_0_,
       orderheade0_.order_status       as order_st8_4_0_,
       orderheade0_.shipping_address   as shipping9_4_0_,
       orderheade0_.shipping_city      as shippin10_4_0_,
       orderheade0_.shipping_state     as shippin11_4_0_,
       orderheade0_.shipping_zip_code  as shippin12_4_0_,
       foos1_.order_header_id          as order_he5_2_1_,
       foos1_.id                       as id1_2_1_,
       foos1_.id                       as id1_2_2_,
       foos1_.created_date             as created_2_2_2_,
       foos1_.last_modified_date       as last_mod3_2_2_,
       foos1_.amount                   as amount4_2_2_,
       foos1_.order_header_id          as order_he5_2_2_,
       orderappro2_.id                 as id1_3_3_,
       orderappro2_.created_date       as created_2_3_3_,
       orderappro2_.last_modified_date as last_mod3_3_3_,
       orderappro2_.approved_by        as approved4_3_3_,
       orderappro2_.order_header_id    as order_he5_3_3_
from order_header orderheade0_
         left outer join foo foos1_ on orderheade0_.id = foos1_.order_header_id
         left outer join order_approval orderappro2_ on orderheade0_.id = orderappro2_.order_header_id
where orderheade0_.id = ?

