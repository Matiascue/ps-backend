INSERT INTO room (id, user1Id, user2Id)
VALUES ('1_2', 1, 2);

INSERT INTO chat(room_id, sender_id, content, time_stamp)
VALUES ('1_2', 1, 'Hola desde H2!', '2023-01-01 12:00:00');