SELECT denotation FROM cmd_info
JOIN cmd_list ON cmd_list.cmd_id = cmd_info.id
JOIN animals ON animals.id = cmd_list.anm_id
WHERE animals.id = 1
;