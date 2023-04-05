# java-explore-with-me
Template repository for ExploreWithMe project.

# Ссылка на PR feature_comments
https://github.com/KukushkinIgorA/java-explore-with-me/pull/6

# Описание сторей для фичи комментарии:

## Private
- Я как пользователь хочу оставить комментарий к событию.
Я могу оставлять комментарии только на события, на которые подтверждена моя заявка.

[POST] /users/{userId}/events/{eventId}/comments

RequestBody:
NewCommentDto

ResponseBody:
CommentDto

- Я как пользователь хочу получить все мои комментарии.

[GET] /users/{userId}/comments

RequestParam:
int from,
int size

ResponseBody:
List<CommentDto>

- Я как пользователь хочу отредактировать комментарий по id.
Я могу редактировать только свой комментарий.
Я могу отредактировать только комментарий в статусе PENDING.

[PATCH] /users/{userId}/comments/{commentId}

RequestBody:
NewCommentDto

ResponseBody:
CommentDto

- Я как пользователь хочу удалить свой комментарий.
Я могу удалить только свой комментарий.

[DELETE] 	/users/{userId}/comments/{commentId}


## Admin
- Я как админ хочу видеть все комментарии к определенному событию с возможностью фильтрации по статусам.

[GET] /admin/events/{eventId}/comments

RequestParam:
List<String> states,
int from,
int size,

ResponseBody:
List<CommentDto>

- Я как админ хочу массово менять статус событиям.

[PATCH] /admin/comments

RequestParam:
String state

RequestBody:
AdminUpdateCommentsDto

ResponseBody:
List<CommentDto>

## Public
- Я как человек хочу видеть комментарии к событию при получении подробной информации о событии по его идентификатору.
Я хочу видеть только подтвержденные комментарии.

Доработка [GET] /events/{id}

Добавим List<CommentDto> в EventFullDto