This is a readme!

CRUD


User
- id
- name
- age
- heightInCm

POST /users\
Body:{name:"newUser",age:25,heightInCm:180}

GET /users/{user-id}

GET /users/

PUT /users/{user-id}\
Body:
```
{
name:       [stringUpdatedName],
age:        [intUpdatedAge],
heightInCm: [intUpdatedAge]
}
```

DELETE /users/{user-id}