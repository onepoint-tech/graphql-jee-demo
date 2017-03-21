-- PROJECT
INSERT INTO project(id, code, label) VALUES ('5cd37f0c-4d38-4b71-a6a1-842d3d115be6', 'QUHE', 'Quality Heart');
INSERT INTO project(id, code, label) VALUES ('9e940608-abb2-487b-9985-adef50a44c13', 'GLLA', 'Gloomy Lama');
INSERT INTO project(id, code, label) VALUES ('22f235be-78c0-4da4-b341-d0abf582f1e8', 'NETE', 'Nervous Tea');
INSERT INTO project(id, code, label) VALUES ('c351af08-fa10-43ce-b3be-49c0b2407173', 'LONI', 'Lost Nitrogen');
INSERT INTO project(id, code, label) VALUES ('a8c5caf4-a00d-4630-88ad-a009d5ec0e30', 'MAZE', 'Magenta Zeus');
INSERT INTO project(id, code, label) VALUES ('c655af2d-9cc1-43c5-8758-ef5842c6b0c4', 'TIAR', 'Timely Artificial');

-- USER
INSERT INTO "user"(id, firstname, lastname) VALUES ('b0964eeb-fe4e-44e2-af86-ff7e29636bbe', 'John', 'Doe');
INSERT INTO "user"(id, firstname, lastname) VALUES ('a4bc977c-ec69-47c3-8558-4e5e2361aee2', 'Zaynab', 'Gregorius');
INSERT INTO "user"(id, firstname, lastname) VALUES ('90adccc9-5ca5-4334-93bb-e79f43a8dbc5', 'Bulat', 'Ulloriaq');
INSERT INTO "user"(id, firstname, lastname) VALUES ('7205f215-bb1b-4f69-a500-dfc0d9ada787', 'Jozef', 'Hulda');
INSERT INTO "user"(id, firstname, lastname) VALUES ('918c912a-70dc-4fa9-a8ed-cff7d81959a0', 'Aliyah', 'Bihotz');
INSERT INTO "user"(id, firstname, lastname) VALUES ('b6104f9f-0d53-4fed-a5df-5c816bf3f95c', 'Emiliya', 'Ingolf');

-- ASSIGNMENT
INSERT INTO assignment(user_id, project_id) VALUES ('b0964eeb-fe4e-44e2-af86-ff7e29636bbe', '5cd37f0c-4d38-4b71-a6a1-842d3d115be6');
INSERT INTO assignment(user_id, project_id) VALUES ('b0964eeb-fe4e-44e2-af86-ff7e29636bbe', 'a8c5caf4-a00d-4630-88ad-a009d5ec0e30');
INSERT INTO assignment(user_id, project_id) VALUES ('918c912a-70dc-4fa9-a8ed-cff7d81959a0', 'c655af2d-9cc1-43c5-8758-ef5842c6b0c4');
INSERT INTO assignment(user_id, project_id) VALUES ('b6104f9f-0d53-4fed-a5df-5c816bf3f95c', '5cd37f0c-4d38-4b71-a6a1-842d3d115be6');
