
CREATE TABLE IF NOT EXISTS  app_info (
  id integer not null generated always as identity (start with 1 increment by 1),
  project_group varchar(255) DEFAULT NULL ,
  project_name varchar(255) DEFAULT NULL,
  file_name varchar(255) DEFAULT NULL ,
  path varchar(255) DEFAULT NULL,
  dubbo_port varchar(255) DEFAULT NULL,
  port varchar(255) DEFAULT NULL ,
  remark varchar(255) DEFAULT NULL ,
  status int DEFAULT 0,
  create_time TIMESTAMP DEFAULT NULL ,
  start_time TIMESTAMP DEFAULT NULL,
  server_addr varchar(255) DEFAULT NULL,
  process_id varchar(255) DEFAULT NULL,
  auto_restart int DEFAULT 0,
  test_url varchar(255) DEFAULT NULL,
  start_cmd varchar(255) default null
) ;



CREATE TABLE IF NOT EXISTS  server_info (
  id integer not null generated always as identity (start with 1 increment by 1),
  ip varchar(255) DEFAULT NULL ,
  port varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL ,
  username varchar(255) DEFAULT NULL,
  remark varchar(255) DEFAULT NULL,
  status int DEFAULT NULL,
  create_time timestamp DEFAULT NULL,
  update_time timestamp DEFAULT NULL,
  report_time timestamp DEFAULT NULL
) ;