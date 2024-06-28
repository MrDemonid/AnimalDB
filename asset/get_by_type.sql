SELECT animals.id, anm_data.nick, anm_data.birth_day, anm_type.denotation FROM anm_data
JOIN animals ON animals.data_id = anm_data.id
JOIN anm_type ON animals.type_id = anm_type.id
WHERE animals.type_id = 1;
;
