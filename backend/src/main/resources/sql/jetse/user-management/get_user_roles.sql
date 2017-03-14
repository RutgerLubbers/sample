SELECT
  user_id
, role
FROM user_roles
WHERE user_id = :id
