CREATE TABLE adm_project
(
   project_name varchar(60) not null, -- we accept 60 (3x20)
   project_description varchar(512), -- description fields always 512 chars
   version integer,
   PRIMARY KEY (project_name)
);
CREATE TABLE adm_project_whitelist_uri
(
   project_project_name varchar(60) not null,  -- we accept 60 (3x20)
   project_whitelist_uris varchar(255) not null,
   PRIMARY KEY (project_project_name, project_whitelist_uris)
);
CREATE TABLE adm_user
(
   user_name varchar(45) not null, -- we accpet 45 (3 x 15) 
   user_email_adress varchar(255) not null,
   user_apitoken varchar(255) not null,
   user_onetimetoken varchar(255),
   user_ott_created timestamp,
   version integer,
   PRIMARY KEY (user_name)
);
CREATE TABLE adm_project_to_owner
(
   projects_project_name varchar(60) not null, -- we accept 60 (3x20)s
   users_user_name varchar(45) not null, -- max 15, utf8=15*3 = 45
   PRIMARY KEY (projects_project_name, users_user_name)
);
CREATE TABLE adm_project_to_user
(
   projects_project_name varchar(60) not null, -- we accept 60 (3x20)s
   users_user_name varchar(45) not null, -- max 15, utf8=15*3 = 45
   PRIMARY KEY (projects_project_name, users_user_name)
);
ALTER TABLE adm_project_to_user ADD CONSTRAINT c02_adm_project2user_username FOREIGN KEY (users_user_name) REFERENCES adm_user (user_name);
ALTER TABLE adm_project_to_user ADD CONSTRAINT c03_adm_project2user_projectname FOREIGN KEY (projects_project_name) REFERENCES adm_project (project_name);
CREATE TABLE adm_user_to_roles
(
   user_user_name varchar(45) not null, -- we accpet 45 (3 x 15) 
   user_roles varchar(30) not null, -- enum value, max:30
   PRIMARY KEY (user_user_name, user_roles)
);
CREATE TABLE adm_user_selfregistration
(
   user_name varchar(45) not null, -- we accpet 45 (3 x 15) 
   email_adress varchar(255) not null,
   version integer,
   PRIMARY KEY (user_name)
);
CREATE TABLE auth_user
(
   user_name varchar(45) not null, -- we accpet 45 (3 x 15) 
   user_apitoken varchar(255),
   role_admin boolean,
   role_user boolean,
   role_owner boolean,
   version integer,
   PRIMARY KEY (user_name)
);
CREATE TABLE scan_product_result
(
   uuid uuid not null,
   product_id varchar(30) not null, -- enum value, max:30
   result text,
   sechub_job_uuid uuid not null,
   version integer,
   PRIMARY KEY (uuid)
);
CREATE TABLE scan_report
(
   uuid uuid not null,
   config varchar(8192), -- we accept maximum of 8192 chars (8kb)
   result text,
   sechub_job_uuid uuid,
   traffic_light varchar(30),  -- enum value, max:30
   version integer,
   PRIMARY KEY (uuid)
);
CREATE TABLE schedule_access
(
   projectname varchar(60) not null,  -- we accept 60 (3x20)
   username varchar(45) not null,  -- we accpet 45 (3 x 15) 
   version integer,
   PRIMARY KEY (projectname, username)
);
CREATE TABLE schedule_project_whitelist
(
   projectname varchar(60) not null, -- we accept 60 (3x20)
   uri varchar(255) not null,
   version integer,
   PRIMARY KEY (projectname, uri)
); 
CREATE TABLE schedule_sechub_job
(
   uuid uuid not null,
   created timestamp not null,
   ended timestamp,
   result varchar(30) not null, -- enum value, max:30
   state varchar(30) not null, -- enum value, max:30
   configuration varchar(8192), -- we accept maximum of 8192 chars (8kb)
   owner varchar(45) not null, -- we accpet 45 (3 x 15) 
   project_name varchar(60) not null, -- we accept 60 (3x20)
   started timestamp,
   traffic_light varchar(30),  -- enum value, max:30
   version integer,
   PRIMARY KEY (uuid)
);
