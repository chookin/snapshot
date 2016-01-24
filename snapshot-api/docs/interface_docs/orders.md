# 订单接口
## 获取指定用户的所有订单
method: GET

path：/orders/userOrders/get

request paras:

* uid, long, 用户ID

response data:

* orders, json array

## 获取指定用户的订单总数
method: GET

path：/orders/userOrdersCount/get

request paras:

* uid, long, 用户ID

response data:

* count, int, 订单总数

## 获取指定摄影师的所有订单
method: GET

path：/orders/grapherOrders/get

request paras:

* uid, long, 用户ID

response data:

* orders, json array

## 获取指定摄影师的订单总数
method: GET

path：/orders/grapherOrdersCount/get

request paras:

* uid, long, 用户ID

response data:

* count, int, 订单总数