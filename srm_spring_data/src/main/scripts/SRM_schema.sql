create table address (id bigint not null auto_increment, first_line varchar(255), postcode varchar(255), second_line varchar(255), primary key (id)) engine=InnoDB;
create table assignment_type (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB;
create table assignment_type_student_results (assignment_type_id bigint not null, student_results_id bigint not null, primary key (assignment_type_id, student_results_id)) engine=InnoDB;
create table contact_detail (id bigint not null auto_increment, email varchar(255), phone_number varchar(255), primary key (id)) engine=InnoDB;
create table form_group_list (id bigint not null auto_increment, group_name varchar(255), teacher_id bigint, primary key (id)) engine=InnoDB;
create table guardians (id bigint not null auto_increment, first_name varchar(255), last_name varchar(255), contact_detail_id bigint, address_id bigint, primary key (id)) engine=InnoDB;
create table report (id bigint not null auto_increment, comments varchar(255), student_id bigint, subject_id bigint, teacher_id bigint, primary key (id)) engine=InnoDB;
create table student_guardian (student_id bigint not null, guardian_id bigint not null, primary key (student_id, guardian_id)) engine=InnoDB;
create table student_subjectlist (student_id bigint not null, subjectclasslist_id bigint not null, primary key (student_id, subjectclasslist_id)) engine=InnoDB;
create table student_result (id bigint not null auto_increment, comments varchar(255), score varchar(255), student_id bigint, student_work_id bigint, teacher_id bigint, primary key (id)) engine=InnoDB;
create table students (id bigint not null auto_increment, first_name varchar(255), last_name varchar(255), contact_detail_id bigint, form_group_list_id bigint, teacher_id bigint, primary key (id)) engine=InnoDB;
create table student_work (id bigint not null auto_increment, contributor bit not null, max_score integer, title varchar(255), assignment_type_id bigint, subject_id bigint, teacher_uploader_id bigint, primary key (id)) engine=InnoDB;
create table subject_class_list (id bigint not null auto_increment, group_name varchar(255), subject_id bigint, teacher_id bigint, primary key (id)) engine=InnoDB;
create table subjects (id bigint not null auto_increment, subject_name varchar(255), primary key (id)) engine=InnoDB;
create table teacher_subject (teacher_id bigint not null, subject_id bigint not null, primary key (teacher_id, subject_id)) engine=InnoDB;
create table teachers (id bigint not null auto_increment, first_name varchar(255), last_name varchar(255), department varchar(255), contact_detail_id bigint, primary key (id)) engine=InnoDB;
create table threshold (id bigint not null auto_increment, alphabetical varchar(255), numerical integer not null, primary key (id)) engine=InnoDB;
create table threshold_list (id bigint not null auto_increment, primary key (id)) engine=InnoDB;
create table threshold_list_threshold (thresholdlist_id bigint not null, threshold_id bigint not null, primary key (thresholdlist_id, threshold_id)) engine=InnoDB;
alter table assignment_type_student_results add constraint UK_39982bissb4g939a8ry45dimx unique (student_results_id);
alter table assignment_type_student_results add constraint FKbt58542na3rv50cw8g77o9gtw foreign key (student_results_id) references student_result (id);
alter table assignment_type_student_results add constraint FKpmoxif9y6njdu6k5dbl2xqbhq foreign key (assignment_type_id) references assignment_type (id);
alter table form_group_list add constraint FKt01wdbsksvarlj2545rok7cfm foreign key (teacher_id) references teachers (id);
alter table guardians add constraint FKbsjm6mhydwb3tpfnnfqluo5xt foreign key (contact_detail_id) references contact_detail (id);
alter table guardians add constraint FKd20g3ng3kbtldj12uwnlhqiyl foreign key (address_id) references address (id);
alter table report add constraint FK3t0ypnabkebc7avco6yt0f0xt foreign key (student_id) references students (id);
alter table report add constraint FK9ifs5m239o2dj1xcrilna249s foreign key (subject_id) references subjects (id);
alter table report add constraint FKhqm4ppa6v9507g3qi8941jloq foreign key (teacher_id) references teachers (id);
alter table student_guardian add constraint FKilnqkwg0m1t2e7kc01lsmo2ib foreign key (guardian_id) references guardians (id);
alter table student_guardian add constraint FKn46qqdyqp1nihhb59owe7dxjk foreign key (student_id) references students (id);
alter table student_subjectlist add constraint FKh8ce89ukrefyui23l608vaue3 foreign key (subjectclasslist_id) references subject_class_list (id);
alter table student_subjectlist add constraint FK9lfxm24pe2pc1s1yclliwhs0a foreign key (student_id) references students (id);
alter table student_result add constraint FKj47lo2iyecr24euqm8qnwd8wn foreign key (student_id) references students (id);
alter table student_result add constraint FK7u58e4birxqy8ppjeu2s7ftc7 foreign key (student_work_id) references student_work (id);
alter table student_result add constraint FKer09tbockdcynbybd93f5k9xu foreign key (teacher_id) references teachers (id);
alter table students add constraint FKfrscrmen2ar48a9eo2woj1n2y foreign key (contact_detail_id) references contact_detail (id);
alter table students add constraint FKl3qt1lyy9qtvvouo9e4jfp7tn foreign key (form_group_list_id) references form_group_list (id);
alter table students add constraint FKbrb7umgbkqrmj9lfwkf0p2r7r foreign key (teacher_id) references teachers (id);
alter table student_work add constraint FKiqwl6qeil5fbnp74cvg9er34u foreign key (assignment_type_id) references assignment_type (id);
alter table student_work add constraint FKh0kea5vyxjboylk5tcmn1me2j foreign key (subject_id) references subjects (id);
alter table student_work add constraint FKjds3pg0wlbmx17fvglygdbd8s foreign key (teacher_uploader_id) references teachers (id);
alter table subject_class_list add constraint FKf78kjac0w6nwmf6mcjo1c9myl foreign key (subject_id) references subjects (id);
alter table subject_class_list add constraint FKbqyoj1q588q3oi22ege5lhs3p foreign key (teacher_id) references teachers (id);
alter table teacher_subject add constraint FKlbm776mn6b4lu6hsdf29unq6x foreign key (subject_id) references subjects (id);
alter table teacher_subject add constraint FKa1d1n8m8pv7rr2493x6jp3f3q foreign key (teacher_id) references teachers (id);
alter table teachers add constraint FKe4mgvr2g7umulytevxxb6bxb1 foreign key (contact_detail_id) references contact_detail (id);
alter table threshold_list_threshold add constraint FKjaprn4sp4av4nmkc7ypvmvx4u foreign key (threshold_id) references threshold (id);
alter table threshold_list_threshold add constraint FKsd7kxwgmteyw9swxjsk92i9af foreign key (thresholdlist_id) references threshold_list (id);
