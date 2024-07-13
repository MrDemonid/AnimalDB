SELECT c.denotation AS 'Commands'
FROM commands c
JOIN cmd_list cl ON cl.cmd_id = c.id
WHERE cl.anm_id = 1;
