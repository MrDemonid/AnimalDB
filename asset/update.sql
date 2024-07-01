DROP PROCEDURE IF EXISTS updrec;

DELIMITER //
CREATE PROCEDURE  updrec (obj_id INT, new_nick TEXT, bd DATE, comm TEXT, n1 TEXT, n2 TEXT, n3 TEXT, n4 TEXT)
BEGIN
	START TRANSACTION;
    
	UPDATE anm_data SET nick=new_nick, birth_day=bd, comments=comm
	WHERE id=(SELECT data_id FROM animals WHERE id=obj_id);

	DELETE FROM cmd_list WHERE anm_id=obj_id;
   
	INSERT INTO cmd_list (anm_id, cmd_id) SELECT obj_id, id FROM cmd_info WHERE denotation = n1;
	INSERT INTO cmd_list (anm_id, cmd_id) SELECT obj_id, id FROM cmd_info WHERE denotation = n2;
	INSERT INTO cmd_list (anm_id, cmd_id) SELECT obj_id, id FROM cmd_info WHERE denotation = n3;
	
    COMMIT;
	-- ROLLBACK;
END; //
DELIMITER ;

call updrec(8, 'Olwer', '2017-09-09', "", 'Sit', 'Scratch', 'Run', '');
