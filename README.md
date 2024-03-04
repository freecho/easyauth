## API

符合RESTFUL规范，使用HTTP请求方法来实现对资源的操作。
不使用路径参数，改为使用查询参数来实现对资源的操作。
便于基于URL的权限认证

## 动态权限

### 实现思路

使用RBAC模型来实现权限控制，RBAC模型是基于角色的权限控制模型，通过对角色的控制来实现对用户的权限控制。

基于数据库来实现动态权限的功能， 通过对数据库的操作来实现对用户的权限控制，可以动态的对用户的权限进行增删改查操作。

由于权限的变化并不频繁，所以可以将权限信息缓存到redis中，这样可以减少对数据库的访问，提高系统的性能。

### 相关Mysql表

- 用户表 user
- 员工表 employee
- 角色表 role
- 用户角色关联表 user_role
- 员工角色关联表 employee_role
- 资源表 resource
- 角色权限关联表 role_permission

### 模型图
![](./docs/img/RBAC_model.png)

### 逻辑步骤

1. 通过spring security的自定义jwt过滤器来实现对用户的认证，将解析token后的信息存入SecurityContext
2. 将用户的授权交给自定义的AuthorizationManager（根据当前请求的URL和http_method从redis中查询所需要的权限，再根据SecurityContext和所需要的权限从redis查询是否匹配）

### JWT

员工的`identity`为`employee`,用户的`identity`为`user`

```json
{
  "identity": "user",
  "id": "1"
}
```

### redis相关实现

1. 用户权限信息(UserDetail)

说明：jwt解析后存出SecurityContext，根据SecurityContext向redis查询

(identity:id)`key`: `user:125`

`value`: `序列化后的JSON字符串`

JSON:

```json
{
  "id": 1,
  "username": "admin",
  "email": "123456@qq.com",
  "rolesId": [
    1,
    2
  ]
}
```

2. 资源权限信息(资源id)

(http_method:URI)
`key`: `GET:/admin/user`

(resource_id)
`value`: `8`

3. 权限匹配验证(1->有权限, 0->无权限)

(roleId:resource_id)`key`: `8:1`

(true `or` false)`value`: `true`
