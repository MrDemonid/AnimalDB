SELECT a.id, a.nick AS 'Nick', a.birth_day AS 'Birth Day', t.denotation AS 'Type', s.denotation AS 'Sex',
(SELECT GROUP_CONCAT(c.denotation) FROM commands c JOIN cmd_list cl ON cl.cmd_id = c.id WHERE cl.anm_id = a.id GROUP BY cl.anm_id) AS 'Commands'
FROM animals a
JOIN typ_list tl ON tl.anm_id = a.id
JOIN sex_list sl ON sl.anm_id = a.id
JOIN types t ON tl.typ_id = t.id
JOIN sex s ON sl.sex_id = s.id
ORDER BY a.id;
