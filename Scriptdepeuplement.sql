;              
CREATE USER IF NOT EXISTS "ROOT" SALT '6a796eac7fa2f4c5' HASH 'faf9a8aa81c858ee8b97127fe3553390b7edd4364f5c45694cc1b72216a9ca84' ADMIN;        
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_04F01D2F_31A4_4ED4_A783_84C48359C8C1" START WITH 161 BELONGS_TO_TABLE;               
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_D1C6344B_B3DF_43A9_953C_45112FD733B0" START WITH 38 BELONGS_TO_TABLE;
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_B83C002F_705B_4930_99C7_577292CD749B" START WITH 65 BELONGS_TO_TABLE;
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_9BA4D6A6_5543_417A_8EEA_09B3379E71DC" START WITH 68 BELONGS_TO_TABLE;
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_36BFCB4A_BE66_4C9A_B056_0D019DC74E6D" START WITH 104 BELONGS_TO_TABLE;               
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_B3B53E23_B60E_4C76_AC45_2B6E2116FE83" START WITH 73 BELONGS_TO_TABLE;
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_98A0FB15_5F2E_4500_A682_BE3D8F4580F5" START WITH 34 BELONGS_TO_TABLE;
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_93F20482_9AA4_475B_BDDE_10FBB3DFA34C" START WITH 8 BELONGS_TO_TABLE; 
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_3D6A7CE3_61F0_484D_A72E_8378F0CF9161" START WITH 62 BELONGS_TO_TABLE;
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_E2E2C85E_2907_408A_95E5_6F4DF11F475E" START WITH 66 BELONGS_TO_TABLE;
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_EA7BF31B_03F2_45B3_AF70_3271C3C12025" START WITH 97 BELONGS_TO_TABLE;
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_88CC91E7_EC15_4EE6_AEF4_159A909FB255" START WITH 75 BELONGS_TO_TABLE;
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_E8968A03_5699_4B2E_9CD8_20C8408197B6" START WITH 37 BELONGS_TO_TABLE;
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_8448C5C7_3CF4_441A_88AB_A10863F0D892" START WITH 35 BELONGS_TO_TABLE;
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_EFAC6FD4_A8EF_4215_B74B_548224E2676B" START WITH 177 BELONGS_TO_TABLE;               
CREATE SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_89F06185_18D9_4279_B98A_8EA58C343CA7" START WITH 34 BELONGS_TO_TABLE;
CREATE CACHED TABLE "PUBLIC"."RESERVATION"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_04F01D2F_31A4_4ED4_A783_84C48359C8C1" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_04F01D2F_31A4_4ED4_A783_84C48359C8C1",
    "START" DATETIME NOT NULL,
    "END" DATETIME NOT NULL,
    "LABEL" VARCHAR,
    "MEMO" TEXT,
    "TYPE" ENUM('LESSON', 'ADMISSION_EXAM', 'DEFENCE', 'EXAM_BOARD'),
    "ROOM" INT NOT NULL,
    "STATE" ENUM('NP', 'CANCELLED', 'POSTPONED') NOT NULL
);               
ALTER TABLE "PUBLIC"."RESERVATION" ADD CONSTRAINT "PUBLIC"."RESERVATION_PK" PRIMARY KEY("ID"); 
-- 34 +/- SELECT COUNT(*) FROM PUBLIC.RESERVATION;             
INSERT INTO "PUBLIC"."RESERVATION" VALUES
(57, TIMESTAMP '2021-01-16 08:00:00', TIMESTAMP '2021-01-16 09:00:00', 'No IDEA1', 'No IDEA2', 2, 1, 2),
(68, TIMESTAMP '2021-01-11 09:00:00', TIMESTAMP '2021-01-11 11:00:00', 'test1', 'test2', NULL, 1, 0),
(120, TIMESTAMP '2021-01-12 09:00:00', TIMESTAMP '2021-01-12 11:00:00', 'test test', 'test1 test1', 1, 1, 0),
(123, TIMESTAMP '2021-01-13 09:00:00', TIMESTAMP '2021-01-13 11:00:00', 'test x', 'test xx', 3, 1, 0),
(129, TIMESTAMP '2021-01-11 08:00:00', TIMESTAMP '2021-01-11 09:00:00', '', '', 0, 1, 0),
(132, TIMESTAMP '2021-01-12 08:00:00', TIMESTAMP '2021-01-12 09:00:00', '', '', 0, 1, 0),
(133, TIMESTAMP '2021-01-13 08:00:00', TIMESTAMP '2021-01-13 09:00:00', '', 'Ok', 0, 1, 0),
(134, TIMESTAMP '2021-01-14 08:00:00', TIMESTAMP '2021-01-14 09:00:00', '', '', 0, 1, 0),
(135, TIMESTAMP '2021-01-15 08:00:00', TIMESTAMP '2021-01-15 09:00:00', '', '', 0, 1, 0),
(136, TIMESTAMP '2021-01-11 13:30:00', TIMESTAMP '2021-01-11 16:30:00', '', '', 0, 1, 1),
(137, TIMESTAMP '2021-01-12 08:00:00', TIMESTAMP '2021-01-12 09:00:00', '', '', 0, 2, 0),
(138, TIMESTAMP '2021-01-14 16:30:00', TIMESTAMP '2021-01-14 18:00:00', STRINGDECODE('R\u00e9servation de salle'), '', NULL, 3, 0),
(139, TIMESTAMP '2021-01-12 18:00:00', TIMESTAMP '2021-01-12 19:00:00', STRINGDECODE('R\u00e9servation de salle'), '', NULL, 4, 0),
(140, TIMESTAMP '2021-01-19 08:00:00', TIMESTAMP '2021-01-19 09:00:00', '', '', 0, 1, 0),
(141, TIMESTAMP '2021-01-19 09:00:00', TIMESTAMP '2021-01-19 10:00:00', '', '', 1, 1, 2),
(142, TIMESTAMP '2021-01-19 10:00:00', TIMESTAMP '2021-01-19 11:00:00', '', '', 3, 1, 0),
(143, TIMESTAMP '2021-01-19 11:00:00', TIMESTAMP '2021-01-19 12:00:00', '', '', 2, 1, 0),
(144, TIMESTAMP '2021-01-19 12:00:00', TIMESTAMP '2021-01-19 13:00:00', '', '', NULL, 1, 0),
(145, TIMESTAMP '2021-01-13 13:00:00', TIMESTAMP '2021-01-13 14:00:00', '', '', NULL, 1, 0),
(146, TIMESTAMP '2021-01-23 08:00:00', TIMESTAMP '2021-01-23 09:00:00', 'No IDEA1', 'No IDEA2', 2, 1, 0),
(147, TIMESTAMP '2021-01-20 08:00:00', TIMESTAMP '2021-01-20 09:00:00', '', '', 1, 1, 0),
(148, TIMESTAMP '2021-01-04 08:00:00', TIMESTAMP '2021-01-04 10:00:00', '', '', 0, 1, 0),
(149, TIMESTAMP '2021-01-04 10:00:00', TIMESTAMP '2021-01-04 12:00:00', '', '', 0, 1, 0),
(150, TIMESTAMP '2021-01-04 14:00:00', TIMESTAMP '2021-01-04 18:00:00', '', '', 0, 1, 0),
(151, TIMESTAMP '2021-01-05 08:30:00', TIMESTAMP '2021-01-05 10:00:00', '', '', 0, 1, 0),
(152, TIMESTAMP '2021-01-05 10:00:00', TIMESTAMP '2021-01-05 11:30:00', '', '', 0, 1, 0),
(153, TIMESTAMP '2021-01-05 13:30:00', TIMESTAMP '2021-01-05 15:30:00', '', '', 0, 1, 0),
(154, TIMESTAMP '2021-01-06 09:00:00', TIMESTAMP '2021-01-06 12:00:00', '', '', 0, 1, 0),
(155, TIMESTAMP '2021-01-07 13:00:00', TIMESTAMP '2021-01-07 16:00:00', '', '', 1, 1, 0),
(156, TIMESTAMP '2021-01-07 09:00:00', TIMESTAMP '2021-01-07 11:30:00', '', '', 2, 1, 2),
(157, TIMESTAMP '2021-01-08 08:00:00', TIMESTAMP '2021-01-08 12:00:00', '', '', 3, 1, 0),
(158, TIMESTAMP '2021-01-08 12:00:00', TIMESTAMP '2021-01-08 18:00:00', '', '', NULL, 1, 0),
(159, TIMESTAMP '2021-01-09 08:00:00', TIMESTAMP '2021-01-09 12:00:00', '', '', 0, 2, 1),
(160, TIMESTAMP '2021-02-27 08:00:00', TIMESTAMP '2021-02-27 09:00:00', '', '', 2, 1, 0);       
CREATE CACHED TABLE "PUBLIC"."ROOM"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_8448C5C7_3CF4_441A_88AB_A10863F0D892" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_8448C5C7_3CF4_441A_88AB_A10863F0D892",
    "BUILDING" VARCHAR NOT NULL,
    "NUMBER" INT,
    "CAPACITY" INT NOT NULL,
    "LABEL" VARCHAR,
    "INFO" VARCHAR
);         
ALTER TABLE "PUBLIC"."ROOM" ADD CONSTRAINT "PUBLIC"."ROOM_PK" PRIMARY KEY("ID");               
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.ROOM;     
INSERT INTO "PUBLIC"."ROOM" VALUES
(1, 'U1', 110, 15, 'salle info', '15 pc'),
(2, 'U1', 111, 20, 'salle info', '20 pc'),
(3, 'AMPHI 400', 1, 150, 'amphi', NULL),
(4, 'T1', 310, 20, NULL, NULL),
(34, 'W1', 210, 60, 'Salle de classe', STRINGDECODE('R\u00e9troprojecteur'));
CREATE CACHED TABLE "PUBLIC"."LESSON_GROUPS"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_EA7BF31B_03F2_45B3_AF70_3271C3C12025" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_EA7BF31B_03F2_45B3_AF70_3271C3C12025",
    "LESSON" INT NOT NULL,
    "CLASS_GROUP" INT NOT NULL
);              
ALTER TABLE "PUBLIC"."LESSON_GROUPS" ADD CONSTRAINT "PUBLIC"."LESSON_GROUP_PK" PRIMARY KEY("ID");              
-- 22 +/- SELECT COUNT(*) FROM PUBLIC.LESSON_GROUPS;           
INSERT INTO "PUBLIC"."LESSON_GROUPS" VALUES
(71, 129, 2),
(72, 129, 8),
(77, 132, 2),
(78, 133, 8),
(79, 134, 2),
(80, 134, 8),
(81, 135, 2),
(82, 135, 8),
(83, 136, 2),
(84, 136, 8),
(85, 137, 8),
(86, 140, 2),
(87, 148, 8),
(88, 149, 8),
(89, 150, 8),
(90, 151, 2),
(91, 152, 2),
(92, 153, 8),
(93, 153, 2),
(94, 154, 2),
(95, 154, 8),
(96, 159, 2);
CREATE UNIQUE INDEX "PUBLIC"."LESSON_GROUP_ID_UINDEX" ON "PUBLIC"."LESSON_GROUPS"("ID");       
CREATE CACHED TABLE "PUBLIC"."STUDENT"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_93F20482_9AA4_475B_BDDE_10FBB3DFA34C" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_93F20482_9AA4_475B_BDDE_10FBB3DFA34C",
    "SURNAME" VARCHAR NOT NULL,
    "NAME" VARCHAR NOT NULL,
    "BIRTHDATE" DATE NOT NULL,
    "EMAIL" VARCHAR NOT NULL
);     
ALTER TABLE "PUBLIC"."STUDENT" ADD CONSTRAINT "PUBLIC"."STUDENT_PK" PRIMARY KEY("ID");         
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.STUDENT;  
INSERT INTO "PUBLIC"."STUDENT" VALUES
(1, 'REAUBOURG', 'Alexandre', DATE '1997-09-16', 'sasha@sasha.fr'),
(2, 'PALMA', STRINGDECODE('Fran\u00e7ois'), DATE '1899-06-15', 'fran@franfran.fr'),
(3, 'MARGUERIT', 'Nicolas', DATE '1998-02-13', 'nico@nico.fr'),
(7, 'BIANCHERRI', 'Charles', DATE '1999-03-06', 'student');      
CREATE UNIQUE INDEX "PUBLIC"."STUDENT_EMAIL_UINDEX" ON "PUBLIC"."STUDENT"("EMAIL");            
CREATE CACHED TABLE "PUBLIC"."LESSON"(
    "ID" INT NOT NULL,
    "TYPE" ENUM('TD', 'TP', 'CM', 'CC', 'CT') NOT NULL
);        
ALTER TABLE "PUBLIC"."LESSON" ADD CONSTRAINT "PUBLIC"."CLASSE_PK" PRIMARY KEY("ID");           
-- 16 +/- SELECT COUNT(*) FROM PUBLIC.LESSON;  
INSERT INTO "PUBLIC"."LESSON" VALUES
(129, 0),
(132, 0),
(133, 0),
(134, 0),
(135, 0),
(136, 1),
(137, 0),
(140, 0),
(148, 2),
(149, 2),
(150, 1),
(151, 0),
(152, 0),
(153, 3),
(154, 4),
(159, 2);           
CREATE CACHED TABLE "PUBLIC"."DEFENCE"(
    "ID" INT NOT NULL,
    "STUDENT" INT NOT NULL
);   
ALTER TABLE "PUBLIC"."DEFENCE" ADD CONSTRAINT "PUBLIC"."DEFENCE_PK" PRIMARY KEY("ID");         
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.DEFENCE;  
INSERT INTO "PUBLIC"."DEFENCE" VALUES
(57, 3),
(143, 1),
(146, 3),
(156, 7),
(160, 7);         
CREATE CACHED TABLE "PUBLIC"."GROUP_MEMBERS"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_B3B53E23_B60E_4C76_AC45_2B6E2116FE83" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_B3B53E23_B60E_4C76_AC45_2B6E2116FE83",
    "CLASS_GROUP" INT NOT NULL,
    "STUDENT" INT NOT NULL
);             
ALTER TABLE "PUBLIC"."GROUP_MEMBERS" ADD CONSTRAINT "PUBLIC"."GROUP_MEMBERS_PK" PRIMARY KEY("ID");             
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.GROUP_MEMBERS;            
INSERT INTO "PUBLIC"."GROUP_MEMBERS" VALUES
(36, 2, 1),
(37, 2, 2),
(70, 8, 3),
(71, 2, 7),
(72, 8, 7);        
CREATE UNIQUE INDEX "PUBLIC"."GROUP_MEMBERS_ID_UINDEX" ON "PUBLIC"."GROUP_MEMBERS"("ID");      
CREATE CACHED TABLE "PUBLIC"."CLASS_GROUP"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_E8968A03_5699_4B2E_9CD8_20C8408197B6" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_E8968A03_5699_4B2E_9CD8_20C8408197B6",
    "LABEL" VARCHAR NOT NULL
);             
ALTER TABLE "PUBLIC"."CLASS_GROUP" ADD CONSTRAINT "PUBLIC"."GROUP_PK" PRIMARY KEY("ID");       
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.CLASS_GROUP;              
INSERT INTO "PUBLIC"."CLASS_GROUP" VALUES
(2, 'M1 INFO 2'),
(8, 'M1 INFO 1'),
(35, 'M2 Info 1'),
(36, 'M2 Info 2');            
CREATE CACHED TABLE "PUBLIC"."LESSON_MODULES"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_36BFCB4A_BE66_4C9A_B056_0D019DC74E6D" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_36BFCB4A_BE66_4C9A_B056_0D019DC74E6D",
    "LESSON" INT NOT NULL,
    "MODULE" INT NOT NULL
);  
ALTER TABLE "PUBLIC"."LESSON_MODULES" ADD CONSTRAINT "PUBLIC"."LESSON_MODULES_PK" PRIMARY KEY("ID");           
-- 25 +/- SELECT COUNT(*) FROM PUBLIC.LESSON_MODULES;          
INSERT INTO "PUBLIC"."LESSON_MODULES" VALUES
(71, 129, 1),
(80, 132, 1),
(81, 133, 7),
(82, 134, 1),
(83, 134, 2),
(84, 134, 3),
(85, 135, 1),
(86, 135, 2),
(87, 135, 3),
(88, 135, 7),
(89, 136, 1),
(90, 136, 2),
(91, 136, 3),
(92, 136, 7),
(93, 137, 1),
(94, 140, 1),
(95, 148, 1),
(96, 149, 2),
(97, 150, 3),
(98, 151, 2),
(99, 152, 1),
(100, 153, 7),
(101, 154, 3),
(102, 154, 7),
(103, 159, 2); 
CREATE UNIQUE INDEX "PUBLIC"."LESSON_MODULES_ID_UINDEX" ON "PUBLIC"."LESSON_MODULES"("ID");    
CREATE CACHED TABLE "PUBLIC"."YEARGROUP"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_E2E2C85E_2907_408A_95E5_6F4DF11F475E" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_E2E2C85E_2907_408A_95E5_6F4DF11F475E",
    "LABEL" VARCHAR NOT NULL
);               
ALTER TABLE "PUBLIC"."YEARGROUP" ADD CONSTRAINT "PUBLIC"."YEARGROUPE_PK" PRIMARY KEY("ID");    
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.YEARGROUP;
INSERT INTO "PUBLIC"."YEARGROUP" VALUES
(33, 'Master Informatique'),
(65, 'Master 2 Informatique');            
CREATE UNIQUE INDEX "PUBLIC"."YEARGROUP_LABEL_UINDEX" ON "PUBLIC"."YEARGROUP"("LABEL");        
CREATE CACHED TABLE "PUBLIC"."SESSION"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_B83C002F_705B_4930_99C7_577292CD749B" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_B83C002F_705B_4930_99C7_577292CD749B",
    "LOGIN" VARCHAR NOT NULL,
    "PASSWORD" VARCHAR NOT NULL,
    "STATUS" ENUM('STUDENT', 'TEACHER', 'MANAGER', 'ADMIN') NOT NULL
);          
ALTER TABLE "PUBLIC"."SESSION" ADD CONSTRAINT "PUBLIC"."SESSION_PK" PRIMARY KEY("ID");         
-- 7 +/- SELECT COUNT(*) FROM PUBLIC.SESSION;  
INSERT INTO "PUBLIC"."SESSION" VALUES
(6, 'sasha@sasha.fr', '98f6bcd4621d373cade4e832627b4f6', 0),
(47, 'manager', '98f6bcd4621d373cade4e832627b4f6', 2),
(48, 'teacher', '98f6bcd4621d373cade4e832627b4f6', 1),
(49, 'admin', '21232f297a57a5a743894a0e4a801fc3', 3),
(62, 'test', '98f6bcd4621d373cade4e832627b4f6', 0),
(63, 'test2', '98f6bcd4621d373cade4e832627b4f6', 0),
(64, 'student', 'b89f7a5ff3e3a225d572dac38b2a67f7', 0);        
CREATE UNIQUE INDEX "PUBLIC"."SESSION_ID_UINDEX" ON "PUBLIC"."SESSION"("ID");  
CREATE UNIQUE INDEX "PUBLIC"."SESSION_LOGIN_UINDEX" ON "PUBLIC"."SESSION"("LOGIN");            
CREATE CACHED TABLE "PUBLIC"."CONSTRAINTS"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_D1C6344B_B3DF_43A9_953C_45112FD733B0" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_D1C6344B_B3DF_43A9_953C_45112FD733B0",
    "TEACHER" INT NOT NULL,
    "START" TIME NOT NULL,
    "END" TIME NOT NULL,
    "DAY" DATE NOT NULL
);  
ALTER TABLE "PUBLIC"."CONSTRAINTS" ADD CONSTRAINT "PUBLIC"."CONSTRAINTS_PK" PRIMARY KEY("ID"); 
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.CONSTRAINTS;              
INSERT INTO "PUBLIC"."CONSTRAINTS" VALUES
(37, 29, TIME '16:00:00', TIME '19:00:00', DATE '2018-01-06');       
CREATE UNIQUE INDEX "PUBLIC"."CONSTRAINTS_ID_UINDEX" ON "PUBLIC"."CONSTRAINTS"("ID");          
CREATE CACHED TABLE "PUBLIC"."MODULE"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_98A0FB15_5F2E_4500_A682_BE3D8F4580F5" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_98A0FB15_5F2E_4500_A682_BE3D8F4580F5",
    "LABEL" VARCHAR NOT NULL,
    "NB_HOURS" INT NOT NULL
);     
ALTER TABLE "PUBLIC"."MODULE" ADD CONSTRAINT "PUBLIC"."MODULE_PK" PRIMARY KEY("ID");           
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.MODULE;   
INSERT INTO "PUBLIC"."MODULE" VALUES
(1, 'I111', 30),
(2, 'I154', 23),
(3, 'I131', 100),
(7, 'I127', 0);       
CREATE UNIQUE INDEX "PUBLIC"."MODULE_LABEL_UINDEX" ON "PUBLIC"."MODULE"("LABEL");              
CREATE CACHED TABLE "PUBLIC"."ADMISSIONEXAM"(
    "ID" INT NOT NULL,
    "LABEL" INT NOT NULL
);               
ALTER TABLE "PUBLIC"."ADMISSIONEXAM" ADD CONSTRAINT "PUBLIC"."ADMISSIONEXAM_PK" PRIMARY KEY("ID");             
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.ADMISSIONEXAM;            
INSERT INTO "PUBLIC"."ADMISSIONEXAM" VALUES
(120, 1),
(141, 1),
(147, 1),
(155, 2);            
CREATE CACHED TABLE "PUBLIC"."MANAGERS"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_EFAC6FD4_A8EF_4215_B74B_548224E2676B" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_EFAC6FD4_A8EF_4215_B74B_548224E2676B",
    "RESERVATION" INT NOT NULL,
    "MANAGER" INT NOT NULL
);  
ALTER TABLE "PUBLIC"."MANAGERS" ADD CONSTRAINT "PUBLIC"."MANAGERS_PK" PRIMARY KEY("ID");       
-- 43 +/- SELECT COUNT(*) FROM PUBLIC.MANAGERS;
INSERT INTO "PUBLIC"."MANAGERS" VALUES
(36, 68, 28),
(79, 120, 28),
(82, 123, 28),
(97, 129, 28),
(98, 129, 29),
(103, 132, 28),
(104, 133, 29),
(105, 134, 28),
(106, 134, 29),
(107, 135, 28),
(108, 135, 29),
(141, 136, 28),
(142, 136, 29),
(143, 137, 29),
(144, 138, 29),
(145, 139, 29),
(146, 140, 28),
(148, 142, 28),
(149, 143, 28),
(150, 144, 28),
(151, 145, 28),
(152, 146, 28),
(153, 146, 29),
(154, 57, 28),
(155, 57, 29),
(156, 147, 28),
(157, 141, 28),
(158, 148, 28),
(159, 149, 29),
(160, 150, 28),
(161, 151, 29),
(162, 152, 28),
(163, 153, 28),
(164, 154, 29),
(165, 154, 28),
(166, 155, 28),
(169, 157, 28),
(170, 157, 29),
(172, 159, 29),
(173, 160, 29),
(174, 160, 28),
(175, 156, 29),
(176, 156, 28); 
CREATE UNIQUE INDEX "PUBLIC"."MANAGERS_ID_UINDEX" ON "PUBLIC"."MANAGERS"("ID");
CREATE CACHED TABLE "PUBLIC"."ADMISSIONEXAM_STUDENTS"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_88CC91E7_EC15_4EE6_AEF4_159A909FB255" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_88CC91E7_EC15_4EE6_AEF4_159A909FB255",
    "ADMISSIONEXAM" INT NOT NULL,
    "STUDENT" INT NOT NULL
);  
ALTER TABLE "PUBLIC"."ADMISSIONEXAM_STUDENTS" ADD CONSTRAINT "PUBLIC"."ADMISSIONEXAM_STUDENTS_PK" PRIMARY KEY("ID");           
-- 12 +/- SELECT COUNT(*) FROM PUBLIC.ADMISSIONEXAM_STUDENTS;  
INSERT INTO "PUBLIC"."ADMISSIONEXAM_STUDENTS" VALUES
(1, 120, 1),
(2, 120, 2),
(65, 141, 1),
(66, 141, 2),
(67, 141, 3),
(68, 147, 1),
(69, 147, 2),
(70, 147, 3),
(71, 155, 1),
(72, 155, 2),
(73, 155, 3),
(74, 155, 7);     
CREATE CACHED TABLE "PUBLIC"."YEARGROUP_GROUPS"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_9BA4D6A6_5543_417A_8EEA_09B3379E71DC" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_9BA4D6A6_5543_417A_8EEA_09B3379E71DC",
    "YEARGROUP" INT NOT NULL,
    "CLASS_GROUP" INT NOT NULL
);        
ALTER TABLE "PUBLIC"."YEARGROUP_GROUPS" ADD CONSTRAINT "PUBLIC"."YEARGROUP_GROUPS_PK" PRIMARY KEY("ID");       
-- 4 +/- SELECT COUNT(*) FROM PUBLIC.YEARGROUP_GROUPS;         
INSERT INTO "PUBLIC"."YEARGROUP_GROUPS" VALUES
(33, 33, 2),
(34, 33, 8),
(66, 65, 35),
(67, 65, 36);           
CREATE UNIQUE INDEX "PUBLIC"."YEARGROUP_GROUPS_ID_UINDEX" ON "PUBLIC"."YEARGROUP_GROUPS"("ID");
CREATE CACHED TABLE "PUBLIC"."TEACHER"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_3D6A7CE3_61F0_484D_A72E_8378F0CF9161" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_3D6A7CE3_61F0_484D_A72E_8378F0CF9161",
    "SURNAME" VARCHAR NOT NULL,
    "NAME" VARCHAR NOT NULL,
    "BIRTHDATE" DATE NOT NULL,
    "EMAIL" VARCHAR NOT NULL,
    "LABORATORY" VARCHAR NOT NULL,
    "STATUS" ENUM('LECTURER', 'PROFESSOR', 'ADJUNCT_PROF')
);      
ALTER TABLE "PUBLIC"."TEACHER" ADD CONSTRAINT "PUBLIC"."TEACHER_PK" PRIMARY KEY("ID");         
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.TEACHER;  
INSERT INTO "PUBLIC"."TEACHER" VALUES
(28, 'RAZIK', 'Joseph', DATE '2020-12-23', 'joseph.razik@univ-tln.fr', 'LIS', 0),
(29, 'NGUYEN', 'Christian', DATE '2021-01-11', 'teacher', 'IMATH', 0); 
CREATE UNIQUE INDEX "PUBLIC"."TEACHER_EMAIL_UINDEX" ON "PUBLIC"."TEACHER"("EMAIL");            
CREATE CACHED TABLE "PUBLIC"."EXAMBOARD"(
    "ID" INT NOT NULL,
    "YEARGROUP" INT NOT NULL
);               
ALTER TABLE "PUBLIC"."EXAMBOARD" ADD CONSTRAINT "PUBLIC"."EXAMBOARD_PK" PRIMARY KEY("ID");     
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.EXAMBOARD;
INSERT INTO "PUBLIC"."EXAMBOARD" VALUES
(123, 33),
(142, 33),
(157, 33);       
CREATE UNIQUE INDEX "PUBLIC"."EXAMBOARD_ID_UINDEX" ON "PUBLIC"."EXAMBOARD"("ID");              
CREATE CACHED TABLE "PUBLIC"."ADMISSIONEXAM_LABEL"(
    "ID" INT DEFAULT NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_89F06185_18D9_4279_B98A_8EA58C343CA7" NOT NULL NULL_TO_DEFAULT SEQUENCE "PUBLIC"."SYSTEM_SEQUENCE_89F06185_18D9_4279_B98A_8EA58C343CA7",
    "LABEL" VARCHAR NOT NULL
);     
ALTER TABLE "PUBLIC"."ADMISSIONEXAM_LABEL" ADD CONSTRAINT "PUBLIC"."ADMISSIONEXAM_LABEL_PK" PRIMARY KEY("ID"); 
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.ADMISSIONEXAM_LABEL;      
INSERT INTO "PUBLIC"."ADMISSIONEXAM_LABEL" VALUES
(1, 'TOEIC'),
(2, 'PIX'),
(33, 'IELTS');     
CREATE UNIQUE INDEX "PUBLIC"."ADMISSIONEXAM_LABEL_LABEL_UINDEX" ON "PUBLIC"."ADMISSIONEXAM_LABEL"("LABEL");    
CREATE UNIQUE INDEX "PUBLIC"."ADMISSIONEXAM_LABEL_ID_UINDEX" ON "PUBLIC"."ADMISSIONEXAM_LABEL"("ID");          
ALTER TABLE "PUBLIC"."LESSON_GROUPS" ADD CONSTRAINT "PUBLIC"."LESSON_GROUP_LESSON_ID_FK" FOREIGN KEY("LESSON") REFERENCES "PUBLIC"."LESSON"("ID") ON DELETE CASCADE NOCHECK;   
ALTER TABLE "PUBLIC"."YEARGROUP_GROUPS" ADD CONSTRAINT "PUBLIC"."YEARGROUP_GROUPS_YEARGROUP_ID_FK" FOREIGN KEY("YEARGROUP") REFERENCES "PUBLIC"."YEARGROUP"("ID") ON DELETE CASCADE NOCHECK;   
ALTER TABLE "PUBLIC"."EXAMBOARD" ADD CONSTRAINT "PUBLIC"."EXAMBOARD_RESERVATION_ID_FK" FOREIGN KEY("ID") REFERENCES "PUBLIC"."RESERVATION"("ID") ON DELETE CASCADE NOCHECK;    
ALTER TABLE "PUBLIC"."ADMISSIONEXAM_STUDENTS" ADD CONSTRAINT "PUBLIC"."ADMISSIONEXAM_STUDENTS_ADMISSIONEXAM_ID_FK" FOREIGN KEY("ADMISSIONEXAM") REFERENCES "PUBLIC"."ADMISSIONEXAM"("ID") ON DELETE CASCADE ON UPDATE CASCADE NOCHECK;         
ALTER TABLE "PUBLIC"."LESSON_MODULES" ADD CONSTRAINT "PUBLIC"."LESSON_MODULES_LESSON_ID_FK" FOREIGN KEY("LESSON") REFERENCES "PUBLIC"."LESSON"("ID") ON DELETE CASCADE NOCHECK;
ALTER TABLE "PUBLIC"."MANAGERS" ADD CONSTRAINT "PUBLIC"."MANAGERS_RESERVATION_ID_FK" FOREIGN KEY("RESERVATION") REFERENCES "PUBLIC"."RESERVATION"("ID") ON DELETE CASCADE NOCHECK;             
ALTER TABLE "PUBLIC"."ADMISSIONEXAM_STUDENTS" ADD CONSTRAINT "PUBLIC"."ADMISSIONEXAM_STUDENTS_STUDENT_ID_FK" FOREIGN KEY("STUDENT") REFERENCES "PUBLIC"."STUDENT"("ID") ON DELETE CASCADE ON UPDATE CASCADE NOCHECK;           
ALTER TABLE "PUBLIC"."CONSTRAINTS" ADD CONSTRAINT "PUBLIC"."CONSTRAINTS_TEACHER_ID_FK" FOREIGN KEY("TEACHER") REFERENCES "PUBLIC"."TEACHER"("ID") ON DELETE CASCADE NOCHECK;   
ALTER TABLE "PUBLIC"."ADMISSIONEXAM" ADD CONSTRAINT "PUBLIC"."ADMISSIONEXAM_ADMISSIONEXAM_LABEL_ID_FK" FOREIGN KEY("LABEL") REFERENCES "PUBLIC"."ADMISSIONEXAM_LABEL"("ID") ON DELETE CASCADE ON UPDATE CASCADE NOCHECK;       
ALTER TABLE "PUBLIC"."DEFENCE" ADD CONSTRAINT "PUBLIC"."DEFENCE_STUDENT_ID_FK" FOREIGN KEY("STUDENT") REFERENCES "PUBLIC"."STUDENT"("ID") ON DELETE CASCADE NOCHECK;           
ALTER TABLE "PUBLIC"."LESSON" ADD CONSTRAINT "PUBLIC"."LESSON_RESERVATION_ID_FK" FOREIGN KEY("ID") REFERENCES "PUBLIC"."RESERVATION"("ID") ON DELETE CASCADE NOCHECK;          
ALTER TABLE "PUBLIC"."RESERVATION" ADD CONSTRAINT "PUBLIC"."RESERVATION_ROOM_ID_FK" FOREIGN KEY("ROOM") REFERENCES "PUBLIC"."ROOM"("ID") NOCHECK;              
ALTER TABLE "PUBLIC"."YEARGROUP_GROUPS" ADD CONSTRAINT "PUBLIC"."YEARGROUP_GROUPS_GROUP_ID_FK" FOREIGN KEY("CLASS_GROUP") REFERENCES "PUBLIC"."CLASS_GROUP"("ID") ON DELETE CASCADE NOCHECK;   
ALTER TABLE "PUBLIC"."ADMISSIONEXAM" ADD CONSTRAINT "PUBLIC"."ADMISSIONEXAM_RESERVATION_ID_FK" FOREIGN KEY("ID") REFERENCES "PUBLIC"."RESERVATION"("ID") ON DELETE CASCADE NOCHECK;            
ALTER TABLE "PUBLIC"."LESSON_MODULES" ADD CONSTRAINT "PUBLIC"."LESSON_MODULES_MODULE_ID_FK" FOREIGN KEY("MODULE") REFERENCES "PUBLIC"."MODULE"("ID") ON DELETE CASCADE NOCHECK;
ALTER TABLE "PUBLIC"."EXAMBOARD" ADD CONSTRAINT "PUBLIC"."EXAMBOARD_YEARGROUP_ID_FK" FOREIGN KEY("YEARGROUP") REFERENCES "PUBLIC"."YEARGROUP"("ID") ON DELETE CASCADE NOCHECK; 
ALTER TABLE "PUBLIC"."MANAGERS" ADD CONSTRAINT "PUBLIC"."MANAGERS_TEACHER_ID_FK" FOREIGN KEY("MANAGER") REFERENCES "PUBLIC"."TEACHER"("ID") ON DELETE CASCADE NOCHECK;         
ALTER TABLE "PUBLIC"."GROUP_MEMBERS" ADD CONSTRAINT "PUBLIC"."GROUP_MEMBERS_GROUP_ID_FK" FOREIGN KEY("CLASS_GROUP") REFERENCES "PUBLIC"."CLASS_GROUP"("ID") ON DELETE CASCADE NOCHECK;         
ALTER TABLE "PUBLIC"."GROUP_MEMBERS" ADD CONSTRAINT "PUBLIC"."GROUP_MEMBERS_STUDENT_ID_FK" FOREIGN KEY("STUDENT") REFERENCES "PUBLIC"."STUDENT"("ID") ON DELETE CASCADE NOCHECK;               
ALTER TABLE "PUBLIC"."DEFENCE" ADD CONSTRAINT "PUBLIC"."DEFENCE_RESERVATION_ID_FK" FOREIGN KEY("ID") REFERENCES "PUBLIC"."RESERVATION"("ID") ON DELETE CASCADE NOCHECK;        
ALTER TABLE "PUBLIC"."LESSON_GROUPS" ADD CONSTRAINT "PUBLIC"."LESSON_GROUP_GROUP_ID_FK" FOREIGN KEY("CLASS_GROUP") REFERENCES "PUBLIC"."CLASS_GROUP"("ID") ON DELETE CASCADE NOCHECK;          
