DROP PROCEDURE  addrec;

DELIMITER //
CREATE PROCEDURE  addrec (nick TEXT, bd DATE, typ TEXT, n1 TEXT, n2 TEXT, n3 TEXT, n4 TEXT)
BEGIN
	DECLARE ndata_id INT;
	DECLARE nanimal_id INT;

	START TRANSACTION;
    
	INSERT INTO anm_data (nick, birth_day, comments) VALUES (nick, bd, '');
	SET ndata_id = LAST_INSERT_ID();

	INSERT INTO animals (type_id, data_id) VALUES ((SELECT id FROM anm_type WHERE denotation = typ), ndata_id);
	SET nanimal_id = LAST_INSERT_ID();

	INSERT INTO cmd_list (anm_id, cmd_id) SELECT nanimal_id, id FROM cmd_info WHERE denotation = n1;
	INSERT INTO cmd_list (anm_id, cmd_id) SELECT nanimal_id, id FROM cmd_info WHERE denotation = n2;
	INSERT INTO cmd_list (anm_id, cmd_id) SELECT nanimal_id, id FROM cmd_info WHERE denotation = n3;
	IF n4 != NULL THEN
		INSERT INTO cmd_list (anm_id, cmd_id) SELECT nanimal_id, id FROM cmd_info WHERE denotation = n4;
	END IF;
	
    COMMIT;
	-- ROLLBACK;
END; //
DELIMITER ;

call addrec('Fido', '2022-01-01', 'Dog', 'Sit', 'Stay', 'Fetch', NULL);
call addrec('Whiskers', '2021-05-15', 'Cat', 'Sit', 'Pounce', NULL, NULL);
call addrec('Hammy', '2023-03-10', 'Hamster', 'Roll', 'Hide', NULL, NULL);
call addrec('Buddy', '2020-12-10', 'Dog', 'Sit', 'Paw', 'Bark', NULL);
call addrec('Smudge', '2022-02-02', 'Cat', 'Sit', 'Pounce', 'Scratch', NULL); 
call addrec('Peanut', '2023-08-01', 'Hamster', 'Roll', 'Spin', NULL, NULL);
call addrec('Bella', '2021-11-11', 'Dog', 'Sit', 'Stay', 'Roll', NULL);
call addrec('Oliver', '2022-06-30', 'Cat', 'Meow', 'Scratch', 'Jump', NULL);
call addrec('Thunder', '2017-07-21', 'Horse', 'Trot', 'Canter', 'Gallop', NULL);
call addrec('Sandy', '2018-11-03', 'Camel', 'Walk', 'Carry Load', NULL, NULL);
call addrec('Eeyore', '2019-09-18', 'Donkey', 'Walk', 'Carry Load', 'Bray', NULL);
call addrec('Storm', '2016-05-05', 'Horse', 'Trot', 'Canter', NULL, NULL);
call addrec('Dune', '2020-12-12', 'Camel', 'Walk', 'Sit', NULL, NULL);
call addrec('Burro', '2023-01-23', 'Donkey', 'Walk', 'Bray', 'Kick', NULL);
call addrec('Blaze', '2022-02-28', 'Horse', 'Trot', 'Jump', 'Gallop', NULL);
call addrec('Sahara', '2017-08-14', 'Camel', 'Walk', 'Run', NULL, NULL);

