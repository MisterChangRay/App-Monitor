
CREATE TABLE IF NOT EXISTS  app_info (
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
  test_url varchar(255) DEFAULT NULL
) ;



CREATE TABLE IF NOT EXISTS  server_info (
  ip varchar(255) DEFAULT NULL ,
  port varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL ,
  username varchar(255) DEFAULT NULL,
  remark varchar(255) DEFAULT NULL,

) ;