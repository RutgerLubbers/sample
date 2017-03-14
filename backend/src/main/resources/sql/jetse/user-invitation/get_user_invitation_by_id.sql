SELECT
  id
, user_id
, created
, expires
FROM user_invitation
WHERE id = :id
