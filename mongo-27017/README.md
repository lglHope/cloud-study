# 连接mongo
mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
# 显示已存在的数据库
show dbs
# 使用数据库
use DATASOURCE_NAME (example: use admin)
# 创建数据库（如果存在就使用，否则新建，新建成功需插入一条数据才能看到）
use DATASOURCE_NAME (example: use mytest)
# 删除数据库
db.dropDatabase(DATASOURCE_NAME) (example: db.dropDatabase(mytest))
# 创建集合（表）
db.createCollection(COLLECTION_NAME) (example: db.createCollection("user"))
# 删除集合（表）
db.COLLECTION_NAME.drop() (example: db.user.drop())
# 新增文档（行）
db.COLLECTION_NAME.insert(document) (example: db.user.insert(name:"xiaoming"))
# 更新文档（行）
db.COLLECTION_NAME.update(
   <query>,
   <update>,
   {
     upsert: <boolean>,
     multi: <boolean>,
     writeConcern: <document>
   }
)
query : update的查询条件，类似sql update查询内where后面的。
update : update的对象和一些更新的操作符（如$,$inc...）等，也可以理解为sql update查询内set后面的
upsert : 可选，这个参数的意思是，如果不存在update的记录，是否插入objNew,true为插入，默认是false，不插入。
multi : 可选，mongodb 默认是false,只更新找到的第一条记录，如果这个参数为true,就把按条件查出来多条记录全部更新。
writeConcern :可选，抛出异常的级别。

example: db.user.update({name:"xiaoming"},{$set:{name:"xiaohong"}})
# 删除文档（行）
db.COLLECTION_NAME.remove(
   <query>,
   {
     justOne: <boolean>,
     writeConcern: <document>
   }
)
query :（可选）删除的文档的条件。
justOne : （可选）如果设为 true 或 1，则只删除一个文档，如果不设置该参数，或使用默认值 false，则删除所有匹配条件的文档。
writeConcern :（可选）抛出异常的级别。

example: db.user.remove({name:"xiaohong"})
# 查询文档（行）
db.COLLECTION_NAME.find(query, projection)
query ：可选，使用查询操作符指定查询条件
projection ：可选，使用投影操作符指定返回的键。查询时返回文档中所有键值， 只需省略该参数即可（默认省略）。
example: db.user.find({name:"xiaohong",...}).pretty()
# 条件操作符
$lt  小于
$lte 小于等于
$gt  大于
$gte 大于等于
$ne  不等于
db.col.find({likes : {$gt : 100}}) 类似于  Select * from col where likes > 100;