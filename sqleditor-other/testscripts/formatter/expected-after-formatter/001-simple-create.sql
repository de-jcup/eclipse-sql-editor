CREATE TABLE adm_project
(
   project_name varchar (60) not null,
   -- we accept 60 (3x20)
   project_description varchar (512),
   -- description fields always 512 chars
   version integer,
   PRIMARY KEY (project_name)
)