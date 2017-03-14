SELECT id
, first_name
, last_name
, email_confirmed
, email_address
, subject_id
FROM "user"
WHERE subject_id = :subject_id
