
CREATE TABLE IF NOT EXISTS  app_info (
  id integer not null generated always as identity (start with 1 increment by 1),
  project_group varchar(255) DEFAULT NULL ,
  project_name varchar(255) DEFAULT NULL,
  deploy_path varchar(255) DEFAULT NULL ,
  deploy_file varchar(255) DEFAULT NULL ,
  port varchar(255) DEFAULT NULL ,
  remark varchar(255) DEFAULT NULL ,
  status int DEFAULT 0,
  system_type int DEFAULT 0,
  server_ip varchar(255) DEFAULT NULL,
  process_id varchar(255) DEFAULT NULL,
  auto_restart int DEFAULT 0,
  test_cmd varchar(255) DEFAULT NULL,
  start_cmd varchar(255) default null,
  create_time TIMESTAMP DEFAULT NULL ,
  update_time TIMESTAMP DEFAULT NULL,
  last_start_time TIMESTAMP DEFAULT NULL,
  comm_type int DEFAULT 0,
  scan_type int DEFAULT 0
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