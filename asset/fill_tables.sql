DROP PROCEDURE  IF EXISTS add_animal;
DELETE FROM animals;
DELETE FROM cmd_list;
DELETE FROM typ_list;
DELETE FROM sex_list;
DELETE FROM commands;
DELETE FROM types;
DELETE FROM sex;

/* fill tables */
INSERT INTO commands (denotation) VALUES
('Sit'),('Stay'),('Fetch'),('Pounce'),('Roll'),('Hide'),('Paw'),('Bark'),('Scratch'),('Spin'),
('Meow'),('Jump'),('Trot'),('Canter'),('Gallop'),('Walk'),('Carry Load'),('Bray'),('Kick'),('Run');

INSERT INTO types (denotation) VALUES
('Cat'), ('Dog'), ('Hamster'), ('Horse'), ('Camel'), ('Donkey');

INSERT INTO sex (denotation) VALUES
('Male'), ('Female'), ('Unknown');


DELIMITER //
CREATE PROCEDURE  add_animal (nick TEXT, bd DATE, comments TEXT, typ TEXT, sex_anim TEXT, n1 TEXT, n2 TEXT, n3 TEXT, n4 TEXT)
BEGIN
	DECLARE typ_id INT;
    DECLARE sex_id INT;
    DECLARE anm_id INT;		-- id добавленного в animals животного

    DECLARE is_error BOOLEAN DEFAULT false;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    BEGIN
		SET is_error = true;
	END;

	START TRANSACTION;
    
    INSERT INTO animals (nick, birth_day, comments) VALUES (nick, bd, comments);
    
    SET anm_id = LAST_INSERT_ID();
	
	INSERT INTO typ_list (anm_id, typ_id) VALUES (anm_id, (SELECT id FROM types WHERE denotation = typ));
	INSERT INTO sex_list (anm_id, sex_id) VALUES (anm_id, (SELECT id FROM sex WHERE denotation = sex_anim));

	IF (n1 IS NOT NULL) THEN 
		INSERT INTO cmd_list (anm_id, cmd_id) VALUES (anm_id, (SELECT id FROM commands WHERE denotation = n1));
	END IF;
	IF (n2 IS NOT NULL) THEN
		INSERT INTO cmd_list (anm_id, cmd_id) VALUES (anm_id, (SELECT id FROM commands WHERE denotation = n2));
    END IF;
	IF (n3 IS NOT NULL) THEN
		INSERT INTO cmd_list (anm_id, cmd_id) VALUES (anm_id, (SELECT id FROM commands WHERE denotation = n3));
	END IF;
	IF (n4 IS NOT NULL) THEN
		INSERT INTO cmd_list (anm_id, cmd_id) VALUES (anm_id, (SELECT id FROM commands WHERE denotation = n4));
	END IF;
	
    IF is_error THEN
		SELECT "Error: uncorrect data!";
		ROLLBACK;
	ELSE
		COMMIT;
	END IF;
END//

DELIMITER ;

call add_animal('Fido', '2022-01-01', '', 'Dog', 'male', 'Sit', 'Stay', 'Fetch', NULL);
call add_animal('Whiskers', '2021-05-15', '', 'Cat', 'female', 'Sit', 'Pounce', NULL, NULL);
call add_animal('Hammy', '2023-03-10', '', 'Hamster', 'male', 'Roll', 'Hide', NULL, NULL);
call add_animal('Buddy', '2020-12-10', '', 'Dog', 'female', 'Sit', 'Paw', 'Bark', NULL);
call add_animal('Smudge', '2022-02-02', '', 'Cat', 'female', 'Sit', 'Pounce', 'Scratch', NULL); 
call add_animal('Peanut', '2023-08-01', '', 'Hamster', 'male', 'Roll', 'Spin', NULL, NULL);
call add_animal('Bella', '2021-11-11', '', 'Dog', 'male', 'Sit', 'Stay', 'Roll', NULL);
call add_animal('Oliver', '2022-06-30', '', 'Cat', 'female', 'Meow', 'Scratch', 'Jump', NULL);
call add_animal('Thunder', '2017-07-21', '', 'Horse', 'male', 'Trot', 'Canter', 'Gallop', NULL);
call add_animal('Sandy', '2018-11-03', '', 'Camel', 'female', 'Walk', 'Carry Load', NULL, NULL);
call add_animal('Eeyore', '2019-09-18', '', 'Donkey', 'male', 'Walk', 'Carry Load', 'Bray', NULL);
call add_animal('Storm', '2016-05-05', '', 'Horse', 'male', 'Trot', 'Canter', NULL, NULL);
call add_animal('Dune', '2020-12-12', '', 'Camel', 'female', 'Walk', 'Sit', NULL, NULL);
call add_animal('Burro', '2023-01-23', '', 'Donkey', 'female', 'Walk', 'Bray', 'Kick', NULL);
call add_animal('Blaze', '2022-02-28', '', 'Horse', 'male', 'Trot', 'Jump', 'Gallop', NULL);
call add_animal('Sahara', '2017-08-14', '', 'Camel', 'female', 'Walk', 'Run', NULL, NULL);

