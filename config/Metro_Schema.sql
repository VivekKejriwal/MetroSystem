Create database if not exists Metro;

Use Metro;

create table if not exists `stations`(
`station_id` int primary key not null auto_increment,
`station_name` nvarchar(30) not null,
`next_station_id` int,
`prev_station_id` int,
 index (station_id)
); 

create table if not exists `cards` (
  `card_id` int primary key not null,
  `user_id` int not null,
  `balance` decimal,
   index (card_id),
   index(user_id)
);

create table if not exists `user_details` (
  `user_id` int primary key not null auto_increment,
  `first_name` varchar(30) not null,
  `last_name` varchar(30) not null,
  `address` nvarchar(255) not null,
  `phn_number` long not null,
  `email` varchar(50) not null unique,
  `password` varchar(30) not null,
  index(user_id)
  
);

create table if not exists `transaction_histories` (
  `transaction_history_id` int primary key not null auto_increment,
  `card_id` int not null,
  `swipe_in_time` datetime not null,
  `swipe_out_time` datetime not null,
  `boarded_station_id` int not null,
  `destination_station_id` int,
  `fare` decimal default 0.00,
  index(transaction_history_id)
);

ALTER TABLE `cards` ADD FOREIGN KEY (`user_id`) REFERENCES `user_details` (`user_id`);
ALTER TABLE `transaction_histories` ADD FOREIGN KEY (`boarded_station_id`) REFERENCES `stations` (`station_id`);
ALTER TABLE `transaction_histories` ADD FOREIGN KEY (`destination_station_id`) REFERENCES `stations` (`station_id`);
ALTER TABLE `transaction_histories` ADD FOREIGN KEY (`card_id`) REFERENCES `cards` (`card_id`);

insert into stations values(301,'Delhi',302,null);
insert into stations values(302,'Mumbai',303,301);
insert into stations values(303,'Kolkata',304,302);
insert into stations values(304,'Chennai',305,303);
insert into stations values(305,'Bangalore',null,304);

-- select * from transaction_histories;
-- select * from user_details;
-- select * from cards;
-- insert into user_details values(0,"adaf","dfg","dfgdfg",34534,"ail@gmail.com","pass");
-- drop database metro;
