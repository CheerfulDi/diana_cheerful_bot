-- liquibase formatted sql

-- changeset Diana:1
create table notification_task(

                                  id                      serial NOT NULL PRIMARY KEY,
                                  chat_id                 bigint NOT NULL,
                                  notification_date       timestamp NOT NULL,
                                  notification_message    varchar(255) NOT NULL,
                                  status                  varchar(255) NOT NULL DEFAULT 'SCHEDULED'
);

-- changeset Diana:2
CREATE INDEX notification_task_data_idx ON notification_task (notification_date) WHERE status = 'SCHEDULED';

-- changeset Diana:3
ALTER TABLE notification_task ADD COLUMN sent_date timestamp;
