UPDATE "user" SET
  first_name = :first_name
, last_name = :last_name
, email_address = :email_address
, email_confirmed = :email_confirmed
, subject_id = :subject_id
WHERE id = :id
